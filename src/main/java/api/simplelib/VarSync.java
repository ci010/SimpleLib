package api.simplelib;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import api.simplelib.utils.ITagSerializable;

/**
 * @author ci010
 */
public abstract class VarSync<T> extends VarNotify<T> implements ITagSerializable
{
	public final Side side = FMLCommonHandler.instance().getSide();
}
