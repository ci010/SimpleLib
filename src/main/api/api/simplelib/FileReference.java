package api.simplelib;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class FileReference
{
	public static final File mc = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "mcDataDir");
	private static Map<String, FileReference> refers = Maps.newHashMap();
	protected static File assets = getDir(mc, "debug-reports");

	public static final File saveDir;

	static
	{
		SaveFormatOld saveLoader = (SaveFormatOld) Minecraft.getMinecraft().getSaveLoader();
		saveDir = saveLoader.savesDirectory;
	}

	public final File modFile, dirBlockState, dirModelBlock, dirModelItem, dirTextureBlock, dirTextureItem, dirLang;

	public static void registerFile(String modid)
	{
		refers.put(modid, new FileReference(modid));
	}

	private FileReference(String id)
	{
		modFile = getDir(assets, id);
		dirBlockState = getDir(modFile, "blockstates");
		File dirModel = getDir(modFile, "models");
		dirModelBlock = getDir(dirModel, "block");
		dirModelItem = getDir(dirModel, "item");
		dirLang = getDir(modFile, "lang");
		File dirTexutre = getDir(modFile, "textures");
		dirTextureBlock = getDir(dirTexutre, "blocks");
		dirTextureItem = getDir(dirTexutre, "items");
	}

	public static FileReference getRefer(String modid)
	{
		return refers.get(modid);
	}

	static void close()
	{
		refers = null;
	}

	private static File getDir(File parent, String name)
	{
		File f = new File(parent, name);
		if (!f.exists())
			f.mkdirs();
		return f;
	}
}
