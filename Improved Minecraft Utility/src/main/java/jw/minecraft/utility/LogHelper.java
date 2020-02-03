package jw.minecraft.utility;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LogHelper {
	
	private static Logger logger = LogManager.getLogger("BlockGuard");
	
	public static void debug(String message) {
		logger.debug(message);
	}
	
	public static void error(String message) {
		logger.error(message);
	}
	
	public static void info(String message) {
		logger.info(message);
	}
	
	public static void warn(String message) {
		logger.warn(message);
	}
	
	public static void trace(String message) {
		logger.trace(message);
	}
	
	public static void fatal(String message) {
		logger.fatal(message);
	}
	
	public static void log(Level level, String message) {
		logger.log(level, message);
	}
}
