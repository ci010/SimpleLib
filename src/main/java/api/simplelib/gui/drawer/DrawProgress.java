package api.simplelib.gui.drawer;

import api.simplelib.utils.TextureInfo;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.DrawNode;
import api.simplelib.gui.Properties;
import api.simplelib.gui.components.GuiBar;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.Var;
import api.simplelib.vars.VarOption;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawProgress extends Gui implements DrawNode
{
	@Override
	public void draw(int x, int y, Properties properties)
	{
		VarOption<TextureInfo> texture = properties.property(ComponentAPI.PROP_TEXTURE);
		if (!texture.isPresent())
			return;
		GuiUtil.bindToTexture(texture.get());
		VarOption<GuiBar.Direction> dir = properties.property(ComponentAPI.PROP_DIRECTION);
		if (!dir.isPresent())
			dir.set(GuiBar.Direction.toRight);
		Var<Float> target = properties.cache("supplier");
		if (target.get() != null)
			target.set(0F);
		switch (dir.get())
		{
			case toRight:
				this.drawTexturedModalRect(x, y, texture.get().getU(), texture.get().getV(), (int) (texture.get().getWidth() * target.get()),
						texture.get().getHeight());
				break;
			case toLeft:
				int width = (int) (texture.get().getWidth() * target.get());
				this.drawTexturedModalRect(x + x - width, y, texture.get().getU() + texture.get().getU() - width, texture.get().getV(),
						width, texture.get().getHeight());
				break;
			case toTop:
				int height = (int) (texture.get().getHeight() * target.get());
				this.drawTexturedModalRect(x, y + y - height, texture.get().getU(), texture.get().getV() + texture.get().getV() - height,
						texture.get().getWidth(), height);
				break;
			case toBottom:
				this.drawTexturedModalRect(x, y, texture.get().getU(), texture.get().getV(), texture.get().getWidth(),
						(int) (texture.get().getHeight() * target.get()));
				break;
		}
	}
}
