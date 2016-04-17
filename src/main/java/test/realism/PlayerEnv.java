package test.realism;

import api.simplelib.VarFactory;
import api.simplelib.entity.IStatusUpdate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author ci010
 */
public class PlayerEnv implements IStatusUpdate
{
	private EntityPlayer player;
	private World world;
	private BlockPos.MutableBlockPos pos;
	private IBlockState[] left, right, front, back, center;
	private EnumFacing facing;

	@Override
	public void build(Entity entity, VarFactory factory)
	{
		player = (EntityPlayer) entity;
		world = entity.worldObj;
		left = new IBlockState[4];
		right = new IBlockState[4];
		front = new IBlockState[4];
		back = new IBlockState[4];
		center = new IBlockState[4];
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {}

	@Override
	public void writeToNBT(NBTTagCompound tag) {}

	public IBlockState getLeft(int i)
	{
		return left[i];
	}

	public IBlockState getRight(int i)
	{
		return right[i];
	}

	public IBlockState getFront(int i)
	{
		return front[i];
	}

	public IBlockState getBack(int i)
	{
		return back[i];
	}

	public IBlockState getCenter(int i)
	{
		return center[i];
	}

	public EnumFacing getFacing()
	{
		return facing;
	}

	public BlockPos getPos()
	{
		return pos;
	}

	@Override
	public void update()
	{
		pos = new BlockPos.MutableBlockPos(
				round(player.posX),
				round(player.posY),
				round(player.posZ));
		this.facing = player.getHorizontalFacing();
		handle(center, pos.getX(), pos.getY() + 2, pos.getZ());
		switch (facing)
		{
			case NORTH:
				handle(left, pos.getX() - 1, pos.getY() + 2, pos.getZ());
				handle(right, pos.getX() + 1, pos.getY() + 2, pos.getZ());
				handle(front, pos.getX(), pos.getY() + 2, pos.getZ() + 1);
				handle(back, pos.getX(), pos.getY() + 2, pos.getZ() - 1);
				break;
			case SOUTH:
				handle(right, pos.getX() - 1, pos.getY() + 2, pos.getZ());
				handle(left, pos.getX() + 1, pos.getY() + 2, pos.getZ());
				handle(back, pos.getX(), pos.getY() + 2, pos.getZ() + 1);
				handle(front, pos.getX(), pos.getY() + 2, pos.getZ() - 1);
				break;
			case WEST:
				handle(front, pos.getX() - 1, pos.getY() + 2, pos.getZ());
				handle(back, pos.getX() + 1, pos.getY() + 2, pos.getZ());
				handle(right, pos.getX(), pos.getY() + 2, pos.getZ() + 1);
				handle(left, pos.getX(), pos.getY() + 2, pos.getZ() - 1);
				break;
			case EAST:
				handle(back, pos.getX() - 1, pos.getY() + 2, pos.getZ());
				handle(front, pos.getX() + 1, pos.getY() + 2, pos.getZ());
				handle(left, pos.getX(), pos.getY() + 2, pos.getZ() + 1);
				handle(right, pos.getX(), pos.getY() + 2, pos.getZ() - 1);
				break;
		}
	}

	private void handle(IBlockState[] blockState, int x, int y, int z)
	{
		int oldX = pos.getX(), oldY = pos.getY(), oldZ = pos.getZ();
		for (int i = 0; i < 4; i++)
			blockState[i] = world.getBlockState(pos.set(x, y - i, z));
		pos.set(oldX, oldY, oldZ);
	}

	private int round(double d)
	{
		return (int) Math.round(d);
	}
}
