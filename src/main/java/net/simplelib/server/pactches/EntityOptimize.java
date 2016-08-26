package net.simplelib.server.pactches;

import api.simplelib.coremod.ClassPatch;

/**
 * @author ci010
 */
public class EntityOptimize extends ClassPatch
{
	@Override
	public String patchClass()
	{
		return "net.minecraft.entity.Entity";
	}

	{
		this.patchMethod(new MethodPatch("moveEntity", "(DDD)V")
		{
			@Override
			public void visitCode()
			{
				super.visitCode();
			}
		});
	}
}
