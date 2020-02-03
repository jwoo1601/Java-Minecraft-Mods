package jwk.minecraft.garden;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.client.flower.FlowerInfoCache;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketDelivery;
import jwk.minecraft.garden.network.PacketFlowerProperty;
import jwk.minecraft.garden.network.PacketPlaySound;
import jwk.minecraft.garden.network.PacketShop;
import jwk.minecraft.garden.network.PacketTeam;
import jwk.minecraft.garden.network.PacketWarp;
import jwk.minecraft.garden.repository.FlowerPropsRepository;
import jwk.minecraft.garden.util.EnumLanguageType;
import net.minecraft.block.BlockFlower;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION,
     acceptedMinecraftVersions = ModInfo.MC_VERSION, acceptableRemoteVersions = ModInfo.VERSION)//, dependencies = "required-before:flowercraftmod")
public class ProjectGarden {
	
	public static final String PREFIX = "정원의 花人 * ";
	public static final String FORMATTED_PREFIX = EnumChatFormatting.DARK_GREEN + "정원의 " + EnumChatFormatting.GOLD + "花人" + EnumChatFormatting.GRAY + " * ";
	
	@Instance
	public static ProjectGarden instance;
	
	@SidedProxy(clientSide = "jwk.minecraft.garden.client.Client", serverSide = "jwk.minecraft.garden.server.Server")
	public static CommonProxy proxy;
	
	public static FlowerPropsRepository[] repository = null;
	
	public static final SimpleNetworkWrapper NET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);
	
	static {
		NET_HANDLER.registerMessage(PacketFlowerProperty.ClientHandler.class, PacketFlowerProperty.class, 0, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketCurrency.ClientHandler.class, PacketCurrency.class, 1, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketTeam.ClientHandler.class, PacketTeam.class, 2, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketDelivery.ClientHandler.class, PacketDelivery.class, 3, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketDelivery.ServerHandler.class, PacketDelivery.class, 4, Side.SERVER);
		NET_HANDLER.registerMessage(PacketShop.ClientHandler.class, PacketShop.class, 5, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketShop.ServerHandler.class, PacketShop.class, 6, Side.SERVER);
		NET_HANDLER.registerMessage(PacketPlaySound.ClientHandler.class, PacketPlaySound.class, 7, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketWarp.ClientHandler.class, PacketWarp.class, 9, Side.CLIENT);
	}	
	
	public static Logger logger;
	
	public static String getSaveDirectory() {
		return ".\\pjgarden";
	}
	
	// A Flag to decide Whether Debug Mode or not
	public static final boolean DEBUG = true;
	
	public static final EnumLanguageType LOCALE = EnumLanguageType.KO_KR;
	
	@EventHandler
	public void initialize(FMLPreInitializationEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void postInitialize(FMLPostInitializationEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverInitialize(FMLServerAboutToStartEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverPostInitialize(FMLServerStartedEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverStop(FMLServerStoppingEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void postStop(FMLServerStoppedEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	public static IChatComponent toFormatted(String text) {
		return new ChatComponentText(FORMATTED_PREFIX + EnumChatFormatting.WHITE + text);
	}
	
}
