package api.simplelib.gui.animation;

import api.simplelib.gui.ComponentAPI;
import api.simplelib.vars.Var;
import api.simplelib.gui.components.GuiComponent;
import api.simplelib.vars.VarOption;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * @author ci010
 */
public abstract class ControlAlpha implements Controller
{
	protected float time, current;
	private Controller old;

	protected ControlAlpha(float time, Controller old)
	{
		this.time = time;
		this.old = old;
	}

	@Override
	public void onLoad(GuiComponent component)
	{
//		component.getProperties().property(ComponentAPI.PROP_FADETIME).set();
	}

	@Override
	public void onRemoved(GuiComponent component)
	{

	}

	@Override
	public void draw(GuiComponent component)
	{
		VarOption<Integer> totalTime = component.getProperties().property(ComponentAPI.PROP_FADE_TIME);
		if (!totalTime.isPresent())
		{
			Var<Controller> ctrlOp = component.getProperties().cache("oldControl");
//			if (!ctrlOp.isPresent())
//				component.setController(null);
			return;
		}
		float time = totalTime.get(), current = 0;
		Var<Float> currentTime = component.getProperties().cache("currentTime");
//		if (currentTime.isPresent())
//			current = currentTime.get();
		if (++current >= time)
		{
			component.setController(old);
			return;
		}
		currentTime.set(current);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		float alpha = getAlpha();
		GlStateManager.color(alpha, alpha, alpha, alpha);
		component.draw();
	}

	protected abstract float getAlpha();
}
