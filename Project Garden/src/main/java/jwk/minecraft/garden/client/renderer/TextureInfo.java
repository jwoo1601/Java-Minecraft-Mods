package jwk.minecraft.garden.client.renderer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TextureInfo {

	private ResourceLocation textureLocation;
	public final int Width;
	public final int Height;
	
	public TextureInfo(@Nonnull ResourceLocation textureLocation, int width, int height) {
		checkNotNull(textureLocation);
		checkArgument(width > 0 && height > 0);
		
		this.textureLocation = textureLocation;
		Width = width;
		Height = height;
	}
	
	@Override
	public int hashCode() {
		return Width ^ Height + textureLocation.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj != null && obj instanceof TextureInfo) {
			TextureInfo tex = (TextureInfo) obj;
			
			return textureLocation.equals(tex.textureLocation) && Width == tex.Width && Height == tex.Height;
		}
		
		return false;
	}
	
	public boolean isSameTextureWith(@Nonnull TextureInfo texture) {
		return textureLocation.equals(checkNotNull(texture).textureLocation);
	}
	
	@Override
	public TextureInfo clone() {
		return new TextureInfo(textureLocation, Width, Height);
	}
	
	public void setTextureLocation(@Nonnull ResourceLocation location) {
		this.textureLocation = checkNotNull(location);
	}
	
	public ResourceLocation getTextureLocation() { return textureLocation; }
	
}
