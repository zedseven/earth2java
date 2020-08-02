package slexom.earthtojava.mobs.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slexom.earthtojava.mobs.client.renderer.entity.model.FurnaceGolemModel;
import slexom.earthtojava.mobs.entity.passive.FurnaceGolemEntity;

@OnlyIn(Dist.CLIENT)
public class FurnaceGolemTorchLayer extends LayerRenderer<FurnaceGolemEntity, FurnaceGolemModel<FurnaceGolemEntity>> {
    public FurnaceGolemTorchLayer(IEntityRenderer<FurnaceGolemEntity, FurnaceGolemModel<FurnaceGolemEntity>> p_i50935_1_) {
        super(p_i50935_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FurnaceGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.getHoldTorchTick() != 0) {
            matrixStackIn.push();
            ModelRenderer modelrenderer = this.getEntityModel().getArmHoldingTorch();
            modelrenderer.translateRotate(matrixStackIn);
            matrixStackIn.translate(-1.1875D, 1.0625D, -0.9375D);
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-90.0F));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(Blocks.TORCH.getDefaultState(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
            matrixStackIn.pop();
        }
    }
}