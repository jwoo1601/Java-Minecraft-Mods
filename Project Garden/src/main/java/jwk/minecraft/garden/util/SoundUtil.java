package jwk.minecraft.garden.util;

import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.network.PacketPlaySound;
import net.minecraft.entity.player.EntityPlayerMP;

public class SoundUtil {
	
	public static enum SoundType {
		
		SHOP_SLOT_SUCCESS(false, "shop.slot.success", 0.7F, 1.0F),
		SHOP_SLOT_FAIL(false, "shop.slot.fail", 0.8F, 1.0F),
		OPEN_SALE_SHOP_GUI(false, "shop.open", 1.0F, 1.0F),
		CLOSE_SALE_SHOP_GUI(false, "shop.close", 1.0F, 1.0F),
		OPEN_PURCHASE_SHOP_GUI(false, "shop.chicken", 1.0F, 1.0F),
		CLOSE_PURCHASE_SHOP_GUI(false, "shop.chicken", 1.0F, 0.7F),
		
		DELIVER_SUCCESS(true, "random.orb", 1.0F, 0.7F);
		
		public final boolean IsVanillaSound;		
		public final String Name;
		public final float Volume;
		public final float Frequency;
		
		private SoundType(boolean isVanillaSound, String name, float volume, float frequency) {
			IsVanillaSound = isVanillaSound;
			Name = name;
			Volume = volume;
			Frequency = frequency;
		}
		
	}

	public static void playSoundTo(EntityPlayerMP player, boolean isVanilla, String soundName) {
		playSoundTo(player, isVanilla, soundName, 1.F, 1.F);
	}
	
	public static void playSoundTo(EntityPlayerMP player, boolean isVanilla, String soundName, float volume) {
		playSoundTo(player, isVanilla, soundName, volume, 1.F);
	}
	
	public static void playSoundTo(EntityPlayerMP player, boolean isVanilla, String soundName, float volume, float frequency) {
		ProjectGarden.NET_HANDLER.sendTo(new PacketPlaySound(isVanilla, soundName, volume, frequency), player);
	}
	
	public static void playSoundTo(EntityPlayerMP player, SoundType type) {
		ProjectGarden.NET_HANDLER.sendTo(new PacketPlaySound(type.IsVanillaSound, type.Name, type.Volume, type.Frequency), player);
	}
	
//	public static void playSoundTo(EntityPlayerMP player, SoundType type) {
//		player.worldObj.playSoundAtEntity(player, type.IsVanillaSound ? type.Name : ModInfo.ID + ":" + type.Name, type.Volume, type.Frequency);
//	}
	
}
