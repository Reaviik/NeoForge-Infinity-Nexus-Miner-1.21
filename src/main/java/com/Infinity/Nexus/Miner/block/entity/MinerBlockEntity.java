package com.Infinity.Nexus.Miner.block.entity;

import com.Infinity.Nexus.Core.block.entity.WrappedHandler;
import com.Infinity.Nexus.Core.block.entity.common.SetMachineLevel;
import com.Infinity.Nexus.Core.block.entity.common.SetUpgradeLevel;
import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModEnergyStorage;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Miner.block.custom.Miner;
import com.Infinity.Nexus.Miner.block.entity.wrappedHandlerMap.MinerHandler;
import com.Infinity.Nexus.Miner.config.Config;
import com.Infinity.Nexus.Miner.config.ConfigUtils;
import com.Infinity.Nexus.Miner.recipes.MinerRecipe;
import com.Infinity.Nexus.Miner.recipes.MinerRecipeInput;
import com.Infinity.Nexus.Miner.recipes.ModRecipes;
import com.Infinity.Nexus.Miner.screen.miner.MinerMenu;
import com.Infinity.Nexus.Miner.utils.MinerTierStructure;
import com.Infinity.Nexus.Miner.utils.ModUtilsMiner;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MinerBlockEntity extends BlockEntity implements MenuProvider {
    float rotation = 0;
    private final ItemStackHandler itemHandler = new ItemStackHandler(19) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            //TODO Mudança
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0, 1, 2, 3, 4, 5, 6, 7, 8 -> !ModUtils.isComponent(stack) || !ModUtils.isUpgrade(stack);
                case 9, 10, 11, 12 -> ModUtils.isUpgrade(stack);
                case 13 -> ModUtils.isComponent(stack);
                //TODO
                case 14 -> stack.getItem() == Items.ENCHANTED_BOOK || stack.isEnchanted();
                case 15 -> stack.is(ModItems.LINKING_TOOL.get().asItem());
                case 16 -> stack.getBurnTime(null) > 0;
                case 17 -> ConfigUtils.isStructure(stack.getItem());
                case 18 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            switch (slot) {
                case 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 -> {
                    return ItemStack.EMPTY;
                }
            }
            return super.extractItem(slot, amount, simulate);
        }
    };
    private static final int[] OUTPUT_SLOT = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] UPGRADE_SLOTS = {9, 10, 11, 12};
    private static final int COMPONENT_SLOT = 13;
    private static final int LINK_SLOT = 15;
    private static final int FORTUNE_SLOT = 14;
    private static final int FUEL_SLOT = 16;
    private static final int STRUCTURE_SLOT = 17;
    private static final int RECIPE_SLOT = 18;
    private static final int ENERGY_CAPACITY = Config.miner_energy_capacity;
    private static final int ENERGY_TRANSFER = Config.miner_energy_transfer;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(ENERGY_CAPACITY, ENERGY_TRANSFER) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 4);
            }
        };
    }

    private Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);
    private Lazy<IEnergyStorage> lazyEnergyStorage = Lazy.of(() -> ENERGY_STORAGE);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 5;
    private int verify = 0;
    private int maxVerify = 15;
    private int structure = 0;

    private int hasRedstoneSignal = 0;
    private int stillCrafting = 0;
    private int hasSlotFree = 0;
    private int hasComponent = 0;
    private int hasEnoughEnergy = 0;
    private int hasRecipe = 0;

    private int linkx = 0;
    private int linky = 0;
    private int linkz = 0;
    private int linkFace = 0;


    public MinerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MINER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MinerBlockEntity.this.progress;
                    case 1 -> MinerBlockEntity.this.maxProgress;
                    case 2 -> MinerBlockEntity.this.verify;
                    case 3 -> MinerBlockEntity.this.maxVerify;
                    case 4 -> MinerBlockEntity.this.structure;

                    case 5 -> MinerBlockEntity.this.hasRedstoneSignal;
                    case 6 -> MinerBlockEntity.this.stillCrafting;
                    case 7 -> MinerBlockEntity.this.hasSlotFree;
                    case 8 -> MinerBlockEntity.this.hasComponent;
                    case 9 -> MinerBlockEntity.this.hasEnoughEnergy;
                    case 10 -> MinerBlockEntity.this.hasRecipe;

                    case 11 -> MinerBlockEntity.this.linkx;
                    case 12 -> MinerBlockEntity.this.linky;
                    case 13 -> MinerBlockEntity.this.linkz;
                    case 14 -> MinerBlockEntity.this.linkFace;

                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MinerBlockEntity.this.progress = pValue;
                    case 1 -> MinerBlockEntity.this.maxProgress = pValue;
                    case 2 -> MinerBlockEntity.this.verify = pValue;
                    case 3 -> MinerBlockEntity.this.maxVerify = pValue;
                    case 4 -> MinerBlockEntity.this.structure = pValue;

                    case 5 -> MinerBlockEntity.this.hasRedstoneSignal = pValue;
                    case 6 -> MinerBlockEntity.this.stillCrafting = pValue;
                    case 7 -> MinerBlockEntity.this.hasSlotFree = pValue;
                    case 8 -> MinerBlockEntity.this.hasComponent = pValue;
                    case 9 -> MinerBlockEntity.this.hasEnoughEnergy = pValue;
                    case 10 -> MinerBlockEntity.this.hasRecipe = pValue;

                    case 11 -> MinerBlockEntity.this.linkx = pValue;
                    case 12 -> MinerBlockEntity.this.linky = pValue;
                    case 13 -> MinerBlockEntity.this.linkz = pValue;
                    case 14 -> MinerBlockEntity.this.linkFace = pValue;
                }
            }

            @Override
            public int getCount() {
                return 15;
            }
        };

    }

    private final Map<Direction, Lazy<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.UP, Lazy.of(() -> new WrappedHandler(itemHandler, (i) -> MinerHandler.extract(i, Direction.UP), MinerHandler::insert)),
                    Direction.DOWN, Lazy.of(() -> new WrappedHandler(itemHandler, (i) -> MinerHandler.extract(i, Direction.DOWN), MinerHandler::insert)),
                    Direction.NORTH, Lazy.of(() -> new WrappedHandler(itemHandler, (i) -> MinerHandler.extract(i, Direction.NORTH), MinerHandler::insert)),
                    Direction.SOUTH, Lazy.of(() -> new WrappedHandler(itemHandler, (i) -> MinerHandler.extract(i, Direction.SOUTH), MinerHandler::insert)),
                    Direction.EAST, Lazy.of(() -> new WrappedHandler(itemHandler, (i) -> MinerHandler.extract(i, Direction.EAST), MinerHandler::insert)),
                    Direction.WEST, Lazy.of(() -> new WrappedHandler(itemHandler, (i) -> MinerHandler.extract(i, Direction.WEST), MinerHandler::insert)));


    public IItemHandler getItemHandler(Direction direction) {
           return itemHandler;
    }

