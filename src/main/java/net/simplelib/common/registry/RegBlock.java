package net.simplelib.common.registry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.simplelib.RegistryHelper;
import net.simplelib.common.registry.abstracts.RegComponent;

/**
 * @author CI010
 */
public class RegBlock extends RegComponent<Block>
{
	public RegBlock(Block wrap)
	{
		super(wrap);
	}

	@Override
	public Block setUnlocalizedName(String name)
	{
		return this.getComponent().setUnlocalizedName(name);
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.getComponent().getUnlocalizedName();
	}

	@Override
	public Block setCreativeTab(CreativeTabs tab)
	{
		return this.getComponent().setCreativeTab(tab);
	}

	@Override
	public Block register(String name)
	{
		return GameRegistry.registerBlock(this.getComponent(), name);
	}

	@Override
	public Block registerOre(String name)
	{
		OreDictionary.registerOre(name, this.getComponent());
		return this.getComponent();
	}

	@Override
	public Block registerModel(String name)
	{
		Item item = Item.getItemFromBlock(this.getComponent());
		String regName = RegistryHelper.INSTANCE.currentMod().concat(":").concat(name);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
				new ModelResourceLocation(regName, "inventory"));
		ModelBakery.addVariantName(item, regName);
		return this.getComponent();
	}
}
