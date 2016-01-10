package net.simplelib.common.registry;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.simplelib.common.registry.abstracts.MinecraftComponent;
import net.simplelib.common.registry.abstracts.ModelHandler;

import java.util.Set;

/**
 * @author ci010
 */
public class ContainerMeta
{
	public final String modid;
	private boolean ifGenerateLang, ifGenerateModel;
	private String[] langType;
	private Set<Class> rawContainer;
	private ModelHandler<MinecraftComponent> modelHandler;
	private Set<Namespace> unregistered;

	public ContainerMeta(String modid)
	{
		this.modid = modid;
		this.rawContainer = Sets.newHashSet();
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

	public Set<Class> getRawContainer()
	{
		return this.rawContainer;
	}

	public ContainerMeta addRawContainer(Class<?> containers)
	{
		this.rawContainer.add(containers);
		return this;
	}

	public boolean needModel()
	{
		return this.ifGenerateModel;
	}

	public ContainerMeta addUnregistered(ImmutableSet<Namespace> struct)
	{
		this.unregistered.addAll(struct);
		return this;
	}

	public ImmutableSet<Namespace> getUnregistered()
	{
		return ImmutableSet.copyOf(this.unregistered);
	}
}
