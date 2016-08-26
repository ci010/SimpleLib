package net.simplelib.recipe;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

import java.util.Map;

/**
 * @author ci010
 */
public class RecipeRegistry extends IForgeRegistryEntry.Impl<RecipeRegistry>
{
	public static final CallBack callBack = new CallBack();

	public static final FMLControlledNamespacedRegistry<RecipeRegistry> REGISTRY =
			PersistentRegistryManager.createRegistry(new ResourceLocation("minecraft:recipe"),
					RecipeRegistry.class,
					new ResourceLocation("recipe"),
					0, 256, false,
					callBack, callBack, callBack);


	public static class CallBack implements IForgeRegistry.AddCallback<RecipeRegistry>, IForgeRegistry.ClearCallback<RecipeRegistry>, IForgeRegistry.CreateCallback<RecipeRegistry>
	{
		@Override
		public void onAdd(RecipeRegistry obj, int id, Map<ResourceLocation, ?> slaveset)
		{

		}

		@Override
		public void onClear(Map<ResourceLocation, ?> slaveset)
		{

		}

		@Override
		public void onCreate(Map<ResourceLocation, ?> slaveset)
		{

		}
	}
}
