package api.simplelib.remote.gui;

import api.simplelib.remote.gui.components.GuiBar;
import api.simplelib.remote.gui.node.DrawNode;
import api.simplelib.utils.TextureInfo;
import api.simplelib.utils.TypeUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import static api.simplelib.remote.gui.ComponentRepository.repository;

/**
 * @author ci010
 */
public interface ComponentAPI
{
	ResourceLocation
			LOC_DRAW_TEXTURE = new ResourceLocation("draw:texture"),
			LOC_DRAW_STRING = new ResourceLocation("draw:string"),
			LOC_DRAW_BORDER_TEXTS = new ResourceLocation("draw:border_texts"),
			LOC_DRAW_BACKGROUND = new ResourceLocation("draw:background"),
			LOC_DRAW_PROGRESS = new ResourceLocation("draw:progress"),
			LOC_ANIM_FADE_IN = new ResourceLocation("anim:fade_in"),
			LOC_ANIM_FADE_OUT = new ResourceLocation("anim:fade_out");

	DrawNode
			DRAW_TEXTURE = repository.fetchNode(LOC_DRAW_TEXTURE),
			DRAW_STRING = repository.fetchNode(LOC_DRAW_STRING),
			DRAW_BORDER_TEXTS = repository.fetchNode(LOC_DRAW_BORDER_TEXTS),
			DRAW_BACKGROUND = repository.fetchNode(LOC_DRAW_BACKGROUND),
			DRAW_PROGRESS = repository.fetchNode(LOC_DRAW_PROGRESS);

	DrawNode
			PRE = repository.fetchNode(new ResourceLocation("draw:pre")),
			POST = repository.fetchNode(new ResourceLocation("draw:post"));

