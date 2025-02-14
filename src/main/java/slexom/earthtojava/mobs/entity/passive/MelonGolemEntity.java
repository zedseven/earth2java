package slexom.earthtojava.mobs.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import slexom.earthtojava.mobs.entity.ai.control.MelonGolemMoveControl;
import slexom.earthtojava.mobs.entity.ai.goal.MelonGolemFaceRandomGoal;
import slexom.earthtojava.mobs.entity.ai.goal.MelonGolemHopGoal;
import slexom.earthtojava.mobs.entity.ai.goal.MelonGolemProjectileAttackGoal;
import slexom.earthtojava.mobs.entity.projectile.MelonSeedProjectileEntity;

import javax.annotation.Nullable;
import java.util.Random;

public class MelonGolemEntity extends GolemEntity implements RangedAttackMob, Shearable {
    private static final TrackedData<Byte> MELON_EQUIPPED = DataTracker.registerData(MelonGolemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Integer> SHOOTING_TICKS = DataTracker.registerData(MelonGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private int lastBlink = 0;
    private int nextBlinkInterval = new Random().nextInt(740) + 60;
    private int remainingTick = 0;
    private int internalBlinkTick = 0;

    public MelonGolemEntity(EntityType<? extends MelonGolemEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new MelonGolemMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder createMelonGolemAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new MelonGolemProjectileAttackGoal(this, 1.25D, 20, 10.0F));
        this.goalSelector.add(2, new MelonGolemFaceRandomGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new MelonGolemHopGoal(this));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, MobEntity.class, 10, true, false, (entity) -> entity instanceof Monster && !(entity instanceof TropicalSlimeEntity)));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MELON_EQUIPPED, (byte) 16);
        this.dataTracker.startTracking(SHOOTING_TICKS, 0);
    }

    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        compound.putBoolean("Pumpkin", this.isMelonEquipped());
    }

    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        if (compound.contains("Pumpkin")) {
            this.setMelonEquipped(compound.getBoolean("Pumpkin"));
        }

    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {
            int i = MathHelper.floor(this.getX());
            int j = MathHelper.floor(this.getY());
            int k = MathHelper.floor(this.getZ());
            if (this.isInsideWaterOrBubbleColumn()) {
                this.damage(DamageSource.DROWN, 1.0F);
            }
            if (this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F) {
                this.damage(DamageSource.ON_FIRE, 1.0F);
            }

            BlockState blockState = Blocks.SNOW.getDefaultState();
            for (int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.getY());
                k = MathHelper.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos = new BlockPos(i, j, k);
                if (this.world.getBlockState(blockPos).isAir() && this.world.getBiome(blockPos).getTemperature(blockPos) < 0.8F && blockState.canPlaceAt(this.world, blockPos)) {
                    this.world.setBlockState(blockPos, blockState);
                }
            }
        }
        int currentShootingTicks = this.dataTracker.get(SHOOTING_TICKS);
        if (currentShootingTicks > 0) {
            this.dataTracker.set(SHOOTING_TICKS, --currentShootingTicks);
        }
        if (this.remainingTick > 0) {
            --this.remainingTick;
        }
        if (this.internalBlinkTick == (this.lastBlink + this.nextBlinkInterval)) {
            this.lastBlink = this.internalBlinkTick;
            this.nextBlinkInterval = new Random().nextInt(740) + 60;
            this.remainingTick = 4;
        }
        ++this.internalBlinkTick;
    }

    public int getBlinkRemainingTicks() {
        return this.remainingTick;
    }

    public boolean isShooting() {
        return this.dataTracker.get(SHOOTING_TICKS) > 0;
    }

    public void setShootingTicks() {
        this.dataTracker.set(SHOOTING_TICKS, 8);
    }

    public void attack(LivingEntity target, float distanceFactor) {
        this.setShootingTicks();
        MelonSeedProjectileEntity melonSeedEntity = new MelonSeedProjectileEntity(this.world, this);
        double d0 = target.getEyeY() - (double) 1.1F;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - melonSeedEntity.getY();
        double d3 = target.getZ() - this.getZ();
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        melonSeedEntity.setVelocity(d1, d2 + (double) f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(melonSeedEntity);
    }

    public float getEyeHeight(EntityPose poseIn) {
        return 1.7F;
    }

    public boolean isMelonEquipped() {
        return (this.dataTracker.get(MELON_EQUIPPED) & 16) != 0;
    }

    public void setMelonEquipped(boolean melonEquipped) {
        byte b0 = this.dataTracker.get(MELON_EQUIPPED);
        if (melonEquipped) {
            this.dataTracker.set(MELON_EQUIPPED, (byte) (b0 | 16));
        } else {
            this.dataTracker.set(MELON_EQUIPPED, (byte) (b0 & -17));
        }
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
    }

    public int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SHEARS) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                itemStack.damage(1, player, ((playerEntity) -> playerEntity.sendToolBreakStatus(hand)));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public boolean isShearable() {
        return this.isMelonEquipped();
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setMelonEquipped(false);
    }

}
