package test.waggon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.simplelib.annotation.type.ModEntity;

import java.util.List;

/**
 * @author ci010
 */
@ModEntity
@ModEntity.Render(RenderWaggon.class)
public class EntityWaggon extends Entity
{
	EntityHorse horse;
	int distance;

	BlockPos target;

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
	{i = new Vec3i(1, 0, 1);}

	Vec3i i;

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!worldObj.isRemote)
			if (horse != null)
			{
				BlockPos current = horse.getPosition();
				BlockPos pos = this.getPosition();
				if (pos.distanceSq(current) > distance)
				{
					Vec3i speed = current.subtract(pos);
					double sx = speed.getX();
					double sz = speed.getZ();
					if (sx == 0 && sz == 0)
						return;
					double max = Math.max(Math.abs(sx), Math.abs(sz));
					sx /= max;
					sz /= max;

					sx *= 0.4;
					sz *= 0.4;


//				this.moveEntity(speed.getX() / 2, speed.getY() / 2, speed.getZ() / 2);
					this.moveEntity(sx, 0, sz);
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
		int x = tagCompund.getInteger("horseX"), y = tagCompund.getInteger("horseY"), z = tagCompund.getInteger
				("horseZ");
		List list = worldObj.getEntitiesWithinAABB(EntityHorse.class, new AxisAlignedBB(
				tagCompund.getDouble("minX"),
				tagCompund.getDouble("minY"),
				tagCompund.getDouble("minZ"),
				tagCompund.getDouble("maxX"),
				tagCompund.getDouble("maxY"),
				tagCompund.getDouble("maxZ")));
		if (list.size() != 1)
		{System.out.println("Cannot find the horse!"); return;}
		this.horse = (EntityHorse) list.get(0);
		this.horse = (EntityHorse) this.worldObj.getEntityByID(tagCompund.getInteger("horse"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("distance", distance);
		if (horse == null)
			return;
		BlockPos position = horse.getPosition();
		AxisAlignedBB box = new AxisAlignedBB(position, position).expand(1, 1, 1);
		tag.setDouble("minX", box.minX);
		tag.setDouble("minY", box.minY);
		tag.setDouble("minZ", box.minZ);
		tag.setDouble("maxX", box.maxX);
		tag.setDouble("maxY", box.maxY);
		tag.setDouble("maxZ", box.maxZ);

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
