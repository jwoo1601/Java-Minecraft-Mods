package jwk.minecraft.garden.client.util;

import org.lwjgl.opengl.Display;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public class ResolutionHelper {

	public static boolean isFullMaximized() {		
		return Minecraft.getMinecraft().displayWidth == Display.getDesktopDisplayMode().getWidth() && Minecraft.getMinecraft().displayHeight == Display.getDesktopDisplayMode().getHeight();
	}
	
	public static boolean isWidthMaximized() {
		return Minecraft.getMinecraft().displayWidth == Display.getDesktopDisplayMode().getWidth();
	}
	
	public static boolean isHeightMaximized() {
		return Minecraft.getMinecraft().displayHeight == Display.getDesktopDisplayMode().getHeight();
	}
	
	public static float getGuiScaleFactor() {
    	int width = Minecraft.getMinecraft().displayWidth;
    	int height = Minecraft.getMinecraft().displayHeight;

    	if (isWidthMaximized() || isHeightMaximized())
    		return 1.666666666666667F;
    	
    	else if (width < 640 || height < 480)
    		return 1F;
    	
    	else if (width < 1280 || height < 720) {
        	float wscale = 1.5F * 1280 / width;
        	float hscale = 1.5F * 720 / height;
        	
        	return Math.min(wscale, hscale);
    	}
    	
    	return 1F;
	}
	
	public static float getGuiScaledValue(float val) {
		return val * getGuiScaleFactor();
	}
	
	public static float getGuiScaledValueWeighted(float val, float weight) {
    	int width = Minecraft.getMinecraft().displayWidth;
    	int height = Minecraft.getMinecraft().displayHeight;

    	if (isWidthMaximized() || isHeightMaximized())
    		return val * 1.666666666666667F;
    	
    	else if (width < 640 || height < 480)
    		return val;
    	
    	else if (width < 1280 || height < 720) {
        	float wscale = (val + weight) * 1280 / width ;
        	float hscale = (val + weight) * 720 / height;
        	
        	return Math.min(wscale, hscale);
    	}
    	
    	return val;
	}
	
}
