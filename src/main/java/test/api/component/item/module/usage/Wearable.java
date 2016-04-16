package test.api.component.item.module.usage;

import api.simplelib.common.Nullable;
import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.api.world.World;

/**
 * @author ci010
 */
public abstract class Wearable implements RightClickAction //extends Info
{
	@Override
	public StateItem onItemRightClick(StateItem state, World worldIn, StatePlayer playerIn)
	{
//		int i = EntityLiving.getArmorPosition(itemStackIn) - 1;
		int i = this.getWearPosition(state);
		Optional<StateItem> currentArmor = playerIn.getCurrentArmor(i);
		if (currentArmor.isPresent())
		{
//			playerIn.setCurrentItemOrArmor(i + 1, itemStackIn.copy()); //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
			state.setStackSize(0);
		}
		return state;
	}

	protected abstract int getWearPosition(StateItem item);

	@Override
	public Optional<? extends BlockHandler> getBlockHandler()
	{
		return null;
	}

	@Override
	public Optional<? extends EntityHandler> getEntityHandler()
	{
		return null;
	}
	//	@Override
//	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
//	{
//		int i = EntityLiving.getArmorPosition(itemStackIn) - 1;
//		ItemStack itemstack = playerIn.getCurrentArmor(i);
//
//		if (itemstack == null)
//		{
//			playerIn.setCurrentItemOrArmor(i + 1, itemStackIn.copy()); //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
//			itemStackIn.stackSize = 0;
//		}
//
//		return itemStackIn;
//	}

	@Nullable
	protected abstract Handler getArmorHandler();

	public interface Handler
	{
		void onArmorTick(World world, EntityPlayer player, ItemStack itemStack);//TODO make wearable

		boolean isValidArmor(ItemStack stack, int armorType, Entity entity); //TODO wearable

		/**
		 * Called by RenderBiped and RenderPlayer to determine the armor texture that
		 * should be use for the currently equipped item.
		 * This will only be called on instances of ItemArmor.
		 * <p/>
		 * Returning null from this function will use the default value.
		 *
		 * @param stack  ItemStack for the equipped armor
		 * @param entity The entity wearing the armor
		 * @param slot   The slot the armor is in
		 * @param type   The subtype, can be null or "overlay"
		 * @return Path of texture to bind, or null to use default
		 */
		String getArmorTexture(ItemStack stack, Entity entity, int slot, String type);//TODO wearable

		/**
		 * Override this method to have an item handle its own armor rendering.
		 *
		 * @param entityLiving The entity wearing the armor
		 * @param itemStack    The itemStack to render the model of
		 * @param armorSlot    0=head, 1=torso, 2=legs, 3=feet
		 * @return A ModelBiped to render instead of the default
		 */
		net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot);//TODO wearable

		/**
		 * Called when the client starts rendering the HUD, for whatever item the player currently has as a helmet.
		 * This is where pumpkins would render there overlay.
		 *
		 * @param stack        The ItemStack that is equipped
		 * @param player       Reference to the current client entity
		 * @param resolution   Resolution information about the current viewport and configured GUI Scale
		 * @param partialTicks Partial ticks for the renderer, useful for interpolation
		 */
		void renderHelmetOverlay(ItemStack stack, EntityPlayer player, net.minecraft.client.gui.ScaledResolution
				resolution, float partialTicks);

		/**
		 * Player, Render pass, and item usage sensitive version of getIconIndex.
		 *
		 * @param stack        The item stack to get the icon for.
		 * @param player       The player holding the item
		 * @param useRemaining The ticks remaining for the active item.
		 * @return Null to use default model, or a custom ModelResourceLocation for the stage of use.
		 */
		@SideOnly(Side.CLIENT)
		net.minecraft.client.resources.model.ModelResourceLocation getModel(ItemStack stack, EntityPlayer player,
																			int useRemaining);
	}
}
