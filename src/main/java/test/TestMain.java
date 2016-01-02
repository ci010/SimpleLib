package test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.BlockPos;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author ci010
 */
public class TestMain
{
	public static void main(String[] args)
	{
//		IChatComponent component = new ChatComponentText("test&2string");
//		for (Object sub : component)
//		{
//			System.out.println(((IChatComponent) sub).getFormattedText());
//			System.out.println(((char) 167));
//
//		}
		new TestMain().testInner(new TestInterfaceImpl()
		{
			String s = "init";
		});

	}

	void testInner(TestInterface t)
	{
		for (Field field : t.getClass().getDeclaredFields())
		{
			System.out.println(field.getName());
			System.out.println(field.getType());
			System.out.println(field.isAccessible());
			try
			{
				System.out.println(field.get(t));
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}

			field.setAccessible(true);
			System.out.println(field.isAccessible());
			try
			{
				field.set(t, "end");
				System.out.println(field.get(t));

			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}

		}
	}

	class Inner
	{
		Object o = new Object();
	}

	static void test3DPattern()
	{
		List<BlockPos> pos = Lists.newArrayList(), result = Lists.newArrayList();
		pos.add(new BlockPos(0, 0, 0));
		pos.add(new BlockPos(1, 0, 0));
		pos.add(new BlockPos(0, 1, 0));
		pos.add(new BlockPos(0, 0, 1));
		pos.add(new BlockPos(2, 2, 2));
//		pos.add(new BlockPos(2, 0, 1));
//		pos.add(new BlockPos(1, 0, 2));
//		pos.add(new BlockPos(1, 1, 1));

		Collections.sort(pos);

		System.out.println(connected(pos, result, new BlockPos(0, 0, 0), 1, 1));
		for (BlockPos blockPos : result)
		{
			System.out.println(blockPos);
		}
	}

	public static boolean connected(List<BlockPos> blocks, List<BlockPos> done, BlockPos current, int direction, int
			original)
	{
		if (!done.contains(current))
			done.add(current);
		BlockPos next = nextBlock(current, direction);
//		if (!blocks.contains(next) || done.contains(next))
//		{
//			int nextDirection = (direction + 1) % 6;
//			if (nextDirection == original)
//				return true;
//			nextDirection = nextDirection == 0 ? 6 : nextDirection;
//			connected(blocks, done, current, nextDirection, original);
//		}
//		else
		if (blocks.contains(next) && !done.contains(next))
			connected(blocks, done, next, direction, direction);

		int nextDirection = (direction + 1) % 6;
		if (nextDirection == original)
			return true;
		nextDirection = nextDirection == 0 ? 6 : nextDirection;
		connected(blocks, done, current, nextDirection, original);

		return done.size() == blocks.size();
	}

	public static BlockPos nextBlock(BlockPos pos, int direction)
	{
		switch (direction)
		{
			case 1:
				return pos.north();
			case 2:
				return pos.east();
			case 3:
				return pos.south();
			case 4:
				return pos.west();
			case 5:
				return pos.up();
			case 6:
				return pos.down();
		}
		return pos;
	}

	class Pattern3D
	{
		List<BlockPos> sub;

		public boolean connected(List<BlockPos> blocks, List<BlockPos> done, BlockPos current, int direction, int
				original)
		{
			if (!done.contains(current))
				done.add(current);
			BlockPos next = nextBlock(current, direction);
			if (blocks.contains(next) && !done.contains(next))
				connected(blocks, done, next, direction, direction);
			int nextDirection = (direction + 1) % 6;
			if ((nextDirection = nextDirection == 0 ? 6 : nextDirection) == original)
				return true;
			connected(blocks, done, current, nextDirection, original);
			return done.size() == blocks.size();
		}

		public BlockPos nextBlock(BlockPos pos, int direction)
		{
			switch (direction)
			{
				case 1:
					return pos.north();
				case 2:
					return pos.east();
				case 3:
					return pos.south();
				case 4:
					return pos.east();
				case 5:
					return pos.up();
				case 6:
					return pos.down();
			}
			return pos;
		}

		Pattern3D(List<BlockPos> pos)
		{
			Collections.sort(this.sub);

			this.sub = pos;
		}

		public List<BlockPos> transfer(BlockPos pos)
		{
			List<BlockPos> result = Lists.newArrayList();
			BlockPos t = null;
			for (BlockPos blockPos : sub)
			{
				if (sub.contains(blockPos.north()))
					t = blockPos.north();
				result.add(blockPos.add(pos));
			}
			System.out.println(t);
			Collections.sort(result);

			return result;
		}
	}

