package jwk.minecraft.garden.client.font;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.font.Glyph.GlyphType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

/**
 * Converted to v1.7.10 by jwoo__
 * @author noppes
 *
 */
@SideOnly(Side.CLIENT)
public class TrueTypeFont {
	
	static final int MAX_WIDTH = 512;
	
    private static final List<Font> ALL_FONTS = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
	private List<Font> usedFonts = new ArrayList<Font>();
    
	private LinkedHashMap<String, GlyphCache> textcache = new TextureMap(100);
	private Map<Character, Glyph> glyphcache = new HashMap<Character, Glyph>();
	private List<TextureCache> textures = new ArrayList<TextureCache>();
    
	private Font font;
	private int lineHeight = 1;
	
	private Graphics2D globalG = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
		
	public float scale = 1;
	
	public TrueTypeFont(Font font, float scale){
		this.font = font;
		this.scale = scale;
		globalG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		lineHeight = globalG.getFontMetrics(font).getHeight();
	}
	
	public TrueTypeFont(ResourceLocation resource, int fontSize, float scale) throws IOException, FontFormatException {
		InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
		ge.registerFont(font);
		this.font = font.deriveFont(Font.PLAIN, fontSize);
		this.scale = scale;
		globalG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		lineHeight = globalG.getFontMetrics(font).getHeight();
	}
	
	public void drawString (String text, float x, float y) {
		drawString(text, x, y, 0xFFFFFF);
	}
	
	public void drawString(String text, float x, float y, int color) {
		drawString(text, x, y, color, 1.F);
	}
	
	public void drawString(String text, float x, float y, int color, float opacity){
		GlyphCache cache = getOrCreateCache(text);
		
        float r = (color >> 16 & 255) / 255F;
        float g = (color >> 8 & 255) / 255F;
        float b = (color & 255) / 255F;
        
        glEnable(GL_BLEND);
        glColor4f(r, g, b, opacity);
        
        glPushMatrix();
        
        glTranslatef(x, y, 0.F);
		glScalef(scale, scale, 1);
		
		float i = 0;
		for(Glyph gl : cache.glyphs){
			
			if(gl.type != GlyphType.NORMAL){
				
				if(gl.type == GlyphType.RESET)
					glColor4f(r, g, b, opacity);
				
				else if(gl.type == GlyphType.COLOR)
					glColor4f((gl.color >> 16 & 255) / 255F, (gl.color >> 8 & 255) / 255F, (gl.color & 255) / 255F, 1.F);
			}
			
			else{
				glBindTexture(GL_TEXTURE_2D, gl.texture);
		        drawTexturedModalRect(i, 0, gl.x * textureScale(), gl.y * textureScale(), gl.width * textureScale(), gl.height * textureScale());	
		        i += gl.width * textureScale();
			}
		}	
		
		glPopMatrix();
		glDisable(GL_BLEND);
	}
	
	public void drawString(String text, float x, float y, float[] color, float opacity){
		if (color == null || color.length < 3)
			return;
		
		GlyphCache cache = getOrCreateCache(text);
		
        float r = color[0];
        float g = color[1];
        float b = color[2];
        
        glEnable(GL_BLEND);
        glColor4f(r, g, b, opacity);
        
        glPushMatrix();
        
        glTranslatef(x, y, 0.F);
		glScalef(scale, scale, 1);
		
		float i = 0;
		for(Glyph gl : cache.glyphs){
			
			if(gl.type != GlyphType.NORMAL){
				
				if(gl.type == GlyphType.RESET)
					glColor4f(r, g, b, opacity);
				
				else if(gl.type == GlyphType.COLOR)
					glColor4f((gl.color >> 16 & 255) / 255F, (gl.color >> 8 & 255) / 255F, (gl.color & 255) / 255F, 1.F);
			}
			
			else{
				glBindTexture(GL_TEXTURE_2D, gl.texture);
		        drawTexturedModalRect(i, 0, gl.x * textureScale(), gl.y * textureScale(), gl.width * textureScale(), gl.height * textureScale());	
		        i += gl.width * textureScale();
			}
		}	
		
		glPopMatrix();
		glDisable(GL_BLEND);
	}
	
	public void drawString(String text, float x, float y, Color color){
		GlyphCache cache = getOrCreateCache(text);
		
        float r = color.getRed() / 255F;
        float g = color.getGreen() / 255F;
        float b = color.getBlue() / 255F;
        float a = color.getAlpha() / 255F;
        
        glEnable(GL_BLEND);
        glColor4f(r, g, b, a);
        
        glPushMatrix();
        
        glTranslatef(x, y, 0.F);
		glScalef(scale, scale, 1);
		
		float i = 0;
		for(Glyph gl : cache.glyphs){
			
			if(gl.type != GlyphType.NORMAL){
				
				if(gl.type == GlyphType.RESET)
					glColor4f(r, g, b, a);
				
				else if(gl.type == GlyphType.COLOR)
					glColor4f((gl.color >> 16 & 255) / 255F, (gl.color >> 8 & 255) / 255F, (gl.color & 255) / 255F, 1.F);
			}
			
			else{
				glBindTexture(GL_TEXTURE_2D, gl.texture);
		        drawTexturedModalRect(i, 0, gl.x * textureScale(), gl.y * textureScale(), gl.width * textureScale(), gl.height * textureScale());	
		        i += gl.width * textureScale();
			}
		}	
		
		glPopMatrix();
		glDisable(GL_BLEND);
	}
	
