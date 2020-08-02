package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.DefendVillageTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import slexom.earthtojava.mobs.entity.passive.FurnaceGolemEntity;

import java.util.EnumSet;

public class FurnaceGolemDefendVillageTargetGoal extends DefendVillageTargetGoal {
    private final FurnaceGolemEntity golem;
    private LivingEntity villageAgressorTarget;

    public FurnaceGolemDefendVillageTargetGoal(FurnaceGolemEntity ironGolemIn) {
        super(ironGolemIn);
        this.golem = ironGolemIn;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public void startExecuting() {
        this.golem.setAngry(true);
        this.golem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }

    public void resetTask() {
        this.golem.setAngry(false);
        super.resetTask();
    }
}