	static void test2DPattern()
	{
		RecipePatter t = new RecipePatter("workbench", "workbench", 0, true);
		t.recipe[0][0] = "wood";
		t.recipe[0][1] = "wood";
		t.recipe[1][0] = "wood";
		t.recipe[1][1] = "wood";
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(RecipePatter.class, new
				TestAdaptor());

//		StringBuilder recipe = new StringBuilder(builder.create().toJson(t));
//		recipe.insert(recipe.indexOf(": {")+1, "\n   ");
		Gson recipe = builder.create();
		String json = recipe.toJson(t);
		System.out.println(json);
		RecipePatter r = recipe.fromJson(json, RecipePatter.class);

//		System.out.print(r.name);
	}

	static class TestPatternBuilder
	{
		String name, domain;
		int id, length, height;
		boolean shapeless;
		List<String> inputBuff = Lists.newArrayList();

		public void setLength(int length)
		{
			this.length = length;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public void setShapeless(boolean shapless)
		{
			this.shapeless = shapless;
		}

		public void add(String aNull)
		{
			inputBuff.add(aNull);
		}

		public RecipePatter build()
		{
			RecipePatter p = new RecipePatter(domain, name, id, shapeless);
			p.recipe = new String[length][height];
			Iterator<String> itr = inputBuff.iterator();
			for (int i = 0; i < length; ++i)
				for (int j = 0; j < height; ++j)
					p.recipe[i][j] = itr.next();
			return p;
		}
	}

	static class TestAdaptor extends TypeAdapter<RecipePatter>
	{
		@Override
		public void write(JsonWriter out, RecipePatter value) throws IOException
		{
			out.setIndent("    ");
			out.beginObject();
			{
				out.name("id");
//				out.value(value.name);
				out.name("windowId");
//				out.value(value.id);
				out.name("shapeless");
				out.value(value.shapeless);
				out.name("length");
				out.value(value.recipe.length);
				out.name("column");
				out.value(value.recipe[0].length);
				out.name("recipe");
				out.beginObject();
				{
					int line = 0;
					for (int i = 0; i < value.recipe.length; i++)
					{
						out.name(++line + "");
						out.beginArray();
						out.setIndent("");
						for (int j = 0; j < value.recipe[0].length; j++)
							out.value(value.recipe[i][j]);
						out.endArray();
						out.setIndent("    ");
					}
				}
				out.endObject();
			}
			out.endObject();
		}

		@Override
		public RecipePatter read(JsonReader in) throws IOException
		{
			TestPatternBuilder builder = new TestPatternBuilder();

			while (in.peek() != JsonToken.END_DOCUMENT)
				discover(in, builder);

			return builder.build();
		}

		private void discover(JsonReader in, TestPatternBuilder builder) throws IOException
		{
			JsonToken token = in.peek();
			String name = null, string = null;
			int num = 0;
			List<String> arr = null;
			switch (token)
			{
				case BEGIN_ARRAY:
					in.beginArray();
					System.out.println("arr start");
					arr = Lists.newArrayList();
					parseArr(in, builder);
					break;
				case END_ARRAY:
					in.endArray();
					break;
				case BEGIN_OBJECT:
					in.beginObject();
					break;
				case END_OBJECT:
					in.endObject();
					break;
				case NAME:
					parseElement(in, builder);
					break;
				case END_DOCUMENT:
					in.close();
					break;
			}
			if (arr != null)
				for (String s : arr)
					System.out.println(s);
		}

		private void parseElement(JsonReader in, TestPatternBuilder builder) throws IOException
		{
			String type = in.nextName();
			if (type.equals("id"))
				builder.setName(in.nextString());
			else if (type.equals("windowId"))
				builder.setId(in.nextInt());
			else if (type.equals("shapeless"))
				builder.setShapeless(in.nextBoolean());
			else if (type.equals("length"))
				builder.setLength(in.nextInt());
			else if (type.equals("column"))
				builder.setHeight(in.nextInt());
		}

		private void parseArr(JsonReader in, TestPatternBuilder builder) throws IOException
		{
			while (in.hasNext())
			{
				if (in.peek() == JsonToken.NULL)
				{
					builder.add("NULL");
					in.nextNull();
				}
				else
					builder.add(in.nextString());
			}
		}
	}
}
