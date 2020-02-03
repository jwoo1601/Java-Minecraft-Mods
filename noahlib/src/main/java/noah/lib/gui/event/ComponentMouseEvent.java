package noah.lib.gui.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import noah.lib.gui.ComponentType;
import noah.lib.gui.IWindowComponent;

public class ComponentMouseEvent extends Event {
	
	public final IWindowComponent component;
	public final ComponentType componentType;
	public final int mouseX;
	public final int mouseY;
	
	public ComponentMouseEvent(IWindowComponent component, int mX, int mY) {
		this.component = component;
		this.componentType = component.getType();
		this.mouseX = mX;
		this.mouseY = mY;
	}
	
	@Cancelable
	public static class Click extends ComponentMouseEvent {
		public final int clickedButton;
		
		public Click(IWindowComponent component,int mX, int mY, int clickedButton) {
			super(component, mX, mY);
			this.clickedButton = clickedButton;
		}
	}
	
	@Cancelable
	public static class Over extends ComponentMouseEvent {
		
		public Over(IWindowComponent component,int mX, int mY) {
			super(component, mX, mY);
		}
	}
	
	@Cancelable
	public static class Drag extends ComponentMouseEvent {
		
		@Deprecated
		public final int mouseX = 0;
		@Deprecated
		public final int mouseY = 0;
		
		public final int firstMouseX;
		public final int firstMouseY;		
		public final int lastMouseX;
		public final int lastMouseY;
		
		public final int clickedButton;
		
		public final long timeSinceLastClick;
		
		public Drag(IWindowComponent component, int firstMouseX, int firstMouseY, int lastMouseX, int lastMouseY, int clickedButton, long timeSinceLastClick) {
			super(component, 0, 0);
			this.firstMouseX = firstMouseX;
			this.firstMouseY = firstMouseY;
			this.lastMouseX = lastMouseX;
			this.lastMouseY = lastMouseY;
			this.clickedButton = clickedButton;
			this.timeSinceLastClick = timeSinceLastClick;
		}
	}
	
	@Cancelable
	public static class Release extends ComponentMouseEvent {
		
		public final int currentButton;
		
		public Release(IWindowComponent component, int mX, int mY, int currentButton) {
			super(component, mX, mY);
			this.currentButton = currentButton;
		}
	}
}
