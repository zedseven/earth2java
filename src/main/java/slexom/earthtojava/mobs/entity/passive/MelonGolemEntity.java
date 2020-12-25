package slexom.earthtojava.mobs.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import slexom.earthtojava.mobs.entity.ai.controller.MelonGolemMovementController;
import slexom.earthtojava.mobs.entity.ai.goal.MelonGolemHopGoal;
import slexom.earthtojava.mobs.entity.ai.goal.MelonGolemRangedAttackGoal;
import slexom.earthtojava.mobs.entity.projectile.MelonSeedProjectileEntity;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class MelonGolemEntity extends GolemEntity implements IRangedAttackMob, IForgeShearable {
    private static final DataParameter<Byte> MELON_EQUIPPED = EntityDataManager.createKey(MelonGolemEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> SHOOTING_TICKS = EntityDataManager.createKey(MelonGolemEntity.class, DataSerializers.VARINT);
    private int lastBlink = 0;
    private int nextBlinkInterval = new Random().nextInt(740) + 60;
    private int remainingTick = 0;
    private int internalBlinkTick = 0;

    public MelonGolemEntity(EntityType<? extends MelonGolemEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new MelonGolemMovementController(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MelonGolemRangedAttackGoal(this, 1.25D, 20, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1.0000001E-5F));
//        this.goalSelector.addGoal(2, new MelonGolemEntity.FaceRandomGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new MelonGolemHopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MobEntity.class, 10, true, false, (entity) -> entity instanceof IMob && !(entity instanceof TropicalSlimeEntity)));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(MELON_EQUIPPED, (byte) 16);
        this.dataManager.register(SHOOTING_TICKS, 0);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Pumpkin", this.isMelonEquipped());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Pumpkin")) {
            this.setMelonEquipped(compound.getBoolean("Pumpkin"));
        }

    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            int i = MathHelper.floor(this.getPosX());
            int j = MathHelper.floor(this.getPosY());
            int k = MathHelper.floor(this.getPosZ());
            if (this.isInWaterRainOrBubbleColumn()) {
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }
            if (this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F) {
                this.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                return;
            }
            BlockState blockstate = Blocks.SNOW.getDefaultState();
            for (int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.getPosX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.getPosY());
                k = MathHelper.floor(this.getPosZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);
                if (this.world.isAirBlock(blockpos) && this.world.getBiome(blockpos).getTemperature(blockpos) < 0.8F && blockstate.isValidPosition(this.world, blockpos)) {
                    this.world.setBlockState(blockpos, blockstate);
                }
            }
        }
        int currentShootingTicks = this.dataManager.get(SHOOTING_TICKS);
        if (currentShootingTicks > 0) {
            this.dataManager.set(SHOOTING_TICKS, --currentShootingTicks);
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
        return this.dataManager.get(SHOOTING_TICKS) > 0;
    }

    public void setShootingTicks() {
        this.dataManager.set(SHOOTING_TICKS, 8);
    }

    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        this.setShootingTicks();
        MelonSeedProjectileEntity melonSeedEntity = new MelonSeedProjectileEntity(this.world, this);
        double d0 = target.getPosYEye() - (double) 1.1F;
        double d1 = target.getPosX() - this.getPosX();
        double d2 = d0 - melonSeedEntity.getPosY();
        double d3 = target.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        melonSeedEntity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(melonSeedEntity);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.7F;
    }

    public boolean isMelonEquipped() {
        return (this.dataManager.get(MELON_EQUIPPED) & 16) != 0;
    }

    public void setMelonEquipped(boolean melonEquipped) {
        byte b0 = this.dataManager.get(MELON_EQUIPPED);
        if (melonEquipped) {
            this.dataManager.set(MELON_EQUIPPED, (byte) (b0 | 16));
        } else {
            this.dataManager.set(MELON_EQUIPPED, (byte) (b0 & -17));
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
        return this.rand.nextInt(20) + 10;
    }

    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return this.isMelonEquipped();
    }

    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        this.setMelonEquipped(false);
        return new java.util.ArrayList<>();
    }

    static class FaceRandomGoal extends Goal {
        private final MelonGolemEntity melonGolem;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FaceRandomGoal(MelonGolemEntity melonGolem) {
            this.melonGolem = melonGolem;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean shouldExecute() {
            return this.melonGolem.getAttackTarget() == null && (this.melonGolem.onGround || this.melonGolem.isInWater() || this.melonGolem.isInLava() || this.melonGolem.isPotionActive(Effects.LEVITATION)) && this.melonGolem.getMoveHelper() instanceof MelonGolemMovementController;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 40 + this.melonGolem.getRNG().nextInt(60);
                this.chosenDegrees = (float) this.melonGolem.getRNG().nextInt(360);
            }
            ((MelonGolemMovementController) this.melonGolem.getMoveHelper()).setDirection(this.chosenDegrees, false);
        }
    }
}
