package jw.minecraft.utility;

import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.registry.LanguageRegistry;
import jw.minecraft.utility.math.PipedComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public abstract class Common {
	
	public static final String MOD_ID = "imu";
	public static final String MOD_VERSION = "1.7.10-0.1.2.0";
	public static final String MOD_DEFAULT_NAME = "Improved Minecraft Utility";
	public static final String MOD_NAME = translateToLocal("imu.info.name");
	public static final String MOD_AUTHOR = "jwoo__";
	public static final String MOD_CREDIT = "Team 삼꼬맹이";
	public static final String MOD_DESCRIPTION = translateToLocal("imu.info.desc");
	public static final String MOD_LOGO_DIR = "assets/" + MOD_ID + "/textures/misc/logo.png";
	
	public static final int NETHER_ID = -1;
	public static final String NETHER_NAME = "Nether";
	public static final int OVERWORLD_ID = 0;
	public static final String OVERWORLD_NAME = "Overworld";
	public static final int THEEND_ID = 1;
	public static final String THEEND_NAME = "The End";
	
	public static void setupModMetadata(ModMetadata data) {
		data.autogenerated = false;
		data.name = EnumChatFormatting.GOLD + MOD_NAME;
		data.authorList.add(MOD_AUTHOR);
		data.credits = EnumChatFormatting.YELLOW + MOD_CREDIT;
		data.description = MOD_DESCRIPTION;
		data.logoFile = MOD_LOGO_DIR;
	}
	
	public static String translateToLocal(String str) {
		 return LanguageRegistry.instance().getStringLocalization(str, "ko_KR");
	}
	
	public static String translateToLocalFormatted(String str, Object...args) {
		 return String.format(LanguageRegistry.instance().getStringLocalization(str, "ko_KR"), args);
	}
	
}