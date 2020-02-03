package jwk.minecraft.garden.client.util;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureDelegate {

	public static SimpleSkinTexture createTexture(GameProfile profile, PlayerSkinCache cache, TextureFilter filter) {
		int textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        
        int minFilterParam = -1, magFilterParam = -1;
        
        if (filter == TextureFilter.NICEST) { minFilterParam = GL11.GL_LINEAR; magFilterParam = GL_LINEAR; }
        else if (filter == TextureFilter.FASTEST) { minFilterParam = GL_NEAREST; magFilterParam = GL_NEAREST; }
        else { minFilterParam = GL_LINEAR; magFilterParam = GL_NEAREST; }
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilterParam);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilterParam);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, cache.getWidth(), cache.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, cache.getBuffer());
        
        return new SimpleSkinTexture(profile, textureId);
	}
	
	public static enum TextureFilter {
		
		NICEST,
		FASTEST,
		DONT_CARE;
		
	}
	
}
