package api.simplelib.io.cache;

import api.simplelib.utils.TextureInfo;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author ci010
 */
public class ImageCache
{
	private final Map<ResourceLocation, ITextureObject> mapTextureObjects = ReflectionHelper.getPrivateValue
			(TextureManager.class, Minecraft.getMinecraft().getTextureManager(), "mapTextureObjects");
	private TextureManager manager = Minecraft.getMinecraft().getTextureManager();

	public void cacheImage(final ResourceLocation location, final URL url)
	{
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
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					catch (ExecutionException e)
					{
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

	public void removeImage(ResourceLocation location)
	{
		ITextureObject itextureobject = manager.getTexture(location);
		if (itextureobject != null)
		{
			TextureUtil.deleteTexture(itextureobject.getGlTextureId());
			mapTextureObjects.remove(location);
		}
		CacheSystem.INSTANCE.remove(location);
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
