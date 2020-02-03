package jwk.minecraft.garden.client.util;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ISkinLoadCallback {

	void onSkinLoadSucceeded(GameProfile profile, PlayerSkinCache cache);
	
	void onSkinLoadFailed(GameProfile profile);
	
}
