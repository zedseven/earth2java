package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import slexom.earthtojava.mobs.entity.ai.controller.TropicalSlimeMovementController;
import slexom.earthtojava.mobs.entity.passive.TropicalSlimeEntity;

import java.util.EnumSet;

public class TropicalSlimeHopGoal extends Goal {
    private final TropicalSlimeEntity slime;

    public TropicalSlimeHopGoal(TropicalSlimeEntity slimeIn) {
        this.slime = slimeIn;
        this.setMutexFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    public boolean shouldExecute() {
        return !this.slime.isPassenger();
    }

    public void tick() {
        ((TropicalSlimeMovementController) this.slime.getMoveHelper()).setSpeed(1.0D);
    }
}
