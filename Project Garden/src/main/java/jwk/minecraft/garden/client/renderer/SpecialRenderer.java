package jwk.minecraft.garden.client.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import jwk.minecraft.garden.client.font.Fonts;
import jwk.minecraft.garden.client.font.TrueTypeFont;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

/**
 * Under Development
 * @author jwoo__
 *
 */
public class SpecialRenderer {

	public static void renderLabelOver(RenderManager manager, Entity entity, String label, Color color, double x, double y, double z, double yoffset, double maxDistance) {
        TrueTypeFont font = Fonts.fontDohyeon;
        
        if (font == null)
        	return;
        
		double distance = entity.getDistanceSqToEntity(manager.livingPlayer);

		if (distance <= maxDistance * maxDistance)
		{
			float f = 1.6F;
			float f1 = 0.016666668F * f;
			
			glPushMatrix(); {
				
				glTranslated(x, y + entity.height + yoffset, z);
				glNormal3f(0.0F, 1.0F, 0.0F);
	            glRotatef(-manager.playerViewY, 0.0F, 1.0F, 0.0F);
	            glRotatef(manager.playerViewX, 1.0F, 0.0F, 0.0F);
	            glScalef(-f1, -f1, f1);
	            
	            glDisable(GL_LIGHTING);
	            glDepthMask(false);
	            glDisable(GL_DEPTH_TEST);
	            glEnable(GL_BLEND);
	            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	            
	            Tessellator tessellator = Tessellator.instance;
	            
	            int j = font.getWidth(label) / 2;
	            
	            glDisable(GL_TEXTURE_2D);	            
	            tessellator.startDrawingQuads();
	            
	            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
	            tessellator.addVertex((double)(-j - 1), -1.0D, 0.0D);
	            tessellator.addVertex((double)(-j - 1), 8.0D, 0.0D);
	            tessellator.addVertex((double)(j + 1), 8.0D, 0.0D);
	            tessellator.addVertex((double)(j + 1), -1.0D, 0.0D);
	            
	            tessellator.draw();
	            
	            glEnable(GL_TEXTURE_2D);
	            font.drawString(label, -j, 0, color);

	            glEnable(GL_DEPTH_TEST);
	            glDepthMask(true);
	            font.drawString(label, -j, 0, color);
	            
	            glEnable(GL_LIGHTING);
	            glDisable(GL_BLEND);	
	            
			} glPopMatrix();		
		}
	}
	
}
