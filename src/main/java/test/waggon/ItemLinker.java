package test.waggon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import api.simplelib.component.ModComponent;

/**
 * @author ci010
 */
@ModComponent(name = "linker")
public class ItemLinker extends Item
{
	public ItemLinker()
	{
		this.setCreativeTab(CreativeTabs.tabFood);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer playerIn, Entity entity)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (entity instanceof EntityWaggon /*|| entity instanceof EntityRewrite*/)
			tag.setInteger("waggon", entity.getEntityId());
		else if (entity instanceof EntityHorse)
			tag.setInteger("horse", entity.getEntityId());
		if (tag.hasKey("waggon") && tag.hasKey("horse"))
		{
			EntityWaggon waggon = (EntityWaggon) playerIn.worldObj.getEntityByID(tag.getInteger("waggon"));
			waggon.linkTo((EntityHorse) playerIn.worldObj.getEntityByID(tag.getInteger("horse")), 10);
			System.out.println("link start!");
		}
		return false;
	}
}
