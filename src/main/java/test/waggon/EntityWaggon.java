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
	BlockPos lastHorse;
	int distance;
	Vec3i lastSpeed;

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
	{
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (horse != null)
		{
			BlockPos current = horse.getPosition();
			if (this.getPosition().distanceSq(current) > distance)
			{
				if (lastHorse != null)
				{
					Vec3i vec = current.subtract(lastHorse);
					this.moveEntity(vec.getX(), vec.getY(), vec.getZ());
					this.lastSpeed = vec;
				}
				lastHorse = current;
			}
			else if (lastSpeed != null)
				this.moveEntity(lastSpeed.getX(), lastSpeed.getY(), lastSpeed.getZ());
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		this.horse = (EntityHorse) this.worldObj.getEntityByID(tagCompund.getInteger("horse"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		if (this.horse != null)
			tag.setInteger("horse", this.horse.getEntityId());
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
