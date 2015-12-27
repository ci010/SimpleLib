package net.simplelib.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.annotation.type.Handler;
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
public class FileGenerator
{
	private static boolean inited;
	public static final File mc = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "mcDataDir");

	private static Map<String, FileGenerator> generators = Maps.newHashMap();
	private static File assets = getDir(mc, "FakeAsset");

	private File modFile, dirBlockState, dirModelBlock, dirModelItem, dirTextureBlock, dirTextureItem, dirLang,
			standardItem;
	/**
	 * The support language type of the mod.
	 */
	private Set<File> fileLang = Sets.newHashSet();
	/**
	 * The keys needed to be localized.
	 */
	private List<String> langNodes = Lists.newArrayList(), missingBlock = Lists.newArrayList(), missingItem = Lists
			.newArrayList();

	public FileGenerator(String id)
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
		generators.put(id, this);
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
			BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(lang, "UTF-8", inited));
			Collections.sort(langNodes);
			for (String name : langNodes)
			{
				writer.write(name);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
	}

	public void lang(String unlocalizedName)
	{
		langNodes.add(unlocalizedName.concat(".name="));
	}

	public void lang(Block block)
	{
		this.lang(block.getUnlocalizedName());
	}

	public void lang(Item item)
	{
		this.lang(item.getUnlocalizedName());
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		Gson gson = new Gson();
		File miss = new File(modFile, "missing_texture.json");
		if (!miss.exists())
			miss.createNewFile();
		FileWriter writer = new FileWriter(miss, inited);
		writer.write("Block:\n");
		writer.write(gson.toJson(this.missingBlock));
		writer.write("\nItem:\n");
		writer.write(gson.toJson(this.missingItem));
		writer.close();
		inited = true;
	}

	private static File getDir(File parent, String name)
	{
		File f = new File(parent, name);
		if (!f.exists())
			f.mkdirs();
		return f;
	}
