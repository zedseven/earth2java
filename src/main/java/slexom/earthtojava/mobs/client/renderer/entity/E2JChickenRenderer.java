package slexom.earthtojava.mobs.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import slexom.earthtojava.mobs.entity.base.E2JBaseChickenEntity;

import java.text.MessageFormat;

@Environment(EnvType.CLIENT)
public class E2JChickenRenderer extends MobEntityRenderer<E2JBaseChickenEntity<? extends ChickenEntity>, ChickenEntityModel<E2JBaseChickenEntity<? extends ChickenEntity>>> {
    private final String registryName;

    public E2JChickenRenderer(EntityRenderDispatcher renderManagerIn, String registryName) {
        super(renderManagerIn, new ChickenEntityModel<>(), 0.3F);
        this.registryName = registryName;
    }

    protected float getAnimationProgress(E2JBaseChickenEntity<? extends ChickenEntity> chickenEntity, float f) {
        float g = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
        float h = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }

    public Identifier getTexture(E2JBaseChickenEntity<? extends ChickenEntity> entity) {
        String resourceTexture = MessageFormat.format("earthtojavamobs:textures/mobs/chicken/{0}/{0}.png", this.registryName);
        String resourceTextureBlink = MessageFormat.format("earthtojavamobs:textures/mobs/chicken/{0}/{0}_blink.png", this.registryName);
        Identifier texture = new Identifier(resourceTexture);
        Identifier textureBlink = new Identifier(resourceTextureBlink);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }


}
