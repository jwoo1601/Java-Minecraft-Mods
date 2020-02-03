package jw.minecraft.utility.catchable;

import static jw.minecraft.utility.Common.translateToLocal;
import static jw.minecraft.utility.Common.translateToLocalFormatted;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cpw.mods.fml.common.registry.LanguageRegistry;
import jw.minecraft.utility.IChatComponentProvider;
import jw.minecraft.utility.localization.ILocalizable;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class Catchable implements ILocalizable, IChatComponentProvider {
	
	public final int Code;
	
	public final String DefaultMessage;
	
	protected String LocalKey;
	
	protected Object[] Arguments;
	
	protected ICause<ICommandSender> Cause;
	
	public Catchable(int code, @Nonnull String defaultMessage, @Nullable String localKey, Object... args) {
		if (defaultMessage == null)
			throw new NullPointerException("String defaultMessage");
		
		Code = code;
		DefaultMessage = defaultMessage;
		LocalKey = localKey;
		Arguments = args;
	}

	@Override
	public String getLocalizedString() {
		return LocalKey == null? DefaultMessage : Arguments == null? translateToLocal(LocalKey) : translateToLocalFormatted(LocalKey, Arguments);
	}
	


	@Override
	public IChatComponent getChatComponent() {
		return new ChatComponentText(getLocalizedString());
	}
	
	public Catchable args(Object... args) {
		Arguments = args;
		return this;
	}
	
	public Catchable cause(ICause<ICommandSender> cause) {
		Cause = cause;
		return this;
	}
	
	public ICause<ICommandSender> getCauseObject() {
		return Cause;
	}
}