//    @Override
//    public @NotNull <T> Lazy<T> getCapability(@NotNull Capabilities<?> cap, @Nullable Direction side) {
//        if (cap == Capabilities.EnergyStorage.BLOCK) {
//            return lazyEnergyStorage.cast();
//        }
//
//        if (cap == Capabilities.ItemHandler.BLOCK) {
//            if (side == null) {
//                return lazyItemHandler.cast();
//            }
//
//            if (directionWrappedHandlerMap.containsKey(side)) {
//                Direction localDir = this.getBlockState().getValue(Miner.FACING);
//
//                if (side == Direction.UP || side == Direction.DOWN) {
//                    return directionWrappedHandlerMap.get(side).cast();
//                }
//
//                return switch (localDir) {
//                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
//                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
//                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
//                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
//                };
//            }
//        }
//
//
//        return super.getCapability(cap, side);
//    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return ENERGY_STORAGE;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = Lazy.of(() -> itemHandler);
        lazyEnergyStorage = Lazy.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    public static int getComponentSlot() {
        return COMPONENT_SLOT;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots() -1; i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.infinity_nexus_miner.miner").append(" LV " + getMachineLevel());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MinerMenu(pContainerId, pPlayerInventory,this, this.data, itemHandler);
    }

    public IEnergyStorage getEnergyStorage() {
        return this.ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
    public static int getRecipeSlot() {
        return RECIPE_SLOT;
    }
    public static int getStructureSlot() {
        return STRUCTURE_SLOT;
    }

    public int[] getDisplayInfo() {
        return new int[] { data.get(5), data.get(8), data.get(9), data.get(4), data.get(7), data.get(10)};
    }

    public String getHasLink() {
        if (data.get(11) != 0 || data.get(12) != 0 || data.get(13) != 0) {
            return Component.translatable("gui.infinity_nexus_miner.link_on").getString() + " X: " + this.data.get(11) + ", Y: " + this.data.get(12) + ", Z: " + this.data.get(13);
        } else {
            return Component.translatable("gui.infinity_nexus_miner.link_off").getString();
        }
    }

    public ItemStack getLikedBlock() {
        return new ItemStack(level.getBlockState(new BlockPos(this.data.get(11), this.data.get(12), this.data.get(13))).getBlock().asItem());
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        pTag.put("inventory", itemHandler.serializeNBT(registries));
        pTag.putInt("miner.progress", progress);
        pTag.putInt("miner.energy", ENERGY_STORAGE.getEnergyStored());

        pTag.putInt("miner.hasStructure", data.get(4));

        pTag.putInt("miner.hasRedstoneSignal", data.get(5));
        pTag.putInt("miner.stillCrafting", data.get(6));
        pTag.putInt("miner.hasSlotFree", data.get(7));
        pTag.putInt("miner.hasComponent", data.get(8));
        pTag.putInt("miner.hasEnoughEnergy", data.get(9));
        pTag.putInt("miner.hasRecipe", data.get(10));

        pTag.putInt("miner.linkx", data.get(11));
        pTag.putInt("miner.linky", data.get(12));
        pTag.putInt("miner.linkz", data.get(13));
        pTag.putInt("miner.linkFace", data.get(14));

        super.saveAdditional(pTag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        itemHandler.deserializeNBT(registries, pTag.getCompound("inventory"));
        progress = pTag.getInt("miner.progress");
        ENERGY_STORAGE.setEnergy(pTag.getInt("miner.energy"));

        structure = pTag.getInt("miner.hasStructure");

        hasRedstoneSignal = pTag.getInt("miner.hasRedstoneSignal");
        stillCrafting = pTag.getInt("miner.stillCrafting");
        hasSlotFree = pTag.getInt("miner.hasSlotFree");
        hasComponent = pTag.getInt("miner.hasComponent");
        hasEnoughEnergy = pTag.getInt("miner.hasEnoughEnergy");
        hasRecipe = pTag.getInt("miner.hasRecipe");

        linkx = pTag.getInt("miner.linkx");
        linky = pTag.getInt("miner.linky");
        linkz = pTag.getInt("miner.linkz");
        linkFace = pTag.getInt("miner.linkFace");
    }

    public <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide) {
            return;
        }
        int machineLevel = getMachineLevel() - 1 <= 0 ? 0 : getMachineLevel() - 1;
        if (this.structure == 0) {
            if(pState.getValue(Miner.LIT) != machineLevel) {
                pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            }
        }
        if (isRedstonePowered(pPos)) {
            this.data.set(5, 1);
            if(pState.getValue(Miner.LIT) != machineLevel) {
                pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            }
            return;
        }
        this.data.set(5, 0);

        if (!hasProgressFinished()) {
            this.data.set(6, 0);
            increaseCraftingProgress();
            return;
        }
        this.data.set(6, 1);

        if (!hasEmptySlot()) {
            this.data.set(7, 0);
            if(pState.getValue(Miner.LIT) != machineLevel) {
                pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            }
            if(hasProgressFinished()){
                insertItemOnInventory(ItemStack.EMPTY);
            }
            return;
        }
        this.data.set(7, 1);

        resetProgress();
        if (!hasComponent()) {
            this.data.set(8, 0);
            if(pState.getValue(Miner.LIT) != machineLevel) {
                pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            }
            return;
        }
        this.data.set(8, 1);

        setMaxProgress(machineLevel);
        if (!hasEnoughEnergy(machineLevel)) {
            verifySolidFuel();
            this.data.set(9, 0);
            if(pState.getValue(Miner.LIT) != machineLevel) {
                pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel), 3);
            }
            return;
        }
        this.data.set(9, 1);
        if (hasRecipe(pPos, machineLevel)) {
            renderParticles(pPos);
            this.data.set(10, 1);
            if(pState.getValue(Miner.LIT) != machineLevel + 9) {
                pLevel.setBlock(pPos, pState.setValue(Miner.LIT, machineLevel + 9), 3);
            }
            craftItem(pPos, machineLevel);
            extractEnergy(this, machineLevel);
            verifySolidFuel();
            ModUtils.ejectItemsWhePusher(pPos,UPGRADE_SLOTS, OUTPUT_SLOT, itemHandler, pLevel);
            setChanged(pLevel, pPos, pState);
        }
        this.data.set(10, 0);

    }

    private void renderParticles(BlockPos pPos) {
        double x = pPos.getX()+0.5;
        double y = pPos.getY()+1;
        double z = pPos.getZ()+0.5;
        var level = (ServerLevel) this.getLevel();
        level.sendParticles(ParticleTypes.PORTAL, x, y, z, 12, 0, 0, 0, 0.1D);
    }

