package net.simplelib.time;

import net.minecraft.launchwrapper.IClassTransformer;

/**
 * @author ci010
 */
public class Transformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
//		System.out.println(name);
//		System.out.println(transformedName);
		return bytes;
	}
}
