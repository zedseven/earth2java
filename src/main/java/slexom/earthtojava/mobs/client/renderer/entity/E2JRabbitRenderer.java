package slexom.earthtojava.mobs.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.RabbitEntityModel;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import slexom.earthtojava.mobs.entity.base.E2JBaseRabbitEntity;

import java.text.MessageFormat;

@Environment(EnvType.CLIENT)
public class E2JRabbitRenderer extends MobEntityRenderer<E2JBaseRabbitEntity<? extends RabbitEntity>, RabbitEntityModel<E2JBaseRabbitEntity<? extends RabbitEntity>>> {

    private final String registryName;

    public E2JRabbitRenderer(EntityRenderDispatcher renderManagerIn, String registryName) {
        super(renderManagerIn, new RabbitEntityModel<>(), 0.3F);
        this.registryName = registryName;
    }

    @Override
    public Identifier getTexture(E2JBaseRabbitEntity<? extends RabbitEntity> entity) {
        String resourceTexture = MessageFormat.format("earthtojavamobs:textures/mobs/rabbit/{0}/{0}.png", this.registryName);
        String resourceTextureBlink = MessageFormat.format("earthtojavamobs:textures/mobs/rabbit/{0}/{0}_blink.png", this.registryName);
        Identifier texture = new Identifier(resourceTexture);
        Identifier textureBlink = new Identifier(resourceTextureBlink);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }

}
