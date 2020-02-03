package jw.minecraft.utility.catchable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jw.minecraft.utility.IChatComponentProvider;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ErrorBase extends Catchable {
	
	public static final EnumChatFormatting COLOR = EnumChatFormatting.RED;
	
	public final String AddonId;

	public ErrorBase(@Nonnull String addonId, int code, @Nonnull String defaultMessage, Object... args) {
		super(code, defaultMessage, null, args);
		
		if (addonId == null)
			throw new NullPointerException("String addonId");		

		AddonId = addonId;
		LocalKey = "imu." + addonId + ".error.message[" + code + "]";
	}

	@Override
	public IChatComponent getChatComponent() {
		return new ChatComponentText(COLOR.toString() + getLocalizedString());
	}
	
}
