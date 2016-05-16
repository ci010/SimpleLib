package net.simplelib;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.common.CommonLogger;
import net.simplelib.common.registry.ContainerMeta;
import net.simplelib.common.registry.Namespace;
import net.simplelib.common.registry.NamespaceMakerComplex;
import api.simplelib.utils.NameFormattor;
import net.simplelib.login.restriction.ModRestriction;

import java.util.Iterator;

public class CommonProxy
{
	public Side getSide()
	{
		return Side.SERVER;
	}

	public boolean isClient() {return false;}

	public boolean isClientSide() {return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;}

	public boolean isSinglePlayer()
	{
		return Minecraft.getMinecraft().isSingleplayer();
	}

	@SideOnly(Side.CLIENT)
	public EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	void preInit(FMLPreInitializationEvent event)
	{
//		FMLCommonHandler.instance().getMinecraftServerInstance().registerTickable(TickServerSide.INSTANCE);
		NamespaceMakerComplex maker = new NamespaceMakerComplex(RegistryHelper.INSTANCE.getAnnotationMap());
		ModRestriction.preInit(event);
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			RegistryHelper.INSTANCE.start(meta);
			CommonLogger.info("Start to register [".concat(meta.modid).concat("] mod."));
			ImmutableSet<Namespace> cache;
			for (Class c : meta.getRawContainer())
				if ((cache = maker.make(c)) != null)
					meta.addUnregistered(cache);
			RegistryHelper.INSTANCE.end();
		}
	}

	void init(FMLInitializationEvent event)
	{
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			RegistryHelper.INSTANCE.start(meta);
			this.register(meta);
			RegistryHelper.INSTANCE.end();
		}
	}

	protected void register(ContainerMeta meta)
	{
		String registerName;
		String unlocalizedName;
		for (Namespace namespace : meta.getUnregistered())
		{
			registerName = NameFormattor.upperTo_(namespace.toString());
			unlocalizedName = registerName;//NameFormattor._toPoint(registerName);
			namespace.getComponent().setUnlocalizedName(unlocalizedName);
			namespace.getComponent().register(registerName);
			if (namespace.needRegOre())
				namespace.getComponent().registerOre(namespace.getOreName());
		}
	}
}
