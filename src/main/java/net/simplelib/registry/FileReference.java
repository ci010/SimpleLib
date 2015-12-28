package net.simplelib.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class FileReference
{
	public static final File mc = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "mcDataDir");

	private static Map<String, FileReference> refers = Maps.newHashMap();
	protected static File assets = getDir(mc, "FakeAsset");

	protected File modFile, dirBlockState, dirModelBlock, dirModelItem, dirTextureBlock, dirTextureItem, dirLang,
			standardItem;

	private List<String> missingBlock = Lists.newArrayList(), missingItem = Lists
			.newArrayList();

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

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
//		Gson gson = new Gson();
//		File miss = new File(modFile, "missing_texture.json");
//		if (!miss.exists())
//			miss.createNewFile();
//		FileWriter writer = new FileWriter(miss, inited);
//		writer.write("Block:\n");
//		writer.write(gson.toJson(this.missingBlock));
//		writer.write("\nItem:\n");
//		writer.write(gson.toJson(this.missingItem));
//		writer.close();
//		inited = true;
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
