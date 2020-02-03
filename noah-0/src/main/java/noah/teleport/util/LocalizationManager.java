package noah.teleport.util;

import net.minecraft.util.StatCollector;

public class LocalizationManager {
	public static final String CONNECTOR = ".";
	public static final String TYPE_COMMON = "common";
	public static final String TYPE_BLOCK = "tile";
	public static final String TYPE_ITEM = "item";
	public static final String TYPE_COMMAND = "command";
	public static final String TYPE_MESSAGE = "message";
	
	public static String localize(String type, String sub) {
		return StatCollector.translateToLocal(type + CONNECTOR + sub);
	}
}
