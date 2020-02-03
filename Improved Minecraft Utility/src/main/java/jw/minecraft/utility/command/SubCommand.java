package jw.minecraft.utility.command;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jw.minecraft.utility.localization.Parsed;
import jw.minecraft.utility.localization.Parser.ParseType;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {
	
	@Parsed(type = { ParseType.TRANSLATE, ParseType.SUBCOMMAND })
	String usage();
	
	int minArgsLength() default 0;
	
	int maxArgsLength() default 0;
	
	@Parsed(type = ParseType.TRANSLATE)
	String desc() default "";
	
	boolean allowNonPlayerSender() default false;
	
}