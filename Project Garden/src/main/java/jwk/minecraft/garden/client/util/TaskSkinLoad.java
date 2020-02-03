package jwk.minecraft.garden.client.util;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.TaskManager;
import jwk.minecraft.garden.client.team.TeamDataClient;
import jwk.minecraft.garden.client.util.TextureDelegate.TextureFilter;

@SideOnly(Side.CLIENT)
public class TaskSkinLoad implements ISkinLoadCallback {

	private int count = 0;
	private boolean isTerminated = false;
	
	private TeamDataClient data;
	
	public GameProfile completedProfile;
	public PlayerSkinCache cache;
	
	public TaskSkinLoad(TeamDataClient data) {
		this.data = data;
	}
	
	@Override
	public void onSkinLoadSucceeded(GameProfile profile, PlayerSkinCache cache) {
		completedProfile = profile;
		this.cache = cache;
	}

	@Override
	public void onSkinLoadFailed(GameProfile profile) {
		
		if (this.data.textureObj != null)
			this.data.textureObj = null;
	}
	
	public void updateCount() { ++count; }
	
	public int getCount() { return count; }
	
	public void terminate() { if (!isTerminated) isTerminated = true; }
	
	public boolean isTerminated() { return isTerminated; }
	
	public GameProfile getProfile() { return data.Profile.toGameProfile(); }
	
}
