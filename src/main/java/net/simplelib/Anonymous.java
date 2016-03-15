package net.simplelib;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

/**
 * @author ci010
 */
public class Anonymous extends DummyModContainer
{
	private static Anonymous INSTANCE;

	public Anonymous()
	{
		super(new ModMetadata());
		ModMetadata metadata = this.getMetadata();
		metadata.authorList.add("*");
//		metadata.dependants
	}

	public static Anonymous instance()
	{
		if (INSTANCE == null)
			INSTANCE = new Anonymous();
		return INSTANCE;
	}

	@Override
	public Object getMod()
	{
		return this;
	}
}
