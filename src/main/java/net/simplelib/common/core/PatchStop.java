package net.simplelib.common.core;

import api.simplelib.coremod.ClassPatch;

/**
 * @author ci010
 */
public class PatchStop extends ClassPatch
{
	public PatchStop()
	{
		super();
		this.patchMethod(new MethodPatch("shutdown", "()V")
		{
			@Override
			public void visitEnd()
			{
//				MinecraftForge.EVENT_BUS.post(new )
//				super.visitMethodInsn(INVOKESTATIC,"net.simple");
				super.visitEnd();
			}
		});
	}

	@Override
	public String patchClass()
	{
		return "net.minecraft.client.Minecraft";
	}
}
