package noah.teleport.util;

import net.minecraft.util.EnumChatFormatting;

public class ChatString {
	private String string;
	private EnumChatFormatting color;
	
	public ChatString(String string) {
		this.string = string;
		this.color = EnumChatFormatting.WHITE;
	}
	
	public ChatString(String string, EnumChatFormatting color) {
		this.string = string;
		this.color = color;
	}
	
	public ChatString(String string, String colorstr) {
		this.string = string;
		this.color = EnumChatFormatting.getValueByName(colorstr);
	}
	
	public void setString(String value) {
		this.string = value;
	}
	
	public void setColor(EnumChatFormatting color) {
		this.color = color;
	}
	
	public void setColor(String colorstr) {
		this.color = EnumChatFormatting.getValueByName(colorstr);
	}
	
	public String getFormattedString() {
		return (this.color + this.string);
	}
	
	public String getUnFormattedString() {
		return this.string;
	}
	
	public EnumChatFormatting getColor() {
		return this.color;
	}
	
	public String getColorString() {
		return this.color.getFriendlyName();
	}
}
