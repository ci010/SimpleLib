package test.waggon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import static net.minecraft.client.renderer.GlStateManager.*;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public class RenderRewrite extends Render
{
	static ResourceLocation location = new ResourceLocation("textures/entity/boat.png");
	ModelBase base;

	protected RenderRewrite(RenderManager renderManager)
	{
		super(renderManager);
		base = new ModelTest();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return location;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		pushMatrix();
		translate(x, y, z);
		rotate(180 - entityYaw, 0f, 1f, 0f);
//		enableAlpha();
		scale(1.5, 1.5, 1.5);
		this.bindEntityTexture(entity);
		this.base.render(entity, 0f, 0f, 0f, 0f, 0f, 0.0625F);
//		disableAlpha();
		popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	static class ModelTest extends ModelBase
	{
		ModelRenderer[] parts;

		ModelTest()
		{
			parts = new ModelRenderer[5];
			parts[0] = new ModelRenderer(this, 0, 8);
			parts[1] = new ModelRenderer(this, 0, 0);
			parts[2] = new ModelRenderer(this, 0, 0);
			parts[3] = new ModelRenderer(this, 0, 0);
			parts[4] = new ModelRenderer(this, 0, 0);
			byte x_width_factor = 24;
			byte y_height_factor = 6;
			byte b2 = 20;
			byte b3 = 4;
			this.parts[0].addBox((float) (-x_width_factor / 2), (float) (-b2 / 2 + 2), -3.0F, x_width_factor, b2 - 4, 4, 0.0F);
			this.parts[0].setRotationPoint(0.0F, (float) b3, 0.0F);
			this.parts[1].addBox((float) (-x_width_factor / 2 + 2), (float) (-y_height_factor - 1), -1.0F, x_width_factor - 4, y_height_factor, 2, 0.0F);
			this.parts[1].setRotationPoint((float) (-x_width_factor / 2 + 1), (float) b3, 0.0F);
			this.parts[2].addBox((float) (-x_width_factor / 2 + 2), (float) (-y_height_factor - 1), -1.0F, x_width_factor - 4, y_height_factor, 2, 0.0F);
			this.parts[2].setRotationPoint((float) (x_width_factor / 2 - 1), (float) b3, 0.0F);
			this.parts[3].addBox((float) (-x_width_factor / 2 + 2), (float) (-y_height_factor - 1), -1.0F, x_width_factor - 4, y_height_factor, 2, 0.0F);
			this.parts[3].setRotationPoint(0.0F, (float) b3, (float) (-b2 / 2 + 1));
			this.parts[4].addBox((float) (-x_width_factor / 2 + 2), (float) (-y_height_factor - 1), -1.0F, x_width_factor - 4, y_height_factor, 2, 0.0F);
			this.parts[4].setRotationPoint(0.0F, (float) b3, (float) (b2 / 2 - 1));
			this.parts[0].rotateAngleX = ((float) Math.PI / 2F);
			this.parts[1].rotateAngleY = ((float) Math.PI * 3F / 2F);
			this.parts[2].rotateAngleY = ((float) Math.PI / 2F);
			this.parts[3].rotateAngleY = (float) Math.PI;
		}

		@Override
		public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
		{
			for (ModelRenderer part : parts)
				part.render(scale);
		}
	}
}
