package api.simplelib.io.cache;

import api.simplelib.Instance;
import api.simplelib.LoadingDelegate;
import api.simplelib.utils.FileReference;
import api.simplelib.utils.TextureInfo;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.simplelib.client.loading.ExternalResource;
import net.simplelib.client.loading.PackBase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author ci010
 */
@LoadingDelegate
public class CacheSystem
{
	@Instance
	public static final CacheSystem INSTANCE = new CacheSystem();

	private CacheSystem() {}

	private File root = FileReference.getDir(FileReference.mc, "cache");
	ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
	private Multimap<String, ResourceLocation> cache = HashMultimap.create();

	public ListenableFuture<File> cachePresisit(ResourceLocation location, URL url) throws IOException
	{
		return service.submit(new DownloadTask(getCacheFile(location), url, true));
	}

	public ListenableFuture<File> cache(ResourceLocation location, URL url) throws IOException
	{
		return service.submit(new DownloadTask(getCacheFile(location), url, false));

	}

	public boolean hasCached(ResourceLocation location)
	{
		return cache.containsEntry(location.getResourceDomain(), location);
	}

	public void remove(final ResourceLocation location)
	{
		if (cache.containsEntry(location.getResourceDomain(), location))
		{
			service.submit(new Runnable()
			{
				@Override
				public void run()
				{
					cache.remove(location.getResourceDomain(), location);
					FileUtils.deleteQuietly(getCacheFile(location));
				}
			});
		}
	}

	public void clear(final String domain)
	{
		if (cache.containsKey(domain))
			service.submit(new Runnable()
			{
				@Override
				public void run()
				{
					File dir = new File(root, domain);
					if (dir.exists())
					{
						FileUtils.deleteQuietly(dir);
						cache.removeAll(domain);
					}
				}
			});
	}

	public File getCacheFile(ResourceLocation location)
	{
		return new File(FileReference.getDir(root, location.getResourceDomain()),
				location.getResourcePath());
	}

	public static class DownloadTask implements Callable<File>
	{
		private File file;
		private URL url;
		private boolean persist;

		public DownloadTask(File file, URL url, boolean persist)
		{
			this.file = file;
			this.url = url;
			this.persist = persist;
		}

		@Override
		public File call() throws Exception
		{
			ReadableByteChannel channel = Channels.newChannel(url.openStream());
			FileOutputStream cacheFile = new FileOutputStream(file);
			cacheFile.getChannel().transferFrom(channel, 0, Integer.MAX_VALUE);
			cacheFile.close();
			if (!persist) file.deleteOnExit();
			return file;
		}
	}


	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		ExternalResource.register(new PackBase(root)
		{
			@Override
			public Set<String> domain()
			{
				return Sets.newHashSet("cache");
			}
		});
		for (File domain : root.listFiles(
				new FileFilter()
				{
					@Override
					public boolean accept(File pathname)
					{
						return pathname.isDirectory();
					}
				}))
			for (File f : domain.listFiles())
				buildLoc(f, domain.getName(), "");
	}

	private void buildLoc(File file, String domain, String currentPath)
	{
		if (file.isDirectory())
			for (File next : file.listFiles())
				buildLoc(next, domain, currentPath.concat(file.getName()));
		else
			cache.put(domain, new ResourceLocation(domain, currentPath.concat(file.getName())));
	}
}
