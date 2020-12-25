package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class JollyLlamaEatFernGoal extends Goal {
    private static final Predicate<BlockState> IS_FERN = (blockState) -> blockState != null && (blockState.getBlock() == Blocks.FERN || blockState.getBlock() == Blocks.LARGE_FERN);
    private final MobEntity fernEaterEntity;
    private final World entityWorld;
    private int eatingFernTimer;

    public JollyLlamaEatFernGoal(MobEntity grassEaterEntityIn) {
        this.fernEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (this.fernEaterEntity.getRNG().nextInt(this.fernEaterEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.fernEaterEntity.getPosition();
            return IS_FERN.test(this.entityWorld.getBlockState(blockpos));
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.eatingFernTimer = 40;
        this.entityWorld.setEntityState(this.fernEaterEntity, (byte) 10);
        this.fernEaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.eatingFernTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.eatingFernTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingFernTimer() {
        return this.eatingFernTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.eatingFernTimer = Math.max(0, this.eatingFernTimer - 1);
        if (this.eatingFernTimer == 4) {
            BlockPos blockpos = this.fernEaterEntity.getPosition();
            if (IS_FERN.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.fernEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.fernEaterEntity.eatGrassBonus();
            }
        }
    }
}