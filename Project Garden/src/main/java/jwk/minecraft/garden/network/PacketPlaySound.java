package jwk.minecraft.garden.network;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.util.ClientSoundHandler;
import jwk.minecraft.garden.util.JUtil;
import net.minecraft.util.ResourceLocation;

public class PacketPlaySound implements IMessage {
	
	private boolean isVanillaSound;
	private String soundName;
	private float volume;
	private float frequency;
	
	public PacketPlaySound() { }
	
	public PacketPlaySound(boolean isVanillaSound, @Nonnull String name, float volume, float frequency) {
		this.isVanillaSound = isVanillaSound;
		this.soundName = checkNotNull(name);
		this.volume = volume;
		this.frequency = frequency;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		isVanillaSound = buf.readBoolean();
		
		soundName = JUtil.readString(buf);
		
		volume = buf.readFloat();
		frequency = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isVanillaSound);
		
		JUtil.writeString(buf, soundName);
		
		buf.writeFloat(volume);
		buf.writeFloat(frequency);
	}
	
	
	public static class ClientHandler implements IMessageHandler<PacketPlaySound, IMessage> {

		@Override
		public IMessage onMessage(PacketPlaySound message, MessageContext ctx) {
			
			System.out.println(message.soundName);
			
			if (message.isVanillaSound)
				ClientSoundHandler.handlePlaySound(new ResourceLocation(message.soundName), message.volume, message.frequency);
			
			else
				ClientSoundHandler.handlePlaySound(new ResourceLocation(ModInfo.ID + ":" + message.soundName), message.volume, message.frequency);
			
			return null;
		}
		
	}

}
