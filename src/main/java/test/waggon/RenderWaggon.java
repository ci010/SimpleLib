package test.waggon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class RenderWaggon extends RenderLiving
{
	ModelWaggon model;

	public RenderWaggon()
	{
		super(Minecraft.getMinecraft().getRenderManager(), new ModelWaggon(), 3);
		System.out.println("Render and model init.");
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
//		EntityWaggon waggon = (EntityWaggon) entity;
//		double nextX = insert(x, waggon.lastTickPosX, partialTicks), nextY = insert(y, waggon.lastTickPosY, partialTicks), nextZ =
//				insert(z, waggon.lastTickPosZ, partialTicks);
//		GlStateManager.pushMatrix();
//		GlStateManager.translate(nextX, nextY, nextZ);
//		System.out.println("render on " + x + " " + y + " " + z);
//		System.out.println("render");
//		GlStateManager.translate(x, y, z);

//		GlStateManager.enableAlpha();
//		GlStateManager.scale(0.3, 0.3, 0.3);
//		this.bindEntityTexture(entity);
//		this.model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
//		GlStateManager.disableAlpha();
//		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

	}

	private double insert(double current, double last, float partial)
	{
		double r = last + (current - last) * partial;

//		System.out.println("insert " + current + " " + last + " = " + r);
		return r;
	}

	private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
//	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return cowTextures;
	}

	static class ModelWaggon extends ModelBase
	{
		ModelRenderer block;

		public ModelWaggon()
		{
			this.block = new ModelRenderer(this, 28, 8);
			this.block.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, false);
		}

		@Override
		public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
		{
//			GlStateManager.pushMatrix();
			block.render(scale);
//			GlStateManager.popMatrix();
		}
	}


}
