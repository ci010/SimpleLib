package net.simplelib.server.pactches;

import api.simplelib.coremod.ClassPatch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author ci010
 */
public class HackCmd extends ClassPatch
{
	@Override
	public String patchClass()
	{
		return "net.minecraft.entity.player.EntityPlayerMP";
	}

	{
		this.patchMethod(new MethodPatch("canCommandSenderUseCommand", "(ILjava/lang/String;)Z")
		{
			@Override
			public void visitCode()
			{
				super.visitCode();
				visitVarInsn(ALOAD, 0);
				visitVarInsn(ILOAD, 1);
				visitVarInsn(ALOAD, 2);
				visitMethodInsn(INVOKESTATIC, "net/simplelib/server/PermissionSystem", "hasPermissionHook",
						"(Lnet/minecraft/entity/player/EntityPlayer;ILjava/lang/String;)Z", false);
				visitInsn(IRETURN);
			}
		});
	}
}
