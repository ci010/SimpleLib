package test.api.component.block;

import api.simplelib.ContextBlockInteract;
import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;
import test.api.component.Merger;
import test.api.component.Module;
import test.api.component.block.module.*;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.world.World;
import test.impl.block.module.ModuleRainDrop;

import java.util.List;
import java.util.Random;

/**
 * @author ci010
 */
public class BlockModules
{
//	public static final Module<LeftClick> LEFT_CLICK;
//	public static final Module<RightClick> RIGHT_CLICK;
//
//	public static final Module<StartBreak> START_BREAK;
//	public static final Module<Break> BREAK;
//	public static final Module<ShouldHarvest> SHOULD_HARVEST;
//	public static final Module<Harvest> HARVEST;
//
//	public static final Module<NeighborChange> NEIGHBOR_CHANGE;
//	public static final Module<PowerProvider> POWER_PROVIDER;
//	public static final Module<RainDrop> RAIN_DROP = ModuleRainDrop.INSTANCE;
//	public static final Module<RandomTick> RANDOM_TICK;

//	static
//	{

//		BREAK = new Module<Break>("break", Break.class, new Merger<Break>()
//		{
//			Break container = null;
//			List<Break> delegates = null;
//
//			@Override
//			public Break merge(final Break a, final Break b)
//			{
//				if (container == null)
//				{
//					container = new Break()
//					{
//						@Override
//						public void onBlockBreak(World world, BlockPos pos, StateBlock state)
//						{
//							for (Break delegate : delegates)
//								delegate.onBlockBreak(world, pos, state);
//						}
//					};
//					delegates = Lists.newArrayList();
//				}
//				return new Break()
//				{
//					@Override
//					public void onBlockBreak(World world, BlockPos pos, StateBlock state)
//					{
//						a.onBlockBreak(world, pos, state);
//						b.onBlockBreak(world, pos, state);
//					}
//				};
//			}
//		});
//		HARVEST = new Module<Harvest>("harvest", Harvest.class, new Merger<Harvest>()
//		{
//			@Override
//			public Harvest merge(final Harvest a, final Harvest b)
//			{
//				return new Harvest()
//				{
//					@Override
//					public void onBlockHarvested(World world, BlockPos pos, StateBlock state, StatePlayer player)
//					{
//						a.onBlockHarvested(world, pos, state, player);
//						b.onBlockHarvested(world, pos, state, player);
//					}
//				};
//			}
//		});
//		LEFT_CLICK = new Module<LeftClick>("left_click", LeftClick.class, new Merger<LeftClick>()
//		{
//			@Override
//			public LeftClick merge(final LeftClick a, final LeftClick b)
//			{
//				return new LeftClick()
//				{
//					@Override
//					public void onBlockClicked(World world, BlockPos pos, StatePlayer player)
//					{
//						a.onBlockClicked(world, pos, player);
//						b.onBlockClicked(world, pos, player);
//					}
//				};
//			}
//		});
//		NEIGHBOR_CHANGE = new Module<NeighborChange>("neighbor_change", NeighborChange.class, new Merger<NeighborChange>()
//		{
//			@Override
//			public NeighborChange merge(final NeighborChange a, final NeighborChange b)
//			{
//				return new NeighborChange()
//				{
//					@Override
//					public void onNeighborBlockChange(World world, BlockPos pos, StateBlock state, ComponentBlock neighborBlock)
//					{
//						a.onNeighborBlockChange(world, pos, state, neighborBlock);
//						b.onNeighborBlockChange(world, pos, state, neighborBlock);
//					}
//				};
//			}
//		});
//		POWER_PROVIDER = new Module<PowerProvider>("power_provider", PowerProvider.class, new Merger<PowerProvider>()
//		{
//			@Override
//			public PowerProvider merge(PowerProvider a, PowerProvider b)
//			{
//				return a;
//			}
//		});
//		RANDOM_TICK = new Module<RandomTick>("random_tick", RandomTick.class, new Merger<RandomTick>()
//		{
//			@Override
//			public RandomTick merge(final RandomTick a, final RandomTick b)
//			{
//				return new RandomTick()
//				{
//					@Override
//					public void onRandomTick(World world, Random random)
//					{
//						a.onRandomTick(world, random);
//						b.onRandomTick(world, random);
//					}
//				};
//			}
//		});
//		RIGHT_CLICK = new Module<RightClick>("right_click", RightClick.class, new Merger<RightClick>()
//		{
//			@Override
//			public RightClick merge(final RightClick a, final RightClick b)
//			{
//				return new RightClick()
//				{
//					@Override
//					public boolean onBlockActivated(StateBlock state, StatePlayer playerIn, ContextBlockInteract info)
//					{
//						return a.onBlockActivated(state, playerIn, info) && b.onBlockActivated(state, playerIn, info);
//					}
//				};
//			}
//		});
//		SHOULD_HARVEST = new Module<ShouldHarvest>("should_harvest", ShouldHarvest.class, new Merger<ShouldHarvest>()
//		{
//			@Override
//			public ShouldHarvest merge(final ShouldHarvest a, final ShouldHarvest b)
//			{
//				return new ShouldHarvest()
//				{
//					@Override
//					public boolean shouldBlockHarvest(World world, BlockPos pos, StateBlock state, StatePlayer player)
//					{
//						return a.shouldBlockHarvest(world, pos, state, player) && b.shouldBlockHarvest(world, pos, state, player);
//					}
//				};
//			}
//		});
//		START_BREAK = new Module<StartBreak>("start_break", StartBreak.class, new Merger<StartBreak>()
//		{
//			@Override
//			public StartBreak merge(final StartBreak a, final StartBreak b)
//			{
//				return new StartBreak()
//				{
//					@Override
//					public boolean startBreak(World world, BlockPos pos, StateBlock state)
//					{
//						return a.startBreak(world, pos, state) && b.startBreak(world, pos, state);
//					}
//				};
//			}
//		});
//	}
}
