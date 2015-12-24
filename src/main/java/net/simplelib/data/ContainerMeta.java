package net.simplelib.data;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.simplelib.abstracts.BlockItemStruct;
import net.simplelib.abstracts.ModelHandler;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

/**
 * @author ci010
 */
public class ContainerMeta
{
	public final String modid;
	private boolean ifGenerateLang, ifGenerateModel;
	private Set<Field> fields;
	private Set<BasicInfo> unregistered;
	private String[] langType;
	private ModelHandler<Block> blockM;
	private ModelHandler<Item> item;

	public ContainerMeta(String modid)
	{
		this.modid = modid;
		this.fields = Sets.newHashSet();
		this.unregistered = Sets.newHashSet();
	}

	public ModelHandler<Item> getItemModelHandler()
	{
		return this.item;
	}

	public ModelHandler<Block> getBlockModelHandler()
	{
		return this.blockM;
	}

	public ContainerMeta lang(boolean b)
	{
		this.ifGenerateLang = b;
		return this;
	}

	public ContainerMeta langType(String[] t)
	{
		this.langType = t;
		return this;
	}

	public String[] langType()
	{
		return this.langType;
	}

	public boolean needLang()
	{
		return this.ifGenerateLang;
	}

	public ContainerMeta model(boolean b)
	{
		this.ifGenerateModel = b;
		return this;
	}

	public boolean needModel()
	{
		return this.ifGenerateModel;
	}

	public void addField(Field f)
	{
		System.out.println("ADDFIELD " + f.getName() + " in " + modid);
		this.fields.add(f);
	}

	public ContainerMeta addField(Collection<Field> f)
	{
		this.fields.addAll(f);
		return this;
	}

	public Set<Field> getFields()
	{
		return this.fields;
	}

	public ContainerMeta addUnregistered(BlockItemStruct struct, String name, boolean needOre)
	{
		this.unregistered.add(new BasicInfo(struct, name, null, needOre));
		return this;
	}

	public ContainerMeta addUnregistered(BlockItemStruct struct, String name, String ore)
	{
		this.unregistered.add(new BasicInfo(struct, name, ore, true));
		return this;
	}

	public Iterable<BasicInfo> getUnregistered()
	{
		return this.unregistered;
	}

	public class BasicInfo
	{
		public BlockItemStruct struct;
		public String name;
		public boolean needOre;
		public String oreDicName;

		public BasicInfo(BlockItemStruct struct, String name, String oreDicName, boolean needOre)
		{
			this.struct = struct;
			this.name = name;
			this.oreDicName = oreDicName;
			this.needOre = needOre;
		}
	}
}
