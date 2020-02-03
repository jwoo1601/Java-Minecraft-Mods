package jwk.minecraft.fogmanager.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Color4f {
	
	private float red = 0.f,
			      green = 0.f,
			      blue = 0.f,
			      alpha = 0.f;
	
	private static final float INTERPOLATION_VALUE = 0.00390625f;
	
	public Color4f(Color4f color) {
		this(color.red, color.blue, color.green, color.alpha);
	}
	
	public Color4f(int red, int green, int blue) {
		this (red, green, blue, 0);
	}
	
	public Color4f(int red, int green, int blue, int alpha) {
		if (red < 0 || red > 255)
			throw new IllegalArgumentException("red");
		else if (green < 0 || green > 255)
			throw new IllegalArgumentException("green");
		else if (blue < 0 || blue > 255)
			throw new IllegalArgumentException("blue");
		else if (alpha < 0 || alpha > 255)
			throw new IllegalArgumentException("alpha");
		
		this.red = clampf(red);
		this.green = clampf(green);
		this.blue = clampf(blue);
		this.alpha = clampf(alpha);
	}
	
	public Color4f(float red, float green, float blue) {
		this(red, green, blue, 0.f);
	}
	
	public Color4f(float red, float green, float blue, float alpha) {
		if (red < 0.f || red > 1.f)
			throw new IllegalArgumentException("red");
		else if (green < 0.f || green > 1.f)
			throw new IllegalArgumentException("green");
		else if (blue < 0.f || blue > 1.f)
			throw new IllegalArgumentException("blue");
		else if (alpha < 0.f || alpha > 1.f)
			throw new IllegalArgumentException("alpha");
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color4f setRed(float value) {
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		
		this.red = value;
		return this;
	}
	
	public float getRed() { return red; }
	
	public Color4f setGreen(float value) {
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		
		this.green = value;
		return this;
	}
	
	public float getGreen() { return green; }
	
	public Color4f setBlue(float value) {
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		
		this.blue = value;
		return this;
	}
	
	public float getBlue() { return blue; }
	
	public Color4f setAlpha(float value) {
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		
		this.alpha = value;
		return this;
	}
	
	public float getAlpha() { return alpha; }
	
	public Color4f addRed(float value) {
		float result = red + value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.red = result;
		return this;
	}
	
	public Color4f addGreen(float value) {
		float result = green + value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.green = result;
		return this;
	}
	
	public Color4f addBlue(float value) {
		float result = blue + value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.blue = result;
		return this;
	}
	
	public Color4f addAlpha(float value) {
		float result = alpha + value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.alpha = result;
		return this;
	}
	
	public Color4f subRed(float value) {
		float result = red - value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.red = result;
		return this;
	}
	
	public Color4f subBlue(float value) {
		float result = blue - value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.blue = result;
		return this;
	}
	
	public Color4f subGreen(float value) {
		float result = green - value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.green = result;
		return this;
	}
	
	public Color4f subAlpha(float value) {
		float result = alpha - value;
		
		if (value < 0.f || value > 1.f)
			throw new IllegalArgumentException("value");
		else if (result < 0.f || result > 1.f)
			throw new IllegalArgumentException("value");
		
		this.alpha = result;
		return this;
	}
	
	public float[] toFloatArray() {
		return new float[] { red, green, blue, alpha };
	}
	
	public static Color4f fromFloatArray(float[] values) {
		checkNotNull(values);
		checkArgument(values.length == 4);
		
		return new Color4f(values[0], values[1], values[2], values[3]);
	}
	
	public static float clampf(int value) {
		if (value == 0)
			return 0;
		
		return value * INTERPOLATION_VALUE;
	}
}
