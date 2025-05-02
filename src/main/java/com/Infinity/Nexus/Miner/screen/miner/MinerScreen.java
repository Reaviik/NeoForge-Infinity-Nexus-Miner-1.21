package com.Infinity.Nexus.Miner.screen.miner;

import com.Infinity.Nexus.Core.items.ModItems;
import com.Infinity.Nexus.Core.renderer.EnergyInfoArea;
import com.Infinity.Nexus.Core.renderer.InfoArea;
import com.Infinity.Nexus.Core.renderer.RenderScreenTooltips;
import com.Infinity.Nexus.Core.utils.MouseUtil;
import com.Infinity.Nexus.Miner.InfinityNexusMiner;
import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;

public class MinerScreen extends AbstractContainerScreen<MinerMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(InfinityNexusMiner.MOD_ID, "textures/gui/miner_gui.png");
    private static final ResourceLocation TEXTURE_SLOTS = ResourceLocation.fromNamespaceAndPath(InfinityNexusMiner.MOD_ID, "textures/gui/miner_slots_gui.png");

    private EnergyInfoArea energyInfoArea;
    private Button assembleButton;

    public MinerScreen(MinerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignEnergyInfoArea();

        this.assembleButton = addRenderableWidget(
                Button.builder(
                                Component.literal(" "),
                                this::handleExampleButton)
                        .bounds(this.leftPos +152, this.topPos -10, 8, 9)
                        .tooltip(Tooltip.create(Component.literal("Click a component on the outside of the Miner to assemble it")))
                        .build());
        this.assembleButton.setAlpha(0.0F);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 159, y + 6, menu.getEnergyStorage());
    }
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font,this.playerInventoryTitle,8,74,0XFFFFFF);
        pGuiGraphics.drawString(this.font,this.title,8,-9,0XFFFFFF);

        renderEnergyAreaTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);
        renderTooltips(pGuiGraphics,pMouseX,pMouseY, x, y);

        InfoArea.draw(pGuiGraphics);
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }
    private void handleExampleButton(AbstractButton button) {
        System.out.println("handle");
        menu.blockEntity.makeStructure();
    }
    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 159,  6, 6, 62)) {
            pGuiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(Screen.hasShiftDown()) {
            if (isMouseAboveArea(pMouseX, pMouseY, x, y, -11, 10, 16, 52)) {
                RenderScreenTooltips.renderUpgradeSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 7, 28, 17, 17)) {
                RenderScreenTooltips.renderComponentSlotTooltipAndItems(this.font, pGuiGraphics, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 25, 28, 17, 17)) {
                //TODO Structure Slot
                List<Component> structureTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.structure_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, structureTooltip, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 79, 10, 53, 53)) {
                //TODO Output Slot
                List<Component> outputTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.output_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, outputTooltip, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 43, 10, 17, 17)) {
                //TODO Enchant Slot
                List<Component> enchantTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.enchant_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, enchantTooltip, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 43, 28, 17, 17)) {
                //TODO Linking Slot
                List<Component> linkingTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.linking_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, linkingTooltip, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 43, 46, 17, 17)) {
                //TODO Fuel Slot
                List<Component> fuelTooltip = List.of(Component.translatable("tooltip.infinity_nexus_miner.fuel_slot_tooltip"));
                RenderScreenTooltips.renderTooltipArea(this.font, pGuiGraphics, fuelTooltip, pMouseX, pMouseY, x, y);
            }else if (isMouseAboveArea(pMouseX, pMouseY, x, y, 146,  6, 6, 62)) {
                List<Component> progressTooltip = List.of(Component.literal((int)(((double) menu.getScaledProgress() / 61 ) * 100) + "%"));
                pGuiGraphics.renderTooltip(font, progressTooltip, Optional.empty(), pMouseX - x, pMouseY - y);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x + 2, y-14, 2, 167, 174, 64);
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        if(Screen.hasShiftDown()){
            guiGraphics.blit(TEXTURE_SLOTS, x, y, 0, 0, imageWidth, imageHeight);
            RenderScreenTooltips.renderComponentSlotTooltip(guiGraphics, TEXTURE_SLOTS, x - 15, y + 10, 193, 84, 18, 131);
        }



        renderProgressArrow(guiGraphics, x, y);
        energyInfoArea.render(guiGraphics);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 210, y + 68 , 240, 62,  -64, -menu.getScaledProgress());

        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int index = y;

        int[] displayInfo = menu.getDisplayInfo();
        int hasRedstoneSignal = displayInfo[0];
        int hasComponent = displayInfo[1];
        int hasEnoughEnergy = displayInfo[2];
        int hasStructure = displayInfo[3];
        int hasSlotFree = displayInfo[4];
        int hasRecipe = displayInfo[5];

        String on = "§f]§a✅§f[ §e";
        String off = "§f]§4❎§f[ §c";

        String hasLink = menu.getBlockEntity().getHasLink();
        ItemStack linkedBlock = menu.getBlockEntity().getLikedBlock();

        renderBackground(guiGraphics,mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if (hasRedstoneSignal == 1) {
            guiGraphics.drawString(this.font, on + "§c" + Component.translatable("gui.infinity_nexus_miner.redstone").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Blocks.LEVER), x + 178, index-4);
            index += 15;
        }else{
            guiGraphics.drawString(this.font, off + "§e" + Component.translatable("gui.infinity_nexus_miner.redstone").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Blocks.LEVER), x + 178, index - 4);
            index += 15;
        }
        if (hasComponent == 0){
            guiGraphics.drawString(this.font, off + Component.translatable("gui.infinity_nexus_miner.component").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(ModItems.REDSTONE_COMPONENT.get()), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, on + Component.translatable("gui.infinity_nexus_miner.component").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModItems.REDSTONE_COMPONENT.get()), x + 178, index - 4);
            index += 15;
        }
        if (hasEnoughEnergy == 0){
            guiGraphics.drawString(this.font, off + Component.translatable("gui.infinity_nexus_miner.energy").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.REDSTONE), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, on + Component.translatable("gui.infinity_nexus_miner.energy").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.REDSTONE), x + 178, index - 4);
            index += 15;
        }
        if (hasStructure == 0){
            guiGraphics.drawString(this.font, off + Component.translatable("gui.infinity_nexus_miner.structure").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(ModBlocksMiner.STRUCTURAL_BLOCK.get()), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, on + Component.translatable("gui.infinity_nexus_miner.structure").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModBlocksMiner.STRUCTURAL_BLOCK.get()), x + 178, index - 4);
            index += 15;
        }
        if (hasSlotFree == 0){
            guiGraphics.drawString(this.font, off + Component.translatable("gui.infinity_nexus_miner.slot").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CHEST), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, on + Component.translatable("gui.infinity_nexus_miner.slot").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CHEST), x + 178, index - 4);
            index += 15;
        }
        if (hasRecipe == 1){
            guiGraphics.drawString(this.font, off + Component.translatable("gui.infinity_nexus_miner.ores").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
            index += 15;
        }else {
            guiGraphics.drawString(this.font, on + Component.translatable("gui.infinity_nexus_miner.ores").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
            index += 15;
        }
        if (linkedBlock != null && linkedBlock.getItem() != Items.AIR){
            guiGraphics.drawString(this.font,on + hasLink, x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(linkedBlock, x + 178, index - 4);
            index += 15;
        }else{
            guiGraphics.drawString(this.font, off + hasLink, x + 196, index, 0XB6FF00);
            guiGraphics.renderFakeItem(new ItemStack(ModItems.LINKING_TOOL.get()), x + 178, index - 4);
            index += 15;
        }
        if (hasRedstoneSignal == 0 && hasComponent == 1 && hasEnoughEnergy == 1 &&
                hasStructure == 1 && hasSlotFree == 1 && hasRecipe == 0){
            guiGraphics.drawString(this.font, on + Component.translatable("gui.infinity_nexus_miner.mining").getString(), x + 196, index, 0X00FF00);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
        }else {
            guiGraphics.drawString(this.font, off + Component.translatable("gui.infinity_nexus_miner.mining").getString(), x + 196, index, 0XFF0000);
            guiGraphics.renderFakeItem(new ItemStack(Items.CRAFTING_TABLE), x + 178, index - 4);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}