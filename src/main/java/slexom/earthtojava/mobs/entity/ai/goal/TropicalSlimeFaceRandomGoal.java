package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.potion.Effects;
import slexom.earthtojava.mobs.entity.ai.controller.TropicalSlimeMovementController;
import slexom.earthtojava.mobs.entity.passive.TropicalSlimeEntity;

import java.util.EnumSet;

public class TropicalSlimeFaceRandomGoal extends Goal {
    private final TropicalSlimeEntity slime;
    private float chosenDegrees;
    private int nextRandomizeTime;

    public TropicalSlimeFaceRandomGoal(TropicalSlimeEntity slimeIn) {
        this.slime = slimeIn;
        this.setMutexFlags(EnumSet.of(Flag.LOOK));
    }

    public boolean shouldExecute() {
        return this.slime.getAttackTarget() == null && (this.slime.func_233570_aj_() || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(Effects.LEVITATION)) && this.slime.getMoveHelper() instanceof TropicalSlimeMovementController;
    }

    public void tick() {
        if (--this.nextRandomizeTime <= 0) {
            this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
            this.chosenDegrees = (float) this.slime.getRNG().nextInt(360);
        }
        ((TropicalSlimeMovementController) this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
    }

}
