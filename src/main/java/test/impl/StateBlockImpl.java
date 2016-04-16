package test.impl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.block.ComponentBlock;
import test.api.component.block.StateBlock;

import java.util.Collection;

/**
 * @author ci010
 */
public class StateBlockImpl implements StateBlock
{
	private IBlockState delegate;
	private TileEntity tile;
	private WorldImpl world;

	public StateBlockImpl(WorldImpl world)
	{
		this.world = world;
	}

	StateBlockImpl with(TileEntity tile)
	{
		this.tile = tile;
		return this;
	}

	StateBlockImpl with(IBlockState state)
	{
		this.delegate = state;
		return this;
	}

	void recycle()
	{
		this.delegate = null;
		this.tile = null;
		world.recycle(this);
	}

	@Override
	public <T extends Comparable<T>> T get(IProperty<T> property)
	{
		return delegate.getValue(property);
	}

	@Override
	public <T extends Comparable<T>> void set(IProperty<T> property, T value)
	{
		delegate.withProperty(property, value);
	}

	@Override
	public <T extends Comparable<T>> void nextState(IProperty<T> property)
	{
		delegate.cycleProperty(property);
	}

	@Override
	public Collection<IProperty> getProperties()
	{
		return delegate.getPropertyNames();
	}

	@Override
	public ImmutableMap<IProperty, Comparable> getPropertiesMap()
	{
		return delegate.getProperties();
	}

	@Override
	public ComponentBlock getType()
	{
		return GameComponents.get(delegate.getBlock());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return tile != null && tile.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return tile == null ? null : tile.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return tile == null ? null : tile.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		if (tile != null)
			tile.deserializeNBT(nbt);
	}
}
