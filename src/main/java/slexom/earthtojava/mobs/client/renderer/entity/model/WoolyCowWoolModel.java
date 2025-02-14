package slexom.earthtojava.mobs.client.renderer.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import slexom.earthtojava.mobs.entity.passive.WoolyCowEntity;

@Environment(EnvType.CLIENT)
public class WoolyCowWoolModel<T extends WoolyCowEntity> extends QuadrupedEntityModel<T> {
    private float headRotationAngleX;

    public WoolyCowWoolModel() {
        super(12, 0.0F, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F);
        this.head.setPivot(0.0F, 4.0F, -8.0F);
        this.head.setTextureOffset(22, 0).addCuboid(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
        this.head.setTextureOffset(22, 0).addCuboid(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F);
        this.torso = new ModelPart(this, 18, 4);
        this.torso.addCuboid(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F);
        this.torso.setPivot(0.0F, 5.0F, 2.0F);
        this.torso.setTextureOffset(52, 0).addCuboid(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F);
        --this.backRightLeg.pivotX;
        ++this.backLeftLeg.pivotX;
        this.backRightLeg.pivotZ += 0.0F;
        this.backLeftLeg.pivotZ += 0.0F;
        --this.frontRightLeg.pivotX;
        ++this.frontLeftLeg.pivotX;
        --this.frontRightLeg.pivotZ;
        --this.frontLeftLeg.pivotZ;
    }

    public void animateModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.animateModel(entityIn, limbSwing, limbSwingAmount, partialTick);
//        this.head.pivotY= 6.0F + entityIn.getHeadRotationPointY(partialTick) * 9.0F;
//        this.headRotationAngleX = entityIn.getHeadRotationAngleX(partialTick);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.pitch = this.headRotationAngleX;
    }
}