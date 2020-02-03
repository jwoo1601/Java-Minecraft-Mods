package jwk.minecraft.garden.network;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ProjectGarden;

public class PacketCurrency implements IMessage {

	public static enum Action {
		
		SHOW,
		SET,
		HIDE;		
		
	}
	
	private Action action;
	private Object packetData;
	
	public PacketCurrency() { }
	
	public PacketCurrency(@Nonnull Action action, @Nullable Object data) {
		checkNotNull(action);
		
		if (action == Action.SHOW && data != null)
			throw new IllegalArgumentException();
		else if (action == Action.SET && !(data instanceof Long))
			throw new IllegalArgumentException();
		else if (action == Action.HIDE && data != null)
			throw new IllegalArgumentException();
		
		this.action = action;
		this.packetData = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.action = Action.values()[buf.readInt()];
		
		switch (action) {
		
		case SET:
			this.packetData = buf.readLong();
			break;
			
		default:
			break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(action.ordinal());
		
		switch (action) {
		
		case SET:
			buf.writeLong((Long) packetData);
			break;
			
		default:
			break;
		}
	}	
	
	
	public static class ClientHandler implements IMessageHandler<PacketCurrency, IMessage> {

		@Override
		public IMessage onMessage(PacketCurrency message, MessageContext ctx) {
			
			switch (message.action) {
			
			case SHOW:
				ProjectGarden.proxy.getCurrencyClient().isVisible = true;
				break;
				
			case SET:
				ProjectGarden.proxy.getCurrencyClient().onSetValue((Long) message.packetData);
				break;
				
			case HIDE:
				ProjectGarden.proxy.getCurrencyClient().isVisible = false;
				break;
				
			}
			
			return null;
		}
		
	}
	
}
