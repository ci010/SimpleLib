package net.simplelib;

import com.google.gson.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

/**
 * @author ci010
 */
public class Dumper
{
	public static String dumpAllMod()
	{
		List<ModContainer> activeModList = Loader.instance().getActiveModList();
		JsonObject root = new JsonObject();
		JsonArray arr = new JsonArray();
		root.addProperty("mc-version", Loader.MC_VERSION);
		root.add("mods", arr);
		for (ModContainer modContainer : activeModList)
		{
			ModMetadata metadata = modContainer.getMetadata();
			JsonElement element = new Gson().toJsonTree(metadata);
			arr.add(element);

			Object mod = modContainer.getMod();
			if (mod == null)
				continue;
			Mod annotation = mod.getClass().getAnnotation(Mod.class);
			if (annotation == null)
				continue;
			String acceptedMinecraftVersions = annotation.acceptedMinecraftVersions();
			String dependencies = annotation.dependencies();
			element.getAsJsonObject().addProperty("mod-dependencies", dependencies);
			element.getAsJsonObject().addProperty("mod-acceptedMinecraftVersions", acceptedMinecraftVersions);
		}
		return new GsonBuilder().setPrettyPrinting().create().toJson(root);
	}

	public static void dumpAllMod(File file)
	{
		if (!file.exists())
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		try
		{
			FileUtils.write(file, dumpAllMod());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


}
