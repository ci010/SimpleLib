package net.simplelib.remote;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ci010
 */
//@ModTileEntity
//@ModTileEntity.Render(TileEntityProxy.Render.class)
public class TileEntityProxy extends TileEntity
{
	@SideOnly(Side.CLIENT)
	private IBakedModel model;
	private String id;

	private Block blockProperty;

	public Block getDelegateBlock()
	{
		return blockProperty;
	}

	@SideOnly(Side.CLIENT)
	protected IBakedModel getRenderModel()
	{
		return model;
	}

	public static class Render extends TileEntitySpecialRenderer<TileEntityProxy>
	{
		@Override
		public void renderTileEntityAt(TileEntityProxy te, double x, double y, double z, float partialTicks, int destroyStage)
		{
			BlockPos pos = new BlockPos(x, y, z);
//			Minecraft.isAmbientOcclusionEnabled() && blockStateIn.getBlock().getLightValue() == 0 && modelIn.isAmbientOcclusion()
			RegionRenderCache cache = MinecraftForgeClient.getRegionRenderCache(te.worldObj, pos);
			Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModelAmbientOcclusion
					(cache, te.getRenderModel(), te.getBlockType(), pos, Tessellator.getInstance().getWorldRenderer(), false);
		}
	}
}
