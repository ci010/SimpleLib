package api.simplelib.utils;

import api.simplelib.registry.ModConfig;
import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Map;


/**
 * @author ci010
 */
public class Environment
{
	public static Environment getBean(String modid)
	{
		if (!refers.containsKey(modid))
			refers.put(modid, new Environment(modid));
		return refers.get(modid);
	}


	public File getLanguageDirectory()
	{
		return dirLang;
	}

	public File getModFile()
	{
		return modFile;
	}

	public File getBlockStateDirectory()
	{
		return dirBlockState;
	}

	public File getBlockModelDirectory()
	{
		return dirModelBlock;
	}

	public File getItemModelDirectory()
	{
		return dirModelItem;
	}

	public File getBlockTextureDirectory()
	{
		return dirTextureBlock;
	}

	public File getItemTextureDirectory()
	{
		return dirTextureItem;
	}

	public static File getLogDirectory()
	{
		return logDir;
	}

	public static File getSaveDirectory()
	{
		return saveDir;
	}

	public static File getModDirectory()
	{
		return modDir;
	}

	public static boolean isDebugEnvironment()
	{
		return DEBUG;
	}

	public static boolean isDeobfuscated()
	{
		return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}

	public static File getDirectory(File parent, String name)
	{
		File f = new File(parent, name);
		if (!f.exists())
			f.mkdirs();
		return f;
	}

	public static File getDirectory(String path)
	{
		return getDirectory(mc, path);
	}

	public static File getDirectory(File parent, String... path)
	{
		File f = parent;
		for (String s : path)
			f = getDirectory(f, s);
		return f;
	}

	@SideOnly(Side.CLIENT)
	@Beta
	public static void restartMC()
	{
		Runtime.getRuntime().addShutdownHook(new Restart());
		FMLCommonHandler.instance().exitJava(0, false);
	}

	@SideOnly(Side.CLIENT)
	private static class Restart extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				String cmd;
				List<String> cmds = Lists.newArrayList();
				RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
				String classPath = bean.getClassPath();
				List<String> vmOptions = bean.getInputArguments();
				String mainClass = System.getProperty("sun.java.command");

//				Runtime.getRuntime().exec("java " + Joiner.on(' ').join(vmOptions) + " -cp " + classPath + " " + mainClass);
				cmd = "start " + "\"" + System.getProperty("java.home") + File.separator + "bin" + File.separator +
						"java\"";
				cmds.add("start");
				cmds.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");
				for (String s : vmOptions)
				{
					if (s.contains(" "))
					{
						String[] split = s.split("=");
						if (split.length > 1)
						{
							split[1] = "\"" + split[1] + "\"";
							s = split[0] + split[1];
						}
					}
					if (s.contains("IDEA") || s.contains("idea"))
						continue;
					cmds.add(s);
					cmd = cmd + " " + s;
				}
				cmds.add("-cp");
				cmds.add(classPath);
				cmds.add(mainClass);
				cmd = cmd + " -cp \"" + classPath + "\" " + mainClass;
				System.out.println(cmd);
				Runtime.getRuntime().exec(cmd);
//				ProcessBuilder builder = new ProcessBuilder(cmds);
//				builder.start();

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private Environment(String modId)
	{
		modFile = getDirectory(modDir, modId);
		dirBlockState = getDirectory(modFile, "blockstates");
		File dirModel = getDirectory(modFile, "models");
		dirModelBlock = getDirectory(dirModel, "block");
		dirModelItem = getDirectory(dirModel, "item");
		dirLang = getDirectory(modFile, "lang");
		File dirTexutre = getDirectory(modFile, "textures");
		dirTextureBlock = getDirectory(dirTexutre, "blocks");
		dirTextureItem = getDirectory(dirTexutre, "items");
	}

	static
	{
		boolean fail = false;
		try
		{
			Class.forName("net.minecraftforge.gradle.GradleStartCommon");
		}
		catch (ClassNotFoundException e)
		{
			fail = true;
		}
		DEBUG = !fail;

	}

	public static final File mc = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "mcDataDir");

	private static boolean DEBUG;

	@ModConfig
	public static final boolean DUMP_OREDIC = DEBUG;
	public static final boolean DUMP_BLOCK_REGISTRY = false;

	//TODO dump ore dic

	private static File logDir, saveDir, modDir;
	private static Map<String, Environment> refers = Maps.newHashMap();

	private final File modFile, dirBlockState, dirModelBlock, dirModelItem, dirTextureBlock, dirTextureItem, dirLang;
}
