package jwk.minecraft.garden.client.renderer;

import static jwk.minecraft.garden.ProjectGarden.DEBUG;
import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.flower.Flowers;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;

@SideOnly(Side.CLIENT)
public class FlowerLanguageRenderer {
	
	public void doRender(FlowerInfo info, Entity renderView, float partialTicks) {
		double worldX = renderView.lastTickPosX + (renderView.posX - renderView.lastTickPosX) * partialTicks;
		double worldY = renderView.lastTickPosY + (renderView.posY - renderView.lastTickPosY) * partialTicks;
		double worldZ = renderView.lastTickPosZ + (renderView.posZ - renderView.lastTickPosZ) * partialTicks;

		Minecraft.getMinecraft().getTextureManager().bindTexture(FlowerInfo.FLOWER_LANGUAGE_TEXTURE.getTextureLocation());
		
		glEnable(GL_LIGHTING);
//		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		
		glPushMatrix();
		glTranslated(-worldX, -worldY, -worldZ);
		
		switch (info.Facing) {
		
		case NORTH:
			renderOnNorth(info);
			break;
			
		case SOUTH:
			renderOnSouth(info);
			break;
			
		case EAST:
			renderOnEast(info);
			break;
			
		case WEST:
			renderOnWest(info);
			break;
			
		default:
			throw new IllegalArgumentException();
		
		}
		
		glPopMatrix();
		
		glDisable(GL_LIGHTING);
//		glDisable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
	}
	
	public void renderOnNorth(FlowerInfo info) {
		Vector3f vec = info.RenderObj.getVector();
		
		double realX = vec.x + 1 + FlowerInfo.H_OFFSET;
		double realY = info.IsDoublePlant? vec.y + FlowerInfo.DOUBLE_V_OFFSET : vec.y + FlowerInfo.V_OFFSET;
		double realZ = vec.z + FlowerInfo.D_OFFSET;
		
		Color4f color = info.RenderObj.Color;
		int width = info.RenderObj.getRealWidth();
		int height = info.RenderObj.getRealHeight();
		
		double[] uvcoords = calculateUVCoordinates(info.RenderObj.XOffset, info.RenderObj.YOffset, info.RenderObj.TextureWidth, info.RenderObj.TextureHeight, info.RenderObj.Texture.Width, info.RenderObj.Texture.Height);
		double minU = uvcoords[0];
		double minV = uvcoords[1];
		double maxU = uvcoords[2];
		double maxV = uvcoords[3];
		
		Tessellator.instance.setColorRGBA_F(color.x, color.y, color.z, color.w);
		Tessellator.instance.startDrawingQuads();
		
		Tessellator.instance.setNormal(0.0f, 0.0f, -1.0f);
		Tessellator.instance.addVertexWithUV(realX, realY, realZ, minU, maxV); // min max
		Tessellator.instance.addVertexWithUV(realX -1 -2 * FlowerInfo.H_OFFSET, realY, realZ, maxU, maxV); // max max
		Tessellator.instance.addVertexWithUV(realX -1 -2 * FlowerInfo.H_OFFSET, realY + height, realZ, maxU, minV); //max min
		Tessellator.instance.addVertexWithUV(realX, realY + height, realZ, minU, minV); // min min
		
		Tessellator.instance.draw();
	}
	
	public void renderOnSouth(FlowerInfo info) {
		Vector3f vec = info.RenderObj.getVector();
		
		double realX = vec.x + 1 + FlowerInfo.H_OFFSET;
		double realY = info.IsDoublePlant? vec.y + FlowerInfo.DOUBLE_V_OFFSET : vec.y + FlowerInfo.V_OFFSET;
		double realZ = vec.z + FlowerInfo.D_OFFSET;
		
		Color4f color = info.RenderObj.Color;
		int width = info.RenderObj.getRealWidth();
		int height = info.RenderObj.getRealHeight();
		
		double[] uvcoords = calculateUVCoordinates(info.RenderObj.XOffset, info.RenderObj.YOffset, info.RenderObj.TextureWidth, info.RenderObj.TextureHeight, info.RenderObj.Texture.Width, info.RenderObj.Texture.Height);
		double minU = uvcoords[0];
		double minV = uvcoords[1];
		double maxU = uvcoords[2];
		double maxV = uvcoords[3];
		
		Tessellator.instance.setColorRGBA_F(color.x, color.y, color.z, color.w);
		Tessellator.instance.startDrawingQuads();
		
		Tessellator.instance.setNormal(0.0f, 0.0f, 1.0f);
		Tessellator.instance.addVertexWithUV(realX -1 -2 * FlowerInfo.H_OFFSET, realY, realZ, minU, maxV); // max max
		Tessellator.instance.addVertexWithUV(realX, realY, realZ, maxU, maxV); // min max
		Tessellator.instance.addVertexWithUV(realX, realY + height, realZ, maxU, minV); // min min
		Tessellator.instance.addVertexWithUV(realX -1 -2 * FlowerInfo.H_OFFSET, realY + height, realZ, minU, minV); //max min
		
		Tessellator.instance.draw();
	}
	
