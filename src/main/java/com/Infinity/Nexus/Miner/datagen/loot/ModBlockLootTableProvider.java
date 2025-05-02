package com.Infinity.Nexus.Miner.datagen.loot;

import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocksMiner.MINER.get());
        dropSelf(ModBlocksMiner.WOOD_STRUCTURE.get());
        dropSelf(ModBlocksMiner.STONE_STRUCTURE.get());
        dropSelf(ModBlocksMiner.COPPER_STRUCTURE.get());
        dropSelf(ModBlocksMiner.IRON_STRUCTURE.get());
        dropSelf(ModBlocksMiner.GOLD_STRUCTURE.get());
        dropSelf(ModBlocksMiner.QUARTZ_STRUCTURE.get());
        dropSelf(ModBlocksMiner.DIAMOND_STRUCTURE.get());
        dropSelf(ModBlocksMiner.EMERALD_STRUCTURE.get());
        dropSelf(ModBlocksMiner.NETHERITE_STRUCTURE.get());

        dropSelf(ModBlocksMiner.RED_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.BLUE_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.GREEN_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.YELLOW_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.PURPLE_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.ORANGE_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.WHITE_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.PINK_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.ANCIENT_LIGHT_CRYSTAL.get());
        dropSelf(ModBlocksMiner.CHRISTMAS_LIGHT_CRYSTAL.get());

        dropSelf(ModBlocksMiner.WHITE_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.YELLOW_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.BLUE_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.ORANGE_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.GREEN_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.PURPLE_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.PINK_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.RED_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.ANCIENT_CLEAR_GLASS.get());
        dropSelf(ModBlocksMiner.CHRISTMAS_CLEAR_GLASS.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocksMiner.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
