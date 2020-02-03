package noah.teleport.util;

import noah.teleport.gui.gVec2i;

public class TextureInfo {
	
	public TextureInfo(float u, float v, int width, int height) {
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
	}
	
	private float u;
	private float v;
	
	private int width;
	private int height;
	
	public float getUCoordinate() {
		return this.u;
	}
	
	public float getVCoordinate() {
		return this.v;
	}
	
	public float getMaxU() {
		return this.u + this.width;
	}
	
	public float getMaxV() {
		return this.v + this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public boolean equals(Object target) {
		if (target instanceof TextureInfo) {
			TextureInfo tmp = (TextureInfo) target;
			return this.u == tmp.u && this.v == tmp.v && this.width == tmp.width && this.height == tmp.height;
		}
		
		return false;
	}
}
