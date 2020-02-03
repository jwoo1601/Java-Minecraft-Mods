package jw.minecraft.utility.addon;

import cpw.mods.fml.common.event.*;
import jw.minecraft.utility.command.AddonCommand;
import jw.minecraft.utility.event.EventHandlerRegistry;
import net.minecraft.util.RegistrySimple;

public interface IUnit {
	
	void initialize();
	
	void registerEventHandler(EventHandlerRegistry registry);
	
	void dispose();
	
}
