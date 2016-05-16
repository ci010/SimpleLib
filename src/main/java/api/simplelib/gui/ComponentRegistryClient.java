package api.simplelib.gui;

import api.simplelib.gui.animation.AnimationFadein;
import api.simplelib.gui.animation.Controller;
import api.simplelib.gui.drawer.DrawString;
import api.simplelib.gui.drawer.DrawTexture;
import api.simplelib.gui.drawer.DrawerDefaultBackground;
import api.simplelib.gui.animation.AnimationFadout;
import api.simplelib.gui.drawer.DrawBorderTexts;
import api.simplelib.registry.ModProxy;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
@ModProxy(side = Side.CLIENT, genericType = ComponentRepository.class)
public class ComponentRegistryClient extends ComponentRegistryCommon
{
	@Override
	protected void register()
	{
		registerDrawNode(ComponentAPI.LOC_DRAW_TEXTURE, DrawTexture.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_DRAW_BACKGROUND, DrawerDefaultBackground.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_DRAW_BORDER_TEXTS, DrawBorderTexts.INSTANCE);
		registerDrawNode(ComponentAPI.LOC_DRAW_STRING, DrawString.INSTANCE);

		registerController(ComponentAPI.LOC_CTRL_FADE_IN, AnimationFadein.INSTANCE);
		registerController(ComponentAPI.LOC_CTRL_FADE_OUT, AnimationFadout.INSTANCE);
		registerController(ComponentAPI.LOC_CTRL_DEFAULT, Controller.DEFAULT);
	}
}
