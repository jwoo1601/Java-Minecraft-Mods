package jwk.minecraft.garden.client.font;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Glyph {
	
	public static enum GlyphType{ 
		
		NORMAL, 
		COLOR, 
		RANDOM, 
		BOLD, 
		STRIKETHROUGH, 
		UNDERLINE, 
		ITALIC, 
		RESET, 
		OTHER
		
	}
	
	public GlyphType type = GlyphType.NORMAL;
	
	public int x;
	public int y;
	
	public int height;
	public int width;
	
	public int texture;
	
	public int color = -1;
	
}
