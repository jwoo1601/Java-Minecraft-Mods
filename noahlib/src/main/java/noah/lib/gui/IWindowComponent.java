package noah.lib.gui;

import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public interface IWindowComponent {
	
	ComponentType getType();
	
	void drawComponent(Minecraft mc);
	
	void onKeyDown(char typedChar, int keyCode);	
	void onMouseClicked(Minecraft mc, int mouseX, int mouseY, int clickedButton);
	void onMouseOver(int mouseX, int mouseY);
	void onMouseDragged(Minecraft mc, int firstMouseX, int firstMouseY, int lastMouseX, int lastMouseY, int clickedButton, long timeSinceLastClick);
	void onMouseReleased(int mouseX, int mouseY, int currentButton);
	
	boolean isComponentMouseInputActivated(ComponentState.Mouse state, Object... args);
}
