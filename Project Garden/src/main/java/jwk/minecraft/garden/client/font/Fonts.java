package jwk.minecraft.garden.client.font;

import java.awt.FontFormatException;
import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class Fonts {

	public static final String DEFAULT_FOLDER_NAME = "fonts";
	
	public static final TrueTypeFont newFont(String name, int size) {
		return newFont(name, size, 1.F);
	}
	
	public static final TrueTypeFont newFont(String name, int size, float scale) {
		try {
			return new TrueTypeFont(new ResourceLocation(ModInfo.ID, "fonts/" + name + ".ttf"), size, scale);
		}
		
		catch (Exception e) { throw new RuntimeException("An error occured during creating Font", e); }
	}
	
	public static void initializeFonts() {
		fontDohyeon = newFont("BMDOHYEON", 18);
		fontDohyeonL = newFont("BMDOHYEON", 20);
		fontDohyeonS = newFont("BMDOHYEON", 14);
	}
	
	public static void disposeFonts() {
		fontDohyeon.dispose();
		fontDohyeonL.dispose();
		fontDohyeonS.dispose();
	}
	
	public static TrueTypeFont fontDohyeon;
	public static TrueTypeFont fontDohyeonL;
	public static TrueTypeFont fontDohyeonS;
	
}
