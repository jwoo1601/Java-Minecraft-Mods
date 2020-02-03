package noah.teleport.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.teleport.Main;
import noah.teleport.block.TeleportData;
import noah.teleport.network.PlaceDataChangedPacket;
import noah.teleport.network.TeleportPacketHandler;
import noah.teleport.place.Place;
import noah.teleport.util.Common;
import noah.teleport.util.ILocalizable;
import noah.teleport.util.TextureInfo;

@SideOnly(Side.CLIENT)
public class GuiTeleport extends GuiScreen {
	
	public static final ResourceLocation RES = new ResourceLocation(Common.MOD_ID, "textures/gui/teleport_gui.png");
	
	public static final TextureInfo Background = new TextureInfo(0, 0, 230, 219);
	public static final TextureInfo UpperTexture = new TextureInfo(60, 3, 10, 10);
	public static final TextureInfo ScrollBack = new TextureInfo(230, 0, 8, 76);
	public static final TextureInfo ScrollBar = new TextureInfo(238, 0, 6, 76);
	public static final TextureInfo ScrollUpArrow = new TextureInfo(230, 76, 8, 7);
	public static final TextureInfo ScrollDownArrow = new TextureInfo(230, 83, 8, 7);
	public static final TextureInfo ItemBox = new TextureInfo(0, 219, 30, 22);
	public static final TextureInfo SelectionBox = new TextureInfo(30, 219, 30, 22);
	public static final TextureInfo ItemNull = new TextureInfo(60, 219, 27, 19);
	
	public static final int HOffset = 5;
	public static final int VOffset = 7;
	public static final int InfoWidth = 43;
	public static final int InfoHeight = 206;
	// 폰트 높이
	public final int fontHeight;
	// 각 컴포턴트 간 간격
	public static final int ComponentOffset = 2;
	public static final int ComponentLOffset = 7;
	
//	public static final gVec2i SaveButtonStart = new gVec2i(3, 187);
	public static final gVec2i SaveButtonStart = new gVec2i(184, 220);
	public static final int SaveButtonWidth = 46;
	public static final int SaveButtonHeight = 20;
	
	public static final gVec2i BoxStart = new gVec2i(52, 7);
//	public static final gVec2i Box2Start = new gVec2i(136, 12);
	
	public static final gVec2i ScrollUpArrowStart = new gVec2i(216, 4);
	public static final gVec2i ScrollBackStart = new gVec2i(216, 12);
	public static final gVec2i ScrollBarStart = new gVec2i(217, 12);
	public static final gVec2i ScrollDownArrowStart = new gVec2i(216, 208);
	public static final int ScrollMaxLength = 202;
	
	public static final gVec2i ItemSideEnd = new gVec2i(212, 212);
	
	// 위 스타트 좌표들은 gui 상대좌표. 따라서 실제 적용할땐 x와 y값을 각각 더해서 해야됨
	
	public int x;
	public int y;
	
	public final TeleportData currentData;
	
	private static final String labelPlaceID = "ID";
	private static final String labelOwnPlace = StatCollector.translateToLocal("gui.teleport.labelown");
	private static final String labelLinkedPlace = StatCollector.translateToLocal("gui.teleport.labellinked");
	private static final String labelPlaceName = StatCollector.translateToLocal("gui.teleport.labelplacename");
	private static final String labelPlacePos = StatCollector.translateToLocal("gui.teleport.labelplacepos");
	private static final String labelNoLinkedPlace = StatCollector.translateToLocal("gui.teleport.nolinkedplace");
	private static final String labelSaveButton = StatCollector.translateToLocal("gui.teleport.labelsave");
	
	private final int ORANGE = 0xFF5E00;
	private final int YELLOW = 0xFFE400;
	private final int BOXGRAY = 0xCC5D5D5D;
	
	private GuiTextField O_PNameTextBox;
	private boolean isOPNameTextChanged = false;
	
	private guiSelectionBox[] Items;
	private guiSelectionBox prevBox = null;
	private guiSelectionBox selectedBox = null;
	
	private ArrayList<Place> shownPlaces;
	
