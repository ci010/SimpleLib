package net.ci010.minecrafthelper.interactive;

import net.ci010.minecrafthelper.abstracts.UpdateSafe;
import net.ci010.minecrafthelper.interactive.InteractiveComponent;

/**
 * This interface indicates a core updatable logic.
 * <p>To use this with
 * {@link InteractiveComponent}, I highly recommend use the {@link net.ci010.minecrafthelper.data.VarSync} for those
 * variables needed to be synchronized between client and server.
 * </p>
 * <p>There are two special type of variables which need to be noticed,
 * {@link net.ci010.minecrafthelper.data.VarItemHolder} and {@link net.ci010.minecrafthelper.data.VarInteger}</p>
 * <p>Use {@link net.ci010.minecrafthelper.data.VarItemHolder} instead of {@link net.minecraft.item.ItemStack} </p>
 * <p>Use {@link net.ci010.minecrafthelper.data.VarInteger} instead of normal integer</p>
 *
 * @author ci010
 */
public interface Process extends UpdateSafe
{
	void preUpdate();

	void postUpdate();
}
