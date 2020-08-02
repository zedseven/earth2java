package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import slexom.earthtojava.mobs.entity.passive.MuddyPigEntity;
import slexom.earthtojava.mobs.init.BlockInit;

public class MoveToMudGoal extends MoveToBlockGoal {
    private final MuddyPigEntity muddyPig;

    public MoveToMudGoal(MuddyPigEntity entity, double speedIn) {
        super(entity, speedIn, 16, 3);
        this.muddyPig = entity;
        this.field_203112_e = -1;
    }

    public boolean shouldExecute() {
        return !this.muddyPig.isInMuddyState() && super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        return !this.muddyPig.isInMuddyState() && this.timeoutCounter <= 600 && this.shouldMoveTo(this.muddyPig.world, this.destinationBlock);
    }

    public boolean shouldMove() {
        return this.timeoutCounter % 100 == 0;
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).isIn(BlockInit.MUD_BLOCK.get());
    }
}
