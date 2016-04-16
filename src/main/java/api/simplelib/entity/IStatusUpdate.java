package api.simplelib.entity;

import net.minecraft.util.ITickable;

/**
 * The class implements this interface will be called {@link #update()} every end of world ticks after entity updated.
 *
 * @author ci010
 */
public interface IStatusUpdate extends IStatus, ITickable
{
}

