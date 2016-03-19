package test.waggon;

import api.simplelib.command.ISimpleCommand;
import api.simplelib.command.ModCommand;
import api.simplelib.common.ModHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import api.simplelib.entity.ModEntity;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * @author ci010
 */
@ModEntity
@ModEntity.Render(RenderRewrite.class)
@ModEntity.Spawner(primaryColor = 0, secondaryColor = 20)
public class EntityRewrite extends Entity
{
	public EntityRewrite(World worldIn)
	{
		super(worldIn);
		this.setSize(1.5f, 0.6f);
		System.out.println("new rewrite");
	}

	@Override
	public void setDead()
	{
		System.out.println("setDead");
		super.setDead();
	}

	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		return entityIn.getEntityBoundingBox();
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		System.out.println("update");
	}

	/**
	 * Returns the collision bounding box for this entity
	 */
	public AxisAlignedBB getCollisionBoundingBox()
	{
		return this.getEntityBoundingBox();
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

	public boolean interactFirst(EntityPlayer playerIn)
	{
		return false;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{

	}

	@ModCommand
	public static class Cmd implements ISimpleCommand
	{
		@Override
		public String name()
		{
			return "rewrite";
		}

		@Override
		public void processCommand(ICommandSender sender, String[] args)
		{
			final EntityRewrite entity = new EntityRewrite(sender.getEntityWorld());
			final BlockPos pos = sender.getPosition();
			entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
			sender.getEntityWorld().spawnEntityInWorld(entity);
		}
	}

	@ModHandler
	public static class EntityCheck
	{
		public void canUpdate(EntityEvent.CanUpdate event)
		{
			if (event.entity instanceof EntityRewrite)
			{
				System.out.println("Rewrite can update");
				event.canUpdate = true;
			}
		}
	}
//	@ModEntityHandler
//	public static class Dmg implements IPropertiesHandler<EntityRewrite, StatusLinear>
//	{
//		@Override
//		public StatusLinear createStatus(EntityRewrite entity)
//		{
//			return (StatusLinear) new StatusLinear().setMax(5);
//		}
//
//		public static StatusLinear getStatus(EntityRewrite entity)
//		{
//			return IPropertiesManager.getStatus(entity, Dmg.class);
//		}
//
//		@Override
//		public String getId()
//		{
//			return "DamageTaken";
//		}
//	}
}
