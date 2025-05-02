package com.Infinity.Nexus.Miner.utils;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> MINER_STRUCTURE_0 = tag("machine_tier_0");
        public static final TagKey<Block> MINER_STRUCTURE_1 = tag("machine_tier_1");
        public static final TagKey<Block> MINER_STRUCTURE_2 = tag("machine_tier_2");
        public static final TagKey<Block> MINER_STRUCTURE_3 = tag("machine_tier_3");
        public static final TagKey<Block> MINER_STRUCTURE_4 = tag("machine_tier_4");
        public static final TagKey<Block> MINER_STRUCTURE_5 = tag("machine_tier_5");
        public static final TagKey<Block> MINER_STRUCTURE_6 = tag("machine_tier_6");
        public static final TagKey<Block> MINER_STRUCTURE_7 = tag("machine_tier_7");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(InfinityNexusMiner.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }

    }
    public static class Items {
        public static final TagKey<Item> UP_1 = tag("up_1");
        public static final TagKey<Item> UP_2 = tag("up_2");
        public static final TagKey<Item> UP_3 = tag("up_3");
        public static final TagKey<Item> UP_4 = tag("up_4");
        public static final TagKey<Item> UP_5 = tag("up_5");
        public static final TagKey<Item> UP_6 = tag("up_6");
        public static final TagKey<Item> UP_7 = tag("up_7");
        public static final TagKey<Item> UP_8 = tag("up_8");
        public static final TagKey<Item> UP_9 = tag("up_9");

        public static final TagKey<Item> MINER_STRUCTURE_0 = tag("machine_tier_0");
        public static final TagKey<Item> MINER_STRUCTURE_1 = tag("machine_tier_1");
        public static final TagKey<Item> MINER_STRUCTURE_2 = tag("machine_tier_2");
        public static final TagKey<Item> MINER_STRUCTURE_3 = tag("machine_tier_3");
        public static final TagKey<Item> MINER_STRUCTURE_4 = tag("machine_tier_4");
        public static final TagKey<Item> MINER_STRUCTURE_5 = tag("machine_tier_5");
        public static final TagKey<Item> MINER_STRUCTURE_6 = tag("machine_tier_6");
        public static final TagKey<Item> MINER_STRUCTURE_7 = tag("machine_tier_7");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(InfinityNexusMiner.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
    }

}
