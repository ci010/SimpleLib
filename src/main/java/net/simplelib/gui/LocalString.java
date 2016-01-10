package net.simplelib.gui;

import net.simplelib.common.Local;

/**
 * @author ci010
 */
public class LocalString extends SourceString
{
	private String localized;

	public LocalString(String id)
	{
		super(id);
		localized = Local.translate(id);
	}

	@Override
	public String toString()
	{
		return String.format(localized, source.getSource());
	}
}
