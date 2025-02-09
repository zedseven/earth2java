package slexom.earthtojava.mobs.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CluckshroomModel<T extends Entity> extends AnimalModel<T> {
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart bill;
    private final ModelPart chin;

    public CluckshroomModel() {
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F);
        this.head.setPivot(0.0F, 15.0F, -4.0F);
        this.bill = new ModelPart(this, 14, 0);
        this.bill.addCuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F);
        this.bill.setPivot(0.0F, 15.0F, -4.0F);
        this.chin = new ModelPart(this, 14, 4);
        this.chin.addCuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F);
        this.chin.setPivot(0.0F, 15.0F, -4.0F);
        this.torso = new ModelPart(this, 0, 9);
        this.torso.addCuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F);
        this.torso.setPivot(0.0F, 16.0F, 0.0F);
        this.rightLeg = new ModelPart(this, 26, 0);
        this.rightLeg.addCuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
        this.rightLeg.setPivot(-2.0F, 19.0F, 1.0F);
        this.leftLeg = new ModelPart(this, 26, 0);
        this.leftLeg.addCuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
        this.leftLeg.setPivot(1.0F, 19.0F, 1.0F);
        this.rightWing = new ModelPart(this, 24, 13);
        this.rightWing.addCuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
        this.rightWing.setPivot(-4.0F, 13.0F, 0.0F);
        this.leftWing = new ModelPart(this, 24, 13);
        this.leftWing.addCuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
        this.leftWing.setPivot(4.0F, 13.0F, 0.0F);
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head, this.bill, this.chin);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.torso, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.pitch = headPitch * ((float) Math.PI / 180F);
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180F);
        this.bill.pitch = this.head.pitch;
        this.bill.yaw = this.head.yaw;
        this.chin.pitch = this.head.pitch;
        this.chin.yaw = this.head.yaw;
        this.torso.pitch = ((float) Math.PI / 2F);
        this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightWing.roll = ageInTicks;
        this.leftWing.roll = -ageInTicks;
    }

    public ModelPart getHead() {
        return this.head;
    }
}
