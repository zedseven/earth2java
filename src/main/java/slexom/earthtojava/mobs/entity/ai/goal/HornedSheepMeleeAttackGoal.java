package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import slexom.earthtojava.mobs.entity.passive.HornedSheepEntity;

public class HornedSheepMeleeAttackGoal extends MeleeAttackGoal {

    private final HornedSheepEntity hornedSheepEntity;

    public HornedSheepMeleeAttackGoal(HornedSheepEntity hornedSheepEntity, HornedSheepEntity creatureIn, double speedIn, boolean useLongMemory) {
        super(creatureIn, speedIn, useLongMemory);
        this.hornedSheepEntity = hornedSheepEntity;
    }

    public boolean shouldExecute() {
        return super.shouldExecute() && hornedSheepEntity.func_233678_J__();
    }

    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && hornedSheepEntity.func_233678_J__();
    }
}
