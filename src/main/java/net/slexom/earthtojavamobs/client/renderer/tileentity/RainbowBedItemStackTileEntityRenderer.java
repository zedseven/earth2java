package net.slexom.earthtojavamobs.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.slexom.earthtojavamobs.init.TileEntityTypeInit;

public class RainbowBedItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
    @Override
    public void render(ItemStack itemstack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityRendererDispatcher.instance.renderItem(TileEntityTypeInit.RAINBOW_BED.get().create(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
