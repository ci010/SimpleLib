package net.simplelib.client;

import api.simplelib.Instance;
import api.simplelib.client.ClientHelper;
import api.simplelib.registry.ModHandler;
import com.google.common.collect.Sets;
import javafx.beans.InvalidationListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.animation.Animation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector2f;

import java.util.Set;

/**
 * @author ci010
 */
@ModHandler
@SideOnly(Side.CLIENT)
public class ClientHelperImpl implements ClientHelper
{
	@Instance
	public static ClientHelperImpl INSTANCE = new ClientHelperImpl();
	private ResImpl resolution;
	private Vector2f center;
	private MouseEvent mouseState;

	{
		MinecraftForge.EVENT_BUS.register(new Object()
		{
			@SubscribeEvent
			public void onGuiInit(GuiScreenEvent.InitGuiEvent event)
			{
				resolution = new ResImpl(new ScaledResolution(Minecraft.getMinecraft()));
				center = new Vector2f(resolution.getScaledWidth() / 2F, resolution.getScaledHeight() / 2F);
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		});
	}

	@SubscribeEvent
	public void onMouse(MouseEvent event)
	{
		mouseState = event;
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event)
	{
		ScaledResolution res = event.getResolution();
		if (res.getScaledHeight() != resolution.getScaledHeight() || res.getScaledWidth() != resolution.getScaledHeight())
		{
			resolution.delegate = res;
			center.set(res.getScaledWidth() / 2F, res.getScaledHeight() / 2F);
			if (!resolution.listenerSet.isEmpty())
				for (InvalidationListener listener : resolution.listenerSet)
					listener.invalidated(resolution);
		}
	}

	@Override
	public void playSound(ResourceLocation location)
	{
		SoundEvent event = SoundEvent.REGISTRY.getObject(location);
		if (event == null)
			event = new SoundEvent(location);
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMusicRecord(event));
	}


	@Override
	public Vector2f globalToScreenCoord(int x, int y)
	{
		int w = resolution.getScaledWidth();
		int h = resolution.getScaledHeight();
		return new Vector2f(x * w / Minecraft.getMinecraft().displayWidth,
				h - y * h / Minecraft.getMinecraft().displayHeight - 1);
	}

	@Override
	public MouseEvent getMouseState()
	{
		return this.mouseState;
	}

	@Override
	public float getPartialTick()
	{
		return Animation.getPartialTickTime();
	}

	@Override
	public Vector3d worldPosToLocal(BlockPos pos)
	{
		Vector3d vector3d = new Vector3d();
		vector3d.x = pos.getX() - TileEntityRendererDispatcher.staticPlayerX;
		vector3d.y = pos.getY() - TileEntityRendererDispatcher.staticPlayerY;
		vector3d.z = pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ;
		return vector3d;
	}

	@Override
	public Vector2f getScreenCenter()
	{
		return center;
	}

	@Override
	public Vector2f getSymmetry(int x, int y)
	{
		return new Vector2f(resolution.getScaledWidth() - x, resolution.getScaledHeight() - y);
	}

	@Override
	public RayTraceResult getCurrentRayHit()
	{
		return Minecraft.getMinecraft().objectMouseOver;
	}

	@Override
	public Resolution getResolution()
	{
		return resolution;
	}

	private class ResImpl implements Resolution
	{
		ScaledResolution delegate;
		Set<InvalidationListener> listenerSet = Sets.newHashSet();

		public ResImpl(ScaledResolution resolution)
		{
			this.delegate = resolution;
		}

		@Override
		public int getScaledWidth() {return delegate.getScaledWidth();}

		@Override
		public int getScaledHeight() {return delegate.getScaledHeight();}

		@Override
		public double getScaledWidth_double() {return delegate.getScaledWidth_double();}

		@Override
		public double getScaledHeight_double() {return delegate.getScaledHeight_double();}

		@Override
		public int getScaleFactor() {return delegate.getScaleFactor();}

		@Override
		public void addListener(InvalidationListener listener)
		{
			listenerSet.add(listener);
		}

		@Override
		public void removeListener(InvalidationListener listener)
		{
			listenerSet.remove(listener);
		}
	}
}
