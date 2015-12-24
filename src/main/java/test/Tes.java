package test;

import net.simplelib.interactive.Process;

import java.util.Collection;

/**
 * @author ci010
 */
public class Tes
{
	Collection<Process> processes;

	public void onUpdate()
	{
		for (Process process : processes)
			process.preUpdate();
		for (Process process : processes)
			if (process.shouldUpdate())
				process.update();
		for (Process process : processes)
			process.postUpdate();
	}

}
