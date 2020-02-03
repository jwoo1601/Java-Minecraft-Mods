package mint.seobaragi.packet;

import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.List;
import java.util.UUID;

import mint.seobaragi.exceptions.InvalidFormatException;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SeonengPacket implements IMessage
{
	//PacketType
	public static enum Type
	{
		DIRT_LEVEL((byte) 0),
		WATER_LEVEL((byte) 1),
		DIRT_GAUGE((byte) 2),
		CELL_POINT((byte) 3);
		
		public final byte Value;
		
		private Type(byte value)
		{
			Value = value;
		}
		
		public static Type getTypeFromByte(byte value)
		{
			switch (value)
			{
			case 0:
				return DIRT_LEVEL;
			case 1:
				return WATER_LEVEL;
			case 2:
				return DIRT_GAUGE;
			case 3:
				return CELL_POINT;
				
				default:
					throw new IllegalArgumentException("Value is not valid");
			}
		}
	}
	
	private UUID uuid;
	private Type packetType;
	private int value;
	
	
	public SeonengPacket() {}
	
	public SeonengPacket(UUID uuid, Type type, int value)
	{
		this.uuid = uuid;
		this.packetType = type;
		this.value = value;
	}
	
	public UUID getUniqueID() { return uuid; }
	
	public Type getPacketType() { return packetType; }
	
	public int getValue() { return value; }
	

	@Override
	public void fromBytes(ByteBuf buf)
	{
		char[] tmp = new char[(buf.readableBytes() - 5) / 2];
		
		for (int i=0; i < tmp.length; i++)
			tmp[i] = buf.readChar();
		
		this.uuid = UUID.fromString(String.valueOf(tmp));
		this.packetType = Type.getTypeFromByte(buf.readByte());
		this.value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		char[] tmp = uuid.toString().toCharArray();
		
		for (int i=0; i < tmp.length; i++)
			buf.writeChar(tmp[i]);
		
		buf.writeByte(packetType.Value);
		buf.writeInt(value);
	}
	
	
	//PacketHandler (Client)
	public static class Handler implements IMessageHandler<SeonengPacket, IMessage>
	{
		@Override

		public IMessage onMessage(SeonengPacket message, MessageContext ctx)
		{
			List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
			EntityPlayer target = null;
			
			if (message.uuid.equals(Minecraft.getMinecraft().thePlayer.getUniqueID()))
				target = Minecraft.getMinecraft().thePlayer;
			
			else {
				
				for (EntityPlayer p : players)
				{				
					if(p.getUniqueID().equals(message.getUniqueID()))
					{
						target = p;
						break;
					}
				}
			}
			
			if(target == null)
			{
				System.err.println("Side: Client Player=Null <type= " + message.packetType + " uuid=" + message.getUniqueID() + ">");
				return null;
			}

			PropertyPlayerStat stat = (PropertyPlayerStat)target.getExtendedProperties("PlayerStat");
			
			
			switch (message.packetType)
			{
		     case DIRT_LEVEL:
		       stat.dirtLevel = message.value;
		       break;
		     case WATER_LEVEL:
		       stat.waterLevel = message.value;
		       break;
		     case DIRT_GAUGE:
		       stat.dirtGauge = message.value;
		       break;
		     case CELL_POINT:
		       stat.cellPoint = message.value;
		    }
			return null;
		}
	}
}
