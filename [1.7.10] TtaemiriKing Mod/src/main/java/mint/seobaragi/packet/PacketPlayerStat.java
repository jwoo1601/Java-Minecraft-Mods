package mint.seobaragi.packet;

import static com.google.common.base.Preconditions.checkNotNull;
import static library.Reference.getPlayerByUUID;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import mint.seobaragi.packet.SeonengPacket.Type;
import mint.seobaragi.properties.PropertyPlayerStat;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketPlayerStat implements IMessage
{
	public static enum Type {
		REQUEST ((byte) 0x0A), RESPONSE((byte) 0x0B);
		
	public final byte Value;
		
		private Type(byte value)
		{
			Value = value;
		}
		
		public static Type getTypeFromByte(byte value)
		{
			switch (value)
			{
			case 0x0A:
				return REQUEST;
			case 0x0B:
				return RESPONSE;
				
				default:
					throw new IllegalArgumentException("Value is not valid");
			}
		}
	}

	private Type packetType;
	private UUID uuid;
	private PropertyPlayerStat data;
	
	public PacketPlayerStat() { }
	
	public PacketPlayerStat(@Nonnull Type packetType, @Nonnull UUID uuid, @Nullable PropertyPlayerStat data) {
		this.packetType = checkNotNull(packetType);
		this.uuid = checkNotNull(uuid);
		
		if (packetType == Type.RESPONSE && data == null)
			throw new NullPointerException();
			
		this.data = data;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		packetType = Type.getTypeFromByte(buf.readByte());
		
		if (packetType == Type.REQUEST) {
			char[] tmp = new char[buf.readableBytes() / 2];
			
			for (int i=0; i < tmp.length; i++)
				tmp[i] = buf.readChar();
			
			this.uuid = UUID.fromString(String.valueOf(tmp));
		}
		
		else if (packetType == Type.RESPONSE) {
			char[] tmp = new char[(buf.readableBytes() - 16) / 2];
			
			for (int i=0; i < tmp.length; i++)
				tmp[i] = buf.readChar();
			
			this.uuid = UUID.fromString(String.valueOf(tmp));
			
			data = new PropertyPlayerStat();
			
			data.dirtLevel = buf.readInt();
			
			data.waterLevel = buf.readInt();
			
			data.dirtGauge = buf.readInt();
			
			data.cellPoint = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(packetType.Value);
		
		char[] tmp = uuid.toString().toCharArray();
		
		for (int i=0; i < tmp.length; i++)
			buf.writeChar(tmp[i]);
		
		if (data != null) {
			buf.writeInt(data.dirtLevel);
		
			buf.writeInt(data.waterLevel);
		
			buf.writeInt(data.dirtGauge);
		
			buf.writeInt(data.cellPoint);
		}
	}
	
//	=====================================
	public static class ClientHandler implements IMessageHandler<PacketPlayerStat, IMessage> {

	@Override
	public IMessage onMessage(PacketPlayerStat message, MessageContext ctx) {
		
		if (message.packetType == Type.RESPONSE) {
			
			EntityClientPlayerMP cplayer = Minecraft.getMinecraft().thePlayer;
			
			if (message.uuid.equals(cplayer.getUniqueID())) {
				((PropertyPlayerStat) cplayer.getExtendedProperties(PropertyPlayerStat.ID)).set(message.data);
				return null;
			}
			
			List<EntityPlayer> list = Minecraft.getMinecraft().theWorld.playerEntities;
			
			for (EntityPlayer player : list) {
				

				
				if (player.getUniqueID().equals(message.uuid)) {
					((PropertyPlayerStat) player.getExtendedProperties(PropertyPlayerStat.ID)).set(message.data);
					break;
				}
			}
		}
		
		else
			throw new IllegalArgumentException();
		
		return null;
		}
	}
	
	public static class ServerHandler implements IMessageHandler<PacketPlayerStat, IMessage> {

		@Override
		public PacketPlayerStat onMessage(PacketPlayerStat message,	MessageContext ctx) {
			
			if (message.packetType == Type.REQUEST) {				
				EntityPlayerMP player = getPlayerByUUID(message.uuid);
				PropertyPlayerStat stat = ((PropertyPlayerStat) player.getExtendedProperties(PropertyPlayerStat.ID));
				
				return new PacketPlayerStat(Type.RESPONSE, message.uuid, stat);
			}
			
			else
				throw new IllegalArgumentException();
		}
	}
}
