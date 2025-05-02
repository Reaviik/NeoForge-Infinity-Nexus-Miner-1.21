package com.Infinity.Nexus.Miner.block.entity;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public  static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, InfinityNexusMiner.MOD_ID);

    public static final Supplier<BlockEntityType<MinerBlockEntity>> MINER_BE =
            BLOCK_ENTITY.register("miner_block_entity", () -> BlockEntityType.Builder.of(
                    MinerBlockEntity::new, ModBlocksMiner.MINER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY.register(eventBus);
    }
}
