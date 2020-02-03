package noah.lib.gui.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import noah.lib.Main;
import noah.lib.gui.Component;
import noah.lib.gui.ComponentState;
import noah.lib.gui.ComponentState.Mouse;
import noah.lib.gui.ComponentType;
import noah.lib.gui.IWindowComponent;
import noah.lib.gui.event.MouseButton;

@Component(priority = Component.Priority.HIGH)
public class GuiButton extends Gui implements IWindowComponent {
	
	static enum Size {
		NORMAL, LARGE
	}
	
	private final int NORMAL_TEXTURE_WIDTH = 200;
    private final int NORMAL_TEXTURE_HEIGHT = 21;
	private final int LARGE_TEXTURE_WIDTH = 200;
    private final int LARGE_TEXTURE_HEIGHT = 30;
    
    protected int texture_width, texture_height;
	protected ResourceLocation resource = new ResourceLocation(Main.LIB_ID, "textures/gui/noah_gui_widget.png");
	
	protected float normal_default_u = 0, normal_default_v = 25;
	protected float normal_hover_u = 0, normal_hover_v = 76;
	protected float normal_click_u = 0, normal_click_v = 127;
	
	protected float large_default_u = 0, large_default_v = 46;
	protected float large_hover_u = 0, large_hover_v = 97;
	protected float large_click_u = 0, large_click_v = 148;
	
	private ComponentState currentstate;	
	private Size btnType;
	
	public boolean isEnabled = true;
	public boolean isVisible = true;
	public int xcoord, ycoord;
	public int width, height;
	
	public String displayString;
	public int stringColor;
	
	public GuiButton(String text, int x, int y, int width, int height) {
		this(text, x, y, width, height, Size.NORMAL, 0xFFFFFF);
	}
	
	public GuiButton(String text, int x, int y, int width, int height, Size type) {
		this(text, x, y, width, height, type, 0xFFFFFF);
	}
	
	public GuiButton(String text, int x, int y, int width, int height, Size type, int stringColor) {
		this.displayString = text;
		this.stringColor = stringColor;
		this.xcoord = x;
		this.ycoord = y;
		this.btnType = type;
		this.setupTextureConfig();
		this.width = width;
		this.height = height;
		this.currentstate = new ComponentState();
	}
	
	private void setupTextureConfig() {
		if (btnType == Size.NORMAL) {
			this.texture_width = NORMAL_TEXTURE_WIDTH;
			this.texture_height = NORMAL_TEXTURE_HEIGHT;
		}
		else if (btnType == Size.LARGE) {
			this.texture_width = LARGE_TEXTURE_WIDTH;
			this.texture_height = LARGE_TEXTURE_HEIGHT;
		}
		else
			throw new NullPointerException("Button Texture Type Cannot be Null!");
	}
	
	public Size getButtonType() {
		if (this.btnType == Size.NORMAL)
			return Size.NORMAL;
		
		return Size.LARGE;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.BUTTON;
	}

	@Override
	public void drawComponent(Minecraft mc) {
		if (this.isVisible) {
			FontRenderer fr = mc.fontRendererObj;
			mc.getTextureManager().bindTexture(resource);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            
            if (btnType == Size.NORMAL) {
            	
            	if (currentstate.mouse == ComponentState.Mouse.OVER)
            		this.drawModalRectWithCustomSizedTexture(xcoord, ycoord, normal_hover_u, normal_hover_v, width, height, texture_width, texture_height);
            	else if (currentstate.mouse == ComponentState.Mouse.CLICK)
            		this.drawModalRectWithCustomSizedTexture(xcoord, ycoord, normal_click_u, normal_click_v, width, height, texture_width, texture_height);
            	else
            		this.drawModalRectWithCustomSizedTexture(xcoord, ycoord, normal_default_u, normal_default_v, width, height, texture_width, texture_height);
            }            
            
            else if (btnType == Size.LARGE) {
            	
            	if (currentstate.mouse == ComponentState.Mouse.OVER)
            		this.drawModalRectWithCustomSizedTexture(xcoord, ycoord, large_hover_u, large_hover_v, width, height, texture_width, texture_height);
            	else if (currentstate.mouse == ComponentState.Mouse.CLICK)
            		this.drawModalRectWithCustomSizedTexture(xcoord, ycoord, large_click_u, large_click_v, width, height, texture_width, texture_height);
            	else
            		this.drawModalRectWithCustomSizedTexture(xcoord, ycoord, large_default_u, large_default_v, width, height, texture_width, texture_height);
            }      
            
            this.drawCenteredString(fr, displayString, xcoord, ycoord, stringColor);
		}
	}
	
	@Override
	public void onKeyDown(char typedChar, int keyCode) {
		if (this.isEnabled) {
			this.currentstate.key = ComponentState.Key.DOWN;
		}
	}

	@Override
	public void onMouseClicked(Minecraft mc, int mouseX, int mouseY, int clickedButton) {
		if (this.isEnabled) {
			this.currentstate.mouse = ComponentState.Mouse.CLICK;
		}
	}

	@Override
	public void onMouseOver(int mouseX, int mouseY) {
		if (this.isEnabled) {
			this.currentstate.mouse = ComponentState.Mouse.OVER;
		}
	}

	@Override
	public void onMouseDragged(Minecraft mc, int firstMouseX, int firstMouseY, int lastMouseX, int lastMouseY, int clickedButton, long timeSinceLastClick) {
		if (this.isEnabled) {
			this.currentstate.mouse = ComponentState.Mouse.DRAG;
		}
	}

	@Override
	public void onMouseReleased(int mouseX, int mouseY, int currentButton) {
		if (this.isEnabled) {
			this.currentstate.mouse = ComponentState.Mouse.RELEASE;
		}
	}

	@Override
	public boolean isComponentMouseInputActivated(Mouse state, Object... args) {
		
		switch(state) {
		case CLICK:
			if(this.isEnabled && args.length == 3 && args instanceof Integer[]) {
				Integer mx = (Integer) args[0];
				Integer my = (Integer) args[1];
				Integer mbtn = (Integer) args[2];
				return mbtn == MouseButton.LEFT && mx >= this.xcoord && my >= this.ycoord && mx < this.xcoord + this.width && my < this.ycoord + this.height;
			}
			
			return false;
			
		case DRAG:
			if(this.isEnabled && args.length == 2 && args instanceof Integer[]) {
				Integer mx = (Integer) args[0];
				Integer my = (Integer) args[1];
				return mx >= this.xcoord && my >= this.ycoord && mx < this.xcoord + this.width && my < this.ycoord + this.height;
			}
			return false;
			
		case OVER:
			if (this.isEnabled && args.length == 4 && args instanceof Integer[]) {
				Integer fmx = (Integer) args[0];
				Integer fmy = (Integer) args[1];
				return fmx >= this.xcoord && fmy >= this.ycoord && mx < this.xcoord + this.width && fmy < this.ycoord + this.height;
			}
			return false;
			
		case RELEASE:
			return false;
			
		default:
			return false;
		}
	}
}
