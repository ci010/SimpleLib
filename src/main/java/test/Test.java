package test;

import net.ci010.minecrafthelper.annotation.field.Construct;
import net.ci010.minecrafthelper.annotation.type.BlockItemContainer;
import net.ci010.minecrafthelper.annotation.type.Generate;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
//		testBlock = new TestBlock();
//		GameRegistry.registerBlock(testBlock, TestBlock.name);


	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
//		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
//		{
//			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(testBlock), 0, new
//					ModelResourceLocation("test:".concat(TestBlock.name), "inventory"));
//
//			ModelBakery.addVariantName(Item.getItemFromBlock(testBlock), "test:".concat(TestBlock.name));
//		}
	}
}
