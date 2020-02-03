package jwk.minecraft.fogmanager.network;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.fogmanager.config.Configuration;
import jwk.minecraft.fogmanager.config.Configuration.FogConfig;
import jwk.minecraft.fogmanager.enumerations.EnumFogQuality;
import jwk.minecraft.fogmanager.enumerations.EnumFogRenderOption;
import jwk.minecraft.fogmanager.utils.Color4f;

public class PacketFog implements IMessage {
	
	public static enum Type {
		START((byte) 0), STOP((byte) 1);
		
		public final byte Value;
		
		private Type(byte value) {
			Value = value;
		}
		
		public static Type getType(byte value) {
			
			switch (value) {
			case 0:
				return START;
				
			case 1:
				return STOP;
				
			default:
				throw new IllegalArgumentException("value is not valid for PacketFog.Type!");
			}
		}
		
	}
	
	private Type packetType;
	
	private FogConfig config;
	
	public PacketFog() { }
	
	public PacketFog(@Nonnull Type packetType, FogConfig config) {
		this.packetType = checkNotNull(packetType);
		
		if (packetType == Type.START && config == null)
			throw new NullPointerException();
		
		this.config = config;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// Packet Type
		packetType = Type.getType(buf.readByte());
		
		switch (packetType) {
		
		case START:
			
			// Fog Quality
			EnumFogQuality quality = EnumFogQuality.getQuality(buf.readInt());
			
			// Fog Render Option
			EnumFogRenderOption option = EnumFogRenderOption.getRenderOption(buf.readInt());
			
			// Fog Density
			float density = buf.readFloat();
			
			// Fog Color (R, G, B, A)	
			float[] color = new float[4];
			for (int i=0; i < color.length; i++)
				color[i] = buf.readFloat();
			
			// Fog Start Depth
			float start = buf.readFloat();
			
			// Fog Render Distance
			float distance = buf.readFloat();			
			
			config = new FogConfig(quality, option, density, Color4f.fromFloatArray(color), start, distance);
			break;
		
		case STOP:
			config = null;
			break;
			
		default:
			throw new IllegalArgumentException("packetType is not valid!");
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// Packet Type
		buf.writeByte(packetType.Value);
		
		switch (packetType) {
		
		case START:
			// Fog Quality
			buf.writeInt(config.getQuality().Value);
		
			// Fog Render Option
			buf.writeInt(config.getRenderOption().Value);
		
			// Fog Density
			buf.writeFloat(config.getDensity());
		
			// Fog Color (R, G, B, A)
			for (float f : config.getColor().toFloatArray())
				buf.writeFloat(f);
		
			// Fog Start Depth
			buf.writeFloat(config.getStartDepth());
		
			// Fog Render Distance
			buf.writeFloat(config.getDistance());
			break;
		
		case STOP:
			break;
			
		default:
			throw new IllegalArgumentException("packetType is not valid!");
		}
	}

	public static class ClientHandler implements IMessageHandler<PacketFog, IMessage> {

		@Override
		public IMessage onMessage(PacketFog message, MessageContext ctx) {
			
			switch (message.packetType) {
			
			case START:
				Configuration.getInstance().setFog(message.config).setState(true);
				break;
				
			case STOP:
				FogConfig fc = Configuration.getInstance().fog();
				
				if (fc.getState())
					fc.setState(false);
				break;
			}
			
			return null;
		}
	}
}
