package api.simplelib.minecraft;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ITransformation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @author ci010
 */
public class ModelBakerHot
{
	private FaceBakery faceBakery = new FaceBakery();

	public IBakedModel bakeModel(ModelBlockDefinition modelBlock, ModelResourceLocation... locations)
	{

		return null;
	}

	public IBakedModel bakeModel(ModelBlock modelBlock, ITransformation rotateion, boolean uvLocked)
	{
		return null;

//		TextureAtlasSprite particle = (TextureAtlasSprite) this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName("particle")));
//		SimpleBakedModel.Builder builder = (new SimpleBakedModel.Builder(modelBlock)).setTexture(particle);
//		for (BlockPart blockpart : modelBlock.getElements())
//			for (EnumFacing enumfacing : blockpart.mapFaces.keySet())
//			{
//				BlockPartFace face = blockpart.mapFaces.get(enumfacing);
//				TextureAtlasSprite faceSprite = (TextureAtlasSprite) this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName(face.texture)));
//
//				if (face.cullFace == null || !net.minecraftforge.client.model.TRSRTransformation.isInteger(rotateion.getMatrix()))
//				{
//					builder.addGeneralQuad(faceBakery.makeBakedQuad(
//							blockpart.positionFrom, blockpart.positionTo,
//							face, faceSprite,
//							enumfacing, rotateion,
//							blockpart.partRotation,
//							uvLocked, blockpart.shade));
//				}
//				else
//				{
//					builder.addFaceQuad(rotateion.rotate(face.cullFace),
//							faceBakery.makeBakedQuad(
//									blockpart.positionFrom, blockpart.positionTo,
//									face, faceSprite,
//									enumfacing, rotateion,
//									blockpart.partRotation,
//									uvLocked, blockpart.shade));
//				}
//			}

//		return builder.makeBakedModel();
	}
}
