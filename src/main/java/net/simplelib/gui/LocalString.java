package net.simplelib.gui;

import net.minecraft.util.StatCollector;
import net.simplelib.registry.LanguageReporter;

/**
 * @author ci010
 */
public class LocalString extends SourceString
{
	private String localized;

	public LocalString(String id)
	{
		super(id);
		if (!StatCollector.canTranslate(id))
			LanguageReporter.instance().report(localized = id);
		else
			localized = StatCollector.translateToLocal(id);
	}

	@Override
	public String toString()
	{
		return String.format(localized, source.getSource());
	}
}
