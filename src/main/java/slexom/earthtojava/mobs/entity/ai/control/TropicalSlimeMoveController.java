package slexom.earthtojava.mobs.entity.ai.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import slexom.earthtojava.mobs.entity.passive.TropicalSlimeEntity;

public class TropicalSlimeMoveController extends MoveControl {
    private final TropicalSlimeEntity slime;
    private float yRot;
    private int jumpDelay;
    private boolean isAggressive;

    public TropicalSlimeMoveController(TropicalSlimeEntity slimeIn) {
        super(slimeIn);
        this.slime = slimeIn;
        this.yRot = 180.0F * slimeIn.yaw / (float) Math.PI;
    }

    public void look(float yRotIn, boolean aggressive) {
        this.yRot = yRotIn;
        this.isAggressive = aggressive;
    }

    public void move(double speedIn) {
        this.speed = speedIn;
        this.state = State.MOVE_TO;
    }

    public void tick() {
        this.entity.yaw = this.changeAngle(this.entity.yaw, this.yRot, 90.0F);
        this.entity.headYaw = this.entity.yaw;
        this.entity.bodyYaw = this.entity.yaw;
        if (this.state != State.MOVE_TO) {
            this.entity.setForwardSpeed(0.0F);
        } else {
            this.state = State.WAIT;
            if (this.entity.isOnGround()) {
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getValue()));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = this.slime.getJumpDelay();
                    if (this.isAggressive) {
                        this.jumpDelay /= 3;
                    }
                    this.slime.getJumpControl().setActive();
                    if (this.slime.makesSoundOnJump()) {
                        this.slime.playSound(this.slime.getJumpSound(), 1.0F, ((this.slime.getRandom().nextFloat() - this.slime.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    }
                } else {
                    this.slime.sidewaysSpeed = 0.0F;
                    this.slime.forwardSpeed = 0.0F;
                    this.entity.setMovementSpeed(0.0F);
                }
            } else {
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getValue()));
            }
        }
    }

}
