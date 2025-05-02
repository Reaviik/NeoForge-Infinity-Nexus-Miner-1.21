package com.Infinity.Nexus.Miner.block.entity.renderer;

import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.Infinity.Nexus.Miner.block.entity.MinerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

import java.util.Objects;

public class MinerBlockEntityRenderer implements BlockEntityRenderer<MinerBlockEntity> {

    public MinerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MinerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {


        ItemStack itemStack = pBlockEntity.getRenderStack();
        ItemStack field = new ItemStack(ModBlocksMiner.WHITE_CLEAR_GLASS.get());


        if(!itemStack.equals(ItemStack.EMPTY)) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 1.15, 0.5);
                pPoseStack.scale(0.5f, 0.5f, 0.5f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getRotation()));

                itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
                pPoseStack.popPose();
            }
            {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 1.15, 0.5);
                pPoseStack.scale(0.6f, 0.6f, 0.6f);
                pPoseStack.mulPose(Axis.YN.rotationDegrees(pBlockEntity.getRotation()));

                itemRenderer.renderStatic(field, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()),
                        pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 0);
                pPoseStack.popPose();
            }
        }
    }
    private int getLightLevel(Level pLevel, BlockPos pPos) {
        int slight = pLevel.getBrightness(LightLayer.SKY, pPos);

        return LightTexture.pack(15, slight);
    }
}
