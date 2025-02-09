package slexom.earthtojava.mobs.entity.ai.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import slexom.earthtojava.mobs.entity.passive.MelonGolemEntity;

public class MelonGolemMoveControl extends MoveControl {
    private final MelonGolemEntity melonGolem;
    private float targetYaw;
    private int jumpDelay;
    private boolean jumpOften;

    public MelonGolemMoveControl(MelonGolemEntity entity) {
        super(entity);
        this.melonGolem = entity;
        this.targetYaw = 180.0F * entity.yaw / (float) Math.PI;
    }

    public void setDirection(float targetYaw, boolean jumpOften) {
        this.targetYaw = targetYaw;
        this.jumpOften = jumpOften;
    }

    public void move(double speedIn) {
        this.speed = speedIn;
        this.state = State.MOVE_TO;
    }

    public void tick() {
        this.entity.yaw = this.changeAngle(this.entity.yaw, this.targetYaw, 90.0F);
        this.entity.headYaw = this.entity.yaw;
        this.entity.bodyYaw = this.entity.yaw;
        if (this.state != State.MOVE_TO) {
            this.entity.setForwardSpeed(0.0F);
        } else {
            this.state = State.WAIT;
            if (this.entity.isOnGround()) {
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = this.melonGolem.getJumpDelay();
                    if (this.jumpOften) {
                        this.jumpDelay /= 3;
                    }
                    this.melonGolem.getJumpControl().setActive();

                } else {
                    this.melonGolem.sidewaysSpeed = 0.0F;
                    this.melonGolem.forwardSpeed = 0.0F;
                    this.entity.setMovementSpeed(0.0F);
                }
            } else {
                this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            }

        }
    }
}
