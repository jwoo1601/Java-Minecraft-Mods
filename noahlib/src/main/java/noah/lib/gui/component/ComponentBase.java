package noah.lib.gui.component;

import javafx.scene.input.MouseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import noah.lib.Main;
import noah.lib.gui.ComponentState;
import noah.lib.gui.ComponentState.Key;
import noah.lib.gui.ComponentState.Mouse;
import noah.lib.gui.ComponentType;
import noah.lib.gui.IWindowComponent;

public abstract class ComponentBase extends Gui implements IWindowComponent {
	
	/**
	 * ID of this GUI.
	 */
	public final int guiID;
	
	/**
	 * If true, Mouse and Key Input Method would be Activated.
	 */
	public boolean isEnabled = true;
	/**
	 * If true, this Component would be drawn.
	 */
	public boolean isVisible = true;
	
	/**
	 * Value about Use of its own Texture.
	 */
	public final boolean useTexture;
	
	/**
	 * Relative X Coordinate on the Window. Top Left Corner of the GuiWindow is (0, 0).
	 */
	public int xCoord;
	/**
	 * Relative Y Coordinate on the Window. Top Left Corner of the GuiWindow is (0, 0).
	 */
	public int yCoord;
	
	/**
	 * Horizontal Length of Component.
	 */
	public int width;
	/**
	 * Vertical Length of Component.
	 */
	public int height;
	
	protected ResourceLocation textureResource;
	protected ComponentState currentState;
	
	protected int currentUCoord = 0;
	protected int currentVCoord = 0;
	
	protected int currentTextureWidth = 0;
	protected int currentTextureHeight = 0;
	
	public ComponentBase(int id, int x, int y, int width, int height, String textureLocation) {
		this(id, true, true, true, x, y, width, height, new ResourceLocation(Main.LIB_ID, textureLocation));
	}
	
	public ComponentBase(int id, boolean useTexture, int x, int y, int width, int height, String textureLocation) {
		this(id, true, true, useTexture, x, y, width, height, new ResourceLocation(Main.LIB_ID, textureLocation));
	}
	
	public ComponentBase(int id, int x, int y, int width, int height, ResourceLocation res) {
		this(id, true, true, true, x, y, width, height, res);
	}
	
	public ComponentBase(int id, boolean useTexture, int x, int y, int width, int height, ResourceLocation res) {
		this(id, true, true, useTexture, x, y, width, height, res);
	}
	
	public ComponentBase(int id, boolean isEnabled, boolean isVisible, boolean useTexture, int x, int y, int width, int height, ResourceLocation res) {
		this.guiID = id;
		this.isEnabled = isEnabled;
		this.isVisible = isVisible;
		this.useTexture = useTexture;
		this.xCoord = x;
		this.yCoord = y;
		this.width = width;
		this.height = height;
		this.textureResource = res;
		this.currentState = new ComponentState();
	}

	@Override
	public void onKeyDown(char typedChar, int keyCode) {
		if (isEnabled)
			this.currentState.key = Key.DOWN;
	}

	@Override
	public void onMouseClicked(Minecraft mc, int mouseX, int mouseY, int clickedButton) {
		if (isEnabled)
			this.currentState.mouse = Mouse.CLICK;
	}

	@Override
	public void onMouseOver(int mouseX, int mouseY) {
		if (isEnabled)
			this.currentState.mouse = Mouse.OVER;
	}

	@Override
	public void onMouseDragged(Minecraft mc, int firstMouseX, int firstMouseY, int lastMouseX, int lastMouseY,
			int clickedButton, long timeSinceLastClick) {
		if (isEnabled)
			this.currentState.mouse = Mouse.DRAG;
	}

	@Override
	public void onMouseReleased(int mouseX, int mouseY, int currentButton) {
		if (isEnabled)
			this.currentState.mouse = Mouse.NONE;
	}

	@Override
	public boolean isComponentMouseInputActivated(Mouse state, Object... args) {
		
		if (isEnabled && args.length >= 2 && args instanceof Integer[]) {
			Integer mx = (Integer) args[0];
			Integer my = (Integer) args[1];
			
			switch (state) {
			case OVER:
				return Mouse.NONE == this.currentState.mouse && mx >= this.xCoord && my >= this.yCoord && mx < this.xCoord + this.width && my < this.yCoord + this.height;
				
			case CLICK:
				if (args.length >= 3) {
					Integer mbtn = (Integer) args[2];
					
					return Mouse.OVER == this.currentState.mouse && noah.lib.gui.event.MouseButton.NONE != mbtn;
				}
				
			case DRAG:
				if (args.length >= 3) {
					Integer mbtn = (Integer) args[2];
					
					return Mouse.CLICK == this.currentState.mouse && noah.lib.gui.event.MouseButton.NONE != mbtn;
				}
			}
		}
	}

}
