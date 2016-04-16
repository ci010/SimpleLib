package test.impl;

import net.minecraft.entity.EntityLiving;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author ci010
 */
public class GenEntityClass
{
	static CompiledComponentEntity get(String name)
	{
		return null;
	}

	static Class<? extends EntityLiving> gen(String name)
	{
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		String className = "net.simplelib.entity.".concat(name);
		cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, className, null, "net/minecraft/entity/EntityLiving",
				null);
		CompiledComponentEntity entity = get(name);
		cw.visitSource(".dynamic", null);
		{
			cw.visitField(ACC_PUBLIC, "delegate", /*delegate.classType*/null, null, null).visitEnd();
		}
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/Object;Lnet/minecraft/world/World;)V", null, null);
			//TODO check this

			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
//			mv.visitFieldInsn(PUTFIELD, handlerDesc, "delegate", delegate.classType);
			mv.visitInsn(RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		return null;
	}
}
