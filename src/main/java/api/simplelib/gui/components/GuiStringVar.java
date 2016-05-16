package api.simplelib.gui.components;

import api.simplelib.vars.VarSync;
import net.minecraft.inventory.Container;

/**
 * @author ci010
 */
public class GuiStringVar extends GuiComponent
{
	VarSync src;

	public GuiStringVar(VarSync var, int x, int y)
	{
		this.setPos(x, y);
		this.src = var;
	}

	@Override
	public void onLoad(Container container)
	{
		super.onLoad(container);
		
	}
}
