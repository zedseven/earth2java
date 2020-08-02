package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import slexom.earthtojava.mobs.entity.passive.FurnaceGolemEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public final class FurnaceGolemNearestAttackableTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    FurnaceGolemEntity golem;

    public FurnaceGolemNearestAttackableTargetGoal(FurnaceGolemEntity entity, Class targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, @Nullable Predicate targetPredicate) {
        super(entity, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        this.golem = entity;
    }

    public void startExecuting() {
        this.golem.setAngry(true);
        super.startExecuting();
    }

    public void resetTask() {
        this.golem.setAngry(false);
        super.resetTask();
    }
}
