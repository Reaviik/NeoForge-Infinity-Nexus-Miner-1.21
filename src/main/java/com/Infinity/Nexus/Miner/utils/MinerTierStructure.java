package com.Infinity.Nexus.Miner.utils;

import com.Infinity.Nexus.Core.fakePlayer.IFFakePlayer;
import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.Infinity.Nexus.Miner.block.custom.Structure;
import com.Infinity.Nexus.Miner.block.entity.MinerBlockEntity;
import com.Infinity.Nexus.Miner.config.Config;
import com.Infinity.Nexus.Miner.config.ConfigUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;

public class MinerTierStructure {

    public static boolean hasStructure(int tier, BlockPos pos, Level level, IItemHandler itemHandler, boolean actualize) {
        BlockPos[] offsets;
        int height;
        Block structureLevel = getStructureLevel(tier - 1);

        switch (tier) {
            case 1:
            case 2:
                offsets = new BlockPos[]{
                        new BlockPos(pos.getX() - 2, pos.getY() - 4, pos.getZ() - 2),
                        new BlockPos(pos.getX() + 2, pos.getY() - 4, pos.getZ() - 2),
                        new BlockPos(pos.getX() + 2, pos.getY() - 4, pos.getZ() + 2),
                        new BlockPos(pos.getX() - 2, pos.getY() - 4, pos.getZ() + 2)
                };
                height = 4;
                break;
            case 3:
            case 4:
                offsets = new BlockPos[]{
                        new BlockPos(pos.getX() - 3, pos.getY() - 6, pos.getZ() - 3),
                        new BlockPos(pos.getX() + 3, pos.getY() - 6, pos.getZ() - 3),
                        new BlockPos(pos.getX() + 3, pos.getY() - 6, pos.getZ() + 3),
                        new BlockPos(pos.getX() - 3, pos.getY() - 6, pos.getZ() + 3)
                };
                height = 6;
                break;
            case 5:
            case 6:
                offsets = new BlockPos[]{
                        new BlockPos(pos.getX() - 4, pos.getY() - 8, pos.getZ() - 4),
                        new BlockPos(pos.getX() + 4, pos.getY() - 8, pos.getZ() - 4),
                        new BlockPos(pos.getX() + 4, pos.getY() - 8, pos.getZ() + 4),
                        new BlockPos(pos.getX() - 4, pos.getY() - 8, pos.getZ() + 4)
                };
                height = 8;
                break;
            case 7:
            case 8:
            case 9:
                offsets = new BlockPos[]{
                        new BlockPos(pos.getX() - 5, pos.getY() - 10, pos.getZ() - 5),
                        new BlockPos(pos.getX() + 5, pos.getY() - 10, pos.getZ() - 5),
                        new BlockPos(pos.getX() + 5, pos.getY() - 10, pos.getZ() + 5),
                        new BlockPos(pos.getX() - 5, pos.getY() - 10, pos.getZ() + 5)
                };
                height = 10;
                break;
            default:
                return false;
        }

        boolean complete = true;
        for (int i = 0; i < 4; i++) {
            boolean structureComplete = verificaEstrutura(pos, offsets[i], height, structureLevel, level, height / 2, tier, itemHandler, i, actualize);
            if (!structureComplete) {
                complete = false;
            }
        }

        return complete;
    }


