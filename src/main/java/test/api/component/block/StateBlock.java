package test.api.component.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.properties.IProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import test.api.component.State;

import java.util.Collection;

/**
 * @author ci010
 */
public interface StateBlock extends State, ICapabilitySerializable<NBTTagCompound>
{
	<T extends Comparable<T>> T get(IProperty<T> property);

	<T extends Comparable<T>> void set(IProperty<T> property, T value);

	<T extends Comparable<T>> void nextState(IProperty<T> property);

	Collection<IProperty> getProperties();

	ImmutableMap<IProperty, Comparable> getPropertiesMap();

	@Override
	ComponentBlock getType();
}
