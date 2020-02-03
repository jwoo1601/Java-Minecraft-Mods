package jw.minecraft.utility.blockguard;

import jw.minecraft.utility.addon.IUnit;
import jw.minecraft.utility.blockguard.command.BlockGuardCommand;
import jw.minecraft.utility.blockguard.event.BlockEventHandler;
import jw.minecraft.utility.blockguard.event.NetworkEventHandler;
import jw.minecraft.utility.blockguard.event.PlayerEventHandler;
import jw.minecraft.utility.blockguard.event.WorldEventHandler;
import jw.minecraft.utility.event.EventHandlerRegistry;

public class BlockGuardUnit implements IUnit {
	
	@Override
	public void initialize() {
	}

	@Override
	public void registerEventHandler(EventHandlerRegistry registry) {
		registry.register(BlockEventHandler.class);
		registry.register(PlayerEventHandler.class);
		registry.register(NetworkEventHandler.class);
		registry.register(WorldEventHandler.class);
	}

	@Override
	public void dispose() {
	}

}
