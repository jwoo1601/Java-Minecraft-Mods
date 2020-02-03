package jwk.minecraft.garden.client.event;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.client.renderer.FlowerLanguageRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

@SideOnly(Side.CLIENT)
public class RenderWorldEventHandler {
	
	private final FlowerLanguageRenderer FLRenderer = new FlowerLanguageRenderer();
	
	private static final ResourceLocation GRENADE_TEXTURE = new ResourceLocation(ModInfo.ID, "models/grenade_texture.png");
	private static final ResourceLocation GRENADE_MODEL = new ResourceLocation(ModInfo.ID, "models/grenade.obj");
	
	private static final IModelCustom Model = new ObjModelLoader().loadInstance(GRENADE_MODEL);
	
	@SubscribeEvent
	public void onRenderBlockOverlay(RenderWorldLastEvent e) {
		if (ClientEventHandler.previousTicks != 0L) {
			long sub = System.nanoTime() - ClientEventHandler.previousTicks;
			float result = (float) (sub * Math.pow(10, -9) * 0.05F);
			
			System.out.println("Cal: " + result + " real: " + e.partialTicks);
		}
		
		Iterator<FlowerInfo> iter = ProjectGarden.proxy.getDisplayFlowerInfoIterator();
		
		while (iter.hasNext()) {
			FlowerInfo info = iter.next();
			
			if (info.IsVisible)
				FLRenderer.doRender(info, Minecraft.getMinecraft().renderViewEntity, e.partialTicks);
		}
		
		
		//////////////////////////////// OBJ TEST ////////////////////////////////
		EntityLivingBase renderView = Minecraft.getMinecraft().renderViewEntity;
		double worldX = renderView.lastTickPosX + (renderView.posX - renderView.lastTickPosX) * e.partialTicks;
		double worldY = renderView.lastTickPosY + (renderView.posY - renderView.lastTickPosY) * e.partialTicks;
		double worldZ = renderView.lastTickPosZ + (renderView.posZ - renderView.lastTickPosZ) * e.partialTicks;

		Minecraft.getMinecraft().getTextureManager().bindTexture(GRENADE_TEXTURE);
		
		glPushMatrix(); {
			
			glEnable(GL_LIGHTING);
			glEnable(GL_TEXTURE_2D);
			
			glTranslated(-worldX, - worldY, -worldZ);
			glTranslated(60, 60, 60);
			
			Model.renderAll();
			
			
		} glPopMatrix();
	}
	
}
