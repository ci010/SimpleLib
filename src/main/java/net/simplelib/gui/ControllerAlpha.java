package net.simplelib.gui;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
public abstract class ControllerAlpha implements Controller
{
	protected float time, current;
	private Controller controller;

	public ControllerAlpha(float time, Controller controller)
	{
		this.time = time;
		this.controller = controller;
	}

//	@Override
//	public void draw(Drawer drawer, int x, int y, Target target)
//	{
//		if (++current >= time)
//		{
//			target.setController(controller);
//			return;
//		}
//		GlStateManager.enableBlend();
//		GlStateManager.enableAlpha();
//		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		float alpha = getAlpha();
//		GlStateManager.color(alpha, alpha, alpha, alpha);
//		drawer.draw(x, y, 1);
//	}

	protected abstract float getAlpha();

}
