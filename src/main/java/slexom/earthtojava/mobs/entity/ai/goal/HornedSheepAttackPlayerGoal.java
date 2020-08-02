package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import slexom.earthtojava.mobs.entity.passive.HornedSheepEntity;

public class HornedSheepAttackPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
    public HornedSheepAttackPlayerGoal(HornedSheepEntity sheepEntity) {
        super(sheepEntity, PlayerEntity.class, 10, true, false, sheepEntity::func_233680_b_);
    }

    public boolean shouldExecute() {
        return this.canCharge() && super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        boolean flag = this.canCharge();
        if (flag && this.goalOwner.getAttackTarget() != null) {
            return super.shouldContinueExecuting();
        } else {
            this.target = null;
            return false;
        }
    }

    private boolean canCharge() {
        HornedSheepEntity sheepEntity = (HornedSheepEntity) this.goalOwner;
        return sheepEntity.func_233678_J__();
    }
}
