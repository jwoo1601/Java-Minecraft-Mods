package jwk.minecraft.garden.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.client.util.ResolutionHelper;
import jwk.minecraft.garden.network.PacketShop;
import jwk.minecraft.garden.network.PacketShop.Action;
import jwk.minecraft.garden.shop.PortableSlot;
import jwk.minecraft.garden.shop.ShopData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public abstract class GuiShopSetup extends GuiScreen {
	
	@Deprecated protected List labelList = null;
	
	protected List<GuiTextField> textFieldList = Lists.newArrayList();
	
	protected float scaleFactor = 6;
	
	protected int npcRealWidth;
	protected int npcRealHeight;
	
	protected static final int XOFFSET = 60;
	protected static final int YOFFSET = 15;
	protected static final int NPC_X_OFFSET = 10;
	
	protected final RenderableObject ObjMenu;
	protected final RenderableObject ObjNPC;
	
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
    protected void keyTyped(char c, int keyCode) {
    	
    	for (GuiTextField field : textFieldList)
    		field.textboxKeyTyped(c, keyCode);
    	
    	super.keyTyped(c, keyCode);
    }
	
	@Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int eventButton) {		
			
		if (eventButton == 0)
			onMouseReleased(mouseX, mouseY);
		
		super.mouseMovedOrUp(mouseX, mouseY, eventButton);
    }
	
	protected void onMouseReleased(int mouseX, int mouseY) { }
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int eventButton) {    
    	super.mouseClicked(mouseX, mouseY, eventButton);
    	
    	for (GuiTextField field : textFieldList)
    		field.mouseClicked(mouseX, mouseY, eventButton);
	}
    
    protected void onMouseClicked(GuiSlot slot, int mouseX, int mouseY) { }
	
    @Override
    public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
        this.mc = minecraft;
        this.fontRendererObj = minecraft.fontRenderer;
        this.width = width;
        this.height = height;
        
        if (!MinecraftForge.EVENT_BUS.post(new InitGuiEvent.Pre(this, this.buttonList))) {
        	textFieldList.clear();
        	buttonList.clear();
        	this.initGui();
        }
        
        MinecraftForge.EVENT_BUS.post(new InitGuiEvent.Post(this, this.buttonList));
    }
    
    private void drawBackground(Color4f color) {
    	RenderFrame.drawRect(0.F, 0.F, 0.F, width, height, color);
    }
    
    @Override
    public void onGuiClosed() {
    	ProjectGarden.NET_HANDLER.sendToServer(new PacketShop(Action.CLOSE, null, Minecraft.getMinecraft().getSession().func_148256_e().getId()));
    }
    
    protected GuiShopSetup(RenderableObject objNPC, RenderableObject objMenu) {
    	ObjNPC = objNPC;
    	ObjMenu = objMenu;
    	
    	shopData = ProjectGarden.proxy.getCurrentShopData();
    }
    
    protected ShopData shopData;
    
    @Override
    public void initGui() {
    	setupScale();
    	
		setupPosition();
		
		if (!initialized) {
			
			if (ObjNPC != null)
				frameObj.attach("npc", ObjNPC.clone().setRealWidth(npcRealWidth).setRealHeight(npcRealHeight).setVector(xPos, yPos, 0.F));
			
			frameObj.attach("menu", ObjMenu.clone().setRealWidth(shopWidth).setRealHeight(shopHeight).setVector(menuX, menuY, 0.F));
    	
			initSlots();
			
			initialized = true;
		}
		
		else {
			
			if (ObjNPC != null)
				frameObj.get("npc").setRealWidth(npcRealWidth).setRealHeight(npcRealHeight).setVector(xPos, yPos, 0.F);
			
		    frameObj.get("menu").setRealWidth(shopWidth).setRealHeight(shopHeight).setVector(menuX, menuY, 0.F);
		    
		    updateSlotPosAndScale();
		}
		
		initButtons();
		initTextFields();
    }
    
    protected abstract void initSlots();
    
    protected void initButtons() {
    	final int ButtonXOffset = 5;
    	final int ButtonYOffset = 3;
    	final int ButtonWidth = 50;
    	final int ButtonHeight = 20;
    	
    	int x = (int) (menuX + shopWidth + ButtonXOffset);
    	int y = (int) (menuY + shopHeight - ButtonHeight - ButtonYOffset);
    	
    	buttonList.add(new GuiButton(0, x, y, ButtonWidth, ButtonHeight, "설정 저장"));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	
    	if (button.id == 0) {
    		List<PortableSlot> list = Lists.newArrayList();
    		
    		for (int i=0; i < slotSize; i++) {
    			GuiTextField field = textFieldList.get(i);
    			GuiSlot slot = Slots[i];
    			
    			if (field == null || slot == null)
    				continue;
    			
    			long prevVal = slot.getSlot().getPrice();
    			long val = Long.parseLong(field.getText());
    			
    			if (prevVal == val)
    				continue;
    			
    			list.add(new PortableSlot(i, val));
    		}
    		
    		if (!list.isEmpty())
    			ProjectGarden.NET_HANDLER.sendToServer(new PacketShop(Action.CHANGE_PRICE, list, Minecraft.getMinecraft().getSession().func_148256_e().getId()));
    	}
    }
    
    protected int slotSize = 12;
    
    protected void initTextFields() {
    	final int XOffset = slotRealWidth + 9;
    	final int YOffset = 17;
    	
    	for (int i=0; i < slotSize; i++) {
    		float[] pos = getSlotPosition(i);
    		
    		textFieldList.add(newTextField(Slots[i].getSlot().getPrice(), (int) pos[0] + XOffset, (int) pos[1] + YOffset, 40, 10));
    	}
    	
    }
        
    private GuiTextField newTextField(long value, int x, int y, int width, int height) {
    	GuiTextField field = new GuiTextField(fontRendererObj, x, y, width, height);
    	
    	field.setText("" + value);
    	field.setCursorPositionEnd();
    	field.setTextColor(0xFFFFFF);
    	field.setDisabledTextColour(0xDBDBDB);
    	field.setEnableBackgroundDrawing(true);
    	
    	return field;
    }
    
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
    	
    	for (int i=0; i < buttonList.size(); i++)
    		((GuiButton) buttonList.get(i)).drawButton(mc, mouseX, mouseY);
    	
    	for (GuiTextField field : textFieldList)
    		field.drawTextBox();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
}
