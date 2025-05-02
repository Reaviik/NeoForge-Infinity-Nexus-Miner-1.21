package com.Infinity.Nexus.Miner.slots;

import com.Infinity.Nexus.Miner.config.ConfigUtils;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class StructureSlot extends SlotItemHandler {
    public StructureSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return ConfigUtils.isStructure(stack.getItem());
    }
    @Override
    public int getMaxStackSize() {
        return 64;
    }
}