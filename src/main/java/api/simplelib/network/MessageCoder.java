package api.simplelib.network;

import api.simplelib.vars.Var;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author ci010
 */
public interface MessageCoder<T> extends IMessage, Var<T>
{
	MessageCoder<Void> EMPTY = new MessageCoder<Void>()
	{
		@Override
		public void fromBytes(ByteBuf buf) {}

		@Override
		public void toBytes(ByteBuf buf) {}

		@Override
		public Void get() {return null;}

		@Override
		public void set(Void value) {}
	};

	MessageCoder<Integer> INTEGER = new MessageCoder<Integer>()
	{
		private int value;

		@Override
		public void fromBytes(ByteBuf buf)
		{
			value = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(value);
		}

		@Override
		public Integer get()
		{
			return value;
		}

		@Override
		public void set(Integer value)
		{
			this.value = value;
		}
	};
}