	Properties.Key<Integer> PROP_FADE_TIME = repository.fetchKey(LOC_ANIM_FADE_IN, Integer.class);
	Properties.Key<String[]> PROP_STRING = repository.fetchKey(DRAW_STRING, String[].class);
	Properties.Key<Object[]> PROP_STRING_SRC = repository.fetchKey(DRAW_STRING, Object[].class);
	Properties.Key<TextureInfo> PROP_TEXTURE = repository.fetchKey(DRAW_TEXTURE, TextureInfo.class);
	Properties.Key<Pair<Integer, Integer>> PROP_BACK_SIZE = repository.fetchKey(DRAW_BACKGROUND, TypeUtils.<Pair<Integer, Integer>>cast(Pair.class));
	Properties.Key<GuiBar.Direction> PROP_DIRECTION = repository.fetchKey(DRAW_PROGRESS, GuiBar.Direction.class);
	Properties.Key<Float> PROP_PROGRESS = repository.fetchKey(DRAW_PROGRESS, Float.class);
	Properties.Key<Boolean> PROP_ON_FRONT = repository.fetchKey(new ResourceLocation("container:front"), boolean.class);


//	new Properties.Key<String>()
//	{
//		public IJsonSerializer<String> serializer()
//		{
//			return new IJsonSerializer<String>()
//			{
//				@Override
//				public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context)
//				{
//					return new JsonPrimitive(src);
//				}
//
//				@Override
//				public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
//				{
//					return json.getAsString();
//				}
//			};
//		}
//
//		@Override
//		public String name()
//		{
//			return "string";
//		}
//
//		@Override
//		public Class<String> type()
//		{
//			return String.class;
//		}

//		@Override
//		public ITagSerializer<String> serializer()
//		{
//			return new ITagSerializer<String>()
//			{
//				@Override
//				public String readFromNBT(NBTTagCompound tag, @Nullable String data)
//				{
//					return tag.getString(name());
//				}
//
//				@Override
//				public void writeToNBT(NBTTagCompound tag, String data)
//				{
//					tag.setString(name(), data);
//				}
//			};
//		}
//};
//
//{
//@Override
//public IJsonSerializer<TextureInfo>serializer()
//		{
//		return new IJsonSerializer<TextureInfo>()
//		{
//@Override
//public JsonElement serialize(TextureInfo src,Type typeOfSrc,JsonSerializationContext context)
//		{
//		JsonObject o=new JsonObject();
//		o.addProperty("location",src.getTexture().toString());
//		o.addProperty("u",src.getU());
//		o.addProperty("v",src.getV());
//		o.addProperty("height",src.getHeight());
//		o.addProperty("width",src.getWidth());
//		return o;
//		}
//
//@Override
//public TextureInfo deserialize(JsonElement json,Type typeOfT,JsonDeserializationContext context)throws JsonParseException
//		{
//		if(json.isJsonObject())
//		{
//		JsonObject o=json.getAsJsonObject();
//		return new TextureInfo(
//		new ResourceLocation(o.getAsJsonPrimitive("location").getAsString()),
//		o.getAsJsonPrimitive("u").getAsInt(),
//		o.getAsJsonPrimitive("v").getAsInt(),
//		o.getAsJsonPrimitive("width").getAsInt(),
//		o.getAsJsonPrimitive("height").getAsInt());
//		}
//		return null;
//		}
//		};
//		}
//
//@Override
//public String name()
//		{
//		return"texture";
//		}
//
//@Override
//public Class<TextureInfo>type()
//		{
//		return TextureInfo.class;
//}

//		@Override
//		public ITagSerializer<TextureInfo> serializer()
//		{
//			return new ITagSerializer<TextureInfo>()
//			{
//				@Override
//				public TextureInfo readFromNBT(NBTTagCompound tag, @Nullable TextureInfo data)
//				{
//					ResourceLocation location = new ResourceLocation(tag.getString("location"));
//					int u = tag.getInteger("u"), v = tag.getInteger("v"), width = tag.getInteger("width"),
//							height = tag.getInteger("height");
//					return new TextureInfo(location, u, v, width, height);
//				}
//
//				@Override
//				public void writeToNBT(NBTTagCompound tag, TextureInfo data)
//				{
//					tag.setString("location", data.getTexture().toString());
//					tag.setInteger("u", data.getU());
//					tag.setInteger("v", data.getV());
//					tag.setInteger("width", data.getWidth());
//					tag.setInteger("height", data.getHeight());
//				}
//			};
//		}
//
//public static final Properties.Key<Pair<Integer, Integer>>PROP_BACKSIZE=new Properties.Key<Pair<Integer, Integer>>()
//		{
//
//@Override
//public JsonElement serialize(Pair<Integer, Integer>src,Type typeOfSrc,JsonSerializationContext context)
//		{
//		JsonArray arr=new JsonArray();
//		arr.add(new JsonPrimitive(src.getLeft()));
//		arr.add(new JsonPrimitive(src.getRight()));
//		return arr;
//		}
//
//@Override
//public Pair<Integer, Integer>deserialize(JsonElement json,Type typeOfT,JsonDeserializationContext context)throws JsonParseException
//		{
//		if(json.isJsonArray())
//		{
//		JsonArray array=json.getAsJsonArray();
//		return Pair.of(array.get(0).getAsInt(),array.get(1).getAsInt());
//		}
//		return null;
//		}
//
//@Override
//public IJsonSerializer<Pair<Integer, Integer>>serializer()
//		{
//		return null;
//		}
//
//@Override
//public String name()
//		{
//		return"back_size";
//		}
//
//@Override
//public Class<Pair<Integer, Integer>>type()
//		{
//		return null;
//		}

//		@Override
//		public ITagSerializer<Pair<Integer, Integer>> serializer()
//		{
//			return new ITagSerializer<Pair<Integer, Integer>>()
//			{
//				@Override
//				public Pair<Integer, Integer> readFromNBT(NBTTagCompound tag, @Nullable Pair<Integer, Integer> data)
//				{
//					return Pair.of(tag.getInteger("xSize"), tag.getInteger("ySize"));
//				}
//
//				@Override
//				public void writeToNBT(NBTTagCompound tag, Pair<Integer, Integer> data)
//				{
//					tag.setInteger("xSize", data.getLeft());
//					tag.setInteger("ySize", data.getRight());
//				}
//			};
//		}
}
