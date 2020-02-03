package noah.teleport.network;

import java.util.List;

import com.sun.org.apache.regexp.internal.CharacterArrayCharacterIterator;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.teleport.Main;
import noah.teleport.block.TeleportData;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;
import scala.Char;

public class PlaceDataChangedPacket implements IMessage {
	
	public static final byte OWNPLACENAME = 0; // + String
	public static final byte LINKEDPLACE = 1; // + Int
	public static final byte LINKEDPLACENULL = 2; // + Nothing
	public static final byte DELETEPLACE = 3; // + Int
	
	private int dimensionId;
	private int ownPlaceId;
	private byte packetType;
	private Object data;
	
	public PlaceDataChangedPacket() {}
	
	public PlaceDataChangedPacket (int dimensionId, int placeId, byte type, Object data) {
		this.dimensionId = dimensionId;
		this.ownPlaceId = placeId;
		this.packetType = type;
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dimensionId = buf.readInt();
		ownPlaceId = buf.readInt();
		packetType = buf.readByte();
		
		switch (packetType) {
		case OWNPLACENAME:
			int n = buf.readableBytes();
			if (n > 0) {
				char[] tmp = new char[n / 2];
				
				for (int i =0; i < tmp.length; i++)
					tmp[i] = buf.readChar();
				
				data = String.valueOf(tmp);
			}
			else
				data = null;
			
			break;

		case LINKEDPLACE:
			data = buf.readInt();
			
			break;
		case LINKEDPLACENULL:
			data = null;
			
			break;
		case DELETEPLACE:
			data = null;
			
			break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimensionId);
		buf.writeInt(ownPlaceId);
		buf.writeByte(packetType);
		
		switch (packetType) {
		case OWNPLACENAME:
			char[] tmp = ((String)data).toCharArray();
			
			for (int i=0; i < tmp.length; i++)
				buf.writeChar(tmp[i]);
			
			break;
		case LINKEDPLACE:
			buf.writeInt((Integer) data);
			
			break;
			
			default:
				break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static class CPDCPacketHandler implements IMessageHandler<PlaceDataChangedPacket, IMessage> {

		@Override
		public IMessage onMessage(final PlaceDataChangedPacket message, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				
				@Override
				public void run() {
					processPacket(Minecraft.getMinecraft().theWorld, message);
				}
			});
			
			return null;
		}
		
		void processPacket(WorldClient world, PlaceDataChangedPacket message) {
			
			switch(message.packetType) {
			case OWNPLACENAME:
				Main.Proxy.FakePlaceReg.getPlaceByID(message.ownPlaceId).setName((String)message.data);
				
				break;
			case LINKEDPLACE:
				TeleportData data = TeleportData.searchTeleportDataByID(world, message.ownPlaceId);
				
				if (data == null)
					System.err.println("A Critical Error Occured during Receiving CPDCPacket!");
				else 
					data.setLinkedPlace(Main.Proxy.FakePlaceReg.getPlaceByID((Integer) message.data));
				
				break;
			case LINKEDPLACENULL:
				TeleportData tmp = TeleportData.searchTeleportDataByID(world, message.ownPlaceId);
				
				if (tmp == null)
					System.err.println("A Critical Error Occured during Receiving CPDCPacket!");
				else 
					tmp.setLinkedPlace(null);
				
				break;
			case DELETEPLACE:
				Main.Proxy.FakePlaceReg.getPlaceByID(message.ownPlaceId);
				
				break;
				default:
					System.err.println("A Critical Error Occured during Receiving CPDCPacket!");
					break;
			}
		}
		
	}
	
	public static class SPDCPacketHandler implements IMessageHandler<PlaceDataChangedPacket, IMessage> {

		@Override
		public IMessage onMessage(final PlaceDataChangedPacket message, MessageContext ctx) {
			
			final WorldServer server = ctx.getServerHandler().playerEntity.getServerForPlayer();
			
			server.addScheduledTask(new Runnable() {
				
				@Override
				public void run() {
					processPacket(server, message);
				}
			});
			
			return null;
		}	
		
		void processPacket(WorldServer world, PlaceDataChangedPacket message) {
			int d;
			
			switch(message.dimensionId) {
			case Common.OVERWORLD_ID:
				d = 0;
				break;
			case Common.NETHER_ID:
				d = 1;
				break;
			case Common.THEEND_ID:
				d = 2;
				break;
				default:
					d = message.dimensionId;
					break;
			}
			
			switch(message.packetType) {
			case OWNPLACENAME:
				Main.Proxy.PlaceReg[d].getPlaceByID(message.ownPlaceId).setName((String)message.data);
				
				break;
			case LINKEDPLACE:
				TeleportData data = TeleportData.searchTeleportDataByID(world, message.ownPlaceId);
				
				if (data == null)
					System.err.println("A Critical Error Occured during Receiving SPDCPacket!");
				else {
					data.setLinkedPlace(Main.Proxy.PlaceReg[d].getPlaceByID((Integer) message.data));
					Common.Debug("data=" + (Integer) message.data);
				}
				break;
			case LINKEDPLACENULL:
				TeleportData tmp = TeleportData.searchTeleportDataByID(world, message.ownPlaceId);
				
				if (tmp == null)
					System.err.println("A Critical Error Occured during Receiving SPDCPacket!");
				else 
					tmp.setLinkedPlace(null);
				
				break;
			case DELETEPLACE:
				Main.Proxy.PlaceReg[d].deletePlaceByID(message.ownPlaceId);
				
				break;
				default:
					System.err.println("A Critical Error Occured during Receiving SPDCPacket!");
					break;
			}
			
			TeleportPacketHandler.INSTANCE.sendToDimension(message, d);
		}
	}
}
