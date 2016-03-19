package test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.simplelib.HelperMod;
import api.simplelib.common.ModHandler;

/**
 * @author ci010
 */
@ModHandler
public class TestHandler
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK)
		{
			System.out.println(event.world.getBiomeGenForCoords(event.pos).getFloatTemperature(event.pos));
		}
	}

	@SubscribeEvent
	public void onMouse(GuiScreenEvent.InitGuiEvent.Post e)
	{
//		System.out.println("try play");
//		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation
//				("helper", "bgm.e")));
	}

	@SubscribeEvent
	public void bottom(GuiScreenEvent.ActionPerformedEvent event)
	{
		System.out.println(event.gui);
	}

	@SubscribeEvent
	public void breakBlock(BlockEvent.BreakEvent event)
	{
//		event.setCanceled(true);
	}

	@SubscribeEvent
	public void testTemp(TickEvent.PlayerTickEvent event)
	{
		BlockPos pos = event.player.getPosition();
		event.player.worldObj.getBiomeGenForCoords(pos).getFloatTemperature(pos);
	}

	static int counter = 0;

	//	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (++counter > 20)
		{
			counter = 0;
			HelperMod.proxy.isSinglePlayer();
		}
	}

	//	@SubscribeEvent
	public void onLivingupdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			World w = ((EntityPlayer) event.entityLiving).worldObj;
			if ((!w.isRemote) && (FMLCommonHandler.instance().getSide() == Side.SERVER))
			{
				System.out.println("SAME!");
			}
			else System.out.println("DIFF!!");
		}
	}
}
