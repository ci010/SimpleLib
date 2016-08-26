package api.simplelib.ui.node;

import api.simplelib.ui.ElementAPI;
import api.simplelib.ui.Properties;
import api.simplelib.ui.elements.ElementBar;
import api.simplelib.ui.elements.Element;
import api.simplelib.utils.GuiUtil;
import api.simplelib.utils.TextureInfo;
import api.simplelib.vars.VarRef;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public class DrawProgress extends Gui implements DrawNode
{
	public static final DrawProgress INSTANCE = new DrawProgress();

	private DrawProgress() {}

	@Override
	public boolean apply(@Nonnull Element component)
	{
		Properties prop = component.getProperties();
		VarRef<TextureInfo> texture = prop.var(new ResourceLocation("bar", "texture"), ElementAPI.TEXTURE);
		if (!texture.isPresent())
			return true;
		GuiUtil.bindTexture(texture.get());
		VarRef<ElementBar.Direction> directionVarRef = prop.enm(ElementBar.Direction.class, "bar:dir");
		if (!directionVarRef.isPresent()) directionVarRef.set(ElementBar.Direction.RIGHT);

		int x = component.transform().x, y = component.transform().y;

		VarRef<Number> target = component.getProperties().num("bar:progress");
		if (!target.isPresent())
			return true;

		switch(directionVarRef.get())
		{
			case RIGHT:
				this.drawTexturedModalRect(x, y, texture.get().getU(), texture.get().getV(),
						(int) (texture.get().getWidth() * target.get().floatValue()),
						texture.get().getHeight());
				break;
			case LEFT:
				int width = (int) (texture.get().getWidth() * target.get().floatValue());
				this.drawTexturedModalRect(x + x - width, y, texture.get().getU() + texture.get().getU() - width, texture.get().getV(),
						width, texture.get().getHeight());
				break;
			case UP:
				int height = (int) (texture.get().getHeight() * target.get().floatValue());
				this.drawTexturedModalRect(x, y + y - height, texture.get().getU(), texture.get().getV() + texture.get().getV() - height,
						texture.get().getWidth(), height);
				break;
			case DOWN:
				this.drawTexturedModalRect(x, y, texture.get().getU(), texture.get().getV(), texture.get().getWidth(),
						(int) (texture.get().getHeight() * target.get().floatValue()));
				break;
		}
		return false;
	}
}
