package noah.teleport.gui;

public class gVec2i {
	
	public gVec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private int x;
	private int y;
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	@Override
	public boolean equals(Object target) {
		if (target instanceof gVec2i) {
			gVec2i tmp = (gVec2i) target;
			return this.x == tmp.x && this.y == tmp.y;
		}
		
		return false;
	}
}
