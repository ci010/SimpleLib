package net.ci010.minecrafthelper.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author CI010
 */
public class EntitySitableTemp extends Entity
{
	double offset;

	public EntitySitableTemp(World world, double blockX, double blockY, double blockZ, double offset)
	{
		super(world);
		this.posX = blockX;
		this.posY = blockY + offset;
		this.posZ = blockZ;
		this.offset = offset;
		setPostionConsideringRotation(blockX, blockY, blockZ, 2, offset);
	}

	@Override
	public void onEntityUpdate()
	{}

	@Override
	public double getMountedYOffset()
	{
		return this.height + this.offset;
	}

	@Override
	protected boolean shouldSetPosAfterLoading()
	{
		return false;
	}


	public void setPostionConsideringRotation(double x, double y, double z, int rotation, double rotationOffset)
	{
		switch (rotation)
		{
			case 2:
				z += rotationOffset;
				break;
			case 0:
				z -= rotationOffset;
				break;
			case 3:
				x -= rotationOffset;
				break;
			case 1:
				x += rotationOffset;
				break;
		}
		setPosition(x, y, z);
	}


	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		this.offset = tagCompund.getDouble("offset");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setDouble("offset", this.offset);
	}
}