//    private void notifyOwner(int machineLevel, Level pLevel) {
//        try {
//            if (this.customBlockData != null && this.customBlockData.getInt("ownerNotifyDelay") >= this.customBlockData.getInt("ownerNotifyMaxDelay")) {
//                Player player = pLevel.getPlayerByUUID(this.customBlockData.getUUID("ownerUUID"));
//                player.playNotifySound(SoundEvents.NOTE_BLOCK_GUITAR, SoundSource.BLOCKS, 5.0F, 1.0F);
//                this.customBlockData.putInt("ownerNotifyDelay", 0);
//            }
//            this.customBlockData.putInt("ownerNotifyDelay", this.customBlockData.getInt("ownerNotifyDelay") + 1);
//        } catch (Exception e) {
//
//        }
//    }

    private boolean hasEmptySlot() {
        boolean hasFreeSpace = false;
        for (int slot : OUTPUT_SLOT) {
            if (itemHandler.getStackInSlot(slot).isEmpty()) {
                hasFreeSpace = true;
                break;
            }
        }
        return hasFreeSpace;
    }

    private boolean hasComponent() {
        return ModUtils.isComponent(itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    private void extractEnergy(MinerBlockEntity minerBlockEntity, int machineLevel) {
        int energy = ((machineLevel + 1) * Config.energy_per_operation_base);
        int speed = Math.max(ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS), 2) + machineLevel;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = energy * speed;

        int extractEnergy = var1 + strength;
        minerBlockEntity.ENERGY_STORAGE.extractEnergy(Math.max(extractEnergy, 1), false);
    }

    private boolean hasEnoughEnergy(int machineLevel) {

        int energy = ((machineLevel + 1) * Config.energy_per_operation_base);
        int speed = Math.max(ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS), 2) + machineLevel;
        int strength = (ModUtils.getStrength(itemHandler, UPGRADE_SLOTS) * 10);

        int var1 = energy * speed;

        int extractEnergy = var1 + strength;
        return ENERGY_STORAGE.getEnergyStored() >= extractEnergy;
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean isOre(ItemStack stack) {
        List<TagKey<Item>> tags = stack.getTags().toList();

        return tags.toString().contains("c:ores");
    }
    public void enchantPickaxe(ItemStack pikaxe, Map<Enchantment, Integer> enchantments) {
        if (enchantments.isEmpty()) {
            return;
        }

        //for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
        //    if (entry.getKey() != Enchantments.SILK_TOUCH || Config.miner_can_be_use_silk_touch) {
        //        if (entry.getKey() == Enchantments.FORTUNE) {
        //            pikaxe.enchant(Enchantments.FORTUNE, Math.min(entry.getValue(), Config.max_fortune_level));
        //        } else {
        //            pikaxe.enchant(entry.getKey(), entry.getValue());
        //        }
        //    }
        //}
    }
    private ItemStack getPickaxe() {
        ItemStack enchantedItem = itemHandler.getStackInSlot(FORTUNE_SLOT);
        Item pickaxeInSlot = itemHandler.getStackInSlot(FORTUNE_SLOT).getItem();
        ItemStack pickaxe = new ItemStack(pickaxeInSlot instanceof PickaxeItem ? pickaxeInSlot.getDefaultInstance().getItem() : Items.NETHERITE_PICKAXE);
        //TODO Upgrade
        //Map<Enchantment, Integer> enchantments = enchantedItem.getAllEnchantments();
        if (enchantedItem.getItem() instanceof EnchantedBookItem) {
            //Map<Enchantment, Integer> enchantment = EnchantmentHelper.getEnchantments(enchantedItem);
            //enchantPickaxe(pickaxe, enchantment);
        } else {
            //enchantPickaxe(pickaxe, enchantments);
        }
        return pickaxe;
    }

    private void craftItem(BlockPos pos, int machineLevel) {
        ItemStack component = this.itemHandler.getStackInSlot(COMPONENT_SLOT);
        ItemStack output = getOutputItem(pos, machineLevel);
        Random random = new Random();
        int random1 = random.nextInt(100 * Math.max(machineLevel, 1));
        if(Config.miner_mining_cost_component_durability){
            ModUtils.useComponent(component, level, this.getBlockPos());
        }

        if(random1 <= machineLevel+1){
            for(int i = 1; i <= random1; i++){
                insertItemOnInventory(new ItemStack(ModUtilsMiner.getCrystalType(Math.min(i, 8)).getItem()));
            }
        }

        insertItemOnInventory(output);
        if(ModUtils.getMuffler(itemHandler, UPGRADE_SLOTS) <= 0) {
            level.playSound(null, this.getBlockPos(), SoundEvents.BEE_HURT, SoundSource.BLOCKS, 0.1f, 1.0f);
        }
    }

    private boolean hasRecipe(BlockPos pos, int machineLevel) {
        if (this.verify >= maxVerify) {
            this.structure = MinerTierStructure.hasStructure(machineLevel + 1, pos, level, itemHandler, false) ? 1 : 0;
            this.data.set(4, structure);
            this.verify = 0;
        }
        this.verify++;
        return this.structure > 0;
    }
    private Optional<RecipeHolder<MinerRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.MINER_RECIPE_TYPE.get(), new MinerRecipeInput(itemHandler.getStackInSlot(RECIPE_SLOT)), level);
    }

    private ItemStack getOutputItem(BlockPos pos, int machineLevel) {
        Optional<RecipeHolder<MinerRecipe>> recipe = getCurrentRecipe();

        List<ItemStack> drops = new ArrayList<>();
        int levelMatch = (machineLevel == 8 ? 7 : machineLevel);
        int radio = ((int) Math.floor((double) levelMatch / 2) + 1);

        int startX = pos.getX() - radio;
        int startY = pos.getY() - ((int) Math.floor((double) levelMatch + 4) / 2) * 2;
        int startZ = pos.getZ() - radio;

        int endX = pos.getX() + radio;
        int endY = pos.getY() - 2;
        int endZ = pos.getZ() + radio;

        int randomX = startX + new Random().nextInt(endX - (startX - 1));
        int randomY = startY + new Random().nextInt(endY - (startY - 1));
        int randomZ = startZ + new Random().nextInt(endZ - (startZ - 1));

        BlockPos novoBlockPos = new BlockPos(randomX, randomY, randomZ);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (blockPos.equals(novoBlockPos)) {
                        BlockState blockState = level.getBlockState(blockPos);
                        ItemStack blockStack = new ItemStack(blockState.getBlock().asItem());
                        itemHandler.setStackInSlot(RECIPE_SLOT, blockStack);
                        recipe = getCurrentRecipe();
                        if (!recipe.isEmpty()) {
                            if (blockState.isAir() || isOre(blockStack)) {
                                ItemStack drop = ModUtilsMiner.getDrop(blockStack, level, blockPos, getPickaxe());
                                drops.add(Objects.requireNonNullElse(drop, ItemStack.EMPTY));
                            }
                        }
                    }
                }
            }
        }
        try {
            if (drops.isEmpty()) {
                ItemStack recipeItem = recipe.get().value().getResultItem(null);
                drops.add(recipeItem);
            }
            if(!recipe.get().value().getFortune()){
                ItemStack recipeItem = recipe.get().value().getResultItem(null);
                drops.clear();
                drops.add(recipeItem);
            }
        }catch (Exception e){
            drops.add(ItemStack.EMPTY);
        }
        return drops.isEmpty() ? ItemStack.EMPTY : drops.get(new Random().nextInt(drops.size()));
    }
    private void insertItemOnInventory(ItemStack itemStack) {
        try {
            if (itemHandler.getStackInSlot(LINK_SLOT).is(ModItems.LINKING_TOOL.get())) {
                ItemStack linkingTool = itemHandler.getStackInSlot(LINK_SLOT).copy();
                AtomicBoolean success = new AtomicBoolean(false);
                String name = linkingTool.getDisplayName().getString();
                this.data.set(11, 0);
                this.data.set(12, 0);
                this.data.set(13, 0);
                if (!linkingTool.getHoverName().getString().equals(linkingTool.getItem().getDefaultInstance().getHoverName().getString())) {
                    String[] parts = name.substring(1, name.length() - 1).split(",");
                    int xl = 0;
                    int yl = 0;
                    int zl = 0;
                    String facel = "up";

                    for (String part : parts) {
                        String[] keyValue = part.split("=");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("x")) {
                            xl = Integer.parseInt(value);
                            this.data.set(11, xl);
                        } else if (key.equals("y")) {
                            yl = Integer.parseInt(value);
                            this.data.set(12, yl);
                        } else if (key.equals("z")) {
                            zl = Integer.parseInt(value);
                            this.data.set(13, zl);
                        } else if (key.equals("face")) {
                            facel = value;
                        }
                    }
                    BlockEntity blockEntity = this.level.getBlockEntity(new BlockPos(xl, yl, zl));
                    if (blockEntity.getBlockPos().equals(this.getBlockPos())) {
                        level.addFreshEntity(new ItemEntity(level, xl, yl + 1, zl, itemHandler.getStackInSlot(LINK_SLOT).copy()));
                        itemHandler.extractItem(LINK_SLOT, 1, false);
                        return;
                    }
                    if(!itemHandler.getStackInSlot(OUTPUT_SLOT[7]).isEmpty()) {
                        var object = level.getCapability(Capabilities.ItemHandler.BLOCK, blockEntity.getBlockPos(), null);
                        if (object != null && canLink(blockEntity)) {
                            for (int slot = 0; slot < object.getSlots(); slot++) {
                                if (ModUtils.canPlaceItemInContainer(itemStack.copy(), slot, object) && object.isItemValid(slot, itemStack.copy())) {
                                    object.insertItem(slot, itemStack.copy(), false);
                                    success.set(true);
                                    break;
                                }
                            }
                            for (int slot = 0; slot < object.getSlots(); slot++) {
                                for (int outputSlot : OUTPUT_SLOT) {
                                    if (!itemHandler.getStackInSlot(outputSlot).isEmpty() && object.isItemValid(slot, itemStack.copy()) && ModUtils.canPlaceItemInContainer(itemHandler.getStackInSlot(outputSlot).copy(), slot, object)) {
                                        object.insertItem(slot, itemHandler.getStackInSlot(outputSlot).copy(), false);
                                        itemHandler.extractItem(outputSlot, itemHandler.getStackInSlot(outputSlot).getCount(), false);
                                        success.set(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!success.get()) {
                    insertItemOnSelfInventory(itemStack);
                }
            } else {
                insertItemOnSelfInventory(itemStack);
            }
        } catch (Exception e) {
            System.out.println("§f[INM§f]§c: Failed to insert item in: " + this.getBlockPos());
        }
    }

    private boolean canLink(BlockEntity blockEntity) {
        return (int) Math.sqrt(this.getBlockPos().distSqr(blockEntity.getBlockPos())) < 100;
    }

    private void insertItemOnSelfInventory(ItemStack itemStack) {
        for (int slot : OUTPUT_SLOT) {
            if (ModUtils.canPlaceItemInContainer(itemStack, slot, this.itemHandler)) {
                this.itemHandler.insertItem(slot, itemStack, false);
                break;
            }
        }
    }

    private int getMachineLevel() {
        return ModUtils.getComponentLevel(this.itemHandler.getStackInSlot(COMPONENT_SLOT));
    }

    private boolean isRedstonePowered(BlockPos pPos) {
        return this.level.hasNeighborSignal(pPos);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private void setMaxProgress(int machineLevel) {
        int duration = structure == 0 ? 5 : 120;
        int speed = ModUtils.getSpeed(itemHandler, UPGRADE_SLOTS);

        duration = duration / Math.max(((machineLevel+1) + speed), 1);
        if(itemHandler.getStackInSlot(COMPONENT_SLOT). getItem() == ModItems.ANCESTRAL_COMPONENT.get() && speed == 16) {
            maxProgress = 1;
        }else{
            maxProgress = Math.max(duration, 1);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithFullMetadata(registries);
    }

    public void makeStructure() {
        if(level.isClientSide()) {
            return;
        }
        MinerTierStructure.hasStructure(getMachineLevel(), this.getBlockPos(), this.getLevel(), this.itemHandler, true);
    }
    public void resetVerify() {
        this.data.set(2, this.data.get(3));
        verify = maxVerify;
        progress = maxProgress;
    }
    private void verifySolidFuel(){
        ItemStack slotItem = itemHandler.getStackInSlot(FUEL_SLOT);
        int burnTime = slotItem.getBurnTime(null) * Config.miner_fuel_multiplier;
        if(burnTime > 1){
            while(itemHandler.getStackInSlot(FUEL_SLOT).getCount() > 0 && this.getEnergyStorage().getEnergyStored() + burnTime < this.getEnergyStorage().getMaxEnergyStored()){
                this.getEnergyStorage().receiveEnergy(burnTime, false);
                itemHandler.extractItem(FUEL_SLOT, 1, false);
            }
        }
    }


    public void setMachineLevel(ItemStack itemStack, Player player) {
        SetMachineLevel.setMachineLevel(itemStack, player, this, COMPONENT_SLOT, this.itemHandler);
        makeStructure();
        resetVerify();
    }
    public void setUpgradeLevel(ItemStack itemStack, Player player) {
        SetUpgradeLevel.setUpgradeLevel(itemStack, player, this, UPGRADE_SLOTS, this.itemHandler);
        setChanged();
    }

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(RECIPE_SLOT);
    }

    public float getRotation() {
        return rotation = rotation < 360F ? (float) (rotation + 0.1) : 0.0F;
    }

}