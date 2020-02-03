package noah.teleport.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ModMetadata;
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
import noah.teleport.command.ShowPlaceListCommand;
import noah.teleport.eventhandler.Test;
import noah.teleport.eventhandler.WorldEventHandler;
import noah.teleport.place.Place;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;

public class CommonProxy {
	public static CreativeTabs TAB_TELEPORT;
	public static BlockTeleport BLOCK_TELEPORT;
	public static PlaceRegistry[] PlaceReg; // vanila : 0 => overworld
	                                        //          1 => nether
	                                        //          2 => the end
	@SideOnly(Side.CLIENT)
	public static PlaceRegistry FakePlaceReg;
	
	// FML Client Side Event
	
	public void fmlLifeCycleEvent(FMLPreInitializationEvent e) {
		Common.setupModMetadata(e.getModMetadata());
		
		BLOCK_TELEPORT = new BlockTeleport();
		GameRegistry.registerBlock(BLOCK_TELEPORT, BlockTeleport.REG_NAME);
		
		TAB_TELEPORT = new CreativeTabs("teleporttab") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return GameRegistry.findItem(Common.MOD_ID, BlockTeleport.REG_NAME);
			}
		};
		
		BLOCK_TELEPORT.setCreativeTab(TAB_TELEPORT);

		GameRegistry.registerTileEntity(TeleportData.class, "teleportdata");
		
		WorldEventHandler handler = new WorldEventHandler(MinecraftForge.EVENT_BUS);
		MinecraftForge.EVENT_BUS.register(new Test());
	}
	
	public void fmlLifeCycleEvent(FMLInitializationEvent e) {
	}
	
	public void fmlLifeCycleEvent(FMLPostInitializationEvent e) {
		
	}
	
	// (Integrated/Dedicated) FML Server Side Event
	
	public void fmlLifeCycleEvent(FMLServerAboutToStartEvent e) {
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStartingEvent e) {
		e.registerServerCommand(new ShowPlaceListCommand());
	}
	
	public void fmlLifeCycleEvent(FMLServerStartedEvent e) {
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStoppingEvent e) {
		
	}
	
	public void fmlLifeCycleEvent(FMLServerStoppedEvent e) {
		
	}
	
	public void openTeleportGui(TeleportData data) {
		
	}
}