package jwk.minecraft.garden.client.gui;

import javax.vecmath.Color4f;

import org.lwjgl.opengl.GL11;

import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.renderer.SimpleRenderer2D;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiMap extends GuiScreen {

	private static final ResourceLocation RES_MAP = new ResourceLocation(ModInfo.ID, "textures/gui/map.png");
	
	private SimpleRenderer2D renderer = new SimpleRenderer2D();
	
	protected Color4f backgroundColor = new Color4f(0.F, 0.F, 0.F, 0.4F);
	
	private int xPos;
	private int yPos;
	
	private static final float SCALE_FACTOR = 0.8F;
	
	private int realWidth;
	private int realHeight;
	
	@Override
	public void initGui() {
		this.realWidth = (int) (this.width * SCALE_FACTOR);
		this.realHeight = (int) (this.height * SCALE_FACTOR);
		
		this.xPos = (width - realWidth) / 2;
		this.yPos = (height - realHeight) / 2;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawBackground(backgroundColor);
		
		GL11.glColor4f(1.F, 1.F, 1.F, 1.F);
		mc.getTextureManager().bindTexture(RES_MAP);
		renderer.render(xPos, yPos, 1280, 720, 0, 0, 1280, 720, realWidth, realHeight);
	}
	
    private void drawBackground(Color4f color) {
    	RenderFrame.drawRect(0.F, 0.F, 0.F, width, height, color);
    }
	
	@Override
	public boolean doesGuiPauseGame() { return false; }
	
}
