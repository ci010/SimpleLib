package net.simplelib.registry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.data.ContainerMeta;

@SideOnly(Side.CLIENT)
public class ModelHelper
{
	public void registerItem(ContainerMeta meta, Item item, String... name)
	{
		if (!meta.getItemModelHandler().handle(item))
			register(item, meta.modid, name);
	}

	private void register(Item target, String modId, String... name)
	{
		int index = 0;
		for (String sub : name)
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(target,
					index++,
					new ModelResourceLocation(modId +
							":" +
							sub, "inventory"));
			ModelBakery.addVariantName(target, modId + ":" + sub);
		}
	}

	public void registerBlock(ContainerMeta meta, Block block, String name)
	{
		if (!meta.getBlockModelHandler().handle(block))
			register(Item.getItemFromBlock(block), meta.modid, name);
	}
}
