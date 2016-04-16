package test.impl;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import test.api.component.block.Property;

/**
 * @author ci010
 */
public class MaterialImpl extends Material
{
	private Property.Material deleagte;

	public MaterialImpl(Property.Material material)
	{
		super(MapColor.adobeColor);
		this.deleagte = material;
	}

	@Override
	public boolean blocksMovement() {return deleagte.blocksMovement();}

	@Override
	public int getMaterialMobility() {return deleagte.getMaterialMobility();}

	@Override
	public boolean isLiquid() {return deleagte.isLiquid();}

	@Override
	public boolean isOpaque() {return deleagte.isOpaque();}

	@Override
	public boolean isReplaceable() {return deleagte.isReplaceable();}

	@Override
	public boolean isSolid() {return deleagte.isSolid();}

	@Override
	public boolean blocksLight() {return deleagte.isTranslucent();}

	@Override
	public boolean isToolNotRequired() {return !deleagte.requiredToolHarvest();}

	@Override
	public boolean getCanBurn() {return deleagte.isFlammable();}
}
