package net.simplelib.client;

import api.simplelib.gui.components.GuiComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ci010
 */
@SideOnly(Side.CLIENT)
public class LoadingScreenLoader extends Gui
{
	private LoadingScreenLoader() {}

	private static LoadingScreenLoader instance;

	public static LoadingScreenLoader instance()
	{
		if (instance == null)
			instance = new LoadingScreenLoader();
		return instance;
	}

	private GuiComponent currentScreen;
	private Set<ISound> allowedSound;

	public void start(GuiComponent component)
	{
		Minecraft.getMinecraft().getSoundHandler().pauseSounds();
		MinecraftForge.EVENT_BUS.register(this);
		allowedSound = new HashSet<ISound>();
	}

	public void end()
	{
		Minecraft.getMinecraft().getSoundHandler().resumeSounds();
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@SubscribeEvent
	public void onSoundPlay(PlaySoundEvent event)
	{
		if (!allowedSound.contains(event.sound))
			event.result = null;
	}

	@SubscribeEvent
	public void onGuiOverlayRender(RenderGameOverlayEvent.Post event)
	{
		ScaledResolution resolution = event.resolution;
		drawRect(0, 0, resolution.getScaledWidth(), resolution.getScaledHeight(), 0);
	}

}
