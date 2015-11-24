package net.ci010.minecrafthelper.data;

import com.google.common.collect.Sets;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.ci010.minecrafthelper.abstracts.ModelHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by John on 2015/10/29 0029.
 */
public class ContainerMeta
{
	public final String modid;
	private boolean ifGenerateLang, ifGenerateModel;
	private Set<Field> fields;
	private Set<BlockItemStruct> registered;
	private String[] langType;
	private ModelHandler<Block> blockM;
	private ModelHandler<Item> item;

	public ContainerMeta(String modid)
	{
		this.modid = modid;
		this.fields = Sets.newHashSet();
		this.registered = Sets.newHashSet();
	}

	public void add(BlockItemStruct struct)
	{
		this.registered.add(struct);
	}

	public Iterable<BlockItemStruct> getRegistered()
	{
		return new Iterable<BlockItemStruct>()
		{
			@Override
			public Iterator<BlockItemStruct> iterator()
			{
				return registered.iterator();
			}
		};
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

}
