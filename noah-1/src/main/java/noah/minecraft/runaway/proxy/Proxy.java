package noah.minecraft.runaway.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import noah.minecraft.runaway.Common;
import noah.minecraft.runaway.command.SpongeCommand;
import noah.minecraft.runaway.event.PlayerEventHandler;
import noah.minecraft.runaway.event.RenderHUDEventHandler;
import noah.minecraft.runaway.event.teh;
import noah.minecraft.runaway.packet.SpongePacket;

public class Proxy {
	
	public boolean isGameStarted = false;
	public EntityPlayer Criminal = null;
	
	public boolean CisGameStarted = false;
	public EntityPlayer CCriminal = null;
	
	public static int ID = 0;
	public static final SimpleNetworkWrapper HINSTANCE;
	
	public static PlayerEventHandler Handler = new PlayerEventHandler();
	
	static  {
		HINSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Common.MOD_ID);
		HINSTANCE.registerMessage(SpongePacket.SpongePacketHandler.class, SpongePacket.class, ID++, Side.CLIENT);
		HINSTANCE.registerMessage(SpongePacket.SpongePacketHandler.class, SpongePacket.class, ID++, Side.SERVER);
	}
	
	// FML Client Side Event
	
	public void fmlLifeCycleEvent(FMLPreInitializationEvent e) {
		Common.setupModMetadata(e.getModMetadata());
		MinecraftForge.EVENT_BUS.register(Handler);
		MinecraftForge.EVENT_BUS.register(new teh());
	}
	
	public void fmlLifeCycleEvent(FMLInitializationEvent e) {
	}
	
	public void fmlLifeCycleEvent(FMLPostInitializationEvent e) {
		
	}
	
	// (Integrated/Dedicated) FML Server Side Event
	
	public void fmlLifeCycleEvent(FMLServerAboutToStartEvent e) {
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStartingEvent e) {
		e.registerServerCommand(new SpongeCommand());
	}
	
	public void fmlLifeCycleEvent(FMLServerStartedEvent e) {
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStoppingEvent e) {
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStoppedEvent e) {
		
	}
}
