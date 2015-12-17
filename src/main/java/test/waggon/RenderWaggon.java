package test.waggon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class RenderWaggon extends Render
{
	ModelWaggon model = new ModelWaggon();

	public RenderWaggon()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.enableAlpha();
		GlStateManager.scale(0.1, 0.1, 0.1);
		this.bindEntityTexture(entity);
		this.model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
		GlStateManager.disableAlpha();
		GlStateManager.popMatrix();
	}

	private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
//	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return cowTextures;
	}

	class ModelWaggon extends ModelBase
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
			block.render(scale);
		}
	}


}
