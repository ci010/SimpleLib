package net.simplelib.optimize;

/**
 * @author ci010
 */
public class TickOptimize
{
	public static long lastTick = System.nanoTime(), curTime;
	private static long catchupTime = 0;

	private static final int TPS = 20;
	private static final int TICK_TIME = 1000000000 / TPS;

	public static void test()
	{
		System.out.println("INSERT SUCCESS!!!!");
	}

	public static boolean cal()
	{
		System.out.println("cal");
		curTime = System.nanoTime();
		long wait = TICK_TIME - (curTime - lastTick) - catchupTime;
		if (wait > 0)
		{
			try
			{
				Thread.sleep(wait / 1000000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catchupTime = 0;
			return false;
		}
		else
			catchupTime = Math.min(1000000000, Math.abs(wait));
		lastTick = curTime;
		return true;

		//tick()
		//ALOAD 0
		//INVOKEVIRTUAL net/minecraft/server/MinecraftServer.tick ()V

		//this.running =true;
		//ALOAD 0
		//ICONST_1
		//PUTFIELD net/minecraft/server/MinecraftServer.serverIsRunning : Z

	}
}
