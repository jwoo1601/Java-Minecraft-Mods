package noah.minecraft.runaway;

import org.eclipse.jdt.annotation.NonNull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import noah.minecraft.runaway.command.SpongeCommand;
import noah.minecraft.runaway.event.*;
import noah.minecraft.runaway.proxy.*;

@Mod(modid=Common.MOD_ID, name=Common.MOD_NAME, version=Common.MOD_VERSION)
public class Noah {
	

	
	@Instance(Common.MOD_ID)
	public static Noah Instance;
	
	@SidedProxy(clientSide="noah.minecraft.runaway.proxy.Client", 
                serverSide="noah.minecraft.runaway.proxy.Server")
	public static Proxy Proxy;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent e) {
		Proxy.fmlLifeCycleEvent(e);
		k();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		Proxy.fmlLifeCycleEvent(e);
	}
	
	@EventHandler
	public void start(FMLServerStartingEvent e) {
		Proxy.fmlLifeCycleEvent(e);
	}
}
