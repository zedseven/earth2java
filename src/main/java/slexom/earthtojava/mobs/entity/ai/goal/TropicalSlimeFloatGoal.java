package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import slexom.earthtojava.mobs.entity.ai.controller.TropicalSlimeMovementController;
import slexom.earthtojava.mobs.entity.passive.TropicalSlimeEntity;

import java.util.EnumSet;

public class TropicalSlimeFloatGoal extends Goal {
    private final TropicalSlimeEntity slime;

    public TropicalSlimeFloatGoal(TropicalSlimeEntity slimeIn) {
        this.slime = slimeIn;
        this.setMutexFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        slimeIn.getNavigator().setCanSwim(true);
    }

    public boolean shouldExecute() {
        return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveHelper() instanceof TropicalSlimeMovementController;
    }

    public void tick() {
        if (this.slime.getRNG().nextFloat() < 0.8F) {
            this.slime.getJumpController().setJumping();
        }
        ((TropicalSlimeMovementController) this.slime.getMoveHelper()).setSpeed(1.2D);
    }
}
