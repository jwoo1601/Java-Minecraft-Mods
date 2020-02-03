package mint.seobaragi.renderer;

import static library.Location.SEONENG_FONT;
import static library.Reference.INFO_EIGHT;
import static library.Reference.INFO_FIVE;
import static library.Reference.INFO_FOUR;
import static library.Reference.INFO_NINE;
import static library.Reference.INFO_ONE;
import static library.Reference.INFO_SEVEN;
import static library.Reference.INFO_SIX;
import static library.Reference.INFO_THREE;
import static library.Reference.INFO_TWO;
import static library.Reference.INFO_ZERO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;

public class RenderNumber extends Gui
{
	//Hoffset >= 19
	private static final int Hoffset = 2;
	
	public static void drawNumber(long value, int x, int y, float scale)
	{
		//"123"
		String target = String.valueOf(value);
		Minecraft.getMinecraft().getTextureManager().bindTexture(SEONENG_FONT);
		
		int[] prevWidthArray = new int[target.length()];
		for(int i=0; i < target.length(); i++)
		{
			TextureInfo t = convertToTextureInfo(target.charAt(i));
			
			float w = t.getWidth() * 0.00390625F;
			float h = t.getHeight() * 0.00390625F;
			int currentOffset = 0;
			
			for(int j=0; j < i; j++)
				currentOffset += (prevWidthArray[j] + Hoffset);
			
			int scaledWidth = (int) (t.getWidth() + scale);
			int scaledHeight = (int) (t.getHeight() * scale);
			
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + currentOffset,					y + scaledHeight,	0.0D, t.getUCoord(),		 t.getVCoord() + h);
			tessellator.addVertexWithUV(x + currentOffset + scaledWidth,	y + scaledHeight,	0.0D, t.getUCoord() + w,	 t.getVCoord() + h);
			tessellator.addVertexWithUV(x + currentOffset + scaledWidth,	y,					0.0D, t.getUCoord() + w,	 t.getVCoord());
			tessellator.addVertexWithUV(x + currentOffset,					y,					0.0D, t.getUCoord(),		 t.getVCoord());
			tessellator.draw();
			
			prevWidthArray[i] = scaledWidth;
		}
	}
	
	
	public static TextureInfo convertToTextureInfo(char value)
	{
		switch(value)
		{
		case '0':
			return INFO_ZERO;
			
		case '1':
			return INFO_ONE;
			
		case '2':
			return INFO_TWO;
			
		case '3':
			return INFO_THREE;
			
		case '4':
			return INFO_FOUR;
			
		case '5':
			return INFO_FIVE;
			
		case '6':
			return INFO_SIX;
			
		case '7':
			return INFO_SEVEN;
			
		case '8':
			return INFO_EIGHT;
			
		case '9':
			return INFO_NINE;
			
			default:
				throw new IllegalArgumentException("Not Search Number");
		}
	}
	
	
// ==================== TextureInfo Class ====================	
	public static class TextureInfo
	{
		private float u;
		private float v;
		private int width;
		private int height;
		
		public TextureInfo(float u, float v, int width, int height)
		{
			this.u = u;
			this.v = v;
			this.width = width;
			this.height = height;
		}
		
		public float getUCoord() { return this.u; }
		
		public TextureInfo setUCoord(float value) { this.u = value; return this; }
		
		public float getVCoord() { return this.v; }
		
		public TextureInfo setVCoord(float value) { this.v = value; return this; }
		
		public int getWidth() { return this.width; }
		
		public TextureInfo setWidth(int value) { this.width = value; return this;}
		
		public int getHeight() { return this.height; }
		
		public TextureInfo setHeight(int value) { this.height = value; return this; }
	}
}
