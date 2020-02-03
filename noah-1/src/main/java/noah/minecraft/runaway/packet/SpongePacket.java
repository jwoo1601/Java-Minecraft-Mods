package noah.minecraft.runaway.packet;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noah.minecraft.runaway.Noah;

public class SpongePacket implements IMessage {
	
	public static enum Type {
		START((byte)0), CHANGED((byte)1), END((byte)2);
		
		private final byte index;
		
		Type(byte index) { this.index = index; }
		
		public byte getIndex() { return this.index; }
		
		public static Type getTypefromIndex(byte index) {
			switch (index) {
			case 0:
				return Type.START;
			case 1:
				return Type.CHANGED;
			case 2:
				return Type.END;
				default:
					return null;
			}
		}
	}
	
	public SpongePacket() {}
	
	public SpongePacket(Type packetType, UUID data) {
		this.packetType = packetType;
		this.criminalUUID = data;
	}
	
	private Type packetType = null;
	private UUID criminalUUID = null;

	@Override
	public void fromBytes(ByteBuf buf) {
		packetType = Type.getTypefromIndex(buf.readByte());
		
		switch(packetType) {
		case START:
			int m = buf.readableBytes();
			
			if (m > 0) {
				char[] tmp2 = new char[m/2];
				
				for (int i=0; i < tmp2.length; i++)
					tmp2[i] = buf.readChar();
				
				String str = String.valueOf(tmp2);
				criminalUUID = UUID.fromString(str);
			}
			else
				criminalUUID = null;
			break;
			
		case CHANGED:
			int n = buf.readableBytes();
			
			if (n > 0) {
				char[] tmp = new char[n/2];
				
				for (int i=0; i < tmp.length; i++)
					tmp[i] = buf.readChar();
				
				String str = String.valueOf(tmp);
				criminalUUID = UUID.fromString(str);
			}
			else
				criminalUUID = null;
			break;
			
			default:
				criminalUUID = null;
				break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (this.packetType == null)
			throw new NullPointerException("packetType");
		
		buf.writeByte(packetType.getIndex());
		
		switch(packetType) {
		case START:
			if (this.criminalUUID == null)
				throw new NullPointerException("criminalUUID");
			
			char[] tmp = criminalUUID.toString().toCharArray();
			
			for (char c : tmp)
				buf.writeChar(c);
			
			break;
			
		case CHANGED:
			if (this.criminalUUID == null)
				throw new NullPointerException("criminalUUID");
			
			char[] tmp2 = criminalUUID.toString().toCharArray();
			
			for (char c : tmp2)
				buf.writeChar(c);
			
			break;
			
			default:
				break;
		}
	}
	
	public static class SpongePacketHandler implements IMessageHandler<SpongePacket, IMessage> {

		@Override
		public IMessage onMessage(final SpongePacket message, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {

				@Override
				public void run() {
					switch (message.packetType) {
					case START:
						Noah.Proxy.CCriminal = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(message.criminalUUID);
						Noah.Proxy.CisGameStarted = true;
						
						System.out.println("START Packet Handled");
						
						if (Noah.Proxy.CCriminal == null)
							throw new NullPointerException("START:Criminal NULL");
						break;
					case CHANGED:
						Noah.Proxy.CCriminal = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(message.criminalUUID);
						
						System.out.println("CHANGED Packet Handled");
						
						if (Noah.Proxy.CCriminal == null)
							throw new NullPointerException("CHANGED:Criminal NULL");
						break;
					case END:
						Noah.Proxy.CCriminal = null;
						Noah.Proxy.CisGameStarted = false;
						
						System.out.println("END Packet Handled");
						break;
					}
				}
				
			});
			
			return null;
		}
		
	}
}
