package jwk.minecraft.garden.client.renderer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderFrame {
	
	private final Map<String, RenderableObject> renderableObjects = new HashMap<String, RenderableObject>();
	
	private TextureInfo currentTexture = null;
	
	public boolean attach(@Nonnull String id, @Nonnull RenderableObject obj) {
		checkNotNull(id);
		checkNotNull(obj);
		
		if (renderableObjects.containsKey(id))
			return false;
		
		renderableObjects.put(id, obj);
		return true;
	}
	
	public boolean detach(@Nonnull String id) {
		checkNotNull(id);

		if (renderableObjects.containsKey(id)) {
			renderableObjects.remove(id);
			return true;
		}
		
		return false;
	}
	
	public boolean detach(@Nonnull RenderableObject obj) {
		checkNotNull(obj);
		
		if (renderableObjects.containsValue(obj)) {
			renderableObjects.remove(obj);
			return true;
		}
		
		return false;
	}
	
	public RenderableObject get(@Nonnull String id) {
		checkNotNull(id);
		
		return renderableObjects.get(id);
	}
	
	public Collection<RenderableObject> values() { return renderableObjects.values(); }
	
	public int size() { return renderableObjects.size(); }
	
	public boolean isEmpty() { return renderableObjects.isEmpty(); }
	
	public void clear() { renderableObjects.clear(); }
	
	public void doRender() {
		currentTexture = null;
		
		for (RenderableObject obj : renderableObjects.values()) {
			
			if (obj.Texture == null && currentTexture == null)
				drawRect(obj.getVector().x, obj.getVector().y, obj.getVector().z, obj.getRealWidth(), obj.getRealHeight(), obj.Color);
			
			else if (obj.Texture == null && currentTexture != null) {
				//Minecraft.getMinecraft().getTextureManager().deleteTexture(currentTexture.TextureLocation);
				
				drawRect(obj.getVector().x, obj.getVector().y, obj.getVector().z, obj.getRealWidth(), obj.getRealHeight(), obj.Color);
			}
			
			else if (obj.Texture != null && currentTexture == null) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(obj.Texture.getTextureLocation());
				
				renderWith(obj);
			}
			
			else {
				
				if (currentTexture.isSameTextureWith(obj.Texture))
					renderWith(obj);
				
				else {
					//Minecraft.getMinecraft().getTextureManager().deleteTexture(currentTexture.TextureLocation);
					Minecraft.getMinecraft().getTextureManager().bindTexture(obj.Texture.getTextureLocation());
					
					renderWith(obj);
				}
			}
			
			currentTexture = obj.Texture;
		}
	}
	
	private void renderWith(RenderableObject obj) {
		double[] uvcoords = calculateUVCoordinates(obj.XOffset, obj.YOffset, obj.TextureWidth, obj.TextureHeight, obj.Texture.Width, obj.Texture.Height);
		
		actualRenderFunc(obj.getVector().x, obj.getVector().y, obj.getVector().z, obj.getRealWidth(), obj.getRealHeight(), uvcoords[0], uvcoords[1], uvcoords[2], uvcoords[3], obj.Color);
	}
	
	private double[] calculateUVCoordinates(int textureXOffset, int textureYOffset, int width, int height, int textureWidth, int textureHeight) {
		double minU = (double) textureXOffset / (double) textureWidth;
		double minV = (double) textureYOffset / (double) textureHeight;
		double maxU = (double) (textureXOffset + width) / (double) textureWidth;
		double maxV = (double) (textureYOffset + height) / (double) textureHeight;
		
		return new double[] { minU, minV, maxU, maxV };
	}
	
	public static void actualRenderFunc(float x, float y, float z, float width, float height, double minU, double minV, double maxU, double maxV, @Nonnull Color4f color) {
		checkNotNull(color);
		checkArgument(width > 0 && height > 0);
		
		if (z != 0.f && z != -0.f)
			glEnable(GL_DEPTH_TEST);
		
		float r = color.x;
		float g = color.y;
		float b = color.z;
		float a = color.w;		
		
		glEnable(GL_TEXTURE_2D);
//		glDisable(GL_LIGHTING);
		glEnable(GL_BLEND);
	
		Tessellator.instance.startDrawingQuads();
		
		Tessellator.instance.setColorRGBA_F(r, g, b, a);		
		Tessellator.instance.addVertexWithUV(x, y + height, z, minU, maxV);
		Tessellator.instance.addVertexWithUV(x + width, y + height, z, maxU, maxV);
		Tessellator.instance.addVertexWithUV(x + width, y, z, maxU, minV);
		Tessellator.instance.addVertexWithUV(x, y, z, minU, minV);
		
		Tessellator.instance.draw();
		
	//	glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
//		glEnable(GL_LIGHTING);
		
		if (z != 0.f && z != -0.f)
			glDisable(GL_DEPTH_TEST);
	}
	
