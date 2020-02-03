package jwk.minecraft.garden.client.renderer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderableObject {

	public final TextureInfo Texture;
	public final int XOffset;
	public final int YOffset;	
	
	public final int TextureWidth;
	public final int TextureHeight;
	
	private int realWidth;
	private int realHeight;
	
	public final Color4f Color;
	
	private Vector3f posVec = new Vector3f(0.f, 0.f, 0.f);
	
	
	public RenderableObject(@Nullable TextureInfo texture, int textureWidth, int textureHeight) {
		this(texture, textureWidth, textureHeight, 0, 0);
	}
	
	public RenderableObject(@Nullable TextureInfo texture, int textureWidth, int textureHeight, @Nonnull Color4f color) {
		this(texture, textureWidth, textureHeight, 0, 0, color);
	}
	
	public RenderableObject(@Nullable TextureInfo texture, int textureWidth, int textureHeight, int realWidth, int realHeight, int xoffset, int yoffset) {
		this(texture, textureWidth, textureHeight, realWidth, realHeight, xoffset, yoffset, new Color4f(1.f, 1.f, 1.f, 1.f));
	}
	
	public RenderableObject(@Nullable TextureInfo texture, int textureWidth, int textureHeight, int xoffset, int yoffset) {
		this(texture, textureWidth, textureHeight, xoffset, yoffset, new Color4f(1.f, 1.f, 1.f, 1.f));
	}
	
	public RenderableObject(@Nullable TextureInfo texture, int textureWidth, int textureHeight, int xoffset, int yoffset, @Nonnull Color4f color) {
		this(texture, textureWidth, textureHeight, textureWidth, textureHeight, xoffset, yoffset, color);
	}
	
	public RenderableObject(@Nullable TextureInfo texture, int textureWidth, int textureHeight, int realWidth, int realHeight, int xoffset, int yoffset, @Nonnull Color4f color) {
		checkArgument(textureWidth > 0 && textureHeight > 0 && realWidth > 0 && realHeight > 0 && xoffset >= 0 && yoffset >= 0);
		
		Texture = texture;
	    TextureWidth = textureWidth;
		TextureHeight = textureHeight;
		this.realWidth = realWidth;
		this.realHeight = realHeight;
		XOffset = xoffset;
		YOffset = yoffset;
		Color = checkNotNull(color);
	}
	
	public RenderableObject setVector(@Nonnull Vector3f vec) {
		posVec = checkNotNull(vec);
		
		return this;
	}
	
	public RenderableObject setVector(float x, float y, float z) {
		posVec.set(x, y, z);
		
		return this;
	}
	
	public Vector3f getVector() {
		return posVec;
	}
	
	public RenderableObject setRealWidth(int value) {
		checkArgument(value > 0);
		
		realWidth = value;
		return this;
	}
	
	public int getRealWidth() { return realWidth; }
	
	public RenderableObject setRealHeight(int value) {
		checkArgument(value > 0);
		
		realHeight = value;
		return this;
	}
	
	public int getRealHeight() { return realHeight; }
	
	@Override
	public RenderableObject clone() {
		return new RenderableObject(Texture, TextureWidth, TextureHeight, realWidth, realHeight, XOffset, YOffset, Color);
	}
	
}
