package test;

import net.minecraftforge.fml.common.registry.RegistryDelegate;

/**
 * @author ci010
 */
public class RecipePatter
{
	int length, height;
	boolean shapeless;
	String[][] recipe = new String[3][3];

	public RecipePatter(String domain, String name, int id, boolean shapeless)
	{
		this.shapeless = shapeless;
	}

	class RecipeMeta
	{
		RegistryDelegate regInfo;
		int metadata;
	}
}
