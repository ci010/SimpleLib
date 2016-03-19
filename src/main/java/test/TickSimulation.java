package test;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ci010
 */
public enum TickSimulation
{
	INSTANCE;

	private Set<Tickable> tasks;

	public void start()
	{
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				for (Tickable task : tasks)
					task.tick();
			}
		}, 0, 333);
	}

	public TickSimulation addTask(Tickable tickable)
	{
		if (tasks == null)
			tasks = Sets.newConcurrentHashSet();
		tasks.add(tickable);
		return this;
	}

	public interface Tickable
	{
		void tick();
	}
}
