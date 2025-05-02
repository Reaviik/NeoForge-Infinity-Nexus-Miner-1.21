package com.Infinity.Nexus.Miner.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class EnchantSlot extends SlotItemHandler {
    public EnchantSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getItem() == Items.ENCHANTED_BOOK || stack.isEnchanted();
    }
    @Override
    public int getMaxStackSize() {
        return 1;
    }
}