//			Map<RegistryDelegate<Block>, IStateMapper> map = ReflectionHelper.getPrivateValue(ModelLoader.class, null,
//					"customStateMappers");
//			for (Map.Entry<RegistryDelegate<Block>, IStateMapper> entry : map.entrySet())
//			{
//				RegistryDelegate<Block> key = entry.getKey();
//				IStateMapper value = entry.getValue();
//
//
//			}

	@Handler
	public static class FileHandler
	{
		static ModelResourceLocation MISS = new ModelResourceLocation("builtin/missing", "missing");

		@SubscribeEvent
		public void onModelPost(ModelBakeEvent event)
		{
			List<BlockStateCollection> list = Lists.newArrayList();
			Set<ModelResourceLocation> set = ReflectionHelper.getPrivateValue(ModelLoader.class, event.modelLoader,
					"missingVariants");
			IRegistry modelReg = event.modelRegistry;

			Object missingModel = modelReg.getObject(MISS);
			for (ModelResourceLocation missing : set)
			{
				BlockStateCollection temp = new BlockStateCollection(missing.getResourceDomain(), missing
						.getResourcePath());
				if (list.contains(temp))
					list.get(list.indexOf(temp)).addVar(missing.getVariant());
				else
					list.add(temp.addVar(missing.getVariant()));
			}
			Gson gson = this.buildGson();
			for (BlockStateCollection state : list)
			{
				FileGenerator generator = generators.get(state.domain);
				File stateFile = new File(generator.dirBlockState, state.path.concat(".json"));
				try
				{
					if (!stateFile.exists())
						stateFile.createNewFile();
					FileWriter writer = new FileWriter(stateFile);
					writer.write(gson.toJson(state).replace('`', '='));
					writer.flush();
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				for (String s : state.varPath)
				{
					File modelFile = new File(generator.dirModelBlock, state.domain.concat(s).concat(".json"));
					BlockModelSimpleInfo info = new BlockModelSimpleInfo();
					info.texture = state.domain.concat(":").concat(s).concat("_").concat("texture");
					try
					{
						if (!modelFile.exists())
							modelFile.createNewFile();
						FileWriter writer = new FileWriter(modelFile);
						writer.write(gson.toJson(info));
						writer.flush();
						writer.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				File itemModelFile = new File(generator.dirModelItem, state.path.concat(".json"));
				ItemModelSimpleInfo info = new ItemModelSimpleInfo();
				info.thirdperson = new PersonView();
				info.thirdperson.rotation = new int[]{10, -45, 170};
				info.thirdperson.translation = new float[]{0f, 1.5f, -2.75f};
				info.thirdperson.scale = new float[]{0.375f, 0.375f, 0.375f};

				try
				{
					if (!itemModelFile.exists())
						itemModelFile.createNewFile();
					FileWriter writer = new FileWriter(itemModelFile);
					writer.write(gson.toJson(info));
					writer.flush();
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
//				String out = gson.toJson(state).replace('`', '=');
//				System.out.println(out);
			}
			standard = null;
			generators = null;
		}

		private Gson buildGson()
		{
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BlockStateCollection.class,
					new TypeAdapter<BlockStateCollection>()
					{
						@Override
						public void write(JsonWriter out, BlockStateCollection value) throws IOException
						{
							out.setIndent("  ");
							out.beginObject();
							out.name("variants").beginObject();
							for (int i = 0; i < value.vars.size(); ++i)
							{
								String var = value.vars.get(i);
								String varPth = value.varPath.get(i);
								var = var.replace('=', '`');
								out.name(var).beginObject();
								out.name("model").value(value.domain.concat(":").concat(varPth)).endObject();
							}
							out.endObject().endObject();
						}

						@Override
						public BlockStateCollection read(JsonReader in) throws IOException {return null;}
					}).registerTypeAdapter(BlockModelSimpleInfo.class,
					new TypeAdapter<BlockModelSimpleInfo>()
					{
						@Override
						public void write(JsonWriter out, BlockModelSimpleInfo value) throws IOException
						{
							out.setIndent("  ");
							out.beginObject();
							out.name("parent").value(value.parent);
							out.name("textures").beginObject();
							out.name("all").value(value.texture);
							out.endObject().endObject();
						}

						@Override
						public BlockModelSimpleInfo read(JsonReader in) throws IOException {return null;}
					}).registerTypeAdapter(ItemModelSimpleInfo.class,
					new TypeAdapter<ItemModelSimpleInfo>()
					{
						@Override
						public void write(JsonWriter out, ItemModelSimpleInfo value) throws IOException
						{
							out.setIndent("  ");
							out.beginObject();
							out.name("parent").value(value.parent);
							out.name("display").beginObject();
							this.write("thirdperson", out, value.thirdperson);
							if (value.firstperson != null)
								this.write("firstperson", out, value.firstperson);
							out.endObject().endObject();
						}

						private void write(String name, JsonWriter out, PersonView view) throws IOException
						{
							out.setIndent("  ");
							out.name(name).beginObject();
							out.name("rotation").beginArray();
							for (int i : view.rotation)
								out.value(i);
							out.endArray();
							out.name("translation").beginArray();
							for (float i : view.translation)
								out.value(i);
							out.endArray();
							out.name("scale").beginArray();
							for (float v : view.scale)
								out.value(v);
							out.endArray().endObject();
						}

						@Override
						public ItemModelSimpleInfo read(JsonReader in) throws IOException {return null;}
					});
			return builder.create();
		}
	}

	private static class ItemModelSimpleInfo
	{
		String parent;
		PersonView thirdperson, firstperson;
	}

	private static class PersonView
	{
		int[] rotation;
		float[] scale, translation;
	}

	private static class BlockModelSimpleInfo
	{
		String parent = "block/cube_all", texture;
	}

	private static ItemModelSimpleInfo standard = new ItemModelSimpleInfo()
	{
		{
			parent = "builtin/generated";
			thirdperson = new PersonView();
			thirdperson.rotation = new int[]{-90, 0, 0};
			thirdperson.translation = new float[]{0, 1, -3};
			thirdperson.scale = new float[]{0.55f, 0.55f, 0.55f};
			firstperson = new PersonView();
			firstperson.rotation = new int[]{0, -135, 25};
			firstperson.translation = new float[]{0, 4, 2};
			firstperson.scale = new float[]{1.7f, 1.7f, 1.7f};
		}
	};

	private static class BlockStateCollection
	{
		String domain, path;
		List<String> vars;
		List<String> varPath;

		public BlockStateCollection(String domain, String path)
		{
			this.domain = domain;
			this.path = path;
			vars = Lists.newArrayList();
			varPath = Lists.newArrayList();
		}

		BlockStateCollection addVar(String var)
		{
			this.vars.add(var);
			int idx = var.indexOf("=");
			if (idx != -1)
				this.varPath.add(path.concat("_").concat(var.substring(var.indexOf("=") + 1)));
			else
				this.varPath.add(path);
			return this;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof BlockStateCollection)
			{
				BlockStateCollection stat = (BlockStateCollection) obj;
				return stat.domain.equals(domain) && stat.path.equals(path);
			}
			return super.equals(obj);
		}

		@Override
		public String toString()
		{
			return domain.concat(":").concat(path);
		}
	}

}
