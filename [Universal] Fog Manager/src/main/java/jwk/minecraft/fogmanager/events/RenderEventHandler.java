package jwk.minecraft.fogmanager.events;

import static org.lwjgl.opengl.GL11.GL_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_FOG;
import static org.lwjgl.opengl.GL11.GL_FOG_COLOR;
import static org.lwjgl.opengl.GL11.GL_FOG_DENSITY;
import static org.lwjgl.opengl.GL11.GL_FOG_END;
import static org.lwjgl.opengl.GL11.GL_FOG_HINT;
import static org.lwjgl.opengl.GL11.GL_FOG_MODE;
import static org.lwjgl.opengl.GL11.GL_FOG_START;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFog;
import static org.lwjgl.opengl.GL11.glFogf;
import static org.lwjgl.opengl.GL11.glFogi;
import static org.lwjgl.opengl.GL11.glHint;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.fogmanager.config.Configuration;
import jwk.minecraft.fogmanager.config.Configuration.FogConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

@SideOnly(Side.CLIENT)
public class RenderEventHandler {

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldEvent.Pre e) {
		FogConfig config = Configuration.getInstance().fog();
		
		if (config != null && config.getState()) {
			FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(4);
			colorBuffer.put(config.getColor().toFloatArray()).flip();
			
			float start = config.getStartDepth();
			{
				glColor4f(config.getColor().getRed(), config.getColor().getGreen(), config.getColor().getBlue(), config.getColor().getAlpha());
				glFogi(GL_FOG_MODE, config.getQuality().Value);
				glFog(GL_FOG_COLOR, colorBuffer);
				glFogf(GL_FOG_DENSITY, config.getDensity());
				glHint(GL_FOG_HINT, config.getRenderOption().Value);
				glFogf(GL_FOG_START, start);
				glFogf(GL_FOG_END, start + config.getDistance());
				System.out.println("포그 드로잉");
				System.out.println(config.getStartDepth());
				System.out.println(Configuration.getInstance().AnimationHelper.getQueueSize());
			}
			glEnable(GL_FOG);
		}
		
		else
			glDisable(GL_FOG);
	}
	
}
