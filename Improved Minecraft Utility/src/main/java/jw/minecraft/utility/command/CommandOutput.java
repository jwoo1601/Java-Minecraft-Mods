package jw.minecraft.utility.command;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import jw.minecraft.utility.addon.AddonData;
import jw.minecraft.utility.catchable.Catchable;
import jw.minecraft.utility.localization.Parser;
import jw.minecraft.utility.localization.Parser.ParseType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class CommandOutput {
	
	public static void sendRaw(EntityPlayer player, @Nonnull String message) {
		if (message == null)
			throw new NullPointerException("String message");
		
		if (player == null)
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(message));
		else
			player.addChatMessage(new ChatComponentText(message));
	}
	
	public static IOutputHandler getHandler(@Nonnull String name) {
		checkNotNull(name, "name must not be null!");
		
		return new StandardHandler(name);
	}
	
	public static IOutputHandler getHandler(@Nonnull AddonData data) {
		checkNotNull(data, "data must not be null!");
		
		return new StandardHandler(data.Name);
	}
	
	public static class StandardHandler implements IOutputHandler {
		
		private final String Prefix;
		private final StringBuilder buffer = new StringBuilder();
		private ICommandSender executor = null;
		
		public StandardHandler(String prefix) {
			Prefix = prefix;
		}
		
		@Override
		public IOutputHandler bind(ICommandSender executor) {
			this.executor = executor;
			return this;
		}

		@Override
		public IOutputHandler bind(EntityPlayer player) {
			executor = player;
			return this;
		}
		
		public void send(IChatComponent component) {
			checkNotNull(component, "component must not be null!");
			
			if (executor == null)
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(component);
			else
				executor.addChatMessage(component);
		}

		@Override
		public IOutputHandler printf(String msg, Object... args) {
			String message = checkNotNull(msg, "msg must not be null!");
			message = String.format(message, args);
			
			ChatComponentText text = new ChatComponentText(Prefix + message);
			send(text);
			return this;
		}

		@Override
		public IOutputHandler printp(String msg) {
			String message = checkNotNull(msg, "msg must not be null!");
			message = Parser.parse(message, ParseType.TRANSLATE);
			
			ChatComponentText text = new ChatComponentText(Prefix + message);
			send(text);
			return this;
		}

		@Override
		public IOutputHandler printc(Catchable target) {
			IChatComponent text = new ChatComponentText(Prefix + checkNotNull(target, "target must not be null!").getChatComponent().getUnformattedTextForChat());
			send(text);
			return this;
		}

		@Override
		public IOutputHandler append(String text) {
			buffer.append(text);
			return this;
		}

		@Override
		public IOutputHandler flush() {
			if (!buffer.toString().equals(""))
				buffer.delete(0, buffer.length());
			return this;
		}

		@Override
		public IOutputHandler print() {
			printf(buffer.toString());
			return this;
		}
		
	}

}