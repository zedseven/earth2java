package slexom.earthtojava.mobs.entity.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import slexom.earthtojava.mobs.entity.base.E2JBaseCowEntity;

public class WoolyCowEntity extends E2JBaseCowEntity<WoolyCowEntity> implements Shearable {

    private static final TrackedData<Byte> isSheared = DataTracker.registerData(WoolyCowEntity.class, TrackedDataHandlerRegistry.BYTE);

    private int shearTimer;
    private EatGrassGoal eatGrassGoal;

    public WoolyCowEntity(EntityType<WoolyCowEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(5, this.eatGrassGoal);
    }

    protected void mobTick() {
        this.shearTimer = this.eatGrassGoal.getTimer();
        super.mobTick();
    }

    public void tickMovement() {
        if (this.world.isClient) {
            this.shearTimer = Math.max(0, this.shearTimer - 1);
        }
        super.tickMovement();
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte id) {
        if (id == 10) {
            this.shearTimer = 40;
        } else {
            super.handleStatus(id);
        }
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(isSheared, (byte) 0);
    }

    public boolean isSheared() {
        return (this.dataTracker.get(isSheared) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b0 = this.dataTracker.get(isSheared);
        if (sheared) {
            this.dataTracker.set(isSheared, (byte) (b0 | 16));
        } else {
            this.dataTracker.set(isSheared, (byte) (b0 & -17));
        }
    }

    public void onEatingGrass() {
        this.setSheared(false);
        if (this.isBaby()) {
            this.growUp(30);
        }
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SHEARS) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                itemStack.damage(1, (LivingEntity) player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }


    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem((ItemConvertible) Blocks.BROWN_WOOL, 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double) (this.random.nextFloat() * 0.05F), (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
            }
        }

    }

    public boolean isShearable() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        compound.putBoolean("Sheared", this.isSheared());
    }

    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        this.setSheared(compound.getBoolean("Sheared"));
    }

}
