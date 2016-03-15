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
import net.simplelib.common.registry.abstracts.RegComponentBase;

/**
 * @author CI010
 */
public class RegBlock extends RegComponentBase<Block>
{
	public RegBlock(Block wrap)
	{
		super(wrap);
	}

	@Override
	public RegComponentBase<Block> setUnlocalizedName(String name)
	{
		this.getComponent().setUnlocalizedName(name);
		return this;
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.getComponent().getUnlocalizedName();
	}

	@Override
	public RegComponentBase<Block> setCreativeTab(CreativeTabs tab)
	{
		this.getComponent().setCreativeTab(tab);
		return this;
	}

	@Override
	public RegComponentBase<Block> register(String name)
	{
		GameRegistry.registerBlock(this.getComponent(), name);
		return this;
	}

	@Override
	public RegComponentBase<Block> registerOre(String name)
	{
		OreDictionary.registerOre(name, this.getComponent());
		return this;
	}

	@Override
	public RegComponentBase<Block> registerModel(String name)
	{
		Item item = Item.getItemFromBlock(this.getComponent());
		String regName = RegistryHelper.INSTANCE.currentMod().concat(":").concat(name);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
				new ModelResourceLocation(regName, "inventory"));
		ModelBakery.addVariantName(item, regName);
		return this;
	}
}
