package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import slexom.earthtojava.mobs.entity.passive.MoolipEntity;

public class MoolipPlaceBlockGoal extends Goal {
    private final MoolipEntity moolip;

    public MoolipPlaceBlockGoal(MoolipEntity entity) {
        this.moolip = entity;
    }

    public boolean canStart() {
        return this.moolip.getRandom().nextInt(2000) == 0;
    }

    public boolean canPlace(WorldView world, BlockState target, BlockPos targetPos, BlockState downTarget, BlockPos downTargetPos) {
        return !downTarget.isAir() && downTarget.isFullCube(world, downTargetPos) && target.isAir() && target.canPlaceAt(world, targetPos);
    }

    public void tick() {
        WorldAccess world = this.moolip.world;
        int i = MathHelper.floor(this.moolip.getX());
        int j = MathHelper.floor(this.moolip.getY());
        int k = MathHelper.floor(this.moolip.getZ());
        double random = Math.random();
        Block flower = Blocks.PEONY;
        if (random > 0.2) {
            flower = Blocks.ALLIUM;
        }
        if (random > 0.6) {
            flower = Blocks.LILAC;
        }
        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = flower.getDefaultState();
        BlockPos blockDownPos = blockPos.down();
        BlockState blockDownState = world.getBlockState(blockDownPos);
        if (canPlace(world, blockState, blockPos, blockDownState, blockDownPos)) {
            world.removeBlock(blockPos, false);
            world.setBlockState(blockPos, blockState, 3);
        }
    }
}
