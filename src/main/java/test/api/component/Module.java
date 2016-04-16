package test.api.component;


/**
 * @author ci010
 */
public class Module<T>
{
	private final String name;
	private final Class<T> type;
	private final ModuleContainer<T> container;

	public Module(String name, Class<T> type, ModuleContainer<T> container)
	{
		this.name = name;
		this.type = type;
		this.container = container;
	}


	public String getName()
	{
		return name;
	}

	public ModuleContainer<T> getContainer()
	{
		return container;
	}

	public Class<T> getType()
	{
		return type;
	}
}
