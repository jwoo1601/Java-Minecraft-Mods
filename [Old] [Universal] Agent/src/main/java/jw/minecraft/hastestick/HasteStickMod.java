package jw.minecraft.hastestick;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import jw.minecraft.hastestick.commands.AdminCommand;
import jw.minecraft.hastestick.commands.RemoveNominationCommand;
import jw.minecraft.hastestick.events.PlayerEventHandler;
import jw.minecraft.hastestick.events.WorldEventHandler;
import jw.minecraft.hastestick.repository.AdminData;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "hastestick", name = "성급함 막대기 모드", version = "1.1.5-release", useMetadata = true, acceptedMinecraftVersions = "0.0.0", acceptableRemoteVersions = "*")
public class HasteStickMod {
	
	public static AdminData globalAdminData;
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e) {
		FMLLog.info("[HasteStickMod] Haste Stick Mod is loaded");
		
		PlayerEventHandler player = new PlayerEventHandler(MinecraftForge.EVENT_BUS);
		WorldEventHandler world = new WorldEventHandler(MinecraftForge.EVENT_BUS);
	}
	
	@EventHandler
	public void serverInitialize(FMLServerStartingEvent e) {
		e.registerServerCommand(new AdminCommand());
		e.registerServerCommand(new RemoveNominationCommand());
	}
}