	public GuiTeleport(TeleportData data) {
		this.currentData = data;
		this.fontHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
		
		if (!Main.Proxy.FakePlaceReg.isEmpty()) {
			this.shownPlaces = new ArrayList<Place>();
			this.shownPlaces.addAll(Main.Proxy.FakePlaceReg.getAllPlaces());
			
			for (int i=0; i<shownPlaces.size(); i++) {
				if (shownPlaces.get(i).equals(this.currentData.getOwnPlace())) {
					shownPlaces.remove(i);
					shownPlaces.trimToSize();
				}
			}
		}
		
		Items = new guiSelectionBox[this.shownPlaces.size() + 1];
	}
	
	@Override
	protected void actionPerformed(GuiButton btn) {
		switch (btn.id) {
		case 0:
			
			if (this.selectedBox != null && selectedBox.targetPlace != this.currentData.getLinkedPlace()) {
				this.currentData.setLinkedPlace(selectedBox.targetPlace);
				
				if (selectedBox.targetPlace == null) {
					TeleportPacketHandler.INSTANCE.sendToServer(new PlaceDataChangedPacket(this.currentData.getWorld().provider.getDimensionId(), 
							                                                               this.currentData.getOwnPlaceID(), 
							                                                               PlaceDataChangedPacket.LINKEDPLACENULL, null));
				}
				else {
					TeleportPacketHandler.INSTANCE.sendToServer(new PlaceDataChangedPacket(this.currentData.getWorld().provider.getDimensionId(),
							                                                               this.currentData.getOwnPlaceID(), 
							                                                               PlaceDataChangedPacket.LINKEDPLACE, selectedBox.targetPlace.getID()));
				}
			}
			
			String changed = this.O_PNameTextBox.getText();
			
			if (this.isOPNameTextChanged && this.currentData.getOwnPlace().getName() != changed) {
				this.currentData.setOwnPlaceName(changed);
				TeleportPacketHandler.INSTANCE.sendToServer(new PlaceDataChangedPacket(this.currentData.getWorld().provider.getDimensionId(),
						                                                               this.currentData.getOwnPlaceID(),
						                                                               PlaceDataChangedPacket.OWNPLACENAME, changed));
				}
			
			break;
		default:
			if (btn instanceof guiSelectionBox) {
				if (this.prevBox == null && this.selectedBox == null) {
					this.prevBox = (guiSelectionBox) btn;
					this.selectedBox = prevBox;
					this.selectedBox.isSelected = true;
					break;
				}
				
				this.prevBox.isSelected = false;
			    this.prevBox = this.selectedBox;
				this.selectedBox = (guiSelectionBox) btn;
				this.selectedBox.isSelected = true;
			}
			break;
		}
	}
	
	@Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		if (this.O_PNameTextBox.textboxKeyTyped(typedChar, keyCode))
			this.isOPNameTextChanged = true;
		else {
			super.keyTyped(typedChar, keyCode);
		}
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.O_PNameTextBox.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawDefaultBackground();
    	
    	this.mc.getTextureManager().bindTexture(RES);
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	this.drawTexturedModalRect(x, y, (int)Background.getUCoordinate(), (int)Background.getVCoordinate(), Background.getWidth(), Background.getHeight());
    	
    	this.drawInformation();
    	
    	if (this.selectedBox != null)
    		this.drawRect(selectedBox.xPosition - 5, selectedBox.yPosition - 5, 
    				      selectedBox.xPosition + selectedBox.width + 5, selectedBox.yPosition + selectedBox.height + 5, 0x0054FF);
    	
