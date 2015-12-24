package test;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.simplelib.annotation.field.Construct;
import net.simplelib.annotation.type.BlockItemContainer;
import net.simplelib.annotation.type.Generate;

/**
 * @author ci010
 */
@Mod(modid = "test")
@Generate({Generate.GenerateType.language, Generate.GenerateType.model})
@BlockItemContainer
public class Test
{
	@Mod.Instance("test")
	public static Test test;

	@Construct(TestBlock.class)
	public static Block testBlock;

	public static String name;

	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
	}
}
