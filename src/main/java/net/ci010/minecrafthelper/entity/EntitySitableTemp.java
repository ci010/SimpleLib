package net.ci010.minecrafthelper.entity;

import net.ci010.minecrafthelper.annotation.ModEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author CI010
 */
@ModEntity
public class EntitySitableTemp extends Entity
{
	public int blockPosX;
	public int blockPosY;
	public int blockPosZ;

	public EntitySitableTemp(World world, double blockX, double blockY, double blockZ)
	{
		super(world);
		this.noClip = true;
		this.preventEntitySpawning = true;
		this.width = 0;
		this.height = (float) 0.5;
		this.blockPosX = (int) blockX;
		this.blockPosY = (int) blockY;
		this.blockPosZ = (int) blockZ;
		this.setPosition(blockX + 0.5d, blockY + 0.5, blockZ + 0.5d);
	}

	public EntitySitableTemp(World world)
	{
		super(world);
		this.noClip = true;
		this.preventEntitySpawning = true;
		this.width = 0;
		this.height = 0;
	}

	@Override
	public void onEntityUpdate()
	{}

	@Override
	public double getMountedYOffset()
	{
		return this.height;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected boolean shouldSetPosAfterLoading()
	{
		return false;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{}
}
