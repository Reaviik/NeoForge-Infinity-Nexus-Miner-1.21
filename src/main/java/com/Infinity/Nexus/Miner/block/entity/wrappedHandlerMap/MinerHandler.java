package com.Infinity.Nexus.Miner.block.entity.wrappedHandlerMap;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.utils.ModUtils;
import com.Infinity.Nexus.Miner.config.ConfigUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class MinerHandler {

    public static boolean extract(int slot, Direction direction) {
        return switch (slot) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8  -> true; //Output
            case 9, 10, 11, 12, 13-> direction == Direction.UP; //Upgrade an Component
            default -> false;
        };
    }
    public static boolean insert(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case 0,1,2,3,4,5,6,7,8 -> false;
            case 9,10,11,12 -> ModUtils.isUpgrade(stack); //Upgrade
            case 13 -> ModUtils.isComponent(stack); //Component
            case 14 -> stack.getItem() == Items.ENCHANTED_BOOK || stack.isEnchanted();
            case 15 -> stack.is(ModItems.LINKING_TOOL.get().asItem());
            case 16 -> stack.getBurnTime(null) > 0;
            case 17 -> ConfigUtils.isStructure(stack.getItem());
            default -> false;
        };
    }
}
