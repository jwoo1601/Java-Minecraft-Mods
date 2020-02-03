package jw.minecraft.utility.command;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import jw.minecraft.utility.Optional;
import jw.minecraft.utility.addon.Addon;
import jw.minecraft.utility.localization.Parsed;
import jw.minecraft.utility.localization.Parser.ParseType;
import jw.minecraft.utility.permissions.IPermission;
import jw.minecraft.utility.permissions.PermissionManager;
import jw.minecraft.utility.permissions.Permissions;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AddonCommand {
	
	String addonId();
	
	@Parsed(type = ParseType.TRANSLATE)
	String name();
	
	@Parsed(type = ParseType.TRANSLATE)
	String usage();
	
	String[] permissions() default "default";
	
	boolean allowCallback() default false;
	
	
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@Optional
	public static @interface Action {
		
		ActionType type();
		
	}
	
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@Optional
	public static @interface OutputHandler {
		
		@Parsed(type = ParseType.TRANSLATE)
		String prefix() default "";
		
	}
	
}