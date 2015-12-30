package test.waggon;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.simplelib.annotation.type.ModEntity;

import java.util.List;

/**
 * @author ci010
 */
@ModEntity
@ModEntity.Render(RenderWaggon.class)
public class EntityWaggon extends EntityLiving
{
	private EntityHorse horse;
	private double distance;

	public EntityWaggon(World worldIn)
	{
		super(worldIn);
		this.setSize(4, 3);
	}

	public void linkTo(EntityHorse horse, double distance)
	{
		this.horse = horse;
		this.distance = distance;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!worldObj.isRemote)
			if (horse != null)
			{
				BlockPos current = horse.getPosition();
				BlockPos pos = this.getPosition();
				double distance = Math.sqrt(pos.distanceSq(current));
				if (distance > this.distance * 2)
				{
					//break
				}
				if (distance > this.distance)
					this.moveHelper.setMoveTo(current.getX(), current.getY(), current.getZ(), 0.4d);
			}
	}


	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.distance = tagCompund.getDouble("distance");
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
		super.writeEntityToNBT(tag);
		tag.setDouble("distance", distance);
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

}
