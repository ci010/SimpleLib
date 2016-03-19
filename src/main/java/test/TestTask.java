package test;

import java.util.concurrent.RecursiveTask;

/**
 * @author ci010
 */
public class TestTask extends RecursiveTask<Object>
{
	@Override
	protected Object compute()
	{
		TestTask testTask = new TestTask();
		testTask.fork();

		return null;
	}
}
