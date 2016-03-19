package test;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.RegistryHelper;
import api.simplelib.component.Construct;

/**
 * @author ci010
 */
@Mod(modid = "test")
//@ComponentsReference
public class TestMod
{
	@Mod.Instance("test")
	public static TestMod test;

	@Construct(TestBlock.class)
	public static Block testBlock;

	//TODO handle the situation that the only parameter is the material.
//	@Construct(TestGrowableBlock.class)
//	public static Block testGrowth;

	public static String name;

	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{
		RegistryHelper.INSTANCE.setLang("test");
		RegistryHelper.INSTANCE.setModel("test");

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
	}
}
