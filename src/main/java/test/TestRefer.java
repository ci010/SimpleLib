package test;

import com.google.common.collect.Queues;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ci010
 */
public class TestRefer
{

	static void test()
	{
		Struct a = new Struct("a", 0), b = new Struct("b", 1);
		CachePool<Struct> p = new CachePool<Struct>();
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < 1000; i++)
		{
			p.push(new Struct("" + i, i));
		}
		p.push(a);
		p.push(b);
		System.out.println(a);
		System.out.println(p.pop());
	}

	static class CachePool<T>
	{
		HashSet<WeakReference<T>> cache = new HashSet<WeakReference<T>>();
		ReferenceQueue<T> queue = new ReferenceQueue<T>();
		Queue<T> pool = Queues.newConcurrentLinkedQueue();

		public CachePool()
		{
			ExecutorService service = Executors.newSingleThreadExecutor();
			service.submit(new Runnable()
			{
				@Override
				public void run()
				{
					System.out.println("hello");
					while (true)
					{
						Reference<? extends T> poll = null;
						try
						{
							poll = queue.remove(1);
							if (poll != null)
								if (poll.get() != null)
								{
									pool.add(poll.get());
									System.out.println("add");
								}
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			});
		}

		public T pop()
		{
			if (pool.size() != 0)
				return pool.remove();
			return null;
		}

		void push(T ref)
		{
			cache.add(new WeakReference<T>(ref, queue));
		}
	}

	static class Struct
	{
		String name;
		int v;

		public Struct(String name, int v)
		{
			this.name = name;
			this.v = v;
		}

		@Override
		public String toString()
		{
			return "Struct{" +
					"name='" + name + '\'' +
					", v=" + v +
					'}';
		}


	}
}
