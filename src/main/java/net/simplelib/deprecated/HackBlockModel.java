package net.simplelib.deprecated;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
@SideOnly(Side.CLIENT)
@Deprecated
public class HackBlockModel
{
	private static Map<IBlockState, IBakedModel> bakedModelMapping;

	static
	{
		BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockModelShapes shapes = dispatcher.getBlockModelShapes();
		bakedModelMapping = ReflectionHelper.getPrivateValue(BlockModelShapes.class, shapes, "bakedModelStore");
//		PersistentRegistryManager.GameDataSnapshot snapshot = PersistentRegistryManager.takeSnapshot();
//		PersistentRegistryManager.GameDataSnapshot.Entry blocksEntry = snapshot.entries.get(PersistentRegistryManager.BLOCKS);
//		System.out.println("ids");
//		Set<Integer>
//		BlockState b;
//		b.getBaseState()
//		for (int id : blocksEntry.ids.values())
//		{
//			Block block = Block.getBlockById(id);
//			int startId = id << 4;
//			for (IBlockState state : block.getBlockState().getValidStates())
//			{
//				int stateId = id << 4 | block.getMetaFromState(state);
//
//			}
//		}
//		GameData.getBlockItemMap().

	}

	static Item proxy;

	static void hackItem()
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(proxy, new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				NBTTagCompound tag = stack.getTagCompound();
				String location = tag.getString("location"), var = tag.getString("variant");
				return new ModelResourceLocation(location, var);
			}
		});
	}

	public static IBlockState createDummyBlockState()
	{
		return null;
	}

	public static void testSwitch()
	{
		IBlockState brick = Blocks.brick_block.getDefaultState(), stoneBrick = Blocks.stonebrick.getDefaultState();
		IBakedModel brickModel = bakedModelMapping.get(brick), stoneBrickModel = bakedModelMapping.get(stoneBrick);
		bakedModelMapping.put(brick, stoneBrickModel);
		bakedModelMapping.put(stoneBrick, brickModel);
	}

	public static IBakedModel get(IBlockState state)
	{
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
	}

	public static void setBakedModel(final IBlockState state, final IBakedModel model)
	{
		Minecraft.getMinecraft().addScheduledTask(new Runnable()
		{
			@Override
			public void run()
			{
				bakedModelMapping.put(state, model);
			}
		});
	}
}
