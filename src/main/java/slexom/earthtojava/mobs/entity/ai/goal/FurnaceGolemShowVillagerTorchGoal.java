package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import slexom.earthtojava.mobs.entity.passive.FurnaceGolemEntity;

import java.util.EnumSet;

public class FurnaceGolemShowVillagerTorchGoal extends Goal {
    private final EntityPredicate field_220738_a = (new EntityPredicate()).setDistance(6.0D).allowFriendlyFire().allowInvulnerable();
    private final FurnaceGolemEntity furnaceGolem;
    private VillagerEntity villager;
    private int lookTime;

    public FurnaceGolemShowVillagerTorchGoal(FurnaceGolemEntity ironGolemIn) {
        this.furnaceGolem = ironGolemIn;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean shouldExecute() {
        if (!this.furnaceGolem.world.isDaytime()) {
            return false;
        } else if (this.furnaceGolem.getRNG().nextInt(8000) != 0) {
            return false;
        } else {
            this.villager = this.furnaceGolem.world.getClosestEntityWithinAABB(VillagerEntity.class, field_220738_a, this.furnaceGolem, this.furnaceGolem.getPosX(), this.furnaceGolem.getPosY(), this.furnaceGolem.getPosZ(), this.furnaceGolem.getBoundingBox().grow(6.0D, 2.0D, 6.0D));
            return this.villager != null;
        }
    }

    public boolean shouldContinueExecuting() {
        return this.lookTime > 0;
    }

    public void startExecuting() {
        this.lookTime = 400;
        this.furnaceGolem.setHoldingTorch(true);
    }

    public void resetTask() {
        this.furnaceGolem.setHoldingTorch(false);
        this.villager = null;
    }

    public void tick() {
        this.furnaceGolem.getLookController().setLookPositionWithEntity(this.villager, 30.0F, 30.0F);
        --this.lookTime;
    }
}
