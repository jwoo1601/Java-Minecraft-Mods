package jwk.minecraft.garden.client.gui;

public enum MouseButton {

	LEFT(0),
	RIGHT(1),
	NONE(-1);
	
	public final int Value;
	
	private MouseButton(int value) {
		Value = value;
	}
	
	public static final MouseButton fromInt(int button) {
		
		switch (button) {
		
		case 0:
			return LEFT;
				
		case 1:
			return RIGHT;
				
		case -1:
			return NONE;
			
		default:
			return null;
		}
	}
	
}
