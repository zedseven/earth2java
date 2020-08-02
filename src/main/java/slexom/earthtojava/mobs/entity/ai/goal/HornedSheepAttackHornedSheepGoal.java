package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import slexom.earthtojava.mobs.entity.passive.HornedSheepEntity;

public class HornedSheepAttackHornedSheepGoal extends NearestAttackableTargetGoal<HornedSheepEntity> {
    public HornedSheepAttackHornedSheepGoal(HornedSheepEntity sheepEntity) {
        super(sheepEntity, HornedSheepEntity.class, 10, true, true, sheepEntity::func_233680_b_);
    }

    public boolean shouldExecute() {
        LivingEntity target = this.goalOwner.getAttackTarget();
        if (target != null) {
            if (target.isChild()) {
                return false;
            }
        }
        boolean chance = this.goalOwner.getRNG().nextInt(100) < 15;
        return chance && super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        boolean chanceToStop = this.goalOwner.getRNG().nextInt(100) < 5;
        if (this.goalOwner.getAttackTarget() != null) {
            return super.shouldContinueExecuting();
        } else if (chanceToStop) {
            return false;
        } else {
            this.target = null;
            return false;
        }
    }

}
