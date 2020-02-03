package jwk.minecraft.garden.client.font;

import static org.lwjgl.opengl.GL11.glGenTextures;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureCache {
	
	public boolean full;
	
	public int x, y;
	public int textureId = glGenTextures();
	
	public BufferedImage bufferedImage = new BufferedImage(TrueTypeFont.MAX_WIDTH, TrueTypeFont.MAX_WIDTH, BufferedImage.TYPE_INT_ARGB);
	
	public Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
	
}
