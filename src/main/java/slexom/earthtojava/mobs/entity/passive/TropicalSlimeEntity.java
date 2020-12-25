
package slexom.earthtojava.mobs.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import slexom.earthtojava.mobs.entity.ai.controller.TropicalSlimeMovementController;
import slexom.earthtojava.mobs.entity.ai.goal.TropicalSlimeAttackGoal;
import slexom.earthtojava.mobs.entity.ai.goal.TropicalSlimeFaceRandomGoal;
import slexom.earthtojava.mobs.entity.ai.goal.TropicalSlimeFloatGoal;
import slexom.earthtojava.mobs.entity.ai.goal.TropicalSlimeHopGoal;

public class TropicalSlimeEntity extends MobEntity implements IMob {

    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;
    private final int size;

    public TropicalSlimeEntity(EntityType<TropicalSlimeEntity> type, World world) {
        super(type, world);
        this.size = 4;
        experienceValue = this.size;
        setNoAI(false);
        this.moveController = new TropicalSlimeMovementController(this);
        this.setAttributes();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TropicalSlimeFloatGoal(this));
        this.goalSelector.addGoal(2, new TropicalSlimeAttackGoal(this));
        this.goalSelector.addGoal(3, new TropicalSlimeFaceRandomGoal(this));
        this.goalSelector.addGoal(5, new TropicalSlimeHopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213811_1_) -> Math.abs(p_213811_1_.getPosY() - this.getPosY()) <= 4.0D));
    }

    private void setAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.BUCKET && !player.abilities.isCreativeMode && !this.isChild()) {
            if (!this.world.isRemote) {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 0.0D, 0.0D, 0.0D);
                this.remove();
                player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, 1.0F);
                giveTropicalFishBucket(player, hand, itemstack);
                spawnWater();
                return ActionResultType.func_233537_a_(this.world.isRemote);
            } else {
                return super.func_230254_b_(player, hand);
            }
        } else {
            return super.func_230254_b_(player, hand);
        }
    }

    private void giveTropicalFishBucket(PlayerEntity player, Hand hand, ItemStack itemstack) {
        itemstack.shrink(1);
        if (!player.inventory.addItemStackToInventory(new ItemStack(Items.TROPICAL_FISH_BUCKET))) {
            player.dropItem(new ItemStack(Items.TROPICAL_FISH_BUCKET), false);
        }
    }

    private void spawnWater() {
        int x = MathHelper.floor(this.getPosX());
        int y = MathHelper.floor(this.getPosY());
        int z = MathHelper.floor(this.getPosZ());
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState waterState = Blocks.WATER.getDefaultState();
        this.world.destroyBlock(blockPos, false);
        this.world.setBlockState(blockPos, waterState, 3);
    }

    protected IParticleData getSquishParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    public void tick() {
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            int i = this.size;
            if (spawnCustomParticles()) i = 0; // don't spawn particles if it's handled by the implementation itself
            for (int j = 0; j < i * 8; ++j) {
                float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
                float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float) i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float) i * 0.5F * f1;
                this.world.addParticle(this.getSquishParticle(), this.getPosX() + (double) f2, this.getPosY(), this.getPosZ() + (double) f3, 0.0D, 0.0D, 0.0D);
            }
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.squishAmount = -0.5F;
        } else if (!this.onGround && this.wasOnGround) {
            this.squishAmount = 1.0F;
        }
        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    public int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }

    public EntityType<? extends TropicalSlimeEntity> getType() {
        return (EntityType<? extends TropicalSlimeEntity>) super.getType();
    }

    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);
        if (entityIn instanceof IronGolemEntity && this.canDamagePlayer()) {
            this.dealDamage((LivingEntity) entityIn);
        }
    }

    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (this.canDamagePlayer()) {
            this.dealDamage(entityIn);
        }

    }

    protected void dealDamage(LivingEntity entityIn) {
        if (this.isAlive()) {
            int i = this.size;
            if (this.getDistanceSq(entityIn) < 0.6D * (double) i * 0.6D * (double) i && this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), this.func_225512_er_())) {
                this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.applyEnchantments(this, entityIn);
            }
        }

    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.625F * sizeIn.height;
    }

    public boolean canDamagePlayer() {
        return this.isServerWorld();
    }

    protected float func_225512_er_() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.ENTITY_SLIME_SQUISH;
    }

    protected ResourceLocation getLootTable() {
        return this.getType().getLootTable();
    }

    public int getVerticalFaceSpeed() {
        return 0;
    }

    public boolean makesSoundOnJump() {
        return true;
    }

    protected void jump() {
        Vector3d vec3d = this.getMotion();
        this.setMotion(vec3d.x, (double) this.getJumpUpwardsMotion(), vec3d.z);
        this.isAirBorne = true;
    }

    public SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    protected boolean spawnCustomParticles() {
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
 