	public void renderOnEast(FlowerInfo info) {		
		Vector3f vec = info.RenderObj.getVector();
		
		double realX = vec.x + FlowerInfo.D_OFFSET;
		double realY = info.IsDoublePlant? vec.y + FlowerInfo.DOUBLE_V_OFFSET : vec.y + FlowerInfo.V_OFFSET;
		double realZ = vec.z + 1 + FlowerInfo.H_OFFSET;
		
		Color4f color = info.RenderObj.Color;
		int width = info.RenderObj.getRealWidth();
		int height = info.RenderObj.getRealHeight();
		
		double[] uvcoords = calculateUVCoordinates(info.RenderObj.XOffset, info.RenderObj.YOffset, info.RenderObj.TextureWidth, info.RenderObj.TextureHeight, info.RenderObj.Texture.Width, info.RenderObj.Texture.Height);
		double minU = uvcoords[0];
		double minV = uvcoords[1];
		double maxU = uvcoords[2];
		double maxV = uvcoords[3];
		
		Tessellator.instance.setColorRGBA_F(color.x, color.y, color.z, color.w);
		Tessellator.instance.startDrawingQuads();
		
		Tessellator.instance.setNormal(1.0f, 0.0f, 0.0f);
		Tessellator.instance.addVertexWithUV(realX, realY, realZ, minU, maxV); // min max
		Tessellator.instance.addVertexWithUV(realX, realY, realZ -1 -2 * FlowerInfo.H_OFFSET, maxU, maxV); // max max
		Tessellator.instance.addVertexWithUV(realX, realY + height, realZ -1 -2 * FlowerInfo.H_OFFSET, maxU, minV); //max min
		Tessellator.instance.addVertexWithUV(realX, realY + height, realZ, minU, minV); // min min
		
		Tessellator.instance.draw();
	}
	
	public void renderOnWest(FlowerInfo info) {		
		Vector3f vec = info.RenderObj.getVector();
		
		double realX = vec.x + FlowerInfo.D_OFFSET;
		double realY = info.IsDoublePlant? vec.y + FlowerInfo.DOUBLE_V_OFFSET : vec.y + FlowerInfo.V_OFFSET;
		double realZ = vec.z + 1 + FlowerInfo.H_OFFSET;
		
		Color4f color = info.RenderObj.Color;
		int width = info.RenderObj.getRealWidth();
		int height = info.RenderObj.getRealHeight();
		
		double[] uvcoords = calculateUVCoordinates(info.RenderObj.XOffset, info.RenderObj.YOffset, info.RenderObj.TextureWidth, info.RenderObj.TextureHeight, info.RenderObj.Texture.Width, info.RenderObj.Texture.Height);
		double minU = uvcoords[0];
		double minV = uvcoords[1];
		double maxU = uvcoords[2];
		double maxV = uvcoords[3];
		
		Tessellator.instance.setColorRGBA_F(color.x, color.y, color.z, color.w);
		Tessellator.instance.startDrawingQuads();
		
		Tessellator.instance.setNormal(-1.0f, 0.0f, 0.0f);
		Tessellator.instance.addVertexWithUV(realX, realY, realZ -1 -2 * FlowerInfo.H_OFFSET, minU, maxV); // max max
		Tessellator.instance.addVertexWithUV(realX, realY, realZ, maxU, maxV); // min max
		Tessellator.instance.addVertexWithUV(realX, realY + height, realZ, maxU, minV); // min min
		Tessellator.instance.addVertexWithUV(realX, realY + height, realZ -1 -2 * FlowerInfo.H_OFFSET, minU, minV); //max min
		
		Tessellator.instance.draw();
	}
	
	public double[] calculateUVCoordinates(int textureXOffset, int textureYOffset, int width, int height, int textureWidth, int textureHeight) {
		double minU = (double) textureXOffset / (double) textureWidth;
		double minV = (double) textureYOffset / (double) textureHeight;
		double maxU = (double) (textureXOffset + width) / (double) textureWidth;
		double maxV = (double) (textureYOffset + height) / (double) textureHeight;
		
		return new double[] { minU, minV, maxU, maxV };
	}
}
