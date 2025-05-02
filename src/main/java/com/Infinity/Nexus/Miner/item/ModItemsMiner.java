package com.Infinity.Nexus.Miner.item;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.item.custom.Crystals;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemsMiner {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(InfinityNexusMiner.MOD_ID);

    public static final DeferredItem<Item> AMBER_CRYSTAL = ITEMS.register("amber_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.COMMON), 1));
    public static final DeferredItem<Item> MARINE_CRYSTAL = ITEMS.register("marine_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.COMMON), 2));
    public static final DeferredItem<Item> CITRIUM_CRYSTAL = ITEMS.register("citrium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.UNCOMMON), 3));
    public static final DeferredItem<Item> RUBIUM_CRYSTAL = ITEMS.register("rubium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.UNCOMMON), 4));
    public static final DeferredItem<Item> DEMETRIUM_CRYSTAL = ITEMS.register("demetrium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.RARE), 5));
    public static final DeferredItem<Item> AGATE_CRYSTAL = ITEMS.register("agate_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.RARE), 6));
    public static final DeferredItem<Item> DARIUM_CRYSTAL = ITEMS.register("darium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.EPIC), 7));
    public static final DeferredItem<Item> TERMURIUM_CRYSTAL = ITEMS.register("termurium_crystal",() -> new Crystals(new Item.Properties().rarity(Rarity.EPIC), 8));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}