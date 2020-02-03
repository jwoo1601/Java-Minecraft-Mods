package noah.teleport.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.teleport.block.BlockTeleport;
import noah.teleport.block.TeleportData;
import noah.teleport.place.Place;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;

public class Server extends CommonProxy {
	@Override
	public void fmlLifeCycleEvent(FMLPreInitializationEvent e) {
		super.fmlLifeCycleEvent(e);
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLInitializationEvent e) {
		
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLPostInitializationEvent e) {
		
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLServerAboutToStartEvent e) {
		
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLServerStartingEvent e) {
		
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLServerStartedEvent e) {
		
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLServerStoppingEvent e) {
		
	}
	
	@Override
	public void fmlLifeCycleEvent(FMLServerStoppedEvent e) {
		
	}
}
