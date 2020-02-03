package jwk.minecraft.garden.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.font.Fonts;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.client.util.ResolutionHelper;
import jwk.minecraft.garden.network.PacketShop;
import jwk.minecraft.garden.network.PacketShop.Action;
import jwk.minecraft.garden.shop.ShopData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public abstract class GuiShop extends GuiScreen {
	
	@Deprecated protected List buttonList = null;
	@Deprecated protected List labelList = null;
	
	protected float scaleFactor = 6;
	
	protected int npcRealWidth;
	protected int npcRealHeight;
	
	protected static final int XOFFSET = 60;
	protected static final int YOFFSET = 15;
	protected static final int NPC_X_OFFSET = 7;
	
	protected static final int BUBBLE_X_OFFSET = -76;
	protected static final int BUBBLE_Y_OFFSET = 90;
	
	protected int bubbleRealXOffset;
	protected int bubbleRealYOffset;
	
	protected int bubbleRealWidth;
	protected int bubbleRealHeight;
	
	protected final RenderableObject ObjMenu;
	protected final RenderableObject ObjNPC;
	protected final RenderableObject ObjSpeechBubble;
	
	protected GuiSlot selectedSlot = null;
	
	protected final GuiSlot[] Slots = new GuiSlot[12];
	
	protected float xPos;
	protected float yPos;
	
	protected float menuX;
	protected float menuY;
	
	protected int shopWidth;
	protected int shopHeight;
	
	protected boolean shouldDrawBackground = true;
	protected Color4f backgroundColor = new Color4f(0.F, 0.F, 0.F, 0.2F);
	
	protected RenderFrame frameObj = new RenderFrame();
	
	protected boolean initialized = false;
	
	@Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int eventButton) {
		

		if (selectedSlot != null && selectedSlot.hovered())
			selectedSlot.mouseReleased(MouseButton.fromInt(eventButton), mouseX, mouseY);
			
		if (eventButton == 0)
			onMouseReleased(mouseX, mouseY);
    }
	
	protected void onMouseReleased(int mouseX, int mouseY) { }
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int eventButton) {
    	
    	for (GuiSlot slot : Slots) {
    		
    		if (slot != null && slot.mouseClicked(MouseButton.fromInt(eventButton), mouseX, mouseY)) {
    			selectedSlot = slot;
    			onMouseClicked(selectedSlot, mouseX, mouseY);
    			return;
    		}
    	}
    	
    	selectedSlot = null;
    }
    
    protected void onMouseClicked(GuiSlot slot, int mouseX, int mouseY) { }
	
    @Override
    public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
        this.mc = minecraft;
        this.fontRendererObj = minecraft.fontRenderer;
        this.width = width;
        this.height = height;
        
        if (!MinecraftForge.EVENT_BUS.post(new InitGuiEvent.Pre(this, this.buttonList)))
        	this.initGui();
        
        MinecraftForge.EVENT_BUS.post(new InitGuiEvent.Post(this, this.buttonList));
    }
    
    private void drawBackground(Color4f color) {
    	RenderFrame.drawRect(0.F, 0.F, 0.F, width, height, color);
    }
    
    @Override
    public void onGuiClosed() {
    	ProjectGarden.NET_HANDLER.sendToServer(new PacketShop(Action.CLOSE, null, Minecraft.getMinecraft().getSession().func_148256_e().getId()));
    }
    
    protected GuiShop(RenderableObject objNPC, RenderableObject objMenu, RenderableObject objSpeechBubble) {
    	ObjNPC = objNPC;
    	ObjMenu = objMenu;
    	ObjSpeechBubble = objSpeechBubble;
    	
    	shopData = ProjectGarden.proxy.getCurrentShopData();
    }
    
    protected ShopData shopData;
    
    @Override
    public void initGui() {
    	setupScale();
    	
		setupPosition();
		
		float bX = xPos - bubbleRealWidth - bubbleRealXOffset;
		float bY = yPos - bubbleRealHeight + bubbleRealYOffset;
		
		if (!initialized) {
			
			if (ObjNPC != null)
				frameObj.attach("npc", ObjNPC.clone().setRealWidth(npcRealWidth).setRealHeight(npcRealHeight).setVector(xPos, yPos, 0.F));
			
			frameObj.attach("menu", ObjMenu.clone().setRealWidth(shopWidth).setRealHeight(shopHeight).setVector(menuX, menuY, 0.F));
			
			if (ObjSpeechBubble != null)
				frameObj.attach("bubble", ObjSpeechBubble.clone().setRealWidth(bubbleRealWidth).setRealHeight(bubbleRealHeight).setVector(bX, bY, 0.F));
    	
			initSlots();
			
			initialized = true;
		}
		
		else {
			
			if (ObjNPC != null)
				frameObj.get("npc").setRealWidth(npcRealWidth).setRealHeight(npcRealHeight).setVector(xPos, yPos, 0.F);
			
		    frameObj.get("menu").setRealWidth(shopWidth).setRealHeight(shopHeight).setVector(menuX, menuY, 0.F);
		    
			if (ObjSpeechBubble != null)
				frameObj.get("bubble").setRealWidth(bubbleRealWidth).setRealHeight(bubbleRealHeight).setVector(bX, bY, 0.F);
		    
		    updateSlotPosAndScale();
		}
    }
    
    protected abstract void initSlots();
    
    private void setupScale() {
    	scaleFactor = 1 / ResolutionHelper.getGuiScaledValueWeighted(6F, 1F);
    	
    	if (ObjNPC != null) {
    		npcRealWidth = (int) (ObjNPC.TextureWidth * scaleFactor);
    		npcRealHeight = (int) (ObjNPC.TextureHeight  * scaleFactor);
    	}
    	
    	else {
    		npcRealWidth = 0;
    		npcRealHeight = 0;
    	}
    	
		shopWidth = (int) (ObjMenu.TextureWidth * scaleFactor);
		shopHeight = (int) (ObjMenu.TextureHeight * scaleFactor);
    	
    	slotRealOffset = (int) (SLOT_OFFSET * scaleFactor);
    	
    	firstSlotRealX = FIRST_SLOT_X * scaleFactor;
    	firstSlotRealY = FIRST_SLOT_Y * scaleFactor;
    	
    	sixthSlotRealX = SIXTH_SLOT_X * scaleFactor;
    	sixthSlotRealY = firstSlotRealY;
    	
    	slotRealWidth = (int) (SLOT_WIDTH * scaleFactor);
    	slotRealHeight = (int) (SLOT_HEIGHT * scaleFactor);
    	
    	slotInnerRealWidth = (int) (SLOT_INNER_WIDTH * scaleFactor);
    	slotInnerRealHeight = (int) (SLOT_INNER_HEIGHT * scaleFactor);
    	
    	
    	if (ObjSpeechBubble != null) {
    		float f = 0.6F;
    	
    		bubbleRealXOffset = (int) (BUBBLE_X_OFFSET * f);
    		bubbleRealYOffset = (int) (BUBBLE_Y_OFFSET * f);
    	
    		float f1 = 0.46F;
    	
    		bubbleRealWidth = (int) (ObjSpeechBubble.TextureWidth * f1);
    		bubbleRealHeight = (int) (ObjSpeechBubble.TextureHeight * f1);
    	}
    }
    
    public static float getScaleFactor() {
    	
    	int width = Minecraft.getMinecraft().displayWidth;
    	int height = Minecraft.getMinecraft().displayHeight;
    	
    	int dw = Display.getDesktopDisplayMode().getWidth();
    	int dh = Display.getDesktopDisplayMode().getHeight();
    	if (dw == width || dh == height)
    		return 10F;
    	
    	else if (width < 640 || height < 480)
    		return 6F;
    	
    	else if (width < 1280 || height < 720) {
        	float wscale = (6 + 1) * 1280 / width;
        	float hscale = (6 + 1) * 720 / height;
        	
        	return Math.min(wscale, hscale);
    	}
    	
    	return 6F;
    }
    
    public void onUpdate(int mouseX, int mouseY) {    	
    	updateSlots(mouseX, mouseY);
    }
    
    protected void setupPosition() {	
    	xPos = (float) ((width - XOFFSET - NPC_X_OFFSET - npcRealWidth - shopWidth) * 0.5);
    	yPos = (float) height - npcRealHeight;
    	
    	menuX = xPos + NPC_X_OFFSET + npcRealWidth;
    	menuY = (float) ((height - shopHeight) * 0.5) - YOFFSET;
    }
    
    protected static final int SLOT_OFFSET = 232;
    protected int slotRealOffset;
	
    protected static final float FIRST_SLOT_X = 196;
    protected static final float FIRST_SLOT_Y = 282;
	
    protected float firstSlotRealX;
    protected float firstSlotRealY;
	
    protected static final float SIXTH_SLOT_X = 779;
	
    protected float sixthSlotRealX;
    protected float sixthSlotRealY;
	
    protected static final int SLOT_WIDTH = 187;
    protected static final int SLOT_HEIGHT = 186;
	
    protected int slotRealWidth;
    protected int slotRealHeight;
	
    protected static final int SLOT_INNER_WIDTH = 164;
    protected static final int SLOT_INNER_HEIGHT = 163;
	
    protected int slotInnerRealWidth;
    protected int slotInnerRealHeight;
    
    protected void updateSlotPosAndScale() {
    	
    	for (int i=0; i < 12; i++) {
    		
    		if (Slots[i] == null)
    			continue;
    		
    		float[] pos = getSlotPosition(i);
    		
			Slots[i].updatePosAndScale(pos[0], pos[1], 1 / ResolutionHelper.getGuiScaleFactor());
    	}
    }
    
    protected void updateSlots(int mouseX, int mouseY) {
    	
    	for (int i=0; i < 12; i++) {
    		
    		if (Slots[i] == null)
    			continue;
    		
			Slots[i].onUpdate(mouseX, mouseY);
    	}
    }
    
    protected float[] getSlotPosition(int index) {
    	
    	if (index < 0 || index > 11)
    		return null;
    	
    	float x = 0.F, y = 0.F;
    	
    	if (index <= 5) {
    		x = menuX + firstSlotRealX;
    		y = menuY + firstSlotRealY + slotRealOffset * index;
    	}
    	
    	else {
    		index -= 6;
    		
    		x = menuX + sixthSlotRealX;
    		y = menuY + sixthSlotRealY + slotRealOffset * index;
    	}
    	
    	return new float[] { x, y };
    }
	
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	onUpdate(mouseX, mouseY);
    	
    	if (shouldDrawBackground)
    		drawBackground(backgroundColor);
    	
    	frameObj.doRender();
    	
    	for (GuiSlot slot : Slots) {
    		
    		if (slot != null)
    			slot.draw(this.itemRender, partialTicks);
    	}
    	
    	final float fontX = (menuX + 40);
    	final float fontY = (menuY + shopHeight - 40);
    	
    	Fonts.fontDohyeon.drawString("좌클릭: 1개, 우클릭: 10개", fontX, fontY, Color.BLACK);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
}
