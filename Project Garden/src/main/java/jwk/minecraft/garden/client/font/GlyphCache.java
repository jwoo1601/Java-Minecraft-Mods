package jwk.minecraft.garden.client.font;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GlyphCache {
	
	public int width;
	public int height;
	
	public List<Glyph> glyphs = new ArrayList<Glyph>();
	
}
