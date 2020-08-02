package slexom.earthtojava.mobs.entity.ai.controller;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;
import slexom.earthtojava.mobs.entity.passive.GlowSquidEntity;

public class GlowSquidMovementController extends MovementController {
    private final GlowSquidEntity glowSquidEntity;

    public GlowSquidMovementController(GlowSquidEntity glowSquidEntity) {
        super(glowSquidEntity);
        this.glowSquidEntity = glowSquidEntity;
    }

    @Override
    public void tick() {
        if (glowSquidEntity.areEyesInFluid(FluidTags.WATER))
            glowSquidEntity.setMotion(glowSquidEntity.getMotion().add(0, 0.005, 0));
        if (this.action == Action.MOVE_TO && !glowSquidEntity.getNavigator().noPath()) {
            double dx = this.posX - glowSquidEntity.getPosX();
            double dy = this.posY - glowSquidEntity.getPosY();
            double dz = this.posZ - glowSquidEntity.getPosZ();
            dy = dy / (double) MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
            glowSquidEntity.rotationYaw = this.limitAngle(glowSquidEntity.rotationYaw,
                    (float) (MathHelper.atan2(dz, dx) * (double) (180 / (float) Math.PI)) - 90, 90);
            glowSquidEntity.renderYawOffset = glowSquidEntity.rotationYaw;
            glowSquidEntity.setAIMoveSpeed(MathHelper.lerp(0.125f, glowSquidEntity.getAIMoveSpeed(),
                    (float) (this.speed * glowSquidEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue())));
            glowSquidEntity.setMotion(glowSquidEntity.getMotion().add(0, glowSquidEntity.getAIMoveSpeed() * dy * 0.1, 0));
        } else {
            glowSquidEntity.setAIMoveSpeed(0);
        }
    }
}
