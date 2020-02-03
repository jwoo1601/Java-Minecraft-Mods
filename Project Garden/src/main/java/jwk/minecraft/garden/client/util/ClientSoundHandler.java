package jwk.minecraft.garden.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ClientSoundHandler {

	public static void handlePlaySound(ResourceLocation res, float volume, float frequency) {
		SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if (player == null)
			return;
		
		handler.playSound(new PositionedSoundRecord(res, volume, frequency, (float) player.posX, (float) player.posY, (float) player.posZ));
	}
	
}
