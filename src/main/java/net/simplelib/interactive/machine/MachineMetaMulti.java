package net.simplelib.interactive.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.simplelib.interactive.ContainerCommon;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.util.Pattern3DBlockPos;

/**
 * @author ci010
 */
public class MachineMetaMulti extends InteractiveMetadata
{
	protected Pattern3DBlockPos pattern;
	protected BlockMultiCore block;
	protected BlockMutltiSub sub;

	MachineMetaMulti(MachineMultiInfo info)
	{
		super(info);
		this.pattern = Pattern3DBlockPos.newPattern(info.pattern);
		this.sub = info.sub;
		this.block = info.block;
	}

	@Override
	public Container getContainer(EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntityDummy temp = ((TileEntityDummy) world.getTileEntity(new BlockPos(x, y, z)));
		return temp.real.loadToContainer(new ContainerCommon().loadPlayerSlot(player.inventory));
	}
}
