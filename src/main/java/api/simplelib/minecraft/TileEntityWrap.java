package api.simplelib.minecraft;

import api.simplelib.Var;
import api.simplelib.VarSync;
import api.simplelib.minecraft.inventory.*;
import api.simplelib.utils.GenericUtil;
import api.simplelib.utils.ITagSerializer;
import com.google.common.collect.ImmutableList;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Collections;

/**
 * @author ci010
 */
public abstract class TileEntityWrap extends TileEntity
{
	protected ImmutableList<VarSync> varSync = ImmutableList.of();
	protected Inventory inventory;

	public TileEntityWrap()
	{
		TileBuilderImpl builder = new TileBuilderImpl(this);
		this.build(builder);
		builder.done();
	}

	public void accept(Container container)
	{

	}

	protected abstract void build(TileEntityBuilder builder);

	public interface TileEntityBuilder
	{
		<T> Var<T> newSyncVar(T initValue, ITagSerializer<T> serializer);

		InventorySpace newSpace(int size, EnumFacing facing, InventoryRule rule);

		InventorySpace newSpace(int size, EnumFacing facing);

		InventorySlot newSlot(EnumFacing facing);

		InventorySlot newSlot(EnumFacing facing, InventoryRule rule);
	}
}
