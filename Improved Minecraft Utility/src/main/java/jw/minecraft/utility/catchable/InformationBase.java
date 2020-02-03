package jw.minecraft.utility.catchable;

import javax.annotation.Nonnull;

import jw.minecraft.utility.IChatComponentProvider;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class InformationBase extends Catchable {
	
	public static final EnumChatFormatting COLOR = EnumChatFormatting.WHITE;
	
	public final String AddonId;

	public InformationBase(@Nonnull String addonId, int code, @Nonnull String defaultMessage, Object... args) {
		super(code, defaultMessage, null, args);
		
		if (addonId == null)
			throw new NullPointerException("String addonId");		

		AddonId = addonId;
		LocalKey = "imu." + addonId + ".info.message[" + code + "]";
	}

	@Override
	public IChatComponent getChatComponent() {
		return new ChatComponentText(COLOR.toString() + getLocalizedString());
	}
	
}
