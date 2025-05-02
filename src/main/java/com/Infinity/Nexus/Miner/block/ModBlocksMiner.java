package com.Infinity.Nexus.Miner.block;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.block.custom.LightCrystal;
import com.Infinity.Nexus.Miner.block.custom.Miner;
import com.Infinity.Nexus.Miner.block.custom.Structure;
import com.Infinity.Nexus.Miner.item.ModItemsMiner;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocksMiner {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(InfinityNexusMiner.MOD_ID);
    public static final DeferredBlock<Block> MINER = registerBlock("miner", () -> new Miner(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).lightLevel((state) -> state.getValue(Miner.LIT) >= 8 ? 2 : 0).noOcclusion().mapColor(MapColor.METAL)));
    public static final DeferredBlock<Block> STRUCTURAL_BLOCK = registerBlock("structural_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).replaceable().noLootTable().noOcclusion().noCollission().mapColor(MapColor.COLOR_RED).instabreak()));

    public static final DeferredBlock<Block> PURPLE_CLEAR_GLASS = registerBlock("purple_clear_glass", () -> new StainedGlassBlock(DyeColor.PURPLE,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> YELLOW_CLEAR_GLASS = registerBlock("yellow_clear_glass", () -> new StainedGlassBlock(DyeColor.YELLOW,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> WHITE_CLEAR_GLASS = registerBlock("white_clear_glass", () -> new StainedGlassBlock(DyeColor.WHITE,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> RED_CLEAR_GLASS = registerBlock("red_clear_glass", () -> new StainedGlassBlock(DyeColor.RED,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> ORANGE_CLEAR_GLASS = registerBlock("orange_clear_glass", () -> new StainedGlassBlock(DyeColor.ORANGE,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> BLUE_CLEAR_GLASS = registerBlock("blue_clear_glass", () -> new StainedGlassBlock(DyeColor.BLUE,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> PINK_CLEAR_GLASS = registerBlock("pink_clear_glass", () -> new StainedGlassBlock(DyeColor.PINK,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> GREEN_CLEAR_GLASS = registerBlock("green_clear_glass", () -> new StainedGlassBlock(DyeColor.GREEN,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> ANCIENT_CLEAR_GLASS = registerBlock("ancient_clear_glass", () -> new StainedGlassBlock(DyeColor.CYAN,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));

    public static final DeferredBlock<Block> CHRISTMAS_CLEAR_GLASS = registerBlock("christmas_clear_glass", () -> new StainedGlassBlock(DyeColor.CYAN,BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.3f, 0.1f).sound(SoundType.GLASS).lightLevel((state) -> 5).noOcclusion().mapColor(MapColor.COLOR_RED)));

    public static final DeferredBlock<Block> WOOD_STRUCTURE = registerBlock("wood_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).requiresCorrectToolForDrops().strength(2.0f, 1.0f).sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
    public static final DeferredBlock<Block> STONE_STRUCTURE = registerBlock("stone_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.COPPER).mapColor(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<Block> COPPER_STRUCTURE = registerBlock("copper_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.METAL).mapColor(MapColor.GOLD)));
    public static final DeferredBlock<Block> IRON_STRUCTURE = registerBlock("iron_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> GOLD_STRUCTURE = registerBlock("gold_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> QUARTZ_STRUCTURE = registerBlock("quartz_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.COPPER).mapColor(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<Block> DIAMOND_STRUCTURE = registerBlock("diamond_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<Block> EMERALD_STRUCTURE = registerBlock("emerald_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> NETHERITE_STRUCTURE = registerBlock("netherite_structure",() -> new Structure(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(2.0f, 5.0f).sound(SoundType.METAL).mapColor(MapColor.COLOR_BLACK)));

    public static final DeferredBlock<Block> PURPLE_LIGHT_CRYSTAL = registerBlock("purple_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_PURPLE), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> YELLOW_LIGHT_CRYSTAL = registerBlock("yellow_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_YELLOW), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> WHITE_LIGHT_CRYSTAL = registerBlock("white_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.TERRACOTTA_WHITE), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> RED_LIGHT_CRYSTAL = registerBlock("red_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_RED), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> ORANGE_LIGHT_CRYSTAL = registerBlock("orange_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_ORANGE), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> PINK_LIGHT_CRYSTAL = registerBlock("pink_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_PINK), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> GREEN_LIGHT_CRYSTAL = registerBlock("green_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_GREEN), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> BLUE_LIGHT_CRYSTAL = registerBlock("blue_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_BLUE), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> ANCIENT_LIGHT_CRYSTAL = registerBlock("ancient_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_BLUE), BlockSetType.POLISHED_BLACKSTONE, 1, true));
    public static final DeferredBlock<Block> CHRISTMAS_LIGHT_CRYSTAL = registerBlock("christmas_light_crystal", () -> new LightCrystal(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(2.0f, 6.0f).sound(SoundType.GLASS).lightLevel((state) -> 15).noOcclusion().mapColor(MapColor.COLOR_GRAY), BlockSetType.POLISHED_BLACKSTONE, 1, true));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItemsMiner.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}