    	super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	protected void drawInformation() {		
		int cx = 0, cy = 0, cx2 = 0, cy2 = 0;
		int i = fontHeight + ComponentOffset;
		int j = fontHeight + ComponentLOffset;
		
		cx = x + HOffset; cy = y + VOffset;
		this.drawString(this.fontRendererObj, labelPlaceID, cx, cy, ORANGE);
		
		cy += i; cx2 = cx + InfoWidth - 3; cy2 = cy + fontHeight;
		this.drawRect(cx, cy, cx2, cy2, BOXGRAY);
		cx += ComponentOffset * 3;
		this.drawCenteredString(this.fontRendererObj, "" + this.currentData.getOwnPlaceID(), cx, cy, 0xFFFFFF);
		
		cx -= ComponentOffset * 3; cy += j;
		this.drawString(this.fontRendererObj, labelOwnPlace, cx, cy, ORANGE);
		
		cx += ComponentOffset; cy += i;
		this.drawString(this.fontRendererObj, labelPlaceName, cx, cy, 0x000000);
		
		cy += i;
		this.O_PNameTextBox.xPosition = cx; this.O_PNameTextBox.yPosition = cy;
		this.O_PNameTextBox.drawTextBox();
		
		cy += i;
		this.drawString(this.fontRendererObj, labelPlacePos, cx, cy, 0x000000);
		
		cx += ComponentOffset; cy += i;
		this.drawString(this.fontRendererObj, "X: " + this.currentData.getOwnPlace().getX(), cx, cy, 0x000000);
		cy += i;
		this.drawString(this.fontRendererObj, "Y: " + this.currentData.getOwnPlace().getY(), cx, cy, 0x000000);
		cy += i;
		this.drawString(this.fontRendererObj, "Z: " + this.currentData.getOwnPlace().getZ(), cx, cy, 0x000000);
		
		cx -= ComponentOffset * 2; cy += j;
		this.drawString(this.fontRendererObj, labelLinkedPlace, cx, cy, ORANGE);
		
		cx += ComponentOffset; cy += i;
		if (this.currentData.getLinkedPlace() == null)
			this.drawString(this.fontRendererObj, labelNoLinkedPlace, cx, cy, YELLOW);
		else {
			this.drawString(this.fontRendererObj, labelPlaceID, cx, cy, ORANGE);
			
			cy += i; cx2 = cx + InfoWidth - 3; cy2 = cy + fontHeight;
			this.drawRect(cx, cy, cx2, cy2, BOXGRAY);
			cx += ComponentOffset * 3;
			this.drawCenteredString(this.fontRendererObj, "" + this.currentData.getLinkedPlace().getID(), cx, cy, 0xFFFFFF);
			
			cx -= ComponentOffset * 3; cy += i;
			this.drawString(this.fontRendererObj, labelPlaceName, cx, cy, 0x000000);
			
			cy += i; cx2 = cx + InfoWidth - 3; cy2 = cy + fontHeight;
			this.drawRect(cx, cy, cx2, cy2, BOXGRAY);
			cx += ComponentOffset * 3;
			this.drawString(this.fontRendererObj, this.currentData.getLinkedPlace().getName(), cx, cy,0xFFFFFF);
			
			cx -= ComponentOffset * 3; cy += i;
			this.drawString(this.fontRendererObj, labelPlacePos, cx, cy, 0x000000);
			
			cy += i;
			this.drawString(this.fontRendererObj, "X: " + this.currentData.getLinkedPlace().getX(), cx, cy, 0x000000);
			cy += i;
			this.drawString(this.fontRendererObj, "Y: " + this.currentData.getLinkedPlace().getY(), cx, cy, 0x000000);
			cy += i;
			this.drawString(this.fontRendererObj, "Z: " + this.currentData.getLinkedPlace().getZ(), cx, cy, 0x000000);
		}
	}
	
	private GuiButton saveButton;
	
