package test.impl;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import test.api.component.item.ComponentItem;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author ci010
 */
public class AdapterItem extends Item
{
	public AdapterItem(ComponentItem item)
	{

	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt)
	{
		return super.updateItemStackNBT(nbt);
	}

	@Override
	public Item setMaxStackSize(int maxStackSize)
	{
		return super.setMaxStackSize(maxStackSize);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block state)
	{
		return super.getStrVsBlock(stack, state);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		return super.onItemUseFinish(stack, worldIn, playerIn);
	}

	@Override
	public int getItemStackLimit()
	{
		return super.getItemStackLimit();
	}

	@Override
	public int getMetadata(int damage)
	{
		return super.getMetadata(damage);
	}

	@Override
	public boolean getHasSubtypes()
	{
		return super.getHasSubtypes();
	}

	@Override
	public Item setHasSubtypes(boolean hasSubtypes)
	{
		return super.setHasSubtypes(hasSubtypes);
	}

	@Override
	public int getMaxDamage()
	{
		return super.getMaxDamage();
	}

	@Override
	public Item setMaxDamage(int maxDamageIn)
	{
		return super.setMaxDamage(maxDamageIn);
	}

	@Override
	public boolean isDamageable()
	{
		return super.isDamageable();
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn)
	{
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
	}

	@Override
	public boolean canHarvestBlock(Block blockIn)
	{
		return super.canHarvestBlock(blockIn);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
	{
		return super.itemInteractionForEntity(stack, playerIn, target);
	}

	@Override
	public Item setFull3D()
	{
		return super.setFull3D();
	}

	@Override
	public boolean isFull3D()
	{
		return super.isFull3D();
	}

	@Override
	public boolean shouldRotateAroundWhenRendering()
	{
		return super.shouldRotateAroundWhenRendering();
	}

	@Override
	public Item setUnlocalizedName(String unlocalizedName)
	{
		return super.setUnlocalizedName(unlocalizedName);
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack stack)
	{
		return super.getUnlocalizedNameInefficiently(stack);
	}

	@Override
	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack);
	}

	@Override
	public Item setContainerItem(Item containerItem)
	{
		return super.setContainerItem(containerItem);
	}

	@Override
	public boolean getShareTag()
	{
		return super.getShareTag();
	}

	@Override
	public Item getContainerItem()
	{
		return super.getContainerItem();
	}

	@Override
	public boolean hasContainerItem()
	{
		return super.hasContainerItem();
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		return super.getColorFromItemStack(stack, renderPass);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		super.onCreated(stack, worldIn, playerIn);
	}

	@Override
	public boolean isMap()
	{
		return super.isMap();
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return super.getItemUseAction(stack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return super.getMaxItemUseDuration(stack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
	{
		super.onPlayerStoppedUsing(stack, worldIn, playerIn, timeLeft);
	}

	@Override
	public Item setPotionEffect(String potionEffect)
	{
		return super.setPotionEffect(potionEffect);
	}

	@Override
	public String getPotionEffect(ItemStack stack)
	{
		return super.getPotionEffect(stack);
	}

	@Override
	public boolean isPotionIngredient(ItemStack stack)
	{
		return super.isPotionIngredient(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return super.hasEffect(stack);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return super.getRarity(stack);
	}

	@Override
	public boolean isItemTool(ItemStack stack)
	{
		return super.isItemTool(stack);
	}

	@Override
	protected MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn, boolean useLiquids)
	{
		return super.getMovingObjectPositionFromPlayer(worldIn, playerIn, useLiquids);
	}

	@Override
	public int getItemEnchantability()
	{
		return super.getItemEnchantability();
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		super.getSubItems(itemIn, tab, subItems);
	}

	@Override
	public Item setCreativeTab(CreativeTabs tab)
	{
		return super.setCreativeTab(tab);
	}

	@Override
	public CreativeTabs getCreativeTab()
	{
		return super.getCreativeTab();
	}

	@Override
	public boolean canItemEditBlocks()
	{
		return super.canItemEditBlocks();
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers()
	{
		return super.getItemAttributeModifiers();
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack)
	{
		return super.getAttributeModifiers(stack);
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
	{
		return super.onDroppedByPlayer(item, player);
	}

	@Override
	public String getHighlightTip(ItemStack item, String displayName)
	{
		return super.getHighlightTip(item, displayName);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, IBlockState state)
	{
		return super.getDigSpeed(itemstack, state);
	}

	@Override
	public boolean isRepairable()
	{
		return super.isRepairable();
	}

	@Override
	public Item setNoRepair()
	{
		return super.setNoRepair();
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player)
	{
		return super.onBlockStartBreak(itemstack, pos, player);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		super.onUsingTick(stack, player, count);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		return super.getModel(stack, player, useRemaining);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		return super.getContainerItem(itemStack);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return super.hasContainerItem(stack);
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world)
	{
		return super.getEntityLifespan(itemStack, world);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return super.hasCustomEntity(stack);
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return super.createEntity(world, location, itemstack);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		return super.onEntityItemUpdate(entityItem);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		return super.getCreativeTabs();
	}

	@Override
	public float getSmeltingExperience(ItemStack item)
	{
		return super.getSmeltingExperience(item);
	}

	@Override
	public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original)
	{
		return super.getChestGenBase(chest, rnd, original);
	}

	@Override
	public boolean doesSneakBypassUse(World world, BlockPos pos, EntityPlayer player)
	{
		return super.doesSneakBypassUse(world, pos, player);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		super.onArmorTick(world, player, itemStack);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		return super.isValidArmor(stack, armorType, entity);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return super.isBookEnchantable(stack, book);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return super.getArmorTexture(stack, entity, slot, type);
	}

	@Override
	public FontRenderer getFontRenderer(ItemStack stack)
	{
		return super.getFontRenderer(stack);
	}

	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return super.getArmorModel(entityLiving, itemStack, armorSlot);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		return super.onEntitySwing(entityLiving, stack);
	}

	@Override
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks)
	{
		super.renderHelmetOverlay(stack, player, resolution, partialTicks);
	}

	@Override
	public int getDamage(ItemStack stack)
	{
		return super.getDamage(stack);
	}

	@Override
	public int getMetadata(ItemStack stack)
	{
		return super.getMetadata(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return super.showDurabilityBar(stack);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return super.getDurabilityForDisplay(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return super.getMaxDamage(stack);
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return super.isDamaged(stack);
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, damage);
	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack)
	{
		return super.canHarvestBlock(par1Block, itemStack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return super.getItemStackLimit(stack);
	}

	@Override
	public void setHarvestLevel(String toolClass, int level)
	{
		super.setHarvestLevel(toolClass, level);
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		return super.getToolClasses(stack);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		return super.getHarvestLevel(stack, toolClass);
	}

	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		return super.getItemEnchantability(stack);
	}

	@Override
	public boolean isBeaconPayment(ItemStack stack)
	{
		return super.isBeaconPayment(stack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
	{
		return super.initCapabilities(stack, nbt);
	}


}
