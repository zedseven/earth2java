package slexom.earthtojava.mobs.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slexom.earthtojava.mobs.entity.passive.WoolyCowEntity;

@OnlyIn(Dist.CLIENT)
public class WoolyCowRenderer extends MobRenderer<WoolyCowEntity, CowModel<WoolyCowEntity>> {

    public WoolyCowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
    }

    public ResourceLocation getEntityTexture(WoolyCowEntity entity) {
        ResourceLocation texture = new ResourceLocation("earthtojavamobs:textures/mobs/cow/wooly_cow/wooly_cow.png");
        ResourceLocation textureBlink = new ResourceLocation("earthtojavamobs:textures/mobs/cow/wooly_cow/wooly_cow_blink.png");
        ResourceLocation textureSheared = new ResourceLocation("earthtojavamobs:textures/mobs/cow/wooly_cow/wooly_cow_sheared.png");
        ResourceLocation textureShearedBlink = new ResourceLocation("earthtojavamobs:textures/mobs/cow/wooly_cow/wooly_cow_sheared_blink.png");
        boolean blink = entity.getBlinkRemainingTicks() > 0;
        return entity.getSheared() ?
                blink ?
                        textureShearedBlink : textureSheared
                :
                blink ?
                        textureBlink : texture;
    }
}