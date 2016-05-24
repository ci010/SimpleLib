package api.simplelib.io.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author ci010
 */
public class ImageCache
{
	public static final ImageCache INSTANCE = new ImageCache();
	private Map<ResourceLocation, ITextureObject> mapTextureObjects = ReflectionHelper.getPrivateValue
			(TextureManager.class, Minecraft.getMinecraft().getTextureManager(), "mapTextureObjects");
	private TextureManager manager = Minecraft.getMinecraft().getTextureManager();
	private Set<ResourceLocation> loading = Sets.newHashSet();

	private ImageCache() {}

	/**
	 * Cache an image into game.
	 *
	 * @param location The resource location of this image will be identified in game.
	 * @param url The image URL.
	 */
	public void cacheImage(final ResourceLocation location, final URL url)
	{
		if (CacheSystem.INSTANCE.hasCached(location))
			return;
		if(CacheSystem.INSTANCE.isPending(location))
			return;
		if (loading.contains(location))
			return;
		try
		{
			final ListenableFuture<File> cache = CacheSystem.INSTANCE.cache(location, url);
			cache.addListener(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						BufferedImage image = ImageIO.read(cache.get());
						int id = TextureUtil.glGenTextures();
						TextureUtil.uploadTextureImage(id, image);
						TextureImage obj = new TextureImage(id);
						mapTextureObjects.put(location, obj);
						loading.add(location);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					catch (ExecutionException e)
					{
						//TODO log
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}, CacheSystem.INSTANCE.service);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Remove the image from game. The cache file will be also removed.
	 *
	 * @param location
	 */
	public void removeImage(ResourceLocation location)
	{
		ITextureObject itextureobject = manager.getTexture(location);
		if (itextureobject != null)
		{
			TextureUtil.deleteTexture(itextureobject.getGlTextureId());
			mapTextureObjects.remove(location);
		}
		loading.remove(location);
		CacheSystem.INSTANCE.remove(location);
	}

	/**
	 * Clear all loading cache.
	 */
	public void clear()
	{
		HashSet<ResourceLocation> set = Sets.newHashSet(loading);
		for (ResourceLocation location : set)
			removeImage(location);
	}

	private class TextureImage implements ITextureObject
	{
		int id;

		TextureImage(int id)
		{
			this.id = id;
		}

		@Override
		public void setBlurMipmap(boolean p_174936_1_, boolean p_174936_2_)
		{}

		@Override
		public void restoreLastBlurMipmap()
		{}

		@Override
		public void loadTexture(IResourceManager resourceManager) throws IOException
		{}

		@Override
		public int getGlTextureId()
		{
			return id;
		}
	}
}
