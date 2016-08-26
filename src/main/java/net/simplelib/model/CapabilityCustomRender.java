package net.simplelib.model;

import api.simplelib.capabilities.Capabilities;
import api.simplelib.capabilities.ICapability;
import api.simplelib.capabilities.ModCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.model.test.RenderPlayerCustom;

import java.util.concurrent.Callable;

/**
 * @author ci010
 */
@ModCapability
@SideOnly(Side.CLIENT)
public class CapabilityCustomRender implements ICapability<RenderPlayerCustom>
{
	@CapabilityInject(RenderPlayerCustom.class)
	public static Capability<RenderPlayerCustom> RENDER_CAP = null;

	@Override
	public Capability.IStorage<RenderPlayerCustom> storage()
	{
		return Capabilities.emptyStorage();
	}

	@Override
	public Callable<RenderPlayerCustom> factory()
	{
		return null;
	}

	@Override
	public Class<RenderPlayerCustom> type()
	{
		return RenderPlayerCustom.class;
	}
}
