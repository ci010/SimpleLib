package api.simplelib.entity;

import api.simplelib.VarFactory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.simplelib.common.nbt.ITagSerial;

/**
 * A easy interface to create a {@link net.minecraftforge.common.IExtendedEntityProperties}.
 * <p>Use {@link PropertyHook} to attach this on an entity.</p>
 *
 * @author ci010
 * @see net.minecraftforge.common.IExtendedEntityProperties
 * @see PropertyHook
 */
public interface IStatus extends ITagSerial
{
	/**
	 * The initialization method of this status. This will be called when entity just created.
	 * <p>
	 * The factory here can create the variables that sync automatically between client and server. So you don't
	 * need to consider about the sync problem when you use these variables.
	 * </p>
	 * <p>
	 * They are implemented by {@link net.minecraft.entity.DataWatcher} which has a maximum limit. So use this only
	 * if you want a value
	 * showing on screen or having any other usages in client side.
	 * </p>
	 * <p>
	 * The variable created by factory also doesn't need to be saved and loaded when
	 * {@link ITagSerial#readFromNBT(NBTTagCompound)} and {@link ITagSerial#writeToNBT(NBTTagCompound)}, since they
	 * will be saved automatically.
	 * <p/>
	 * The name of the variable when you create it is the reference for save/load. So, changing its name will
	 * lost the old data.
	 * <p/>
	 *
	 * @param entity  The entity.
	 * @param factory The factory which provide the variable auto-synchronized between Server and Client sides.
	 */
	void build(Entity entity, VarFactory factory);
}
