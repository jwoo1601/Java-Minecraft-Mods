package jwk.minecraft.garden.client.util;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SimpleSkinTexture implements ITexture {

	private GameProfile profile;
	private int glTextureId;
	
	public SimpleSkinTexture(GameProfile profile, int textureId) {
		this.profile = profile;
		this.glTextureId = textureId;
	}
	
	public void bindTexture() { GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTextureId); }
	
	public void deleteTexture() { GL11.glDeleteTextures(glTextureId); }
	
	public GameProfile getProfile() { return profile; }
	
}
