package com.Infinity.Nexus.Miner.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record MinerRecipe(Integer energy, Ingredient tier, Boolean fortune, Ingredient inputItem, ItemStack result)
        implements Recipe<MinerRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(MinerRecipeInput minerRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return inputItem.test(minerRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(MinerRecipeInput minerRecipeInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MINER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MINER_RECIPE_TYPE.get();
    }

    public Ingredient getIngredient() {
        return inputItem;
    }

    public ItemStack getResultItem() {
        return result;
    }

    public Boolean getFortune() {
        return fortune;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Ingredient getTier() {
        return tier;
    }

    public static class Serializer implements RecipeSerializer<MinerRecipe> {
        public static final MapCodec<MinerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.INT.fieldOf("energy").forGetter(MinerRecipe::energy),
                Ingredient.CODEC_NONEMPTY.fieldOf("level").forGetter(MinerRecipe::tier),
                Codec.BOOL.fieldOf("fortune").forGetter(MinerRecipe::fortune),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(MinerRecipe::inputItem),
                ItemStack.CODEC.fieldOf("output").forGetter(MinerRecipe::result)
        ).apply(inst, MinerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, MinerRecipe::energy,
                Ingredient.CONTENTS_STREAM_CODEC, MinerRecipe::tier,
                ByteBufCodecs.BOOL, MinerRecipe::fortune,
                Ingredient.CONTENTS_STREAM_CODEC, MinerRecipe::inputItem,
                ItemStack.STREAM_CODEC, MinerRecipe::result,
                MinerRecipe::new
        );


        @Override
        public MapCodec<MinerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}