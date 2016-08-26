package api.simplelib.ui.elements;

import api.simplelib.vars.VarRef;
import net.minecraft.client.gui.FontRenderer;

import java.util.Map;

/**
 * One of the most basic components in gui.
 * This just simply draw a string.
 *
 * @author ci010
 */
public class ElementText extends Element
{
	public ElementText(String s, boolean localized, Object... args)
	{
		getProperties().str("text:content").set(s);
		getProperties().bool("text:localized").set(localized);

		if (args != null)
		{
			for(int i = 0; i < args.length; i++)
			{
				if (args[i] instanceof VarRef)
				{
					final VarRef ref = (VarRef) args[i];
					args[i] = new Object()
					{
						@Override
						public String toString() {return ref.get().toString();}
					};
				}
			}
			this.getProperties().arr("text:args").set(args);
		}
	}
}
