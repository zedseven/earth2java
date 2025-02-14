package slexom.earthtojava.mobs.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import slexom.earthtojava.mobs.client.renderer.entity.feature.E2JMonoColorSheepWoolFeature;
import slexom.earthtojava.mobs.entity.base.E2JBaseMonoColorSheepEntity;

import java.text.MessageFormat;

@Environment(EnvType.CLIENT)
public class E2JMonoColorSheepRenderer extends MobEntityRenderer<E2JBaseMonoColorSheepEntity, SheepEntityModel<E2JBaseMonoColorSheepEntity>> {

    private final String registryName;

    public E2JMonoColorSheepRenderer(EntityRenderDispatcher entityRenderDispatcher, String registryName) {
        super(entityRenderDispatcher, new SheepEntityModel(), 0.7F);
        this.registryName = registryName;
        String woolTexture = MessageFormat.format("earthtojavamobs:textures/mobs/sheep/{0}/{0}_fur.png", this.registryName, this.registryName);
        this.addFeature(new E2JMonoColorSheepWoolFeature(this, woolTexture));
    }

    public Identifier getTexture(E2JBaseMonoColorSheepEntity entity) {
        String textureString = MessageFormat.format("earthtojavamobs:textures/mobs/sheep/{0}/{0}.png", this.registryName, this.registryName);
        String textureBlinkString = MessageFormat.format("earthtojavamobs:textures/mobs/sheep/{0}/{0}_blink.png", this.registryName, this.registryName);
        Identifier texture = new Identifier(textureString);
        Identifier textureBlink = new Identifier(textureBlinkString);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }
}