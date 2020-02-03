package jwk.minecraft.garden.client.gui;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import javax.annotation.Nonnull;
import javax.vecmath.Color4f;

import org.lwjgl.opengl.GL11;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.font.Fonts;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import jwk.minecraft.garden.item.category.ItemCategory;
import jwk.minecraft.garden.network.PacketShop;
import jwk.minecraft.garden.network.PacketShop.Action;
import jwk.minecraft.garden.shop.ISlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;

public class GuiSlot {
	
	private static final Color4f OVERLAY_COLOR = new Color4f(0.F, 0.F, 0.F, 0.2F);
	
	private Minecraft mc;
	
	private static final int IMG_WIDTH = 187;
	private static final int IMG_HEIGHT = 186;
	
	private static final int FONT_X_OFFSET = 40;
	private static final int FONT_Y_OFFSET = 17;
	
	private int fontRealXOffset = FONT_X_OFFSET;
	private int fontRealYOffset = FONT_Y_OFFSET;
	
	private static final float ICON_SCALE = 1.5F;
	
	private float iconRealScale = 1.5F;

	private int slotNumber;
	private ISlot slot;
	
	private float xPos = 0.0F;
	private float yPos = 0.0F;
	
	private int width;
	private int height;
	
	private int innerWidth;
	private int innerHeight;
	
	private boolean hovered = false;
	
	private boolean isSetup;
	
	public GuiSlot(Minecraft mc, int number, @Nonnull ISlot slot, float x, float y) {
		this (mc, number, slot, x, y, 187, 186, 164, 163, false);
	}

	public GuiSlot(Minecraft mc, int number, @Nonnull ISlot slot, float x, float y, int width, int height, int innerWidth, int innerHeight, boolean isSetup) {
		checkArgument(number >= 0); checkArgument(width > 0); checkArgument(height > 0);
		
		this.mc = mc;
		slotNumber = number;
		this.slot = checkNotNull(slot);
		this.xPos = x;
		this.yPos = y;
		this.width = width;
		this.height = height;
		this.innerWidth = innerWidth;
		this.innerHeight = innerHeight;
		this.isSetup = isSetup;
	}
	
	public boolean mouseClicked(MouseButton clicked, int mouseX, int mouseY) {
		return (clicked == MouseButton.LEFT || clicked == MouseButton.RIGHT) && mouseX >= xPos && mouseY >= yPos && mouseX < xPos + innerWidth && mouseY < yPos + innerHeight;
	}
	
	public void mouseReleased(MouseButton clicked, int mouseX, int mouseY) {
		
		if (clicked == MouseButton.LEFT)
			ProjectGarden.NET_HANDLER.sendToServer(new PacketShop(Action.SLOT_CLICK, slotNumber, Minecraft.getMinecraft().thePlayer.getUniqueID()));
		
		else if (clicked == MouseButton.RIGHT)
			ProjectGarden.NET_HANDLER.sendToServer(new PacketShop(Action.SLOT_RIGHT_CLICK, slotNumber, Minecraft.getMinecraft().thePlayer.getUniqueID()));
	}
	
	public void draw(RenderItem renderer, float partialTicks) {
		glDisable(GL_LIGHTING);		
		
		int x = (int) (xPos + width - innerWidth);
		int y = (int) (yPos + height - innerHeight);
		
		if (slot.getCategory().getIcon().getItem() == null)
			return;
		
		if (hovered)
			RenderFrame.drawRect(x - 1, y - 1.F, 0.F, innerWidth, innerHeight + 2, OVERLAY_COLOR);
		
		int xoffset = 1;
		int yoffset = slotNumber == 5? 2 : 1;
		
		drawCategoryIcon(renderer, slot.getCategory(), x + xoffset, y + yoffset, iconRealScale);
		
		glDisable(GL_LIGHTING);
		
		if (isSetup)
			return;
		
		if (slotNumber == 2) {
			String amount = String.valueOf(slot.getAmount());
			final int aoffx = 2, aoffy = 3;
			int amountX = (int) (xPos + innerWidth + aoffx - Fonts.fontDohyeonS.getWidth(amount));
			int amountY = (int) (yPos + innerHeight + aoffy - Fonts.fontDohyeonS.getHeight(amount));
		
			Fonts.fontDohyeonS.drawString(amount, amountX, amountY, Color.BLACK);
		}
		
		int fontx = (int) (xPos + fontRealXOffset);
		int fonty = (int) (yPos + fontRealYOffset);
		
		Fonts.fontDohyeonL.drawString(String.valueOf(slot.getPrice() + " Y"), fontx, fonty, Color.BLACK);
	}
	
	private void drawCategoryIcon(RenderItem renderer, ItemCategory category, int x, int y, float scale) {
		float l = 1 / scale;
		int lx = (int) (l * x), ly = (int) (l * y);

		glEnable(GL_DEPTH_TEST);
		renderer.zLevel = 200.F;

		glPushMatrix(); {
			glScalef(scale, scale, 1.F);
			glTranslatef(0.F, 0.F, 32.F);
			
			renderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), category.getIcon(), lx, ly);
			
		} glPopMatrix();
		
		renderer.zLevel = 0F;
		glDisable(GL_DEPTH_TEST);
	}
	
	public void onUpdate(int mouseX, int mouseY) {		
		hovered = mouseX >= xPos && mouseY >= yPos && mouseX < xPos + innerWidth && mouseY < yPos + innerHeight;
	}
	
	public void updatePosAndScale(float x, float y, float scaleFactor) {
		xPos = x;
		yPos = y;
		
		fontRealXOffset = (int) (FONT_X_OFFSET * scaleFactor);
		fontRealYOffset = (int) (FONT_Y_OFFSET * scaleFactor);
		iconRealScale = ICON_SCALE * scaleFactor;
	}
	
	public float getX() { return xPos; }
	
	public float getY() { return yPos; }
	
	public int getWidth() { return width; }
	
	public int getInnerWidth() { return innerWidth; }
	
	public int getHeight() { return height; }
	
	public int getInnerHeight() { return innerHeight; }
	
	public ISlot getSlot() { return slot; }
	
	public int getNumber() { return slotNumber; }
	
	public boolean hovered() { return hovered; }
	
}
