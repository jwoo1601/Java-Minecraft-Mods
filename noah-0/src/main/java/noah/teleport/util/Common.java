package noah.teleport.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Common {
	
	public static final String MOD_ID = "noahtp";
	public static final String MOD_VERSION = "1.1.3-beta";
	public static final String MOD_DEF_NAME = "Teleport Mod";
	public static final String MOD_AUTHOR = "jwoo__";
	public static final String MOD_CREDIT = "Noah";
	public static final String MOD_LOCAL_NAME = LocalizationManager.localize(LocalizationManager.TYPE_COMMON, "mod.name");
	public static final String MOD_DESCRIPTION = LocalizationManager.localize(LocalizationManager.TYPE_COMMON, "mod.description");
	public static final String MOD_LOGO_DIR = "assets/" + MOD_ID + "/textures/misc/logo.png";
	
	public static final String CLIENT_THREAD_NAME = "Client thread";
	public static final String SERVER_THREAD_NAME = "Server thread";
	
	public static final int NETHER_ID = -1;
	public static final String NETHER_NAME = "Nether";
	public static final int OVERWORLD_ID = 0;
	public static final String OVERWORLD_NAME = "Overworld";
	public static final int THEEND_ID = 1;
	public static final String THEEND_NAME = "The End";
	
	public static MinecraftServer SERVER = MinecraftServer.getServer();
	
	public static final String NOAH_DEBUG = "[Noah:Debug]";
	
	public static void Debug(String message) {
		System.out.println(NOAH_DEBUG + ": " + message);
	}
	
	public static void setupModMetadata(ModMetadata data) {
		ChatString name = new ChatString(MOD_LOCAL_NAME, EnumChatFormatting.BLUE);
		ChatString credit = new ChatString(MOD_CREDIT, EnumChatFormatting.YELLOW);
		
		data.autogenerated = false;
		data.name = name.getFormattedString();
		data.authorList.add(MOD_AUTHOR);
		data.credits = credit.getFormattedString();
		data.description = MOD_DESCRIPTION;
		data.logoFile = MOD_LOGO_DIR;
	}
	
	public static ServerType getCurrentServerType() {
		return SERVER != null ? (SERVER.isDedicatedServer()? ServerType.DEDICATED : ServerType.INTEGRATED) : null;
	}
	
	public static boolean isClientThread() {
		return Thread.currentThread().getName().equals(CLIENT_THREAD_NAME);
	}
	
	public static boolean isServerThread() {
		return Thread.currentThread().getName().equals(SERVER_THREAD_NAME);
	}
	
	public static boolean isIntegratedServer() {
		return getCurrentServerType() == ServerType.INTEGRATED;
	}
	
	public static boolean isDedicatedServer() {
		return getCurrentServerType() == ServerType.DEDICATED;
	}
}