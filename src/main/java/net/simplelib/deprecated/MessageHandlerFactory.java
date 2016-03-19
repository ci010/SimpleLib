package net.simplelib.deprecated;

import api.simplelib.utils.GenericUtil;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author ci010
 */
public class MessageHandlerFactory
{
	private final ASMClassLoader classLoader = new ASMClassLoader();
	private final String MSG_HANDLER = Type.getInternalName(IMessageHandler.class);
	private final String METHOD_ON_MESSAGE, ON_MESSAGE_SIG, METHOD_ON_CLIENT, METHOD_ON_SERVER;

	private static MessageHandlerFactory instance;

	public static MessageHandlerFactory getInstance()
	{
		if (instance == null)
			instance = new MessageHandlerFactory();
		return instance;
	}


	{
		METHOD_ON_MESSAGE = Type.getMethodDescriptor(IMessageHandler.class.getMethods()[0]);
		ON_MESSAGE_SIG = "(TT;Lnet/minecraftforge/fml/common/network/simpleimpl/MessageContext;)" +
				"Lnet/minecraftforge/fml/common/network/simpleimpl/IMessage;";
		METHOD_ON_CLIENT = Type.getMethodDescriptor(IClientMessage.class.getMethods()[0]);
		METHOD_ON_SERVER = Type.getMethodDescriptor(IServerMessage.class.getMethods()[0]);
	}

	public <T extends IMessage> Class<IMessageHandler<T, IMessage>> generateClass(MessageType delegate, T message)
	{
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		String handlerClassName = getName(message);
		String handlerDesc = handlerClassName.replace("\\.", "/");
		final String HOOK = Type.getInternalName(NetworkHook.class);
		final String GET_PLAYER = Type.getMethodDescriptor(NetworkHook.class.getMethods()[0]);

		if (delegate == null)
			throw new IllegalArgumentException();

		cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, handlerDesc, null, "java/lang/Object",
				new String[]{MSG_HANDLER});
		cw.visitSource(".dynamic", null);
		{
			cw.visitField(ACC_PUBLIC, "delegate", delegate.classType, null, null).visitEnd();
		}
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/Object;)V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitFieldInsn(PUTFIELD, handlerDesc, "delegate", delegate.classType);
			mv.visitInsn(RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}

		{
			mv = cw.visitMethod(ACC_PUBLIC, "onMessage", METHOD_ON_MESSAGE, ON_MESSAGE_SIG, null);
			mv.visitCode();
			Label one = new Label(), two = new Label(), three = new Label();

			mv.visitLabel(one);
			{
				mv.visitVarInsn(ALOAD, 2);
				mv.visitFieldInsn(GETFIELD, "net/minecraftforge/fml/common/network/simpleimpl/MessageContext", "side",
						"Lnet/minecraftforge/fml/relauncher/Side;");
				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fml/relauncher/Side", "isClient", "()Z", false);
				mv.visitJumpInsn(IFEQ, three);
			}
			mv.visitLabel(two);//client
			{
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, handlerDesc, "delegate", delegate.classType);
				mv.visitMethodInsn(INVOKESTATIC, HOOK, "getPlayer", GET_PLAYER, false);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ALOAD, 2);
				switch (delegate)
				{
					case CLIENT:
					case FULL:
						mv.visitMethodInsn(INVOKEVIRTUAL, delegate.className, "onClientMessage", METHOD_ON_CLIENT, true);
						break;
					case SERVER:
						break;
				}
				mv.visitInsn(ARETURN);
			}
			mv.visitLabel(three);//server
			{
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, handlerDesc, "delegate", delegate.classType);
				mv.visitMethodInsn(INVOKESTATIC, HOOK, "getPlayer", GET_PLAYER, false);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitVarInsn(ALOAD, 2);
				switch (delegate)
				{
					case SERVER:
					case FULL:
						mv.visitMethodInsn(INVOKEVIRTUAL, delegate.className, "onServerMessage", METHOD_ON_SERVER, true);
						break;
					case CLIENT:
						break;
				}
				mv.visitInsn(ARETURN);
			}
			mv.visitMaxs(4, 3);
			mv.visitEnd();
		}
		cw.visitEnd();
		final byte[] bytes = cw.toByteArray();
		Class<?> ret = null;
//		try
//		{
//			FileOutputStream stream = new FileOutputStream(handlerClassName);
//			stream.write(bytes);
//			stream.close();
//			//net/simplelib/network/MessageHandlerFactoryPlayerSitMessageHandler
//			//net.simplelib.network.MessageHandlerFactoryPlayerSitMessageHandler
//		}
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		try
		{
			ret = classLoader.define(handlerClassName, bytes);
		}
		catch (ClassFormatError error)
		{
			error.printStackTrace();
		}
		return GenericUtil.cast(ret);
	}

	private <T extends IMessage> String getName(T message)
	{
		return String.format("%s%s", getClass().getName(),
				message.getClass().getSimpleName().concat("Manager"));
	}

	class ASMClassLoader extends ClassLoader
	{
		public ASMClassLoader()
		{
			super(ASMClassLoader.class.getClassLoader());
		}

		public Class<?> define(String name, byte[] data)
		{
			return defineClass(name, data, 0, data.length);
		}
	}

}
