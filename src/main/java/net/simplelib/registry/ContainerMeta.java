package net.simplelib.registry;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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
	private String[] langType;
	private ModelHandler<MinecraftComponent> modelHandler;
	private Set<ImmutableSet<Namespace>> unregistered;

	public ContainerMeta(String modid)
	{
		this.modid = modid;
		this.fields = Sets.newHashSet();
		this.unregistered = Sets.newHashSet();
	}

	public ContainerMeta setModelHandler(ModelHandler<MinecraftComponent> modelHandler)
	{
		this.modelHandler = modelHandler;
		return this;
	}

	public ModelHandler<MinecraftComponent> getModelHandler()
	{
		return modelHandler;
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

	public ContainerMeta addUnregistered(ImmutableSet<Namespace> struct)
	{
		this.unregistered.add(struct);
		return this;
	}

	public Iterable<ImmutableSet<Namespace>> getUnregistered()
	{
		return this.unregistered;
	}
}
