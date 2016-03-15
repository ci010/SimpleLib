package net.simplelib.common.registry;

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
public class RegItem extends RegComponentBase<Item>
{
	public RegItem(Item wrap)
	{
		super(wrap);
	}

	@Override
	public RegComponentBase<Item> setUnlocalizedName(String name)
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
	public RegComponentBase<Item> setCreativeTab(CreativeTabs tab)
	{
		this.getComponent().setCreativeTab(tab);
		return this;
	}

	@Override
	public RegComponentBase<Item> register(String name)
	{
		GameRegistry.registerItem(this.getComponent(), name);
		return this;
	}

	@Override
	public RegComponentBase<Item> registerOre(String name)
	{
		OreDictionary.registerOre(name, this.getComponent());
		return this;
	}

	@Override
	public RegComponentBase<Item> registerModel(String name)
	{
		Item item = this.getComponent();
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,
				0, new ModelResourceLocation(RegistryHelper.INSTANCE.currentMod() +
						":" + name, "inventory"));
		ModelBakery.addVariantName(item, RegistryHelper.INSTANCE.currentMod() + ":" + name);
		return this;
	}
}
