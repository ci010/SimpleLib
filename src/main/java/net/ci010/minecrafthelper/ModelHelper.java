package net.ci010.minecrafthelper;

import java.util.Map;

import com.google.common.collect.Maps;

import net.ci010.minecrafthelper.abstracts.ModelHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class ModelHelper
{
	Map<String, ModelHandler<Item>> itemHandlers = Maps.newHashMap();
	Map<String, ModelHandler<Block>> blockHandlers = Maps.newHashMap();

	public void registerItemModelHandler(String modid, ModelHandler<Item> handler)
	{
		itemHandlers.put(modid, handler);
	}

	public void registerBlockModelHandler(String modid, ModelHandler<Block> handler)
	{
		blockHandlers.put(modid, handler);
	}

	void registerItem(Item item, String... name)
	{
		String modid = Loader.instance().activeModContainer().getModId();
		for (ModelHandler<Item> h : itemHandlers.values())
			if (!h.handle(item))
				register(item, modid, name);
	}

	private void register(Item target, String modId, String... name)
	{
		int index = 0;
		for (String sub : name)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(	target,
																					index++,
																					new ModelResourceLocation(modId +
																												":" +
																												sub, "inventory"));
		ModelBakery.addVariantName(target, modId + ":" + name);
	}

	void registerBlock(Block block, String name)
	{
		String modid = Loader.instance().activeModContainer().getModId();
		for (ModelHandler<Block> h : blockHandlers.values())
			if (!h.handle(block))
				register(Item.getItemFromBlock(block), modid, name);
	}
}
