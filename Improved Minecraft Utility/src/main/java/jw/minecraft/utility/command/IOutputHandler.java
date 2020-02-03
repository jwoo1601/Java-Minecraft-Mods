package jw.minecraft.utility.command;

import javax.annotation.Nonnull;

import jw.minecraft.utility.addon.AddonData;
import jw.minecraft.utility.catchable.Catchable;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public interface IOutputHandler {
	
	IOutputHandler bind(ICommandSender executor);
	
	IOutputHandler bind(EntityPlayer player);
	
	IOutputHandler printf(@Nonnull String msg, Object... args);
	
	IOutputHandler printp(@Nonnull String msg);
	
	IOutputHandler printc(@Nonnull Catchable c);
	
	IOutputHandler append(@Nonnull String text);
	
	IOutputHandler flush();
	
	IOutputHandler print();
}