package com.Infinity.Nexus.Miner.tab;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.Infinity.Nexus.Miner.item.ModItemsMiner;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, InfinityNexusMiner.MOD_ID);
    public static final Supplier<CreativeModeTab> INFINITY_TAB_ADDITIONS = CREATIVE_MODE_TABS.register("infinity_nexus_miner",
            //Tab Icon
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocksMiner.MINER.get()))
                    .title(Component.translatable("itemGroup.infinity_nexus_miner"))
                    .displayItems((pParameters, pOutput) -> {
                        //-------------------------//-------------------------//
                        //Machines
                        pOutput.accept(new ItemStack(ModBlocksMiner.MINER.get()));
                        //-------------------------//-------------------------//
                        //Estruturas
                        pOutput.accept(new ItemStack(ModBlocksMiner.WOOD_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.STONE_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.COPPER_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.IRON_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.GOLD_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.QUARTZ_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.DIAMOND_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.EMERALD_STRUCTURE.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.NETHERITE_STRUCTURE.get()));
                        //-------------------------//-------------------------//
                        //Lights
                        pOutput.accept(new ItemStack(ModBlocksMiner.WHITE_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.YELLOW_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.BLUE_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.ORANGE_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.GREEN_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.PURPLE_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.PINK_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.RED_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.ANCIENT_LIGHT_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.CHRISTMAS_LIGHT_CRYSTAL.get()));
                        //-------------------------//-------------------------//
                        //Crystal
                        pOutput.accept(new ItemStack(ModItemsMiner.AMBER_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.MARINE_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.CITRIUM_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.RUBIUM_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.DEMETRIUM_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.AGATE_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.DARIUM_CRYSTAL.get()));
                        pOutput.accept(new ItemStack(ModItemsMiner.TERMURIUM_CRYSTAL.get()));
                        //-------------------------//-------------------------//
                        //Glasses
                        pOutput.accept(new ItemStack(ModBlocksMiner.WHITE_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.YELLOW_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.BLUE_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.ORANGE_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.GREEN_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.PURPLE_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.PINK_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.RED_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.ANCIENT_CLEAR_GLASS.get()));
                        pOutput.accept(new ItemStack(ModBlocksMiner.CHRISTMAS_CLEAR_GLASS.get()));
                        //-------------------------//-------------------------//
                        //Structural Block
                        //pOutput.accept(new ItemStack(ModBlocks.STRUCTURAL_BLOCK.get()));
                        //-------------------------//-------------------------//

                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
