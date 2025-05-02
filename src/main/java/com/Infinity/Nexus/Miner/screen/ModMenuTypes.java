package com.Infinity.Nexus.Miner.screen;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.screen.miner.MinerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, InfinityNexusMiner.MOD_ID);


    //public static final Supplier<MenuType<MinerMenu>> MINER_MENU = MENUS.register("miner_menu", () -> new MenuType(new MinerMenu(), FeatureFlags.DEFAULT_FLAGS));

    //TODO Mudança
    public static final DeferredHolder<MenuType<?>, MenuType<MinerMenu>> MINER_MENU = registerMenuType("miner_menu", MinerMenu::new);

    //TODO Mudança
    public static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
