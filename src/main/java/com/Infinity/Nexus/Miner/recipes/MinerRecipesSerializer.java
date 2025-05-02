package com.Infinity.Nexus.Miner.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MinerRecipesSerializer implements RecipeSerializer<MinerRecipe> {
    public static final MapCodec<MinerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.INT.fieldOf("energy").forGetter(MinerRecipe::getEnergy),
            Ingredient.CODEC.fieldOf("level").forGetter(MinerRecipe::getTier),
            Codec.BOOL.fieldOf("fortune").forGetter(MinerRecipe::getFortune),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(MinerRecipe::getIngredient),
            ItemStack.CODEC.fieldOf("result").forGetter(MinerRecipe::getResultItem)
    ).apply(inst, MinerRecipe::new));


    public static final StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, MinerRecipe::getEnergy,
                    Ingredient.CONTENTS_STREAM_CODEC, MinerRecipe::getTier,
                    ByteBufCodecs.BOOL, MinerRecipe::getFortune,
                    Ingredient.CONTENTS_STREAM_CODEC, MinerRecipe::getIngredient,
                    ItemStack.STREAM_CODEC, MinerRecipe::getResultItem,
                    MinerRecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<MinerRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}