	@Override
	public void initGui() {
		this.x = (this.width - this.Background.getWidth()) / 2;
		this.y = (this.height - this.Background.getHeight()) / 2;
		Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        
        this.O_PNameTextBox = new GuiTextField(-1, fontRendererObj, 0, 0, InfoWidth - ComponentOffset, fontHeight);        
        this.O_PNameTextBox.setText(this.currentData.getOwnPlace().getName());
        this.O_PNameTextBox.setCursorPositionEnd();
        this.O_PNameTextBox.setTextColor(0xF6F6F6);
        this.O_PNameTextBox.setDisabledTextColour(0x8C8C8C);
        this.O_PNameTextBox.setEnableBackgroundDrawing(true);
        this.O_PNameTextBox.setMaxStringLength(5);
        
        this.saveButton = new GuiButton(0, x + SaveButtonStart.getX(), y + SaveButtonStart.getY(), SaveButtonWidth, SaveButtonHeight, labelSaveButton);
        this.buttonList.add(saveButton);
    	this.Items[0] = new guiSelectionBox(1, x + BoxStart.getX(), y + BoxStart.getY(), null);
		this.buttonList.add(Items[0]);
		
		
		for (int i=1; i<Items.length; i++) {
			this.Items[i] = new guiSelectionBox(i + 1, x + BoxStart.getX(), y + BoxStart.getY() + guiSelectionBox.BoxHeight * i, this.shownPlaces.get(i - 1));
			this.buttonList.add(Items[i]);
			
			if (i > 19)
				this.Items[i].visible = false;
			}
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	
	static class guiSelectionBox extends GuiButton {
		
		private static int DefaultColor = 0xFFBDBDBD;
		private static int SelectedColor = 0xFF353535;
		
		private static int FSkyBlueColor = 0x3DB7CC;
		private static int FWhiteColor = 0xFFFFFF;
		
		private static final String LabelNotSelected = "(" +StatCollector.translateToLocal("gui.teleport.labelnotselected") + ")";
		
		public static final int BoxWidth = 165;
		public static final int BoxHeight = 10;
		
		public final Place targetPlace;
		public final int width;
		public final int height;
		
		public boolean isSelected = false;
		
		@Deprecated
		public String displayString = null;
		@Deprecated
		public int packedFGColour = 0;
		
		public guiSelectionBox(int boxID, int x, int y, Place target) {
			super(boxID, x, y, BoxWidth, BoxHeight, null);
			this.width = BoxWidth;
			this.height = BoxHeight;
			this.targetPlace = target;
		}
		
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY)
	    {
	        if (this.visible)
	        {
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            this.hovered = this.enabled && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	            
	            GlStateManager.enableBlend();
	            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	            GlStateManager.blendFunc(770, 771); 
	            
	            if (!hovered && !isSelected)
	            	this.drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, DefaultColor);            
	            else
	            	this.drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, SelectedColor);
	            
	            this.drawPlaceData(mc); 

	            this.mouseDragged(mc, mouseX, mouseY);
	            }
	    }
		
		protected void drawPlaceData(Minecraft mc) {
			int cx = this.xPosition, cy = this.yPosition;
			
			if (this.targetPlace == null)
				this.drawString(mc.fontRendererObj, LabelNotSelected, cx, cy, FWhiteColor);
			
			else {
				cx += ComponentOffset;				
				this.drawString(mc.fontRendererObj, labelPlaceID + ":", cx, cy, FSkyBlueColor);
				
				cx += mc.fontRendererObj.getStringWidth(labelPlaceID + ": ");
				this.drawString(mc.fontRendererObj, "" + this.targetPlace.getID(), cx, cy, FWhiteColor);
				
				cx += mc.fontRendererObj.getStringWidth(this.targetPlace.getID() + " ");
				this.drawString(mc.fontRendererObj, labelPlaceName + ":", cx, cy, FSkyBlueColor);
				
				cx += mc.fontRendererObj.getStringWidth(labelPlaceName + ": ");
				this.drawString(mc.fontRendererObj, this.targetPlace.getName(), cx, cy, FWhiteColor);
				
				cx += mc.fontRendererObj.getStringWidth(this.targetPlace.getName() + " ");
				this.drawString(mc.fontRendererObj, labelPlacePos + ">", cx, cy, FSkyBlueColor);
				
				cx += mc.fontRendererObj.getStringWidth(labelPlacePos + "> ");
				this.drawString(mc.fontRendererObj, "X: " + this.targetPlace.getX() +
				                                    " Y: " + this.targetPlace.getY() +
				                                    " Z: " + this.targetPlace.getZ(), cx, cy, FWhiteColor);
			}
		}
		
		@Override
	    public int getButtonWidth() {
	        return this.width;
	    }
		
		public int getButtonHeight() {
			return this.height;
		}
		
		@Override @Deprecated
	    public void setWidth(int width) {}
		
	}
}
