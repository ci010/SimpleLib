package test.impl;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.capabilities.Capability;
import test.api.component.GameComponent;
import test.api.component.Module;
import test.api.component.block.ComponentBlock;

import java.util.ArrayList;

/**
 * @author ci010
 */
class BlockCompiledInfo implements ComponentBlock.Builder
{
	private ArrayList<IProperty> properties = new ArrayList<IProperty>();
	private ArrayList<Comparable> values = new ArrayList<Comparable>();
	private ComponentBlock block;

	public BlockCompiledInfo(ComponentBlock block)
	{
		this.block = block;
		block.build(this);
	}

	public ComponentBlock delegate()
	{
		return block;
	}

	@Override
	public IProperty<Integer> newInteger(String name, int defaultV, int min, int max)
	{
		PropertyInteger propertyInteger = PropertyInteger.create(name, min, max);
		properties.add(propertyInteger);
		values.add(defaultV);
		return propertyInteger;
	}

	@Override
	public IProperty<Boolean> newBoolean(String name, boolean defaultV)
	{
		PropertyBool bool = PropertyBool.create(name);
		properties.add(bool);
		values.add(defaultV);
		return bool;
	}

	@Override
	public <T extends Enum<T> & IStringSerializable> IProperty<T> newEnum(String name, Class<T> clz, T defualtV)
	{
		PropertyEnum<T> anEnum = PropertyEnum.create(name, clz);
		properties.add(anEnum);
		values.add(defualtV);
		return anEnum;
	}

	@Override
	public IProperty<EnumFacing> newDirection(String name, EnumFacing defaultV)
	{
		PropertyDirection direction = PropertyDirection.create(name);
		properties.add(direction);
		values.add(defaultV);
		return direction;
	}

	@Override
	public <T> GameComponent.Builder addModule(Module<T> moduleHook, T module)
	{
		return null;
	}

	@Override
	public GameComponent.Builder addCapability(Capability<?> capability, NBTBase initValue)
	{
		return null;
	}

	IProperty[] defaultProperties()
	{
		return (IProperty[]) properties.toArray();
	}

	Comparable[] getValues()
	{
		return (Comparable[]) values.toArray();
	}

	Material material()
	{
		return new MaterialImpl(block.getProperty().getMaterial());
	}
}
