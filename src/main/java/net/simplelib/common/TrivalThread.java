package net.simplelib.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ci010
 */
public class TrivalThread
{
	private static ExecutorService delegate = Executors.newCachedThreadPool();

	public static ExecutorService getExecutor()
	{
		return delegate;
	}

	public static void newTask(Callable<?> task, int time)
	{
		Future<?> future = delegate.submit(task);
		future.isDone();
	}

}
