package test.api.component.entity.livingbase;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import test.api.component.entity.AbilityEntity;
import test.api.component.item.StateItem;

import java.util.Random;

/**
 * @author ci010
 */
public interface AbilityLivingBase extends AbilityEntity
{
	void setItemInUse(StateItem stack, int duration, EnumAction action);

	void moveEntityWithHeading(float strafe, float forward);

	void knockBack(Entity entityIn, float p_70653_2_, double p_70653_3_, double p_70653_5_);

	Random getRandom();

	void jump();

	void mountEntity(Entity entityIn);

	void dismountEntity(Entity entityIn);

	Vec3 getLookVec();
}
