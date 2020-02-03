package noah.lib.gui;

public class ComponentState {
	public Mouse mouse = Mouse.NONE;
	public Key key = Key.NONE;
	
	public static enum Mouse {
	    NONE(0), OVER(1), CLICK(2), DRAG(3),
	    RELEASED(-1);
		
		private final int stateid;
		
		Mouse(int state) {
			this.stateid = state;
		}
		
		public int getStateID() {
			return this.stateid;
		}
	}
	
	public static enum Key {
		NONE(0), DOWN(1);
		
		private final int stateid;
		
		Key(int state) {
			this.stateid = state;
		}
		
		public int getStateID() {
			return this.stateid;
		}
	}
}
