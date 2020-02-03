package jw.minecraft.utility.command;

import java.lang.reflect.InvocationTargetException;

import jw.minecraft.utility.permissions.IPermission;
import net.minecraft.command.ICommandSender;

public interface IExecutable {
	
	void execute(ICommandSender executor, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
}
