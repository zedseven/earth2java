
package slexom.earthtojava.mobs.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.World;
import slexom.earthtojava.mobs.entity.ai.goal.CluckshroomPlaceBlockGoal;
import slexom.earthtojava.mobs.entity.base.E2JBaseChickenEntity;


public class CluckshroomEntity extends E2JBaseChickenEntity<CluckshroomEntity> {

    public CluckshroomEntity(EntityType<CluckshroomEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new CluckshroomPlaceBlockGoal(this));
    }

}
