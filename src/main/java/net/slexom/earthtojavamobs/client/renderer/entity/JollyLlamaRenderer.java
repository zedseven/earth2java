package net.slexom.earthtojavamobs.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.slexom.earthtojavamobs.entity.passive.JollyLlamaEntity;

@OnlyIn(Dist.CLIENT)
public class JollyLlamaRenderer extends MobRenderer<JollyLlamaEntity, LlamaModel<JollyLlamaEntity>> {

    public JollyLlamaRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new LlamaModel<>(0.0F), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(JollyLlamaEntity entity) {
        String resourceTexture = "earthtojavamobs:textures/mobs/llama/jolly_llama/jolly_llama.png";
        String resourceTextureBlink = "earthtojavamobs:textures/mobs/llama/jolly_llama/jolly_llama_blink.png";
        ResourceLocation texture = new ResourceLocation(resourceTexture);
        ResourceLocation textureBlink = new ResourceLocation(resourceTextureBlink);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }

}
