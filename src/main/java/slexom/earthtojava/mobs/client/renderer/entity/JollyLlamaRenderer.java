package slexom.earthtojava.mobs.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import slexom.earthtojava.mobs.client.renderer.entity.model.JollyLlamaModel;
import slexom.earthtojava.mobs.entity.passive.JollyLlamaEntity;

@Environment(EnvType.CLIENT)
public class JollyLlamaRenderer extends MobEntityRenderer<JollyLlamaEntity, JollyLlamaModel> {

    public JollyLlamaRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new JollyLlamaModel(0.0F), 0.7F);
    }

    @Override
    public Identifier getTexture(JollyLlamaEntity entity) {
        String resourceTexture = "earthtojavamobs:textures/mobs/llama/jolly_llama/jolly_llama.png";
        String resourceTextureBlink = "earthtojavamobs:textures/mobs/llama/jolly_llama/jolly_llama_blink.png";
        Identifier texture = new Identifier(resourceTexture);
        Identifier textureBlink = new Identifier(resourceTextureBlink);
        return entity.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
    }

}
