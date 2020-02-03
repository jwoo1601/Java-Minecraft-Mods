package jw.minecraft.utility.addon;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import jw.minecraft.utility.command.*;
import jw.minecraft.utility.localization.Parsed;
import jw.minecraft.utility.localization.Parser.ParseType;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Addon {
	
	String id();
	
	@Parsed(type = ParseType.TRANSLATE)
	String name();
	
	@Parsed(type = ParseType.TRANSLATE)
	String desc() default "";
	
	String version();
	
	Class<? extends IUnit> unit();
	
	Class<?> command();
	
}
