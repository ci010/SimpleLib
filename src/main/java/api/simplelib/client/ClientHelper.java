package api.simplelib.client;

import api.simplelib.registry.ModProxy;
import javafx.beans.Observable;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.util.vector.Vector2f;

/**
 * @author ci010
 */
public interface ClientHelper
{
	@ModProxy.Inject
	ClientHelper INSTANCE = null;

	/**
	 * Play a sound locally.
	 *
	 * @param location The resource location of the sound.
	 */
	void playSound(ResourceLocation location);

	/**
	 * @return Get current partial tick for render.
	 */
	float getPartialTick();

	/**
	 * Transfer a block position in world coordination into local render coordination.
	 *
	 * @param pos The world {@link BlockPos}.
	 * @return The interpolated vector for that pos.
	 */
	Vector3d worldPosToLocal(BlockPos pos);

	Vector2f globalToScreenCoord(int x, int y);

	MouseEvent getMouseState();

	/**
	 * @return The center of the screen.
	 */
	Vector2f getScreenCenter();

	/**
	 * @param x The x position on screen.
	 * @param y The y position on screen.
	 * @return A symmetry vector position to x and y.
	 */
	Vector2f getSymmetry(int x, int y);

	RayTraceResult getCurrentRayHit();

	Resolution getResolution();

	interface Resolution extends Observable
	{
		int getScaledWidth();

		int getScaledHeight();

		double getScaledWidth_double();

		double getScaledHeight_double();

		int getScaleFactor();
	}
}
