package jwk.minecraft.garden.client.team;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.TaskManager;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.client.util.CachedSkinLoader;
import jwk.minecraft.garden.client.util.ISkinLoadCallback;
import jwk.minecraft.garden.client.util.ITexture;
import jwk.minecraft.garden.client.util.PlayerSkinCache;
import jwk.minecraft.garden.client.util.SimpleSkinTexture;
import jwk.minecraft.garden.client.util.TaskSkinLoad;
import jwk.minecraft.garden.client.util.TextureDelegate;
import jwk.minecraft.garden.client.util.TextureDelegate.TextureFilter;
import jwk.minecraft.garden.util.NBTUserProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.SkinManager.SkinAvailableCallback;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TeamDataClient {
	
	public static final int HEAD_BASE_X_OFFSET = 8;
	public static final int HEAD_BASE_Y_OFFSET = 8;
	
	public static final int HEAD_OVERLAY_X_OFFSET = 40;
	public static final int HEAD_OVERLAY_Y_OFFSET = 8;
	
	public static final int HEAD_TEXTURE_SIZE = 8;
	
	public static final int PLAYER_SKIN_WIDTH = 64;
	public static final int PLAYER_SKIN_HEIGHT = 32;

	public final NBTUserProfile Profile;
	public final RenderableObject HeadBase;
	public final RenderableObject HeadOverlay;
	
	public final boolean UseMCTexture;
	public ITexture textureObj;
	
	public final TaskSkinLoad Task;
	
	public TeamDataClient(@Nonnull NBTUserProfile profile, int width, int height) {
		this(profile, width, height, null);
	}

	public TeamDataClient(@Nonnull NBTUserProfile profile, int width, int height, ResourceLocation mcResource) {
		checkNotNull(profile);

		Profile = profile;
		
		if (mcResource == null) {
			UseMCTexture = false;
			
			TextureInfo tex = new TextureInfo(AbstractClientPlayer.locationStevePng, PLAYER_SKIN_WIDTH, PLAYER_SKIN_HEIGHT);
			HeadBase = new RenderableObject(tex, HEAD_TEXTURE_SIZE, HEAD_TEXTURE_SIZE, width, height, HEAD_BASE_X_OFFSET, HEAD_BASE_Y_OFFSET);
			HeadOverlay = new RenderableObject(tex, HEAD_TEXTURE_SIZE, HEAD_TEXTURE_SIZE, width, height, HEAD_OVERLAY_X_OFFSET, HEAD_OVERLAY_Y_OFFSET);
			Task = new TaskSkinLoad(this);
			TaskManager.instance().addTaskSkinLoad(Task);
		}
		
		else {
			UseMCTexture = true;
			
			TextureInfo tex = new TextureInfo(mcResource, PLAYER_SKIN_WIDTH, PLAYER_SKIN_HEIGHT);
			HeadBase = new RenderableObject(tex, HEAD_TEXTURE_SIZE, HEAD_TEXTURE_SIZE, width, height, HEAD_BASE_X_OFFSET, HEAD_BASE_Y_OFFSET);
			HeadOverlay = new RenderableObject(tex, HEAD_TEXTURE_SIZE, HEAD_TEXTURE_SIZE, width, height, HEAD_OVERLAY_X_OFFSET, HEAD_OVERLAY_Y_OFFSET);
			Task = null;
		}
	}
	
	public TeamDataClient(@Nonnull SimpleSkinTexture texture, @Nonnull NBTUserProfile profile, int width, int height) {
		checkNotNull(texture);  checkNotNull(profile);
		
		Profile = profile;
		UseMCTexture = false;
		TextureInfo tex = new TextureInfo(AbstractClientPlayer.locationStevePng, PLAYER_SKIN_WIDTH, PLAYER_SKIN_HEIGHT);
		HeadBase = new RenderableObject(tex, HEAD_TEXTURE_SIZE, HEAD_TEXTURE_SIZE, width, height, HEAD_BASE_X_OFFSET, HEAD_BASE_Y_OFFSET);
		HeadOverlay = new RenderableObject(tex, HEAD_TEXTURE_SIZE, HEAD_TEXTURE_SIZE, width, height, HEAD_OVERLAY_X_OFFSET, HEAD_OVERLAY_Y_OFFSET);
		Task = null;
		textureObj = texture;
		
		System.out.println("applied texture cache");
	}
	
	@Override
	public int hashCode() {
		return Profile.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		
		if (obj instanceof TeamDataClient) {
			TeamDataClient data = (TeamDataClient) obj;
			
			return Profile.equals(data.Profile);
		}
		
		else if (obj instanceof NBTUserProfile) {
			NBTUserProfile profile = (NBTUserProfile) obj;
			
			return Profile.equals(profile);
		}
		
		return false;
	}
	
}
