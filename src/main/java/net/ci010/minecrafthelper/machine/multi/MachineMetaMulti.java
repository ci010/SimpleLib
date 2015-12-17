package net.ci010.minecrafthelper.machine.multi;

import net.ci010.minecrafthelper.machine.MachineMeta;
import net.ci010.minecrafthelper.util.Pattern3DBlockPos;

/**
 * @author ci010
 */
public class MachineMetaMulti extends MachineMeta
{
	Pattern3DBlockPos pattern;
	BlockMulti block;
	BlockSubMachine sub;

	MachineMetaMulti(MachineMultiInfo info)
	{
		super(info);
		this.pattern = new Pattern3DBlockPos(info.pattern);
		this.sub = info.sub;
		this.block = info.block;
	}
}
