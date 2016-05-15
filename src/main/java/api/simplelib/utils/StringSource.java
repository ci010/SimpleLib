package api.simplelib.utils;

import api.simplelib.seril.ITagSerializable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public class StringSource implements ITagSerializable
{
	private String rawContent;
	protected Source source;

	public StringSource(String rawContent)
	{
		this.rawContent = rawContent;
	}

	public StringSource setSource(Source source)
	{
		this.source = source;
		return this;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{

	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{

	}

	public interface Source
	{
		Object[] getSource();
	}

	@Override
	public String toString()
	{
		return String.format(rawContent, source.getSource());
	}
}