	private int getColorCode(char c) {
		return ProjectGarden.proxy.getVanillaColorCode()["0123456789abcdef".indexOf(c)];
	}
	
	private GlyphCache getOrCreateCache(String text){
		GlyphCache cache = textcache.get(text);
		
		if(cache != null)
			return cache;
		
		cache = new GlyphCache();
		for(int i = 0; i < text.length(); i++){
			char c = text.charAt(i);
			
			// check whether is special char (§)
			if(c == 167 || c == 38 && i + 1 < text.length()){
				char next = text.toLowerCase(Locale.ENGLISH).charAt(i + 1);
                int index = "0123456789abcdefklmnor".indexOf(next);
                
                if(index >= 0){
                	Glyph g = new Glyph();

                    if (index < 16){
                    	g.type = GlyphType.COLOR; 
                    	g.color = getColorCode(next);
                    }
                    
                    else if(index == 16)
                    	g.type = GlyphType.RANDOM;
                    
                    else if(index == 17)
                    	g.type = GlyphType.BOLD;
                    
                    else if(index == 18)
                    	g.type = GlyphType.STRIKETHROUGH;
                    
                    else if(index == 19)
                    	g.type = GlyphType.UNDERLINE;
                    
                    else if(index == 20)
                    	g.type = GlyphType.ITALIC;
                    
                    else
                    	g.type = GlyphType.RESET;
                    
        			cache.glyphs.add(g);
                	i++;
                	continue;
                }
			}
			
			Glyph g = getOrCreateGlyph(c);
			cache.glyphs.add(g);
			cache.width += g.width;
			cache.height = Math.max(cache.height, g.height);
		}
		
		textcache.put(text, cache);
		return cache;
	}
	
	private Glyph getOrCreateGlyph(char c){
		Glyph g = glyphcache.get(c);
		
		if(g != null)
			return g;
		
		TextureCache cache = getCurrentTexture();
		Font font = getFontForChar(c);
		FontMetrics metrics = globalG.getFontMetrics(font);
		g = new Glyph();
		g.width = Math.max(metrics.charWidth(c), 1);
		g.height = Math.max(metrics.getHeight(), 1);
		
		if(cache.x + g.width >= MAX_WIDTH){
			cache.x = 0;
			cache.y += lineHeight + 1;
			
			if(cache.y >= MAX_WIDTH){
				cache.full = true;
				cache = getCurrentTexture();
			}
		}
		
		g.x = cache.x;
		g.y = cache.y;		
		cache.x += g.width + 3;
		lineHeight = Math.max(lineHeight, g.height);
		
		cache.g.setFont(font);
		cache.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		cache.g.drawString(c + "", g.x, g.y + metrics.getAscent());
		g.texture = cache.textureId;

        TextureUtil.uploadTextureImage(cache.textureId, cache.bufferedImage);
        glyphcache.put(c,  g);
        
		return g;
	}
	
	private TextureCache getCurrentTexture(){
		TextureCache cache = null;
		
		for(TextureCache t : textures){
			
			if(!t.full){
				cache = t;
				break;
			}
		}
		
		if(cache == null)
			textures.add(cache = new TextureCache());
		
		return cache;
	}
	
	public void drawCenteredString(String text, float x, float y, int color){
		drawCenteredString(text, x - (getWidth(text) / 2f), y, color);
	}
	
	private Font getFontForChar(char c){
		
		if(font.canDisplay(c))
			return font;

		for(Font font : usedFonts){
			
			if(font.canDisplay(c))
				return font;
		}
		
		for(Font font : ALL_FONTS){
			
			if(font.canDisplay(c)) {
				usedFonts.add(font);
				return font;
			}
		}
		
		return null;
	}

    private void drawTexturedModalRect(float x, float y, float textureX, float textureY, float width, float height){
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        int zLevel = 0;
        
        Tessellator t = Tessellator.instance;
        
        t.startDrawingQuads();
        
        t.disableColor();
        t.addVertexWithUV(x, y + height, zLevel, textureX * f, (textureY + height) * f1);
        t.addVertexWithUV(x + width, y + height, zLevel, (textureX + width) * f, (textureY + height) * f1);
        t.addVertexWithUV(x + width, y, zLevel, (textureX + width) * f, textureY * f1);
        t.addVertexWithUV(x, y, zLevel, textureX * f, textureY * f1);
        
        t.draw();
    }
    
    public int getWidth(String text){
		GlyphCache cache = getOrCreateCache(text);
		
		return (int) (cache.width * scale * textureScale());
    }
    
    public int getHeight(String text){
    	
    	if(text == null || text.trim().isEmpty())
    		return (int) (lineHeight * scale * textureScale());
    	
		GlyphCache cache = getOrCreateCache(text);
		
		return Math.max(1,(int) (cache.height * scale * textureScale()));
    }
    
    private float textureScale(){
    	return 0.5f;
    }
	
	public void dispose(){
		
		for(TextureCache cache : textures)
    		glDeleteTextures(cache.textureId);
		
		textcache.clear();
	}

	public String getFontName() {
		return font.getFontName();
	}
	
}
