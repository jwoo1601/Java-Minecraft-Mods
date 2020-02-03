package mint.seobaragi.packet;

import static library.Reference.DEBUG;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.List;
import java.util.UUID;

import mint.seobaragi.TtaemiriKingMod;
import mint.seobaragi.exceptions.InvalidFormatException;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.Constants.NBT;
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
		CAN_CLEAN((byte) 3),
		ALL((byte) 4);
		
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
				return CAN_CLEAN;
			case 4:
				return ALL;
				
				default:
					throw new IllegalArgumentException("Value is not valid");
			}
		}
	}
	
	private Type packetType;
	private UUID uuid;
	private Object value;
	
	
	public SeonengPacket() {}
	
	public SeonengPacket(UUID uuid, Type type, Object value)
	{
		this.uuid = uuid;
		this.packetType = type;
		
		if (type == Type.ALL && value instanceof PropertyPlayerStat)
			this.value = value;
		
		else if (type == Type.CAN_CLEAN && value instanceof Boolean)
			this.value = value;
		
		else if (type != Type.ALL && type != Type.CAN_CLEAN && value instanceof Integer)
			this.value = value;
		
		else
			throw new IllegalArgumentException();
	}
	
	public UUID getUniqueID() { return uuid; }
	
	public Type getPacketType() { return packetType; }
	
	public Object getValue() { return value; }
	

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.packetType = Type.getTypeFromByte(buf.readByte());
		
		char[] tmp = null;
		if (packetType == Type.ALL) {
			
			if (DEBUG)
				System.out.println("readable bytes = " + buf.readableBytes());
			
			tmp = new char[(buf.readableBytes() - 13) / 2];
			
			for (int i=0; i < tmp.length; i++) {
				tmp[i] = buf.readChar();
				
				if (DEBUG)
					System.out.printf("current char = %d", tmp[i]);
			}
			
			
			this.uuid = UUID.fromString(String.valueOf(tmp));
			
			PropertyPlayerStat stat = new PropertyPlayerStat();
			
			stat.dirtLevel = buf.readInt();
			stat.waterLevel = buf.readInt();
			stat.dirtGauge = buf.readInt();
			stat.canClean = buf.readBoolean();
			
			value = stat;
		}
		
		else if (packetType == Type.CAN_CLEAN) {
			tmp = new char[(buf.readableBytes() - 1) / 2];
			
			for (int i=0; i < tmp.length; i++)
				tmp[i] = buf.readChar();
			
			
			this.uuid = UUID.fromString(String.valueOf(tmp));
			
			this.value = buf.readBoolean();
		}
		
		else {
			tmp = new char[(buf.readableBytes() - 4) / 2];
			
			for (int i=0; i < tmp.length; i++)
				tmp[i] = buf.readChar();
			
			
			this.uuid = UUID.fromString(String.valueOf(tmp));
			
			this.value = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(packetType.Value);
		
		char[] tmp = uuid.toString().toCharArray();
		
		for (int i=0; i < tmp.length; i++)
			buf.writeChar(tmp[i]);
		
		if (packetType == Type.ALL) {
			PropertyPlayerStat stat = (PropertyPlayerStat) value;
			
			buf.writeInt(stat.dirtLevel);
			buf.writeInt(stat.waterLevel);
			buf.writeInt(stat.dirtGauge);
			buf.writeBoolean(stat.canClean);
		}
		
		else if (packetType == Type.CAN_CLEAN)
			buf.writeBoolean((Boolean)value);
		
		else
			buf.writeInt((Integer)value);
	}
	
	
	//PacketHandler (Client)
	public static class Handler implements IMessageHandler<SeonengPacket, IMessage>
	{
		@Override

		public IMessage onMessage(SeonengPacket message, MessageContext ctx)
		{
			List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
			EntityPlayer target = null;
			
			EntityPlayer me = TtaemiriKingMod.proxy.getClientPlayer();
			
			if (message.uuid.equals(me.getUniqueID()))
				target = me;
			
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
		       stat.dirtLevel = (Integer)message.value;
		       break;
		     case WATER_LEVEL:
		       stat.waterLevel = (Integer)message.value;
		       break;
		     case DIRT_GAUGE:
		       stat.dirtGauge = (Integer)message.value;
		       break;
		     case CAN_CLEAN:
		       stat.canClean = (Boolean)message.value;
		       break;
		     case ALL:
		    	 stat.set((PropertyPlayerStat) message.value);
		    	 break;
		    }
			return null;
		}
	}
}
