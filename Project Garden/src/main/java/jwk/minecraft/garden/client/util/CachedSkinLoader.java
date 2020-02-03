package jwk.minecraft.garden.client.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import jwk.minecraft.garden.util.Tuple2;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

@Deprecated
@SideOnly(Side.CLIENT)
public class CachedSkinLoader {
	
	public static final Tuple2<String, File> getSkinImageFile(GameProfile unrefinedProfile) {
		MinecraftSessionService service = Minecraft.getMinecraft().func_152347_ac();
		Map<Type, MinecraftProfileTexture> map = service.getTextures(service.fillProfileProperties(unrefinedProfile, true), true);
		MinecraftProfileTexture texture = map.get(Type.SKIN);
		String hash = texture.getHash();
		
		return new Tuple2<String, File> (hash, new File(".\\assets\\skins\\" + hash.substring(0, 2) + "\\" + hash));
	}
	
	public static final int loadImage(File imageFile) throws IOException {
		BufferedImage image = ImageIO.read(imageFile);
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for(int y = 0; y < image.getHeight(); y++){
        	
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return textureID;
	}
	
	public static final SimpleSkinTexture generateSkinTexture(GameProfile unrefined) {
		Tuple2<String, File> target = getSkinImageFile(unrefined);
		File imageFile = target.Dest;
		
		if (!imageFile.exists())
			return null;
		
		try {
			int textureId = loadImage(imageFile);
			return new SimpleSkinTexture(unrefined, textureId);
		}
		
		catch (IOException e) { throw new RuntimeException(e); }
	}
	
}
