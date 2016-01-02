package net.simplelib.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.simplelib.RegistryHelper;
import net.simplelib.registry.abstracts.MinecraftComponent;

/**
 * @author CI010
 */
public class ComponentItem extends MinecraftComponent<Item>
{
	public ComponentItem(Item wrap)
	{
		super(wrap);
	}

	@Override
	public Item setUnlocalizedName(String name)
	{
		return this.getComponent().setUnlocalizedName(name);
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.getComponent().getUnlocalizedName();
	}

	@Override
	public Item setCreativeTab(CreativeTabs tab)
	{
		return this.getComponent().setCreativeTab(tab);
	}

	@Override
	public Item register(String name)
	{
		return GameRegistry.registerItem(this.getComponent(), name, "");
	}

	@Override
	public Item registerOre(String name)
	{
		OreDictionary.registerOre(name, this.getComponent());
		return this.getComponent();
	}

	@Override
	public Item registerModel(String name)
	{
		Item item = this.getComponent();
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,
				0, new ModelResourceLocation(RegistryHelper.INSTANCE.currentMod() +
						":" + name, "inventory"));
		ModelBakery.addVariantName(item, RegistryHelper.INSTANCE.currentMod() + ":" + name);
		return item;
	}
}
