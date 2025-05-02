package com.Infinity.Nexus.Miner.recipes;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, InfinityNexusMiner.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, InfinityNexusMiner.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MinerRecipe>> MINER_RECIPE_SERIALIZER = SERIALIZERS.register("mining", MinerRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MinerRecipe>> MINER_RECIPE_TYPE = TYPES.register("mining", () -> new RecipeType<MinerRecipe>() {
                @Override
                public String toString() {
                    return "mining";
                }
            });
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
