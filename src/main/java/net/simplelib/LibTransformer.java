package net.simplelib;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ci010
 */
public class LibTransformer implements IClassTransformer
{
	public static Map<String, Class> eventMap = new HashMap<String, Class>();

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		ClassReader reader = new ClassReader(bytes);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		reader.accept(new ClassVisitor(Opcodes.ASM4, writer)
		{
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
			{
				if (name.contains("Event") || name.contains("getName"))
				{
					name = name.replace('/', '.');
					eventMap.put(name, Object.class);
				}
				super.visit(version, access, name, signature, superName, interfaces);
			}
		}, 0);
//		if (patch.patchClass().equals(transformedName))
//		{
//			ClassNode cnode = new ClassNode();
//			reader.accept(cnode, 0);
//			patch.accept(cnode);
//			writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//			cnode.accept(writer);
//			bytes = writer.toByteArray();
//		}
		return bytes;
	}
}
