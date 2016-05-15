package api.simplelib.minecraft;

import api.simplelib.remote.Syncable;
import api.simplelib.inventory.*;
import api.simplelib.seril.ITagSerializer;
import api.simplelib.vars.Var;
import api.simplelib.vars.VarSync;
import api.simplelib.vars.VarSyncBase;
import com.google.common.collect.ImmutableList;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.List;

/**
 * @author ci010
 */
public abstract class TileEntityWrap extends TileEntity implements Syncable
{
	protected ImmutableList<VarSyncBase> varSync = ImmutableList.of();
	protected Inventory inventory;

	public TileEntityWrap()
	{
		TileBuilderImpl builder = new TileBuilderImpl(this);
//		this.build(builder);
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

	@Override
	public List<VarSync> getAllSync()
	{
		return null;
	}
}
