package test.waggon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.simplelib.annotation.type.ModEntity;

/**
 * @author ci010
 */
@ModEntity
@ModEntity.Render(RenderWaggon.class)
public class EntityWaggon extends Entity
{
	EntityHorse horse;
	int distance;

	public EntityWaggon(World worldIn)
	{
		super(worldIn);
	}

	public void linkTo(EntityHorse horse, int distance)
	{
		this.horse = horse;
		this.distance = distance;
	}

	@Override
	protected void entityInit()
	{}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (horse != null)
		{
			BlockPos current = horse.getPosition();

//			System.out.println("update");
			if (this.getPosition().distanceSq(current) > distance)
			{
				Vec3i speed = current.subtract(this.getPosition());
//				this.moveEntity(speed.getX() / 2, speed.getY() / 2, speed.getZ() / 2);
				this.setPosition(current.getX(), current.getY(), current.getZ());
//				this.lastSpeed = speed;
			}
//			else if (lastSpeed != null)
//			{
//				this.moveEntity(Math.max(lastSpeed.getX() / 2, 0), Math.max(lastSpeed.getY() / 2, 0), Math.max(lastSpeed
//						.getZ() / 2, 0));
//			}
//			lastHorsePos = current;
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		this.distance = tagCompund.getInteger("distance");
//		this.horse = (EntityHorse) this.worldObj.getEntityByID(tagCompund.getInteger("horse"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("distance", distance);
//		if (this.horse != null)
//			tag.setInteger("horse", this.horse.getEntityId());
	}

	@Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack stack)
	{

	}

	@Override
	public ItemStack[] getInventory()
	{
		return new ItemStack[0];
	}


}
