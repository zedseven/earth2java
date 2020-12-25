package slexom.earthtojava.mobs.entity.base;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import slexom.earthtojava.mobs.entity.IEntityBlink;

import java.util.Random;

public class E2JBaseChickenEntity<T extends ChickenEntity> extends ChickenEntity implements IEntityBlink {

    private int lastBlink = 0;
    private int nextBlinkInterval = new Random().nextInt(760) + 60;
    private int remainingTick = 0;
    private int internalBlinkTick = 0;

    public E2JBaseChickenEntity(EntityType<? extends E2JBaseChickenEntity> type, World worldIn) {
        super(type, worldIn);
        experienceValue = 3;
        setNoAI(false);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        updateRemainingTicks();
    }

    @Override
    public void updateRemainingTicks() {
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

    @Override
    public int getBlinkRemainingTicks() {
        return this.remainingTick;
    }

    @Override
        public T func_241840_a(ServerWorld world , AgeableEntity ageable) {
        return (T) getType().create(world);
    }


    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
