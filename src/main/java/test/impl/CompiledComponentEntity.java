package test.impl;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.GameComponent;
import test.api.component.Module;
import test.api.component.entity.IAttribute;
import test.api.component.entity.living.ComponentEntityLiving;
import api.simplelib.utils.ITagSerializer;

import java.util.ArrayList;

/**
 * @author ci010
 */
public class CompiledComponentEntity implements ComponentEntityLiving.Builder
{
	private ComponentEntityLiving componentEntityLiving;
	ArrayList<Capability<?>> capabilities = Lists.newArrayList();
	ArrayList<NBTBase> initValues = Lists.newArrayList();

	public CompiledComponentEntity(ComponentEntityLiving componentEntityLiving)
	{
		this.componentEntityLiving = componentEntityLiving;
		this.componentEntityLiving.build(this);
	}

	public ComponentEntityLiving getComponent()
	{
		return componentEntityLiving;
	}

	@Override
	public <T> void applyAttribute(IAttribute<T> attribute, ITagSerializer<T> serializer)
	{

	}

	@Override
	public <T> GameComponent.Builder addModule(Module<T> moduleHook, T module)
	{
		return null;
	}

	@Override
	public GameComponent.Builder addCapability(Capability<?> capability, NBTBase initValue)
	{
		capabilities.add(capability);
		initValues.add(initValue);
		return this;
	}

	public EntityLivingImpl newEntity(World world)
	{
		return null;
	}

	public boolean hasCapability(Capability<?> capability)
	{
		return capabilities.contains(capability);
	}

}
