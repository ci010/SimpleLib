package test.api.component.entity.livingbase;

import net.minecraft.util.DamageSource;
import test.api.component.entity.StateEntity;
import test.api.component.item.StateItem;
import test.api.effect.Effect;
import test.api.effect.StateEffect;

/**
 * @author ci010
 */
public interface StateEntityLivingBase extends StateEntity
{
	AbilityLivingBase getAbility();

	SpecialInventory getArmor();

	Health getHealthInfo();

	boolean isRiding();

	boolean isSneaking();

	boolean isSprinting();

	boolean isMoving();

	boolean isWet();

	boolean isJumping();

	interface EffectCollection
	{
		void addPotionEffect(StateEffect potioneffectIn);

		StateEffect getActivePotionEffect(Effect potionIn);

		boolean isPotionActive(Effect potionIn);

		boolean isPotionApplicable(StateEffect potioneffectIn);
	}

	interface SpecialInventory
	{
		int getTotalArmorValue();

		void wearArmor(StateItem item);

		StateItem getHeldItem();

		StateItem getArmor(int i);
	}

	interface Health
	{
		void heal(float healAmount);

		float getHealth();

		void setHealth(float health);

		float getMaxHealth();

		void damageEntity(DamageSource damageSrc, float damageAmount);
	}
}

