package mint.seobaragi.renderer;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.vecmath.Color4f;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

public class StandardRenderer {

	private RenderableObject target = null;
	
	private Tessellator instance = null;
	
	public StandardRenderer(@Nonnull Tessellator instance, @Nonnull RenderableObject object) {
		this.instance = checkNotNull(instance);
		this.target = checkNotNull(object);
	}
	
	public void render2D(float x, float y, @Nonnull Color4f color) {
		renderf(x, y, 0.f, target.getUCoord(), target.getVCoord(), 1.f, 1.f, color);
	}
	
	public void render2DWithCustomSizedTexture(float x, float y, int textureWidth, int textureHeight, @Nonnull Color4f color) {
		float maxU = (float) textureWidth / target.getWidth();
		float maxV = (float) textureHeight / target.getHeight();
		
		renderf(x, y, 0.f, target.getUCoord(), target.getVCoord(), maxU, maxV, color);
	}
	
	public void render2DWithCustomScaled(float x, float y, int textureStartX, int textureStartY, int textureEndX, int textureEndY, @Nonnull Color4f color, float scaleFactor) {
		checkNotNull(color);
		
		float minU = (float) textureStartX / target.getWidth();
		float minV = (float) textureStartY / target.getHeight();
		float maxU = (float) textureEndX / target.getWidth();
		float maxV = (float) textureEndY / target.getHeight();
		
		float scaledWidth = target.getWidth() * scaleFactor;
		float scaledHeight = target.getHeight()  * scaleFactor;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(target.getTextureLocation());
		
		GL11.glEnable(GL11.GL_BLEND);
		{
			GL11.glColor4f(color.x, color.y, color.z, color.w);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			instance.startDrawingQuads();
			instance.addVertexWithUV(x, y + scaledHeight, 0.f, minU, maxV);
			instance.addVertexWithUV(x + scaledWidth, y + scaledHeight, 0.f, maxU, maxV);
			instance.addVertexWithUV(x + scaledWidth, y, 0.f, maxU, minV);
			instance.addVertexWithUV(x, y, 0.f, minU, minV);
			instance.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void render2DWithCustomScaledAndSized(float x, float y, int width, int height,  int textureStartX, int textureStartY, int textureEndX, int textureEndY, @Nonnull Color4f color) {
		checkNotNull(color);
		
		float minU = (float) textureStartX / target.getWidth();
		float minV = (float) textureStartY / target.getHeight();
		float maxU = (float) textureEndX / target.getWidth();
		float maxV = (float) textureEndY / target.getHeight();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(target.getTextureLocation());
		
		GL11.glEnable(GL11.GL_BLEND);
		{
			GL11.glColor4f(color.x, color.y, color.z, color.w);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			instance.startDrawingQuads();
			instance.addVertexWithUV(x, y + height, 0.f, minU, maxV);
			instance.addVertexWithUV(x + width, y + height, 0.f, maxU, maxV);
			instance.addVertexWithUV(x + width, y, 0.f, maxU, minV);
			instance.addVertexWithUV(x, y, 0.f, minU, minV);
			instance.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void renderf(float x, float y, float z, float minU, float minV, float maxU, float maxV, @Nonnull Color4f color) {
		checkNotNull(color);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(target.getTextureLocation());

		GL11.glEnable(GL11.GL_BLEND);
		{
			GL11.glColor4f(color.x, color.y, color.z, color.w);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			instance.startDrawingQuads();
			instance.addVertexWithUV(x, y + target.getHeight(), z, minU, maxV);
			instance.addVertexWithUV(x + target.getWidth(), y + target.getHeight(), z, maxU, maxV);
			instance.addVertexWithUV(x + target.getWidth(), y, z, maxU, minV);
			instance.addVertexWithUV(x, y, z, minU, minV);
			instance.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void renderd(double x, double y, double z, double minU, double minV, double maxU, double maxV, @Nonnull Color4f color) {
		checkNotNull(color);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(target.getTextureLocation());
		
		GL11.glEnable(GL11.GL_BLEND);
		{
			GL11.glColor4f(color.x, color.y, color.z, color.w);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			instance.startDrawingQuads();
			instance.addVertexWithUV(x, y + target.getHeight(), z, minU, maxV);
			instance.addVertexWithUV(x + target.getWidth(), y + target.getHeight(), z, maxU, maxV);
			instance.addVertexWithUV(x + target.getWidth(), y, z, maxU, minV);
			instance.addVertexWithUV(x, y, z, minU, minV);
			instance.draw();
		}
		GL11.glDisable(GL11.GL_BLEND);
	}
}
