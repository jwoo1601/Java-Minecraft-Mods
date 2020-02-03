package jw.minecraft.utility;

import static jw.minecraft.utility.LogHelper.info;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import jw.minecraft.utility.addon.Addon;
import jw.minecraft.utility.addon.AddonData;
import jw.minecraft.utility.blockguard.BlockGuard;
import jw.minecraft.utility.command.TransformedCommand;
import jw.minecraft.utility.event.EventHandlerRegistry;
import jw.minecraft.utility.permissions.Permissions;
import jw.minecraft.utility.transformers.AddonTransformer;
import jw.minecraft.utility.transformers.CommandTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

@Mod(modid=Common.MOD_ID, name=Common.MOD_DEFAULT_NAME, version=Common.MOD_VERSION, acceptableRemoteVersions = "*")
public class ImprovedMinecraftUtility {

	@Instance(Common.MOD_ID)
	public static ImprovedMinecraftUtility Instance;
	
	
	public static Map<String, AddonData> map = new HashMap<String, AddonData>();
	
	private void registerAddons() {
		AddonTransformer at = new AddonTransformer();
		AddonData bg = at.transform(BlockGuard.class);
		map.put(bg.Id, bg);
		LogHelper.info("Registered Addon <id=" + bg.Id + ", name=" + bg.Name + ", version=" + bg.Version + ">");
	}
	
	private void registerEventHandlers() {
		if (!map.isEmpty()) {		
			for (AddonData addon : map.values())
				addon.Unit.registerEventHandler(EventHandlerRegistry.getInstance());
			
			EventHandlerRegistry.getInstance().post();
		}
	}
	
	private void registerCommands(FMLServerStartingEvent e) {
		if (!map.isEmpty()) {
			CommandTransformer ct = new CommandTransformer();
		
			for (AddonData addon : map.values()) {
				e.registerServerCommand(new TransformedCommand(addon.Command));
				LogHelper.info("Registered SubCommand <name=" + addon.Command.CommandName + ", usage=" + addon.Command.CommandUsage + ">");
			}
		}
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent e) {
		Common.setupModMetadata(e.getModMetadata());
		
		registerAddons();
		
		registerEventHandlers();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent e) {
		
	}
	
	@EventHandler
	public void server_preinit(FMLServerAboutToStartEvent e) {
		
	}
	
	@EventHandler
	public void server_init(FMLServerStartingEvent e) {
		registerCommands(e);
	}
	
	@EventHandler
	public void server_run(FMLServerStartedEvent e) {
		
	}
	
	@EventHandler
	public void server_prestop(FMLServerStoppingEvent e) {
		
	}
	
	@EventHandler
	public void server_stop(FMLServerStoppedEvent e) {
		
	}
}