package slexom.earthtojava.mobs.entity.ai.controller;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import slexom.earthtojava.mobs.entity.passive.MelonGolemEntity;

public class MelonGolemMovementController extends MovementController {
    private float yRot;
    private int jumpDelay;
    private final MelonGolemEntity melonGolem;
    private boolean isAggressive;

    public MelonGolemMovementController(MelonGolemEntity entity) {
        super(entity);
        this.melonGolem = entity;
        this.yRot = 180.0F * entity.rotationYaw / (float) Math.PI;
    }

    public void setDirection(float yRotIn, boolean aggressive) {
        this.yRot = yRotIn;
        this.isAggressive = aggressive;
    }

    public void setSpeed(double speedIn) {
        this.speed = speedIn;
        this.action = Action.MOVE_TO;
    }

    public void tick() {
        this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.yRot, 90.0F);
        this.mob.rotationYawHead = this.mob.rotationYaw;
        this.mob.renderYawOffset = this.mob.rotationYaw;
        if (this.action != Action.MOVE_TO) {
            this.mob.setMoveForward(0.0F);
        } else {
            this.action = Action.WAIT;
            if (this.mob.func_233570_aj_()) {
                this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = this.melonGolem.getJumpDelay();
                    if (this.isAggressive) {
                        this.jumpDelay /= 3;
                    }
                    this.melonGolem.getJumpController().setJumping();

                } else {
                    this.melonGolem.moveStrafing = 0.0F;
                    this.melonGolem.moveForward = 0.0F;
                    this.mob.setAIMoveSpeed(0.0F);
                }
            } else {
                this.mob.setAIMoveSpeed((float) (this.speed * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));
            }

        }
    }
}
