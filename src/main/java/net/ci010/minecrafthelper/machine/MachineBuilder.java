package net.ci010.minecrafthelper.machine;

import net.minecraft.block.material.Material;

/**
 * @author ci010
 */
public class MachineBuilder extends InteractiveComponentBuilder
{
	Material material = Material.iron;

	public MachineBuilder setMaterial(Material material)
	{
		this.material = material;
		return this;
	}

	@Override
	public InteractiveComponent build()
	{
		return new Machine(this);
	}
}
