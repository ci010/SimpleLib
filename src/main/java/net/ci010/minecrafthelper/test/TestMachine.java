package net.ci010.minecrafthelper.test;

import net.ci010.minecrafthelper.annotation.type.ModMachine;
import net.ci010.minecrafthelper.gui.GuiDefaultBackground;
import net.ci010.minecrafthelper.gui.GuiString;
import net.ci010.minecrafthelper.machine.MachineInfo;
import net.ci010.minecrafthelper.interactive.Process;

/**
 * @author ci010
 */
@ModMachine
public class TestMachine extends MachineInfo
{
	public TestMachine()
	{
		this.addGui(new GuiString("string test", 70, 50))
				.addGui(new
						GuiDefaultBackground(0, 0, 157, 157)).addProcess(ProcessTest.class);
	}

	public static class ProcessTest implements Process
	{
		@Override
		public void preUpdate()
		{

		}

		@Override
		public void postUpdate()
		{

		}

		@Override
		public void update()
		{

		}

		@Override
		public boolean shouldUpdate()
		{
			return false;
		}
	}
}
