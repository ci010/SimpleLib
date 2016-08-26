package api.simplelib.ui.node;

import api.simplelib.ui.elements.Element;
import api.simplelib.utils.GuiUtil;
import api.simplelib.vars.VarRef;
import net.minecraft.client.gui.Gui;

import javax.annotation.Nonnull;

/**
 * @author ci010
 */
public class DrawSlots extends Gui implements DrawNode
{
	@Override
	public boolean apply(@Nonnull Element input)
	{
		VarRef<String> typeRef = input.getProperties().str("slot:type");
		if (!typeRef.isPresent())
			return true;
		if (typeRef.get().equals("space"))
		{
			VarRef<Number> xSizeRef = input.getProperties().num("slot:xSize");
			int xSize;
			if (!xSizeRef.isPresent())
				xSize = -1;
			else xSize = xSizeRef.get().intValue();
			VarRef<Number> ySizeRef = input.getProperties().num("slot:ySize");
			int ySize;
			if (!ySizeRef.isPresent())
			{
				ySize = 1;
				if (xSize == -1)
					if (input.getProperties().num("slot:size").isPresent())
						xSize = input.getProperties().num("slot:size").get().intValue();
					else return true;
			}
			else
				ySize = ySizeRef.get().intValue();
			int x = input.transform().x, y = input.transform().y;
			GuiUtil.bindTexture(GuiUtil.slot);
			for(int i = 0; i < ySize; i++)
				for(int j = 0; j < xSize; j++)
					GuiUtil.drawTextureAt(x, y, GuiUtil.slot);
		}
		else if (typeRef.get().equals("slot"))
			GuiUtil.drawTextureAt(input.transform().x, input.transform().y, GuiUtil.slot);
		return false;
	}
}
