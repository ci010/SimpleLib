package api.simplelib.ui;

import api.simplelib.seril.SerialKey;
import api.simplelib.ui.node.DrawNode;
import api.simplelib.utils.TextureInfo;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public interface ElementAPI
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
			DRAW_TEXTURE = ElementRepository.repository.fetchNode(LOC_DRAW_TEXTURE),
			DRAW_STRING = ElementRepository.repository.fetchNode(LOC_DRAW_STRING),
			DRAW_BORDER_TEXTS = ElementRepository.repository.fetchNode(LOC_DRAW_BORDER_TEXTS),
			DRAW_BACKGROUND = ElementRepository.repository.fetchNode(LOC_DRAW_BACKGROUND),
			DRAW_PROGRESS = ElementRepository.repository.fetchNode(LOC_DRAW_PROGRESS);

	DrawNode
			PRE = ElementRepository.repository.fetchNode(new ResourceLocation("draw:pre")),
			POST = ElementRepository.repository.fetchNode(new ResourceLocation("draw:post"));

	SerialKey.Json<TextureInfo> TEXTURE = null;


//	Properties.Key<Integer> PROP_FADE_TIME = ElementRepository.repository.fetchKey(LOC_ANIM_FADE_IN, Integer.class);
//	Properties.Key<String[]> PROP_STRING = ElementRepository.repository.fetchKey(LOC_DRAW_STRING, String[].class);
//	Properties.Key<Object[]> PROP_STRING_SRC = ElementRepository.repository.fetchKey(LOC_DRAW_STRING, Object[].class);
//	Properties.Key<TextureInfo> PROP_TEXTURE = ElementRepository.repository.fetchKey(LOC_DRAW_TEXTURE, TextureInfo.class);
//	Properties.Key<Pair<Integer, Integer>> PROP_BACK_SIZE = ElementRepository.repository.fetchKey(DRAW_BACKGROUND, TypeUtils.<Pair<Integer, Integer>>cast(Pair.class));
//	Properties.Key<ElementBar.Direction> PROP_DIRECTION = ElementRepository.repository.fetchKey(DRAW_PROGRESS, ElementBar.Direction.class);
//	Properties.Key<Float> PROP_PROGRESS = ElementRepository.repository.fetchKey(DRAW_PROGRESS, Float.class);
//	Properties.Key<Boolean> PROP_ON_FRONT = ElementRepository.repository.fetchKey(new ResourceLocation("container:front"), boolean.class);
//
//	new Properties.Updatable<String>()
//	{
//		public IJsonSerializer<String> serializer()
//		{
//			return new IJsonSerializer<String>()
//			{
//				@Override
//				public JsonElement serialize(String src, PrimitiveType typeOfSrc, JsonSerializationContext context)
//				{
//					return new JsonPrimitive(src);
//				}
//
//				@Override
//				public String serialize(JsonElement json, PrimitiveType typeOfT, JsonDeserializationContext context) throws JsonParseException
//				{
//					return json.getAsString();
//				}
//			};
//		}
//
//		@Override
//		public String getName()
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
//		public INBTSerializer<String> serializer()
//		{
//			return new INBTSerializer<String>()
//			{
//				@Override
//				public String readFromNBT(NBTTagCompound tag, @Nullable String data)
//				{
//					return tag.getString(getName());
//				}
//
//				@Override
//				public void writeToNBT(NBTTagCompound tag, String data)
//				{
//					tag.setString(getName(), data);
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
//public JsonElement serialize(TextureInfo src,PrimitiveType typeOfSrc,JsonSerializationContext context)
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
//public TextureInfo serialize(JsonElement json,PrimitiveType typeOfT,JsonDeserializationContext context)throws JsonParseException
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
//public String getName()
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
//		public INBTSerializer<TextureInfo> serializer()
//		{
//			return new INBTSerializer<TextureInfo>()
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
//public static final Properties.Updatable<Pair<Integer, Integer>>PROP_BACKSIZE=new Properties.Updatable<Pair<Integer, Integer>>()
//		{
//
//@Override
//public JsonElement serialize(Pair<Integer, Integer>src,PrimitiveType typeOfSrc,JsonSerializationContext context)
//		{
//		JsonArray arr=new JsonArray();
//		arr.add(new JsonPrimitive(src.getLeft()));
//		arr.add(new JsonPrimitive(src.getRight()));
//		return arr;
//		}
//
//@Override
//public Pair<Integer, Integer>serialize(JsonElement json,PrimitiveType typeOfT,JsonDeserializationContext context)throws JsonParseException
//		{
//		if(json.isJsonArray())
//		{
//		JsonArray array=json.getAsJsonArray();
//		return Pair.of(array.var(0).getAsInt(),array.var(1).getAsInt());
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
//public String getName()
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
//		public INBTSerializer<Pair<Integer, Integer>> serializer()
//		{
//			return new INBTSerializer<Pair<Integer, Integer>>()
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
