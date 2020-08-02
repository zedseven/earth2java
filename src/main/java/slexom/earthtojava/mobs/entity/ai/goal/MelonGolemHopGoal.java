package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import slexom.earthtojava.mobs.entity.ai.controller.MelonGolemMovementController;
import slexom.earthtojava.mobs.entity.passive.MelonGolemEntity;

import java.util.EnumSet;

public class MelonGolemHopGoal extends Goal {
    private final MelonGolemEntity melonGolem;

    public MelonGolemHopGoal(MelonGolemEntity entity) {
        this.melonGolem = entity;
        this.setMutexFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean shouldExecute() {
        return !this.melonGolem.isPassenger();
    }

    public void tick() {
        ((MelonGolemMovementController) this.melonGolem.getMoveHelper()).setSpeed(1.0D);
    }
}
