package slexom.earthtojava.mobs.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class RubyOreBlock extends OreBlock {

    public RubyOreBlock(Properties properties) {
        super(properties);
    }

    protected int getExperience(Random rand) {
        return MathHelper.nextInt(rand, 3, 7);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}
