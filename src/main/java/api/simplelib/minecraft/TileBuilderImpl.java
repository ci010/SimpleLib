package api.simplelib.minecraft;

import api.simplelib.seril.ITagSerializer;
import api.simplelib.vars.Var;
import api.simplelib.vars.VarSyncBase;
import net.simplelib.inventory.InventoryBuilderImpl;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * @author ci010
 */
public class TileBuilderImpl //implements TileEntityWrap.TileEntityBuilder
{
	private InventoryBuilderImpl builder = new InventoryBuilderImpl();
	private TileEntityWrap tileEntityWrap;
	private ArrayList<VarSyncBase> varSyncs = new ArrayList<VarSyncBase>();

	public TileBuilderImpl(TileEntityWrap tileEntityWrap)
	{
		this.tileEntityWrap = tileEntityWrap;
	}

	void done()
	{
		tileEntityWrap.inventory = builder.buildInventory();
		tileEntityWrap.varSync = ImmutableList.copyOf(varSyncs);

	}
	//	@Override
	public <T> Var<T> newSyncVar(T initValue, final ITagSerializer<T> serializer)
	{
		VarSyncBase<T> sync = new VarSyncBase<T>()
		{
			@Override
			public void readFromNBT(NBTTagCompound tag)
			{
				this.set(serializer.readFromNBT(tag, this.get()));
			}

			@Override
			public void writeToNBT(NBTTagCompound tag)
			{
				serializer.writeToNBT(tag, this.get());
			}
		};
		sync.set(initValue);
		varSyncs.add(sync);
		return sync;
	}

	//	@Override
//	public InventorySpace newSpace(int size, EnumFacing facing, InventoryRule rule) {return builder.newSpace(size, facing, rule);}

	//	@Override
//	public InventorySpace newSpace(int size, EnumFacing facing) {return builder.newSpace(size, facing);}

	//	@Override
//	public InventorySlot newSlot(EnumFacing facing) {return builder.newSlot(facing);}

	//	@Override
//	public InventorySlot newSlot(EnumFacing facing, InventoryRule rule) {return builder.newSlot(facing, rule);}
}
