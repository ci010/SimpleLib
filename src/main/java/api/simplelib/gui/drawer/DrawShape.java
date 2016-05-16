package api.simplelib.gui.drawer;

import api.simplelib.gui.DrawNode;
import api.simplelib.gui.Properties;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

/**
 * @author ci010
 */
public class DrawShape extends Gui implements DrawNode
{
	private Shape shape;
	private float[] cache = new float[6];

	public DrawShape(Shape shape)
	{
		this.shape = shape;
	}

	@Override
	public void draw(int x, int y, Properties properties)
	{
		PathIterator pathIterator = shape.getPathIterator(new AffineTransform(0, 0, 0, 0, x, y));
		Tessellator instance = Tessellator.getInstance();
		WorldRenderer renderer = instance.getWorldRenderer();
		renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		while (!pathIterator.isDone())
		{
			switch (pathIterator.currentSegment(cache))
			{
				case PathIterator.SEG_CLOSE:
					break;
				case PathIterator.SEG_MOVETO:
					renderer.pos(cache[0], cache[1], 0).endVertex();
					break;
				case PathIterator.SEG_LINETO:
					break;

				case PathIterator.SEG_QUADTO:
					break;
				case PathIterator.SEG_CUBICTO:
					break;
			}
			pathIterator.next();
		}
//		renderer.pos();//TODO
	}
}