//	public static void actualRenderFunc(@Nonnull Vector3f vec, float width, float height, float minU, float minV, float maxU, float maxV, @Nonnull Color4f color) {
//		checkNotNull(vec);
//		checkNotNull(color);
//		checkArgument(width > 0 && height > 0);
//		
//		if (vec.z != 0.f && vec.z != -0.f)
//			glEnable(GL_DEPTH_TEST);
//		
//		glEnable(GL_TEXTURE_2D);
//		glDisable(GL_LIGHTING);
//		glEnable(GL_BLEND);
//		
//		Tessellator.instance.setColorRGBA_F(color.x, color.y, color.z, color.w);
//		Tessellator.instance.startDrawingQuads();
//		
//		Tessellator.instance.addVertexWithUV(vec.x, vec.y + height, vec.z, maxU, minV); // min max
//		Tessellator.instance.addVertexWithUV(vec.x + width, vec.y + height, vec.z, minU, minV); // max max
//		Tessellator.instance.addVertexWithUV(vec.x + width, vec.y, vec.z, minU, maxV); //max min
//		Tessellator.instance.addVertexWithUV(vec.x, vec.y, vec.z, maxU, maxV); // min min
//		
//		Tessellator.instance.draw();
//		
//		//glDisable(GL_TEXTURE_2D);
//		glDisable(GL_BLEND);
//		glEnable(GL_LIGHTING);
//		
//		if (vec.z != 0.f && vec.z != -0.f)
//			glDisable(GL_DEPTH_TEST);
//	}
	
	public static void drawRect(float x, float y, float z, float width, float height, @Nonnull Color4f color) {
		checkNotNull(color);
		checkArgument(width > 0 && height > 0);
		
//		if (z != 0.f && z != -0.f)
//			glEnable(GL_DEPTH_TEST);
		
//		glDisable(GL_TEXTURE_2D);
//		glDisable(GL_LIGHTING);
//		glEnable(GL_BLEND);
		
		float r = color.x;
		float g = color.y;
		float b = color.z;
		float a = color.w;		

//		Tessellator.instance.startDrawingQuads();
		
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBegin(GL_QUADS);
		glColor4f(r, g, b, a);
		glVertex3f(x, y + height, z);
		glVertex3f(x + width, y + height, z);
		glVertex3f(x + width, y, z);
		glVertex3f(x, y, z);
		glEnd();
//		Tessellator.instance.setColorRGBA_F(r, g, b, a);
//		Tessellator.instance.addVertex(x, y + height, z);
//		Tessellator.instance.addVertex(x + width, y + height, z);
//		Tessellator.instance.addVertex(x + width, y, z);
//		Tessellator.instance.addVertex(x, y, z);
//		
//		Tessellator.instance.draw();
		
		glEnable(GL_TEXTURE_2D);
		
//		glEnable(GL_TEXTURE_2D);
		//glDisable(GL_BLEND);
		
//		if (z != 0.f && z != -0.f)
//			glDisable(GL_DEPTH_TEST);
	}
	
}
