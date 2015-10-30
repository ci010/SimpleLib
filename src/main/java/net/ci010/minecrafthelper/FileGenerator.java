package net.ci010.minecrafthelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.output.FileWriterWithEncoding;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class FileGenerator
{
	static File mc = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "mcDataDir");
	static File f = getDir(mc, "FakeAsset");
	File modF, dirBlockState, dirModel, dirModelBlock, dirModelItem, dirLang;
	/**
	 * The support language type of the mod.
	 */
	Set<File> fileLang = Sets.newHashSet();
	/**
	 * The keys needed to be localized.
	 */
	List<String> nodes = Lists.newArrayList();
	String domain;

	FileGenerator()
	{
		modF = getDir(f, Loader.instance().activeModContainer().getModId());
		dirBlockState = getDir(modF, "blockstates");
		dirModel = getDir(modF, "models");
		dirModelBlock = getDir(dirModel, "block");
		dirModelItem = getDir(dirModel, "item");
		dirLang = getDir(modF, "lang");
		this.domain = Loader.instance().activeModContainer().getModId();
	}

	FileGenerator setLangType(String[] str)
	{
		try
		{
			for (String name : str)
			{
				File f = new File(dirLang, name.concat(".lang"));
				if (!f.exists())
					f.createNewFile();
				fileLang.add(f);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return this;
	}

	void writeLang() throws IOException
	{
		for (File lang : fileLang)
		{
			BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(lang, "UTF-8"));
			Collections.sort(nodes);
			for (String name : nodes)
			{
				writer.write(name.concat("="));
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
	}

	void lang(BlockItemStruct data)
	{
		if (data.blocks() != null)
			for (Block block : data.blocks())
				nodes.add(block.getUnlocalizedName().concat(".name="));
		if (data.items() != null)
			for (Item item : data.items())
				nodes.add(item.getUnlocalizedName().concat(".name="));
	}

	void model(BlockItemStruct data) throws IOException
	{
		if (data.blocks() != null)
			for (Block block : data.blocks())
			{
				String name = block.getUnlocalizedName();
				File blockstate = new File(dirBlockState, name.concat(".json"));
				File modelBlock = new File(dirModelBlock, name.concat(".json"));
				File modelItem = new File(dirModelItem, name.concat(".json"));

				if (!blockstate.exists())
					blockstate.createNewFile();
				if (!modelBlock.exists())
					modelBlock.createNewFile();
				if (!modelItem.exists())
					modelItem.createNewFile();
			}
		if (data.items() != null)
			for (Item item : data.items())
			{
				File model = new File(dirModelItem, item.getUnlocalizedName().replace('.', '_').concat(".json"));
				if (!model.exists())
					model.createNewFile();
			}
	}


	private static File getDir(File parent, String name)
	{
		File f = new File(parent, name);
		if (!f.exists())
			f.mkdirs();
		return f;
	}
}
