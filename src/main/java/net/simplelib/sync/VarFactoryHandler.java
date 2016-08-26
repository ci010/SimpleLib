package net.simplelib.sync;

import api.simplelib.sync.AttributeView;
import api.simplelib.capabilities.Capabilities;
import api.simplelib.capabilities.CapabilityBuilderHandler;
import api.simplelib.sync.VarSync;
import api.simplelib.sync.AttributeFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author ci010
 */
public class VarFactoryHandler implements CapabilityBuilderHandler<AttributeFactory>
{
	@Override
	public AttributeFactory createBuilder(Object contextSrc)
	{
		if (contextSrc instanceof Entity)
			return new EntityVarFactory((Entity) contextSrc);
		else
			return new CommonVarFactory();
	}

	@Override
	public ICapabilityProvider build(AttributeFactory varSyncFactory, Object context)
	{
		Map<String, VarSync<?>> constantly = null, lazy = null;

		if (varSyncFactory instanceof EntityVarFactory)
		{
			constantly = ((EntityVarFactory) varSyncFactory).constantly;
			lazy = ((EntityVarFactory) varSyncFactory).lazy;
		}
		else if (varSyncFactory instanceof CommonVarFactory)
		{
			constantly = ((CommonVarFactory) varSyncFactory).constantly;
			lazy = ((CommonVarFactory) varSyncFactory).lazy;
		}

		return null;
//		storage.put(new ResourceLocation("sync"),
//				Capabilities.newBuilder(AttributeView.CAPABILITY).append(new AttriImpl((ICapabilityProvider)
//						context, constantly, lazy)).build());
	}

	@Nonnull
	@Override
	public Capability<?>[] allCapability()
	{
		return new Capability<?>[0];
	}

	@Nonnull
	@Override
	public ResourceLocation getName()
	{
		return null;
	}
}
