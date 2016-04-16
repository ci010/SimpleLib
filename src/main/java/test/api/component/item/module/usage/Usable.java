package test.api.component.item.module.usage;

import api.simplelib.ContextBlockInteract;
import com.google.common.base.Optional;
import net.minecraft.item.EnumAction;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.api.world.World;

/**
 * @author ci010
 */
public abstract class Usable implements RightClickAction
{
	@Override
	public StateItem onItemRightClick(StateItem itemStackIn, World worldIn, StatePlayer playerIn)
	{
		if (playerIn.getFoodState().needFood() || alwaysEatable(itemStackIn))
			playerIn.getAbility().setItemInUse(itemStackIn, getMaxDuration(itemStackIn), getAction());
		return itemStackIn;
	}

	abstract EnumAction getAction();

	abstract boolean alwaysEatable(StateItem stateItem);

	abstract int getMaxDuration(StateItem stateItem);

	abstract void onItemUseFinish(StateItem stack, World worldIn, StatePlayer playerIn);

	@Override
	public Optional<? extends BlockHandler> getBlockHandler()
	{
		return Optional.of(new BlockHandler()
		{
			@Override
			public boolean onItemUseFirst(StateItem stack, StatePlayer player, ContextBlockInteract info)
			{
				return false;
			}

			@Override
			public boolean onItemUse(StateItem stack, StatePlayer playerIn, ContextBlockInteract info)
			{
				return false;
			}

			@Override
			public StateItem onItemUseFinish(StateItem stack, World worldIn, StatePlayer playerIn)
			{
				stack.setStackSize(stack.getStackSize() - 1);
//				playerIn.getFoodStats().addStats(this, stack);
				worldIn.playSoundAtEntity(playerIn, "random.burp", 0.5F, worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
				Usable.this.onItemUseFinish(stack, worldIn, playerIn);
//				playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
				return stack;
			}

			@Override
			public void onUsingTick(StateItem stack, StatePlayer player, int count)
			{

			}

			@Override
			public void onPlayerStoppedUsing(StateItem stack, World worldIn, StatePlayer playerIn, int timeLeft)
			{

			}
		});
	}

	@Override
	public Optional<? extends EntityHandler> getEntityHandler()
	{
		return Optional.absent();
	}


}
