package net.simplelib.optimize;

import api.simplelib.coremod.ClassPatch;
import com.google.common.base.Predicate;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.util.ListIterator;

/**
 * @author ci010
 */
public class ServerPatch extends ClassPatch
{
	{
		this.patchClass(new Predicate<ClassNode>()
		{
			@Override
			public boolean apply(@Nullable ClassNode input)
			{
				MethodNode methodNode = null;
				for (MethodNode method : input.methods)
				{
					if (method.desc.equals("()V") && method.name.equals("run"))
						methodNode = method;
				}
				if (methodNode == null)
				{
					System.out.println("NOT FOUND METHOD????????");

					return false;
				}
//				methodNode.instructions.clear();
				methodNode.instructions.insert(new MethodInsnNode(INVOKESTATIC, "net/simplelib/optimize/TickOptimize",
						"test", "()V", false));

				AbstractInsnNode codeStartNode = null;
				LabelNode loopStartLabel = null, terminalLabel = null;
				for (int i = 0; i < methodNode.instructions.size(); i++)
				{
					if (terminalLabel != null && loopStartLabel != null && codeStartNode != null) break;
					AbstractInsnNode node = methodNode.instructions.get(i);
					if (node instanceof VarInsnNode && methodNode.instructions.get(i + 1) instanceof FieldInsnNode &&
							methodNode.instructions.get(i + 2) instanceof JumpInsnNode)
					{
						VarInsnNode ALOAD0 = (VarInsnNode) node;
						if (ALOAD0.getOpcode() != ALOAD)
							continue;
						FieldInsnNode getField = (FieldInsnNode) node.getNext();
						System.out.println(getField.name);
						System.out.println(getField.desc);
						System.out.println(getField.owner);
						if (!(getField.owner.equals("net/minecraft/server/MinecraftServer") &&
								getField.desc.equals("Z")))
							continue;
						JumpInsnNode IFEQ = (JumpInsnNode) getField.getNext();
						terminalLabel = IFEQ.label;
						for (int j = i + 1; j < methodNode.instructions.size(); j++)
						{
							AbstractInsnNode insnNode = methodNode.instructions.get(j);
							if (insnNode instanceof LabelNode)
								codeStartNode = insnNode;
						}
						for (int j = i - 1; j > 0; j--)
						{
							AbstractInsnNode insnNode = methodNode.instructions.get(j);
							if (insnNode instanceof LabelNode)
								loopStartLabel = (LabelNode) insnNode;
						}
					}
				}
				System.out.println(loopStartLabel);
				System.out.println(codeStartNode);
				System.out.println(terminalLabel);
				if (loopStartLabel == null || terminalLabel == null || codeStartNode == null)
				{
					System.out.println("NOT FOUND??????");
					return false;
				}

				InsnList list = new InsnList();
				list.add(new MethodInsnNode(INVOKESTATIC, "net/simplelib/optimize/TickOptimize",
						"cal", "()Z", false));
				list.add(new JumpInsnNode(IFEQ, loopStartLabel));
				list.add(new VarInsnNode(ALOAD, 0));
				list.add(new MethodInsnNode(INVOKEVIRTUAL,
						"net/minecraft/server/MinecraftServer", "tick", "()V", false));
				list.add(new VarInsnNode(ALOAD, 0));
				list.add(new InsnNode(ICONST_1));
				list.add(new FieldInsnNode(PUTFIELD,
						"net/minecraft/server/MinecraftServer", "serverIsRunning", "Z"));
				list.add(new FieldInsnNode(GETSTATIC, "net/simplelib/optimize/TickOptimize",
						"curTime", "J"));
				list.add(new FieldInsnNode(PUTSTATIC, "net/simplelib/optimize/TickOptimize",
						"lastTick", "J"));
				list.add(new JumpInsnNode(GOTO, loopStartLabel));

				methodNode.instructions.insert(codeStartNode, list);
				methodNode.instructions.insert(codeStartNode, new MethodInsnNode(INVOKESTATIC,
						"net/simplelib/optimize/TickOptimize",
						"test", "()V", false));

				return false;
			}
		});
	}

	@Override
	public String patchClass()
	{
		return "net.minecraft.server.MinecraftServer";
	}
}
