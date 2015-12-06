package net.ci010.minecrafthelper.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import net.minecraft.block.IGrowable;
import org.apache.commons.io.output.FileWriterWithEncoding;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.collection.generic.Growable;

@SideOnly(Side.CLIENT)
public class FileGenerator
{
	static File mc = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "mcDataDir");
	static File f = getDir(mc, "FakeAsset");
	File modF, dirBlockState, dirModel, dirModelBlock, dirModelItem, dirTextureBlock, dirTextureItem, dirLang,
			standardItem;
	/**
	 * The support language type of the mod.
	 */
	Set<File> fileLang = Sets.newHashSet();
	/**
	 * The keys needed to be localized.
	 */
	List<String> nodes = Lists.newArrayList(), missingBlock = Lists.newArrayList(), missingItem = Lists.newArrayList();

	String domain;

	public FileGenerator(String id)
	{
		this.domain = id;
		modF = getDir(f, domain);
		dirBlockState = getDir(modF, "blockstates");
		dirModel = getDir(modF, "models");
		dirModelBlock = getDir(dirModel, "block");
		dirModelItem = getDir(dirModel, "item");
		dirLang = getDir(modF, "lang");
		File dirTexutre = getDir(modF, "textures");
		dirTextureBlock = getDir(dirTexutre, "blocks");
		dirTextureItem = getDir(dirTexutre, "items");
	}

	public FileGenerator setLangType(String[] str)
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

	public void writeLang() throws IOException
	{
		for (File lang : fileLang)
		{
			BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(lang, "UTF-8"));
			Collections.sort(nodes);
			for (String name : nodes)
			{
				writer.write(name);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
	}

	public void lang(BlockItemStruct data)
	{
		if (data.blocks() != null)
			for (Block block : data.blocks())
				nodes.add(block.getUnlocalizedName().concat(".name="));
		if (data.items() != null)
			for (Item item : data.items())
				nodes.add(item.getUnlocalizedName().concat(".name="));
	}

	public void model(BlockItemStruct data) throws IOException
	{
		if (data.blocks() != null)
			for (Block block : data.blocks())
			{
				String name = block.getUnlocalizedName().substring(5).replace(".", "_");
				if(block instanceof IGrowable)
				{

				}
				File blockState = new File(dirBlockState, name.concat(".json"));
				File modelBlock = new File(dirModelBlock, name.concat(".json"));
				File modelItem = new File(dirModelItem, name.concat(".json"));

				if (!blockState.exists())
					blockState.createNewFile();
				if (!modelBlock.exists())
					modelBlock.createNewFile();
				if (!modelItem.exists())
					modelItem.createNewFile();

				FileWriter writer = new FileWriter(blockState);
				writer.write("{\n" +
						"    \"variants\": {\n" +
						"        \"normal\": { \"model\": \"".concat(domain) + ":".concat(name) + "\" }\n" +
						"    }\n" +
						"}");
				writer.close();
				writer = new FileWriter(modelBlock);
				writer.write("{\n" +
						"    \"parent\": \"block/cube_all\",\n" +
						"    \"textures\": {\n" +
						"        \"all\": \"".concat(domain) + ":blocks/".concat(name) + "\"\n" +
						"    }\n" +
						"}");
				writer.close();
				writer = new FileWriter(modelItem);
				writer.write("{\n" +
						"    \"parent\": \"".concat(domain) + ":block/".concat(name) + "\",\n" +
						"    \"display\": {\n" +
						"        \"thirdperson\": {\n" +
						"            \"rotation\": [ 10, -45, 170 ],\n" +
						"            \"translation\": [ 0, 1.5, -2.75 ],\n" +
						"            \"scale\": [ 0.375, 0.375, 0.375 ]\n" +
						"        }\n" +
						"    }\n" +
						"}");
				writer.close();
				if (!new File(dirTextureBlock, name.concat(".png")).exists())
					this.missingBlock.add(name);
			}
		if (data.items() != null)
			for (Item item : data.items())
			{
				FileWriter writer;
				if (this.standardItem == null)
					standardItem = new File(dirModelItem, "standard_item.json");

				writer = new FileWriter(standardItem);
				writer.write("{\n" +
						"    \"parent\":\"builtin/generated\",\n" +
						"    \"display\": {\n" +
						"        \"thirdperson\": {\n" +
						"            \"rotation\": [ -90, 0, 0 ],\n" +
						"            \"translation\": [ 0, 1, -3 ],\n" +
						"            \"scale\": [ 0.55, 0.55, 0.55 ]\n" +
						"        },\n" +
						"        \"firstperson\": {\n" +
						"            \"rotation\": [ 0, -135, 25 ],\n" +
						"            \"translation\": [ 0, 4, 2 ],\n" +
						"            \"scale\": [ 1.7, 1.7, 1.7 ]\n" +
						"        }\n" +
						"    }\n" +
						"}");
				String name = item.getUnlocalizedName().substring(5).replace('.', '_');
				File model = new File(dirModelItem, name.concat("" + ".json"));
				if (!model.exists())
				{
					model.createNewFile();

					writer = new FileWriter(model);
					writer.write("{\n" +
							"    \"parent\":\"".concat(domain) + ":item/standard_item\",\n" +
							"    \"textures\": {\n" +
							"        \"layer0\":\"".concat(domain) + ":items/".concat(name) + "\"\n" +
							"    }\n" +
							"}");
					if (!new File(dirTextureItem, name.concat(".png")).exists())
						this.missingItem.add(name);
				}
			}
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		Gson gson = new Gson();
		File miss = new File(modF, "missing_texture.json");
		if (!miss.exists())
			miss.createNewFile();
		FileWriter writer = new FileWriter(miss);
		writer.write("Block:\n");
		writer.write(gson.toJson(this.missingBlock));
		writer.write("\nItem:\n");
		writer.write(gson.toJson(this.missingItem));
		writer.close();
	}

	private static File getDir(File parent, String name)
	{
		File f = new File(parent, name);
		if (!f.exists())
			f.mkdirs();
		return f;
	}
}
