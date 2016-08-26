package api.simplelib.ui.plugins;

import api.simplelib.ui.ElementEvent;
import api.simplelib.ui.elements.Element;
import api.simplelib.vars.VarNotify;
import com.google.common.collect.Lists;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.List;

/**
 * @author ci010
 */
public class HoverTooltip implements Plugin, InvalidationListener
{
	private String content;
	private long delay;
	private Object[] args;
	private boolean dirty = true;
	private int maxLength;
	private List<String> cache = Lists.newArrayList();

	public HoverTooltip(String str, long delay, Object... args)
	{
		this(str, delay, 0, args);
	}

	public HoverTooltip(String str, long delay, int maxLength, Object... args)
	{
		this.content = str;
		this.delay = delay;
		this.args = args;
		this.maxLength = maxLength;

		if (args != null)
			for(Object arg : args)
				if (arg instanceof VarNotify)
					((VarNotify<?>) arg).addListener(this);
	}

	@SubscribeEvent
	public void onHover(ElementEvent.Hover event)
	{
		if (event.getHoverTime() < delay)
			return;

		if (dirty)
		{
			dirty = false;
			if (args != null)
			{
				cache.clear();
				Collections.addAll(cache, String.format(content, args).split("\n"));
			}
		}
		Minecraft mc = Minecraft.getMinecraft();
		GuiUtils.drawHoveringText(cache, event.getMouseX(), event.getMouseY(),
				mc.displayWidth, mc.displayHeight, maxLength, mc.fontRendererObj);
	}

	@Override
	public void plugin(Element component)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void dispose(Element element)
	{
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@Override
	public void invalidated(Observable observable)
	{
		this.dirty = true;
	}
}
