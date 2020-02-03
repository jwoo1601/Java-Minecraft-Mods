package noah.minecraft.runaway.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiDirectionArrow extends Gui {
	
	public static enum Axis { X, MINUS_X, Y, MINUS_Y, Z, MINUS_Z }
	
	private static double defaultMargin = 5.0D;
	private static int defaultTWidth = 16, defaultTHeight = 10;
	private static int defaultSWidth = 10, defaultSHeight = 15;
	
	private double x, y, z;
	private double margin;
	private int TWidth, THeight;
	private int SWidth, SHeight;
	
	private double angle_x = 0.0D, angle_y = 0.0D, angle_z = 0.0D;
	
	private float red = 1.0f, green = 0.0f, blue = 0.0f, alpha = 0.5f;
	
	public GuiDirectionArrow() {
		this(0.0D, 0.0D, 0.0D, defaultMargin, defaultTWidth, defaultTHeight, defaultSWidth, defaultSHeight);
	}
	
	public GuiDirectionArrow(int TWidth, int THeight, int SWidth, int SHeight) {
		this(0.0D, 0.0D, 0.0D, defaultMargin, TWidth, THeight, SWidth, SHeight);
	}
	
	public GuiDirectionArrow(double x, double y, double z, double margin, int TWidth, int THeight, int SWidth, int SHeight) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.margin = margin;
		this.TWidth = TWidth;
		this.THeight = THeight;
		this.SWidth = SWidth;
		this.SHeight = SHeight;
	}
	
	public GuiDirectionArrow setColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		
		return this;
	}
	
	public GuiDirectionArrow setAlpha(float alpha) {
		this.alpha = alpha;
		
		return this;
	}
	
	public GuiDirectionArrow setMargin(double margin) {
		this.margin = margin;
		
		return this;
	}
	
	public void UpdatePosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	private void resetRotation() {
		this.angle_x = 0.0D;
		this.angle_y = 0.0D;
		this.angle_z = 0.0D;
	}
	
	public void UpdateRotation(Axis axis, double angle) {
		switch (axis) {
		case X:
			this.angle_x = angle;
			break;
		case MINUS_X:
			this.angle_x = 360.0D - angle;
			break;
		case Y:
			this.angle_y = angle;
			break;
		case MINUS_Y:
			this.angle_y = 360.0D - angle;
			break;
		case Z:
			this.angle_z = angle;
			break;
		case MINUS_Z:
			this.angle_z = 360.0D - angle;
			break;			
		}
	}
	
	public void onDraw(Minecraft mc, double distance, Tessellator t) {		
		WorldRenderer renderer = t.getWorldRenderer();
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.enableDepth();
		
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		
		if (angle_x > 0.0D)
			GL11.glRotated(angle_x, 1.0D, 0.0D, 0.0D);
		
		if (angle_y > 0.0D)
			GL11.glRotated(angle_y, 0.0D, 1.0D, 0.0D);
		
		if (angle_z > 0.0D)
			GL11.glRotated(angle_z, 0.0D, 0.0D, 1.0D);
		
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		renderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		renderer.pos(0.0D, -margin -SHeight -THeight, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(-TWidth * 0.5, -margin -SHeight, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(TWidth * 0.5, -margin -SHeight, 0.0D).color(red, green, blue, alpha).endVertex();
		Tessellator.getInstance().draw();		
		
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		renderer.pos(-SWidth * 0.5, -margin, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(SWidth * 0.5, -margin, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(SWidth * 0.5, -margin -SHeight, 0.0D).color(red, green, blue, alpha).endVertex();
		renderer.pos(-SWidth * 0.5, -margin -SHeight, 0.0D).color(red, green, blue, alpha).endVertex();
		Tessellator.getInstance().draw();

		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		
		String d = String.format("%.2f", distance);
		
		GlStateManager.pushMatrix();
		GL11.glTranslated(x - mc.fontRendererObj.getStringWidth(d) * 0.5, y, z);
		mc.fontRendererObj.drawStringWithShadow(d + "m", 0, 0, 0xFFFFFF);
		GlStateManager.popMatrix();
		
		resetRotation();
	}
}
