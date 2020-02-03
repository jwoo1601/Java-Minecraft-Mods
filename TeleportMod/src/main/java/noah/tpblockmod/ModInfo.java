package noah.tpblockmod;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public final class ModInfo {
	public static final String PROJECT = "Noah";
	public static final String MOD_ID = "teleportblockmod";
	public static final String MOD_NAME = "Teleport Block Mod";
	public static final String MOD_VERSION = "0.7.1T";
	public static final String MOD_AUTHOR = "jwoo__";
	
	public static final String MOD_LOCAL_NAME = StatCollector.translateToLocal("mod.name");
	public static final String MOD_INFO_PREFIX = ("[" + EnumChatFormatting.BLUE + StatCollector.translateToLocal("mod.name") + EnumChatFormatting.WHITE + "] ");
	
	public static final String COMMON_ERR0 = StatCollector.translateToLocal("command.common.err0");
	
	public static final String R_P_NAME = StatCollector.translateToLocal("command.registerplace.name");
	public static final String R_P_USAGE = StatCollector.translateToLocal("command.registerplace.usage");	
	public static final String R_P_ERR0 = StatCollector.translateToLocal("command.registerplace.err0");
	public static final String R_P_ERR1 = StatCollector.translateToLocal("command.registerplace.err1");
	public static final String R_P_ERR2 = StatCollector.translateToLocal("command.registerplace.err2");
	public static final String R_P_SUCCESS = StatCollector.translateToLocal("command.registerplace.success");
	
	public static final String L_B_NAME = StatCollector.translateToLocal("command.linkblock.name");
	public static final String L_B_USAGE = StatCollector.translateToLocal("command.linkblock.usage");
	public static final String L_B_SUCCESS = StatCollector.translateToLocal("command.linkblock.success");
	public static final String L_B_ERR0 = StatCollector.translateToLocal("command.linkblock.err0");
	
	public static final String MSG_TP_PRE = StatCollector.translateToLocal("message.teleport.pre");
	public static final String MSG_TP_POST = StatCollector.translateToLocal("message.teleport.post");
}
