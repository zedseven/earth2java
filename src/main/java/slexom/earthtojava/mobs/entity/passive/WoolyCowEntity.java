package slexom.earthtojava.mobs.entity.passive;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fml.network.NetworkHooks;
import slexom.earthtojava.mobs.entity.base.E2JBaseCowEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WoolyCowEntity extends E2JBaseCowEntity<WoolyCowEntity> implements IForgeShearable {

    private static final DataParameter<Boolean> isSheared = EntityDataManager.createKey(WoolyCowEntity.class, DataSerializers.BOOLEAN);

    private int shearTimer;
    private EatGrassGoal eatGrassGoal;

    public WoolyCowEntity(EntityType<WoolyCowEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(5, this.eatGrassGoal);
    }

    protected void updateAITasks() {
        this.shearTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.shearTimer = Math.max(0, this.shearTimer - 1);
        }
        super.livingTick();
    }

    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.shearTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(isSheared, false);
    }

    public boolean getSheared() {
        return this.dataManager.get(isSheared);
    }

    public void setSheared(boolean sheared) {
        this.dataManager.set(isSheared, sheared);
//        byte b0 = this.dataManager.get(isSheared);
//        if (sheared) {
//            this.dataManager.set(isSheared, (byte) (b0 | 16));
//        } else {
//            this.dataManager.set(isSheared, (byte) (b0 & -17));
//        }
    }

    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(30);
        }
    }

    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return this.isAlive() && !this.getSheared() && !this.isChild();
    }

    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!this.world.isRemote) {
            this.setSheared(true);
            List<ItemStack> items = new ArrayList<>();
            int i = 1 + this.rand.nextInt(3);
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(Blocks.BROWN_WOOL));
            }
            return items;
        }
        return Collections.emptyList();
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Sheared", this.getSheared());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSheared(compound.getBoolean("Sheared"));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
