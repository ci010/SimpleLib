package net.simplelib.interactive.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.simplelib.RegistryHelper;
import net.simplelib.interactive.ContainerCommon;
import net.simplelib.interactive.InteractiveMetadata;
import net.simplelib.interactive.block.BlockMachine;

/**
 * @author ci010
 */
public class MachineMetadata extends InteractiveMetadata
{
	protected BlockMachine block;

	public MachineMetadata(MachineInfo info, String modid)
	{
		super(info);
		this.block = info.getBlock();
//		RegistryHelper.INSTANCE.registerBlock(modid, block, "block_".concat(this.id));
	}

//	@Override
//	public Container getContainer(EntityPlayer player, World world, int x, int y, int z)
//	{
//		TileEntityDummy temp = ((TileEntityDummy) world.getTileEntity(new BlockPos(x, y, z)));
//		return temp.real.loadToContainer(new ContainerCommon().loadPlayerSlot(player.inventory));//TODO check this
//	}
}
