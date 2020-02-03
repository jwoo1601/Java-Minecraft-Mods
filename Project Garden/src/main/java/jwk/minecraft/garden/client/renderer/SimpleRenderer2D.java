package jwk.minecraft.garden.client.renderer;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.lwjgl.opengl.GL11.*;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.util.ITexture;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public class SimpleRenderer2D {
	
	public void enableBlend() { glEnable(GL_BLEND); }
	
	public void enableDepth() { glEnable(GL_DEPTH_TEST); }
	
	public void enableTexture() { glEnable(GL_TEXTURE_2D); }
	
	public void enableLighting() { glEnable(GL_LIGHTING); }
	
	public void enableAlpha() { glEnable(GL_ALPHA_TEST); }
	
	public void disableBlend() { glDisable(GL_BLEND); }
	
	public void disableDepth() { glDisable(GL_DEPTH_TEST); }
	
	public void disableTexture() { glDisable(GL_TEXTURE_2D); }
	
	public void disableLighting() { glDisable(GL_LIGHTING); }
	
	public void disableAlpha() { glDisable(GL_ALPHA_TEST); }
	
	public void render(float x, float y, int texXSize, int texYSize, int textureX, int textureY, int width, int height, int realWidth, int realHeight) {		
		double minU = (double) textureX / (double) texXSize;
		double minV = (double) textureY / (double) texYSize;
		double maxU = (double) (textureX + width) / (double) texXSize;
		double maxV = (double) (textureY + height) / (double) texYSize;
		
		enableTexture();
		
		Tessellator.instance.startDrawingQuads();
		
		Tessellator.instance.addVertexWithUV(x, y + realHeight, 0.D, minU, maxV);
		Tessellator.instance.addVertexWithUV(x + realWidth, y + realHeight, 0.D, maxU, maxV);
		Tessellator.instance.addVertexWithUV(x + realWidth, y, 0.D, maxU, minV);
		Tessellator.instance.addVertexWithUV(x, y, 0.D, minU, minV);
		
		Tessellator.instance.draw();
	}
	
}
