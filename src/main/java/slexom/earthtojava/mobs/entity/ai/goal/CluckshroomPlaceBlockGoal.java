package slexom.earthtojava.mobs.entity.ai.goal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import slexom.earthtojava.mobs.entity.passive.CluckshroomEntity;

public class CluckshroomPlaceBlockGoal extends Goal {
    private final CluckshroomEntity cluckshroom;

    public CluckshroomPlaceBlockGoal(CluckshroomEntity p_i45843_1_) {
        this.cluckshroom = p_i45843_1_;
    }

    public boolean shouldExecute() {
        return this.cluckshroom.getRNG().nextInt(2000) == 0;
    }

    public boolean canPlace(IWorldReader world, BlockState target, BlockPos targetPos, BlockState downTarget, BlockPos downTargetPos) {
        return !downTarget.isAir(world, downTargetPos) && downTarget.isOpaqueCube(world, downTargetPos) && target.isValidPosition(world, targetPos);
    }

    public void tick() {
        World world = this.cluckshroom.world;
        int i = MathHelper.floor(this.cluckshroom.getPosX());
        int j = MathHelper.floor(this.cluckshroom.getPosY());
        int k = MathHelper.floor(this.cluckshroom.getPosZ());
        Block mushroom = Blocks.RED_MUSHROOM;
        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = mushroom.getDefaultState();
        BlockPos blockDownPos = blockPos.down();
        BlockState blockDownState = world.getBlockState(blockDownPos);
        if (canPlace(world, blockState, blockPos, blockDownState, blockDownPos) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(cluckshroom,   net.minecraftforge.common.util.BlockSnapshot.create(world.getDimensionKey(), world, blockDownPos), net.minecraft.util.Direction.UP)) {
            world.destroyBlock(blockPos, false);
            world.setBlockState(blockPos, blockState, 3);
        }
    }
}
