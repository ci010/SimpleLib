package api.simplelib;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.simplelib.common.registry.LanguageReporter;

/**
 * @author ci010
 */
public class Local
{
	public static String trans(String id)
	{
		return trans(id, StatCollector.translateToFallback(id));
	}

	public static String trans(String id, String fallback)
	{
		if (StatCollector.canTranslate(id))
			return StatCollector.translateToLocal(id);
		LanguageReporter.instance().report(id);
		return fallback;
	}

	public static IChatComponent newChat(String id)
	{
		return new ChatComponentText(trans(id));
	}
}
