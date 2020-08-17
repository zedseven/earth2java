package slexom.earthtojava.mobs.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slexom.earthtojava.mobs.entity.ai.goal.JollyLlamaEatFernGoal;
import slexom.earthtojava.mobs.entity.base.E2JBaseLlamaEntity;

import javax.annotation.Nullable;

public class JollyLlamaEntity extends E2JBaseLlamaEntity<JollyLlamaEntity> {

    private int llamaTimer;
    private JollyLlamaEatFernGoal eatFernGoal;

    public JollyLlamaEntity(EntityType<JollyLlamaEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public boolean func_230277_fr_() {
        return false;
    }

    @Override
    public boolean isArmor(ItemStack stack) {
        return false;
    }

    protected void registerGoals() {
        this.eatFernGoal = new JollyLlamaEatFernGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new LlamaFollowCaravanGoal(this, (double) 2.1F));
      //  this.goalSelector.addGoal(3, this.eatFernGoal);
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    protected void updateAITasks() {
        this.llamaTimer = this.eatFernGoal.getEatingFernTimer();
        super.updateAITasks();
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.llamaTimer = Math.max(0, this.llamaTimer - 1);
        }
        super.livingTick();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.llamaTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.llamaTimer <= 0) {
            return 0.0F;
        } else if (this.llamaTimer >= 4 && this.llamaTimer <= 36) {
            return 1.0F;
        } else {
            return this.llamaTimer < 4 ? ((float) this.llamaTimer - p_70894_1_) / 4.0F : -((float) (this.llamaTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.llamaTimer > 4 && this.llamaTimer <= 36) {
            float f = ((float) (this.llamaTimer - 4) - p_70890_1_) / 32.0F;
            return ((float) Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.llamaTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch * ((float) Math.PI / 180F);
        }
    }

    public void eatGrassBonus() {
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    @Override
    @Nullable
    public DyeColor getColor() {
        return null;
    }

}
