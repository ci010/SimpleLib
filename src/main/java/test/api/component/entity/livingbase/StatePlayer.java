package test.api.component.entity.livingbase;

import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import test.api.component.item.StateItem;

/**
 * @author ci010
 */
public interface StatePlayer extends StateEntityLivingBase
{
	AbilityLivingBase getAbility();

	FoodState getFoodState();

	Optional<StateItem> getCurrentArmor(int i);

	boolean interactWith(Entity targetEntity);

	void attackTargetEntityWithCurrentItem(Entity targetEntity);

	int getXPSeed();

	void addExperience(int amount);

	void removeExperienceLevel(int levels);

	void addExperienceLevel(int levels);

	interface FoodState
	{
		int getFoodLevel();

		void setFoodLevel(int foodLevelIn);

		float getSaturationLevel();

		boolean needFood();
	}
}
