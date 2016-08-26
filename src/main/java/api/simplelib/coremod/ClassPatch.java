package api.simplelib.coremod;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * @author ci010
 */
public abstract class ClassPatch
{
	private List<MethodPatch> patches;
	private Predicate<ClassNode> nodePatch;

	public abstract String patchClass();

	public ClassPatch patchMethod(MethodPatch patch)
	{
		if (patches == null)
			patches = Lists.newLinkedList();
		this.patches.add(patch);
		return this;
	}

	public boolean accept(ClassNode node)
	{
		if (nodePatch != null)
			return this.nodePatch.apply(node);
		return true;
	}

	public ClassPatch patchClass(Predicate<ClassNode> predicate)
	{
		nodePatch = predicate;
		return this;
	}

	public ClassVisitor getVisitor(ClassVisitor visitor)
	{
		return new ClassVisitor(Opcodes.ASM4, visitor)
		{
			@Override
			public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
			{
				for (MethodPatch patch : patches)
					if (patch.match(name, desc))
						return patch.apply(super.visitMethod(access, name, desc, signature, exceptions));
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
		};
	}

	public static class MethodMatcher implements Predicate<MethodNode>
	{
		public String name, desc;
		public int opCode = Integer.MIN_VALUE;

		public MethodMatcher() {}

		public MethodMatcher setName(String name)
		{
			this.name = name;
			return this;
		}

		public MethodMatcher setDesc(String desc)
		{
			this.desc = desc;
			return this;
		}

		public MethodMatcher setOpCode(int opCode)
		{
			this.opCode = opCode;
			return this;
		}

		public MethodNode find(Collection<MethodNode> collection)
		{
			for (MethodNode node : collection)
				if (apply(node))
					return node;
			return null;
		}

		@Override
		public boolean apply(@Nullable MethodNode input)
		{
			boolean match = false;
			if (name != null) match &= input.name.equals(name);
			if (desc != null) match &= input.desc.equals(desc);
			if (opCode != Integer.MIN_VALUE) match &= input.access == opCode;
			return match;
		}
	}

	public static class MethodPatch extends MethodVisitor
	{
		private String name, descriptor;

		public boolean match(String name, String desc)
		{
			System.out.println("Try to match " + name + " " + desc);
			return this.name.equals(name) && this.descriptor.equals(desc);
		}

		public MethodPatch(String name, String descriptor)
		{
			super(Opcodes.ASM4);
			this.name = name;
			this.descriptor = descriptor;
		}

		public MethodPatch apply(MethodVisitor visitor)
		{
			this.mv = visitor;
			return this;
		}

		public final void fallbackVisitMethod(int opcode, String owner, String name, String desc, boolean itf)
		{
			super.visitMethodInsn(opcode, owner, name, desc, itf);
		}
	}
}
