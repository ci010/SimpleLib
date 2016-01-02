package test.waggon;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.simplelib.registry.annotation.type.IPropertyHook;
import net.simplelib.registry.annotation.type.ModEntity;
import net.simplelib.status.StatusCommon;
import net.simplelib.status.StatusManager;
import net.simplelib.status.StatusProvider;

/**
 * @author ci010
 */
@ModEntity
@ModEntity.Render(RenderRewrite.class)
@ModEntity.HasSpawner(primaryColor = 0, secondaryColor = 20)
public class EntityRewrite extends Entity
{
//	private double x, y, z, yaw, pitch, speedX, speedY, speedZ;

	public EntityRewrite(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(17, 0);
		this.dataWatcher.addObject(18, 1);
		this.dataWatcher.addObject(19, 0f);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{

	}

	@IPropertyHook
	public static class Dmg implements StatusProvider<EntityRewrite, StatusCommon>
	{
		@Override
		public StatusCommon createStatus(EntityRewrite entity)
		{
			return new StatusCommon(5);
		}

		public static StatusCommon getStatus(EntityRewrite entity)
		{
			return StatusManager.getStatus(entity, Dmg.class);
		}

		@Override
		public String getId()
		{
			return "DamageTaken";
		}
	}
}
