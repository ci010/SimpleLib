package net.ci010.minecrafthelper.util;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import static net.minecraftforge.fml.relauncher.ReflectionHelper.getPrivateValue;
import static net.minecraftforge.fml.relauncher.ReflectionHelper.setPrivateValue;

/**
 * @author ci010
 */
public class PlayerModify
{
	public static void modifyHealth(EntityPlayer player, double factor, Type type)
	{
		modifyShareAttr(SharedMonsterAttributes.maxHealth, "mod_health", player, factor, type);
	}

	public static void modifyAttack(EntityPlayer player, double factor, Type type)
	{
		modifyShareAttr(SharedMonsterAttributes.attackDamage, "mod_attack", player, factor, type);
	}

	public static void modifyMoveSpeed(EntityPlayer player, double factor, Type type)
	{
		modifyShareAttr(SharedMonsterAttributes.movementSpeed, "mod_speed", player, factor, type);
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		player.capabilities.writeCapabilitiesToNBT(nbtTagCompound);
		switch (type)
		{
			case set:
				nbtTagCompound.setFloat("walkSpeed", (float) factor);
				break;
			case add:
				nbtTagCompound.setFloat("walkSpeed", (float) (factor + nbtTagCompound.getFloat("walkSpeed")));
				break;
			case multiply:
				nbtTagCompound.setFloat("walkSpeed", (float) (factor * nbtTagCompound.getFloat("walkSpeed")));
				break;
			case scaleUp:
				nbtTagCompound.setFloat("walkSpeed", (float) ((factor + 1) * nbtTagCompound.getFloat("walkSpeed")));
				break;
		}
		player.capabilities.readCapabilitiesFromNBT(nbtTagCompound);
	}

	public static void modifyJumpSpeed(EntityPlayer player, double factor, Type type)
	{
		switch (type)
		{
			case set:
				//TODO warn
				setPrivateValue(EntityPlayer.class, player, factor, "speedInAir");
				break;
			case add:
				setPrivateValue(EntityPlayer.class, player, (Float) getPrivateValue(EntityPlayer.class, player,
						"speedInAir")
						+ factor, "speedInAir");
				break;
			case multiply:
				setPrivateValue(EntityPlayer.class, player, (Float) getPrivateValue(EntityPlayer.class, player,
						"speedInAir")
						* factor, "speedInAir");
				break;
			case scaleUp:
				setPrivateValue(EntityPlayer.class, player, (Float) getPrivateValue(EntityPlayer.class, player,
						"speedInAir")
						* (1 + factor), "speedInAir");
				break;
		}
	}


	private static void modifyShareAttr(IAttribute attribute, String id, EntityPlayer player, double factor, Type type)
	{
		IAttributeInstance attr = player.getEntityAttribute(attribute);
		AttributeModifier mod;
		if (type == Type.set)
			mod = new AttributeModifier(id, Math.abs(attr.getAttributeValue() - factor), Type.add.operation);
		else
			mod = new AttributeModifier(id, factor, type.operation);
		attr.applyModifier(mod);
	}

	public enum Type
	{
		set(-1),
		/**
		 * Directly add to the original value.
		 * <p/>
		 * value = value + factor
		 */
		add(0),
		/**
		 * Multiply the original value by the factor.
		 * <p/>
		 * value = value * factor
		 */
		multiply(1),
		/**
		 * Scale the original value up by percentage.
		 * <p/>
		 * e.g. value = value * (1 + factor)
		 */
		scaleUp(2);

		int operation;

		private Type(int operation)
		{
			this.operation = operation;
		}
	}
}
