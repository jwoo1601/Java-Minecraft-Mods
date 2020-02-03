package jwk.minecraft.fogmanager.network;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.fogmanager.animation.AnimatedFog;
import jwk.minecraft.fogmanager.animation.FogAnimationHelper;
import jwk.minecraft.fogmanager.config.Configuration;
import jwk.minecraft.fogmanager.config.Configuration.FogConfig;
import jwk.minecraft.fogmanager.enumerations.EnumFogQuality;
import jwk.minecraft.fogmanager.enumerations.EnumFogRenderOption;
import jwk.minecraft.fogmanager.network.PacketFog.Type;
import jwk.minecraft.fogmanager.utils.Color4f;

public class PacketFogAnimation implements IMessage {

	public static enum Type {
		ADD ((byte) 0x02), DELETE((byte) 0x03), START((byte) 0x04), STOP((byte) 0x05);
		
		public final byte Value;
		
		private Type(byte value) {
			Value = value;
		}
		
		public static Type getType(byte value) {
			
			switch (value) {
			case 0x02:
				return ADD;
				
			case 0x03:
				return DELETE;
				
			case 0x04:
				return START;
				
			case 0x05:
				return STOP;
				
			default:
				throw new IllegalArgumentException("value is not valid for PacketFogAnimation.Type!");
			}
		}
		
	}
	
	private Type packetType;
	
	private AnimatedFog target;
	
	public PacketFogAnimation() { }
	
	public PacketFogAnimation(@Nonnull Type packetType, AnimatedFog target) {
		this.packetType = checkNotNull(packetType);
		
		if (packetType == Type.ADD && target == null)
			throw new NullPointerException();
		
		this.target = target;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		packetType = Type.getType(buf.readByte());
		
		switch (packetType) {
		
		case ADD:
			
			float speed = buf.readFloat();
			
			long duration  = buf.readLong();
			
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
			
			FogConfig tmpCfg = new FogConfig(quality, option, density, Color4f.fromFloatArray(color), start, distance);
			
			target = new AnimatedFog(speed, duration, tmpCfg);
			break;
			
		case DELETE:
			target = null;
			break;
			
		case START:
			target = null;
			break;
			
		case STOP:
			target = null;
			break;
			
		default:
			throw new IllegalArgumentException("packetType is not valid!");
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(packetType.Value);
		
		switch (packetType) {
		
		case ADD:
			// AnimatedFog Speed
			buf.writeFloat(target.getSpeed());
			
			// AnimatedFog Duration
			buf.writeLong(target.getDuration());
			
			FogConfig config = target.getCurrentConfig();
			
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
			
		case DELETE:
			break;
			
		case START:
			break;
			
		case STOP:
			break;
			
		default:
			throw new IllegalArgumentException("packetType is not valid!");
		}
	}

	
	public static class ClientHandler implements IMessageHandler<PacketFogAnimation, IMessage> {

		@Override
		public IMessage onMessage(PacketFogAnimation message, MessageContext ctx) {

			
			switch (message.packetType) {
			
			case ADD:
				processAdd(message.target);
				break;
				
			case DELETE:
				processDelete();
				
			case START:
				processStart();
				break;
				
			case STOP:
				processStop();
				break;
			}
			
			return null;
		}
		
		public void processAdd(AnimatedFog anim) {
			Configuration.getInstance().AnimationHelper.push(anim);
		}
		
		public void processDelete() {
			FogAnimationHelper helper = Configuration.getInstance().AnimationHelper;
			
			if (!helper.isQueueEmpty())
				helper.pop();
		}
		
		public void processStart() {
			FogAnimationHelper helper = Configuration.getInstance().AnimationHelper;
			
			if (!helper.isQueueEmpty()) {
				helper.start();
				Configuration.getInstance().setFog(helper.getCurrentAnimation().getCurrentConfig()).setState(true);
			}
		}
		
		public void processStop() {
			FogAnimationHelper helper = Configuration.getInstance().AnimationHelper;
			
			if (helper.getState()) {
				helper.stop();
				helper.pop();
				Configuration.getInstance().setFog(null);
			}
		}
		
	}
}
