package com.Infinity.Nexus.Miner.compat;

import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.Infinity.Nexus.Miner.item.ModItemsMiner;
import com.Infinity.Nexus.Miner.recipes.MinerRecipe;
import com.Infinity.Nexus.Miner.recipes.ModRecipes;
import com.Infinity.Nexus.Miner.screen.miner.MinerScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;


@JeiPlugin
public class JEIModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(InfinityNexusMiner.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
        registration.addRecipeCategories(new MinerCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        //-----------------------------------------Registry--------------------------------------------------//
        List<MinerRecipe> minerRecipes = recipeManager.getAllRecipesFor(ModRecipes.MINER_RECIPE_TYPE.get())
                .stream().map(RecipeHolder::value).toList();

        try {
            registration.addRecipes(MinerCategory.MINER_TYPE, minerRecipes);
            System.out.println("Registry: " + minerRecipes.size() +" "+ Component.translatable("block.infinity_nexus_miner.miner"));
        }catch (Exception ignored){
        }
        registration.addItemStackInfo(new ItemStack(ModBlocksMiner.MINER.get()), Component.translatable("infinity_nexus_mod.jei_information"));

        registration.addItemStackInfo(new ItemStack(ModItemsMiner.AMBER_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §51"));
        registration.addItemStackInfo(new ItemStack(ModItemsMiner.MARINE_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §52"));
        registration.addItemStackInfo(new ItemStack(ModItemsMiner.CITRIUM_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §53"));
        registration.addItemStackInfo(new ItemStack(ModItemsMiner.RUBIUM_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §54"));
        registration.addItemStackInfo(new ItemStack(ModItemsMiner.DEMETRIUM_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §55"));
        registration.addItemStackInfo(new ItemStack(ModItemsMiner.AGATE_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §56"));
        registration.addItemStackInfo(new ItemStack(ModItemsMiner.DARIUM_CRYSTAL.get()), Component.translatable("item.infinity_nexus_miner.crystal_description").append(" §57"));

    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        //TODO
        IModPlugin.super.registerRecipeTransferHandlers(registration);
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocksMiner.MINER.get().asItem().getDefaultInstance(), MinerCategory.MINER_TYPE);
    }
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //-----------------------------------------Registry--------------------------------------------------//
        registration.addRecipeClickArea(MinerScreen.class,162, -10,8,9, MinerCategory.MINER_TYPE);
    }

}
