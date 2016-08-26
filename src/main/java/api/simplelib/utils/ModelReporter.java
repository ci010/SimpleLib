package api.simplelib.utils;

import api.simplelib.registry.ModHandler;
import api.simplelib.registry.components.ModComponent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

/**
 * @author ci010
 */
@ModHandler
public class ModelReporter
{
	@ModComponent
	public static class BlockTest extends Block
	{
		public BlockTest()
		{
			super(Material.AIR);
		}
	}

	@SubscribeEvent
	public void onModelPost(ModelBakeEvent event)
	{
		Map<ResourceLocation, Exception> loadingExceptions = ReflectionHelper.getPrivateValue(ModelLoader.class, event
				.getModelLoader(), "loadingExceptions");
		for (Map.Entry<ResourceLocation, Exception> entry : loadingExceptions.entrySet())
		{
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getMessage());
			Throwable cause = entry.getValue().getCause();
			if (cause != null)
				System.out.println(cause.getMessage());
			System.out.println(entry.getValue().getClass());
			System.out.println();
		}
	}
}
