package api.simplelib;

import api.simplelib.utils.DebugLogger;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.simplelib.registry.RegistryBufferManager;

import java.util.Map;

/**
 * @author ci010
 */
public class ResourceLocations
{
	private static Map<Class<? extends TileEntity>, String> classToNameMap;
	private static Map<String, String> nameToModid;

	static
	{
		nameToModid = Maps.newHashMap();
		classToNameMap = ReflectionHelper.getPrivateValue(TileEntity.class, null, "classToNameMap");
		for (Map.Entry<Class<? extends TileEntity>, String> entry : classToNameMap.entrySet())
		{
			String modid = RegistryBufferManager.instance().getModidByPackage(entry.getKey().getPackage());
			if (modid == null)
				modid = "minecraft";
			nameToModid.put(entry.getValue(), modid);
		}
	}

	public static ResourceLocation of(String domain, String path)
	{
		return new ResourceLocation(domain, path);
	}

	public static ResourceLocation of(String path)
	{
		return new ResourceLocation(path);
	}

	public static ResourceLocation of(Block block)
	{
		return block.getRegistryName();
	}

	public static ResourceLocation of(Item item)
	{
		return item.getRegistryName();
	}

	public static ResourceLocation inspect(Object obj)
	{
		if (obj instanceof Entity)
			return of((Entity) obj);
		if (obj instanceof TileEntity)
			return of((TileEntity) obj);
		if (obj instanceof Block)
			return of((Block) obj);
		if (obj instanceof Item)
			return of((Item) obj);
		if (obj instanceof ItemStack)
			return of(((ItemStack) obj).getItem());
		if (obj instanceof IBlockState)
			return of(((IBlockState) obj).getBlock());
		return new ResourceLocation(obj.getClass().getName());
	}

	public static ResourceLocation of(Entity entity)
	{
		EntityRegistry.EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
		if (registration == null) return null;
		return new ResourceLocation(registration.getContainer().getModId(),
				upperTo_(registration.getEntityName()));
	}

	public static ResourceLocation concat(ResourceLocation location, String path)
	{
		return new ResourceLocation(location.getResourceDomain(), location.getResourcePath().concat(path));
	}

	public static ResourceLocation of(TileEntity tileEntity)
	{
		String s = classToNameMap.get(tileEntity.getClass());
		if (s == null) return null;
		String modid = nameToModid.get(s);
		if (modid == null)
		{
			modid = RegistryBufferManager.instance().getModidByPackage(tileEntity.getClass());
			if (modid == null)
			{
				DebugLogger.fatal("Cannot find the modid for tile entity {}, Maybe the onwer of this doesn't register" +
						" in a common way???", tileEntity.getClass());
				modid = "minecraft";
				nameToModid.put(s, modid);
			}
		}
		return new ResourceLocation(modid, s);
	}

	public static String upperTo_(String name)
	{
		StringBuilder builder = new StringBuilder(name);
		for (int i = 0; i < builder.length(); ++i)
			if (Character.isUpperCase(builder.charAt(i)))
			{
				builder.setCharAt(i, Character.toLowerCase(builder.charAt(i)));
				if (i != 0)
					builder.insert(i, "_");
			}
		return builder.toString();
	}

	public static String _toPoint(String name)
	{
		return name.replace("_", ".");
	}

	public static String _toUp(String str)
	{
		int i;
		while ((i = str.indexOf('_')) != -1 && str.length() > i + 2)
			str = str.substring(0, i).concat(str.substring(i + 1, i + 2).toUpperCase()).concat(str.substring(i + 2));
		return str;
	}

	private ResourceLocations() {}
}
