package net.simplelib.common;

import net.minecraft.util.StatCollector;
import net.simplelib.common.registry.LanguageReporter;

/**
 * @author ci010
 */
public class Local
{
	public static String translate(String id)
	{
		return translate(id, id);
	}

	public static String translate(String id, String fallback)
	{
		if (StatCollector.canTranslate(id))
			return StatCollector.translateToLocal(id);
		LanguageReporter.instance().report(id);
		return fallback;
	}
}
