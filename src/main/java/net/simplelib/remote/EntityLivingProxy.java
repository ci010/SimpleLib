package net.simplelib.remote;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

/**
 * @author ci010
 */
//@ModEntity
//@ModEntity.Render(EntityLivingProxy.RenderAdapt.class)
public class EntityLivingProxy extends EntityLiving implements IEntityAdditionalSpawnData
{
	private ResourceLocation texture, model;

	public EntityLivingProxy(World worldIn)
	{
		super(worldIn);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, texture.toString());
		ByteBufUtils.writeUTF8String(buffer, model.toString());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		this.texture = new ResourceLocation(ByteBufUtils.readUTF8String(additionalData));
		this.model = new ResourceLocation(ByteBufUtils.readUTF8String(additionalData));
	}

	public static class RenderAdapt extends Render<EntityLivingProxy>
	{
		protected RenderAdapt(RenderManager renderManager)
		{
			super(renderManager);
		}

		@Override
		protected ResourceLocation getEntityTexture(EntityLivingProxy entity)
		{
			return null;
		}
	}
}
