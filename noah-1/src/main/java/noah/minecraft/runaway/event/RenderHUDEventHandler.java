package noah.minecraft.runaway.event;

import org.lwjgl.opengl.GL11;

import javafx.scene.input.KeyEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noah.minecraft.runaway.Common;
import noah.minecraft.runaway.Noah;
import noah.minecraft.runaway.gui.GuiDirectionArrow;
import noah.minecraft.runaway.gui.GuiDirectionArrow.Axis;

public class RenderHUDEventHandler {
	public static GuiDirectionArrow arrow = new GuiDirectionArrow();
	
	private double getTheta(Vec3 src, Vec3 dst) {
		return Math.asin((src.xCoord * dst.zCoord - src.zCoord * dst.xCoord) / src.lengthVector() * dst.lengthVector());
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onRenderArrow(RenderGameOverlayEvent e) {
		
		if (e.type.equals(ElementType.CROSSHAIRS)) {
			
			if (Noah.Proxy.CisGameStarted && Noah.Proxy.CCriminal != null
				&& !Noah.Proxy.CCriminal.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
				
				arrow.UpdatePosition(e.resolution.getScaledWidth_double() * 0.5, e.resolution.getScaledHeight_double() * 0.5 + 40.0D, 0.0D);
				arrow.setAlpha(1.0f);
		
				Vec3 _target = Noah.Proxy.CCriminal.getPositionVector().normalize();
				Vec3 _pos = Minecraft.getMinecraft().thePlayer.getPositionVector().normalize();
				Vec3 _look = Minecraft.getMinecraft().thePlayer.getLookVec();
				
				Vec3 target = new Vec3(_target.xCoord, 0, _target.zCoord);
				Vec3 pos = new Vec3(_pos.xCoord, 0, _pos.zCoord);
				Vec3 look = new Vec3(_look.xCoord, 0, _look.zCoord);
				
				Vec3 sub = target.subtract(pos);
				boolean isReversed = sub.crossProduct(look).yCoord < 0;
				double anglez = Math.toDegrees(Math.acos(sub.dotProduct(look) / sub.lengthVector() * look.lengthVector()));
				
				arrow.UpdateRotation(isReversed? Axis.MINUS_Z : Axis.Z, anglez);
				
			/*	Vec3 target2 = new Vec3(0, _target.yCoord, _target.zCoord);
				Vec3 pos2 = new Vec3(0, _pos.yCoord, _pos.zCoord);
				Vec3 look2 = new Vec3(0, _look.yCoord, _look.zCoord);
				
				Vec3 sub2 = target2.subtract(pos2);
				boolean isReversed2 = sub2.crossProduct(look2).xCoord >= 0;
				double anglex = Math.toDegrees(Math.acos(sub2.dotProduct(look2) / sub2.lengthVector() * look2.lengthVector()));
				 
				arrow.UpdateRotation(isReversed2? Axis.MINUS_X : Axis.X, anglex); */
				arrow.onDraw(Minecraft.getMinecraft(), Minecraft.getMinecraft().thePlayer.getDistanceToEntity(Noah.Proxy.CCriminal) ,Tessellator.getInstance());
			}
		}
	}
}
