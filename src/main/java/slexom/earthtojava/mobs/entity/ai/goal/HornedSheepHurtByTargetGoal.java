package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import slexom.earthtojava.mobs.entity.passive.HornedSheepEntity;

public class HornedSheepHurtByTargetGoal extends HurtByTargetGoal {

    public HornedSheepHurtByTargetGoal(HornedSheepEntity sheepIn) {
        super(sheepIn);
    }

    protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
        if (mobIn instanceof HornedSheepEntity && this.goalOwner.canEntityBeSeen(targetIn) && ((HornedSheepEntity) mobIn).setSheepAttacker(targetIn)) {
            mobIn.setAttackTarget(targetIn);
        }
    }
}