    public static boolean verificaEstrutura(BlockPos pPos, BlockPos pilarPos, int height, Block block, Level level, int radius, int tier, IItemHandler itemHandler, int pilar, boolean actualize) {
        boolean hasPilars = true;
        boolean hasArc = true;
        boolean hasRadios = true;
        int structureSlot = MinerBlockEntity.getStructureSlot();
        for (int i = 0; i < height; i++) {
            BlockPos pos = pilarPos.above(i);
            BlockState blockState = level.getBlockState(pos);
            if (!blockState.is(block)) {
                hasPilars = false;
                place(blockState, pos, level, itemHandler, structureSlot, tier);
            }
        }
        int minX = pPos.getX() - radius;
        int maxX = pPos.getX() + radius;
        int minZ = pPos.getZ() - radius;
        int maxZ = pPos.getZ() + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (x == minX || x == maxX || z == minZ || z == maxZ) {
                    BlockPos pos = new BlockPos(x, pPos.getY(), z).below();
                    BlockState blockState = level.getBlockState(pos);
                    if (!blockState.is(block)) {
                        hasArc = false;
                        place(blockState, pos, level, itemHandler, structureSlot, tier);
                    }
                }
            }
        }
        for (Direction dir : Direction.values()) {
            for (int i = 1; i <= radius-1; i++) {
                BlockPos pos = pPos.below().offset(dir.getStepX() * i, dir.getStepY() * i, dir.getStepZ() * i);
                BlockState blockState = level.getBlockState(pos);
                if (!blockState.is(block) && dir != Direction.DOWN && dir != Direction.UP) {
                    hasRadios = false;
                    place(blockState, pos, level, itemHandler, structureSlot, tier);
                }
            }
        }
        boolean complete = hasArc && hasPilars && hasRadios;
        if(complete && actualize) {
            atualizarEstrutura(pPos, pilarPos, height, block, level, radius, tier, pilar);
        }
        return complete;
    }
    public static void atualizarEstrutura(BlockPos pPos, BlockPos pilarPos, int height, Block block, Level level, int radius, int tier, int pilar) {

        for (int i = 0; i < height; i++) {
            BlockPos pos = pilarPos.above(i);
                if (i == (height - 1)) {
                    BlockState blockState = block.defaultBlockState().setValue(Structure.LIT, 3);
                    switch (pilar) {
                        case 0:
                            level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.SOUTH), 3);
                            break;
                        case 1:
                            level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.WEST), 3);
                            break;
                        case 2:
                            level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.EAST), 3);
                            break;
                        case 3:
                            level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.NORTH), 3);
                            break;
                    }
                } else {
                    BlockState blockState = block.defaultBlockState().setValue(Structure.LIT, 1);
                    level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.UP), 3);
                }
            }


        int minX = pPos.getX() - radius;
        int maxX = pPos.getX() + radius;
        int minZ = pPos.getZ() - radius;
        int maxZ = pPos.getZ() + radius;
        int midX = (minX + maxX) / 2;
        int midZ = (minZ + maxZ) / 2;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if ((x == minX && z == minZ) ||
                        (x == minX && z == maxZ) ||
                        (x == maxX && z == minZ) ||
                        (x == maxX && z == maxZ)) {
                    continue;
                }

                BlockPos pos = new BlockPos(x, pPos.getY(), z).below();
                BlockState blockState = block.defaultBlockState().setValue(Structure.LIT, 1);
                BlockState blockStateT = block.defaultBlockState().setValue(Structure.LIT, 2);

                if (x == midX && (z == minZ || z == maxZ)) {
                    Direction facing = (z == minZ) ? Direction.SOUTH : Direction.EAST;
                    level.setBlock(pos, blockStateT.setValue(Structure.FACING, facing), 3);
                } else if (z == midZ && (x == minX || x == maxX)) {
                    Direction facing = (x == minX) ? Direction.NORTH : Direction.WEST;
                    level.setBlock(pos, blockStateT.setValue(Structure.FACING, facing), 3);
                } else if (x == minX) {
                    level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.EAST), 3);
                } else if (x == maxX) {
                    level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.WEST), 3);
                } else if (z == minZ) {
                    level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.SOUTH), 3);
                } else if (z == maxZ) {
                    level.setBlock(pos, blockState.setValue(Structure.FACING, Direction.NORTH), 3);
                }
            }
        }


        for (Direction dir : Direction.values()) {
            for (int i = 1; i <= radius-1; i++) {
                BlockPos pos = pPos.below().offset(dir.getStepX() * i, dir.getStepY() * i, dir.getStepZ() * i);
                BlockState blockState = block.defaultBlockState().setValue(Structure.LIT, 1);
                if (dir != Direction.DOWN && dir != Direction.UP) {
                    level.setBlock(pos, blockState.setValue(Structure.FACING, dir.getClockWise()), 3);
                }
            }
        }
    }
    private static void place(BlockState blockState, BlockPos pos, Level level, IItemHandler itemHandler, int structureSlot, int tier) {
        if (blockState.getBlock() == ModBlocksMiner.STRUCTURAL_BLOCK.get() || blockState.is(Blocks.AIR)){
            IFFakePlayer fakePlayer = new IFFakePlayer((ServerLevel) level);
            if(getStructureAmount(itemHandler, structureSlot, tier) >= 1) {
                fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, itemHandler.getStackInSlot(structureSlot).copy());
                fakePlayer.gameMode.useItemOn(fakePlayer, level, itemHandler.getStackInSlot(structureSlot), InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
                if(itemHandler.getStackInSlot(structureSlot).is(level.getBlockState(pos).getBlock().asItem())) {
                    removeStructureFromSlots(itemHandler, structureSlot);
                }
                //level.setBlock(pos, Block.byItem(ConfigUtils.getStructureByLevel(tier).getItem()).defaultBlockState(), 3);
            }else{
                if(blockState.is(Blocks.AIR)) {
                    fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModBlocksMiner.STRUCTURAL_BLOCK.get()));
                    fakePlayer.gameMode.useItemOn(fakePlayer, level, new ItemStack(ModBlocksMiner.STRUCTURAL_BLOCK.get()), InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
                }
            }
        }
    }

    private static Block getStructureLevel(int t) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(Config.list_of_structures.get(t)));
    }
    private static void removeStructureFromSlots(IItemHandler itemHandler, int slot) {
        if(itemHandler.getStackInSlot(slot).getCount() > 0 && ConfigUtils.isStructure(itemHandler.getStackInSlot(slot).getItem())) {
            itemHandler.extractItem(slot, 1, false);
        }
    }
    private static int getStructureAmount(IItemHandler itemHandler, int slot, int machineLevel) {
        if (ConfigUtils.getStructureByLevel(machineLevel).getItem() == itemHandler.getStackInSlot(slot).getItem()) {
            return itemHandler.getStackInSlot(slot).getCount();
        }else{
            return 0;
        }
    }
}
