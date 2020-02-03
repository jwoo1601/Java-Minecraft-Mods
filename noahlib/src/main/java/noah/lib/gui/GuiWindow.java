package noah.lib.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import noah.lib.Main;

public class GuiWindow extends GuiScreen {
	
	public static final ResourceLocation RESOURCE = new ResourceLocation(Main.LIB_ID, "textures/gui/noah_gui_window.png");
	
	private final float Background_U = 0.0f, Background_V = 0.0f;
	private final int Background_Width = 170, Background_Height = 181;
	
	private final float TitleBar_U = 0.0f, TitleBar_V = 181.0f;
	private final int TitleBar_Width = 170, TitleBar_Height = 10;
	
	private final int Button_Width = 11, Button_Height = 10;
	private final float ButtonEsc_Default_U = 0.0f, ButtonEsc_Default_V = 191.0f;
	private final float ButtonEsc_Hover_U = 0.0f, ButtonEsc_Hover_V = 201.0f;
	private final float ButtonEsc_Click_U = 0.0f, ButtonEsc_Click_V = 211.0f;
	
	private final float ButtonMax_Default_U = 11.0f, ButtonMax_Default_V = 191.0f;
	private final float ButtonMax_Hover_U = 11.0f, ButtonMax_Hover_V = 201.0f;
	private final float ButtonMax_Click_U = 11.0f, ButtonMax_Click_V = 211.0f;
	
	private final float ButtonMin_Default_U = 22.0f, ButtonMin_Default_V = 191.0f;
	private final float ButtonMin_Hover_U = 22.0f, ButtonMin_Hover_V = 201.0f;
	private final float ButtonMin_Click_U = 22.0f, ButtonMin_Click_V = 211.0f;
	
	/**
	 * This Field is not used in the GuiWindow
	 */
	@Deprecated
    protected List<GuiButton> buttonList;	
	/**
	 * This Field is not used in the GuiWindow
	 */
	@Deprecated
    protected List<GuiLabel> labelList;
	
	// Window Property
	public boolean isEnabled = true;
	public boolean isVisible = true;
	
	// Window Side Button Property
	public boolean hasESC = true;
	public boolean hasMaximize = true;
	public boolean hasMinimize = true;
	
	private String title;	
	private int window_x, window_y;
	private int window_width, window_height;
	private float window_h_scale = 1.0f, window_v_scale = 1.0f;
	protected ArrayList<IWindowComponent> components;
	private IWindowComponent currentComponent;
	// 초기에는 다 1부터 시작. 개수 총 3개 - HIGH, NORMAL, LOW
	protected int[] priorityCount;
	
	private int eventMouseButton;
	private long lastMouseEvent;	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawWindow();
		drawComponents();
	}
	
	protected void drawWindow() {
		
		if (isVisible) {
			this.drawDefaultBackground();
			this.mc.getTextureManager().bindTexture(RESOURCE);
			// Draw Background of Window
			this.drawModalRectWithCustomSizedTexture(window_x, window_y, 
					                                 Background_U, Background_V, 
				                                     Background_Width, Button_Height, 
				                                     Background_Width * window_h_scale, Background_Height * window_v_scale);
			// Draw TitleBar of Window
			this.drawModalRectWithCustomSizedTexture(window_x, window_y, 
					                                 TitleBar_U, TitleBar_V, 
                                                     TitleBar_Width, TitleBar_Height, 
                                                     TitleBar_Width * window_h_scale, TitleBar_Height * window_v_scale);
			
			// Draw Title on the TitleBar with Shadow
			this.drawCenteredString(this.fontRendererObj, title, window_x, window_y, 0xFFFFFF);
			this.drawCenteredString(this.fontRendererObj, title, window_x + 1, window_y + 1, 0x000000);
			
			if (hasESC);
			
			if (hasMaximize);
			
			if (hasMinimize);
		}
	}
	
	protected void drawComponents() {
		for (IWindowComponent component : components)
			component.drawComponent(this.mc);
	}
	
	public GuiWindow addComponent(IWindowComponent target) {
		int index = target.getClass().getAnnotation(Component.class).priority().getIndex();	
				
		this.components.add(priorityCount[index], target);
		return this;
	}
	
	@Override
	public void handleMouseInput() throws IOException
    {
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int k = Mouse.getEventButton();

        if (Mouse.getEventButtonState())
        {
            if (this.mc.gameSettings.touchscreen)
            	return;

            this.eventMouseButton = k;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(i, j, this.eventMouseButton);
        }
        else if (k != -1)
        {
            if (this.mc.gameSettings.touchscreen && --this.touchValue > 0)
            {
                return;
            }

            this.eventButton = -1;
            this.mouseReleased(i, j, k);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L)
        {
            long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(i, j, this.eventButton, l);
        }
        Mouse.getx
    }
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int clickedButton) throws IOException
    {
		
		if (this.equals(this.mc.currentScreen)) {
			for (IWindowComponent component : components) {
				if (component.isComponentMouseInputActivated(ComponentState.Mouse.CLICK, mouseX, mouseY)) {
					this.currentComponent = component;
					component.onMouseClicked(this.mc, mouseX, mouseY, clickedButton);
				}
			}
		}
    }

}
