package jwk.minecraft.kongagent;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.kongagent.commands.AdminCommand;
import jwk.minecraft.kongagent.commands.RemoveNominationCommand;
import jwk.minecraft.kongagent.commands.SignCommand;
import jwk.minecraft.kongagent.events.PlayerEventHandler;
import jwk.minecraft.kongagent.events.WorldEventHandler;
import jwk.minecraft.kongagent.items.ItemBlueWaterGun;
import jwk.minecraft.kongagent.items.ItemKongIcon;
import jwk.minecraft.kongagent.items.ItemYellowWaterGun;
import jwk.minecraft.kongagent.repository.AdminData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "kongagent", name = "미스터리를 찾아라 : 에이전트", version = "1.7.10-1.1.3.1", useMetadata = true, acceptedMinecraftVersions = "1.7.10", acceptableRemoteVersions = "1.7.10")
public class KongAgent {
	
	public static AdminData globalAdminData;
	
	public static CreativeTabs tabKongAgent;
	
	public static final ItemKongIcon KONG_ICON = new ItemKongIcon();
	public static final ItemBlueWaterGun BLUE_WATER_GUN = new ItemBlueWaterGun();	
	public static final ItemYellowWaterGun YELLOW_WATER_GUN = new ItemYellowWaterGun();
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e) {
		PlayerEventHandler player = new PlayerEventHandler(MinecraftForge.EVENT_BUS);
		WorldEventHandler world = new WorldEventHandler(MinecraftForge.EVENT_BUS);
		
		GameRegistry.registerItem(KONG_ICON, KONG_ICON.ITEM_NAME);
		
		tabKongAgent = new CreativeTabs("tab_kong_agent") {
			
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return GameRegistry.findItem("kongagent", KONG_ICON.ITEM_NAME);
			}
			
		};
		
		GameRegistry.registerItem(BLUE_WATER_GUN, BLUE_WATER_GUN.ITEM_NAME);
		GameRegistry.registerItem(YELLOW_WATER_GUN, YELLOW_WATER_GUN.ITEM_NAME);
		
		BLUE_WATER_GUN.setCreativeTab(tabKongAgent);
		YELLOW_WATER_GUN.setCreativeTab(tabKongAgent);
		
		FMLLog.info("[KongAgent] Kong Agent Mod is Loaded");
	}
	
	@EventHandler
	public void serverInitialize(FMLServerStartingEvent e) {
		e.registerServerCommand(new AdminCommand());
		e.registerServerCommand(new RemoveNominationCommand());
		e.registerServerCommand(new SignCommand());
	}
}
