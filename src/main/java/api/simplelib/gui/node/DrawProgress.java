package api.simplelib.gui.node;

import api.simplelib.Pipeline;
import api.simplelib.utils.TextureInfo;
import api.simplelib.gui.ComponentAPI;
import api.simplelib.gui.Properties;
import api.simplelib.gui.components.GuiBar;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.Var;
import api.simplelib.vars.VarForward;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawProgress extends Gui implements DrawNode
{
	public static final DrawProgress INSTANCE = new DrawProgress();

	private DrawProgress() {}

	@Override
	public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
	{
		VarForward<TextureInfo> texture = properties.property(ComponentAPI.PROP_TEXTURE);
		if (!texture.isPresent())
			return;
		GuiUtil.bindToTexture(texture.get());
		VarForward<GuiBar.Direction> dir = properties.property(ComponentAPI.PROP_DIRECTION);
		if (!dir.isPresent())
			dir.set(GuiBar.Direction.RIGHT);
		Var<Float> target = properties.property(ComponentAPI.PROP_PROGRESS);
		if (target.get() != null)
			target.set(0F);
		switch (dir.get())
		{
			case RIGHT:
				this.drawTexturedModalRect(x, y, texture.get().getU(), texture.get().getV(), (int) (texture.get().getWidth() * target.get()),
						texture.get().getHeight());
				break;
			case LEFT:
				int width = (int) (texture.get().getWidth() * target.get());
				this.drawTexturedModalRect(x + x - width, y, texture.get().getU() + texture.get().getU() - width, texture.get().getV(),
						width, texture.get().getHeight());
				break;
			case UP:
				int height = (int) (texture.get().getHeight() * target.get());
				this.drawTexturedModalRect(x, y + y - height, texture.get().getU(), texture.get().getV() + texture.get().getV() - height,
						texture.get().getWidth(), height);
				break;
			case DOWN:
				this.drawTexturedModalRect(x, y, texture.get().getU(), texture.get().getV(), texture.get().getWidth(),
						(int) (texture.get().getHeight() * target.get()));
				break;
		}
	}
}
