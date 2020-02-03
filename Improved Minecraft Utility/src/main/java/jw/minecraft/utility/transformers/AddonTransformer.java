package jw.minecraft.utility.transformers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jw.minecraft.utility.LogHelper;
import jw.minecraft.utility.addon.Addon;
import jw.minecraft.utility.addon.AddonData;
import jw.minecraft.utility.localization.Parser;
import jw.minecraft.utility.localization.Parser.ParseType;

public class AddonTransformer extends AbstractTransformer<Class<?>, AddonData> {
	
	public static final String IDENTIFIER_FORMAT = "^[_a-zA-Z][_a-zA-Z0-9]*$";
	
	private Addon addonCache;
	
	private CommandTransformer commandTransformer;
	
	public AddonTransformer() {
		commandTransformer = new CommandTransformer();
	}
	
	private void init() {
		raw = null;
		addonCache = null;
	}

	@Override
	protected void validate() {
		addonCache = checkNotNull(raw.getDeclaredAnnotation(Addon.class), "addon must be annotated with @Addon!");
		checkArgument(Pattern.matches(IDENTIFIER_FORMAT, addonCache.id()));
	}

	@Override
	public @Nullable AddonData transform(@Nonnull Class<?> addon) {
		init();
		raw = checkNotNull(addon, "addon must not be null!");
		validate();
		
		try {
			return new AddonData(addonCache.id(), Parser.parse(addonCache.name(), ParseType.TRANSLATE),
					   Parser.parse(addonCache.desc(), ParseType.TRANSLATE), 
					   addonCache.version(), addonCache.unit().newInstance(), commandTransformer.transform(addonCache.command()));
		} catch (InstantiationException e) {
			LogHelper.error(e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			LogHelper.error(e.getMessage());
			return null;
		}
	}
}
