package mint.seobaragi.renderer;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;

public class RenderableObject {
	
	private int textureXOffset = 0;
	private int textureYOffset = 0;
	
	private int width = 0;
	private int height = 0;
	
	private ResourceLocation texture;
	
	public RenderableObject(@Nonnull RenderableObject object) {
		this.textureXOffset = object.textureXOffset;
		this.textureYOffset = object.textureYOffset;
		this.width = object.width;
		this.height = object.height;
		this.texture = checkNotNull(object.texture);
	}
	
	public RenderableObject(int width, int height, @Nonnull ResourceLocation texture) {
		this(0, 0, width, height, texture);
	}
	
	public RenderableObject(int textureXOffset, int textureYOffset, int width, int height, @Nonnull ResourceLocation texture) {
		this.textureXOffset = textureXOffset;
		this.textureYOffset = textureYOffset;
		this.width = width;
		this.height = height;
		this.texture = checkNotNull(texture);
	}
	
	public ResourceLocation getTextureLocation() { return texture; }
	
	public int getTextureXOffset() { return textureXOffset; }
	
	public int getTextureYOFfset() { return textureYOffset; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
	public float getUCoord() { return textureXOffset / width; }
	
	public float getVCoord() { return textureYOffset / height; }
	
}
