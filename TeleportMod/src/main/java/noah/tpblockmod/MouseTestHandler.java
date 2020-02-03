package noah.tpblockmod;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MouseTestHandler {
	
	public final Minecraft mc = Minecraft.getMinecraft();
	public final FontRenderer fr = mc.fontRendererObj;
	
	private final ResourceLocation cursor = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/test_cursor.png");
	
	public MouseTestHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onTick(RenderGameOverlayEvent.Post e) {
		if (mc.fontRendererObj != null) {
		fr.drawStringWithShadow("마우스 이벤트 버튼: " + Mouse.getEventButton(), 20, 10, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 abX: " + Mouse.getX(), 20, 20, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 abY: " + Mouse.getY(), 20, 30, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 이벤트X: " + Mouse.getEventX(), 20, 40, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 이벤트Y: " + Mouse.getEventY(), 20, 50, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 DX: " + Mouse.getDX(), 20, 60, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 DY: " + Mouse.getDY(), 20, 70, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 이벤트DX: " + Mouse.getEventDX(), 20, 80, 0xFFFFFF);
		fr.drawStringWithShadow("마우스 이벤트DY: " + Mouse.getEventDY(), 20, 90, 0xFFFFFF);
		
		if (mc.getTextureManager() != null && mc.currentScreen != null) {
		mc.getTextureManager().bindTexture(cursor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		int x = Mouse.getEventX() * mc.currentScreen.width / mc.displayWidth;
		int y = mc.currentScreen.height - Mouse.getEventY() * mc.currentScreen.height / mc.displayHeight -1;
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 10, 10, 256, 256);
		}
	}
	}
	
	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Post e) {
		e.renderer.setRenderOutlines(true);
		//ModelRenderer a = e.renderer.getMainModel().bipedHead;
		//a.render(0.0f);
	}
}
