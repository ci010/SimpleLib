package api.simplelib.coremod;

import com.google.common.collect.ImmutableList;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;

/**
 * @author ci010
 */
public abstract class Transformer implements IClassTransformer
{
	private ImmutableList<ClassPatch> lst;
	private boolean isObfscated;

	{
		ImmutableList.Builder<ClassPatch> builder = ImmutableList.builder();
		this.buildClassPatch(builder);
		lst = builder.build();
	}

	protected abstract void buildClassPatch(ImmutableList.Builder<ClassPatch> builder);

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		isObfscated = !name.equals(transformedName);
		for (ClassPatch patch : lst)
			if (patch.patchClass().equals(transformedName))
				return handle(patch, bytes);
		return bytes;
	}

	private byte[] handle(ClassPatch patch, byte[] original)
	{
		try
		{
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			ClassReader classReader = new ClassReader(original);
			ClassNode node = new ClassNode();
			classReader.accept(node, 0);
			if (patch.accept(node))
				classReader.accept(patch.getVisitor(writer), 0);
			node.accept(writer);
			byte[] bytes = writer.toByteArray();
			System.out.println(Arrays.equals(original, bytes));
			return bytes;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return original;
	}
}
