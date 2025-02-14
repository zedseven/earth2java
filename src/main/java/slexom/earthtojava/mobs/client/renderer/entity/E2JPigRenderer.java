package slexom.earthtojava.mobs.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import slexom.earthtojava.mobs.client.renderer.entity.feature.E2JBasePigSaddleLayer;
import slexom.earthtojava.mobs.entity.base.E2JBasePigEntity;

import java.text.MessageFormat;

@Environment(EnvType.CLIENT)
public class E2JPigRenderer extends MobEntityRenderer<E2JBasePigEntity<? extends PigEntity>, PigEntityModel<E2JBasePigEntity<? extends PigEntity>>> {

    private final String registryName;

    public E2JPigRenderer(EntityRenderDispatcher renderManagerIn, String registryName) {
        super(renderManagerIn, new PigEntityModel<>(), 0.7F);
        this.addFeature(new E2JBasePigSaddleLayer(this));
        this.registryName = registryName;
    }

    @Override
    public Identifier getTexture(E2JBasePigEntity<? extends PigEntity> entity) {
        String resourceTexture = MessageFormat.format("earthtojavamobs:textures/mobs/pig/{0}/{0}.png", this.registryName);
        String resourceTextureBlink = MessageFormat.format("earthtojavamobs:textures/mobs/pig/{0}/{0}_blink.png", this.registryName);
        Identifier texture = new Identifier(resourceTexture);
        Identifier textureBlink = new Identifier(resourceTextureBlink);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }

}
