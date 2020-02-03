package noah.lib.gui.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import noah.lib.gui.ComponentType;
import noah.lib.gui.IWindowComponent;

public class ComponentKeyEvent extends Event {
	
	public final IWindowComponent component;
	public final ComponentType componentType;
	public final char typedChar;
	public final int keyCode;
	
	public ComponentKeyEvent(IWindowComponent component, char typedChar, int keyCode) {
		this.component = component;
		this.componentType = component.getType();
		this.typedChar = typedChar;
		this.keyCode = keyCode;
	}
	
	@Cancelable
	public static class Press extends ComponentKeyEvent {
		
		public Press(IWindowComponent component, char typedChar, int keyCode) {
			super(component, typedChar, keyCode);
		}
	}
}
