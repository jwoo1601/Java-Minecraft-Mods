package noah.tpblockmod;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noah.tpblockmod.block.BlockTeleport;
import noah.tpblockmod.command.LinkBlockCommand;
import noah.tpblockmod.command.RegisterPlaceCommand;
import noah.tpblockmod.proxy.CProxy;
import noah.tpblockmod.proxy.Place;
import noah.tpblockmod.proxy.ServerProcessor;

@Mod(modid=ModInfo.MOD_ID, name=ModInfo.MOD_NAME, version=ModInfo.MOD_VERSION)
public class ModMain {
	@Instance(ModInfo.MOD_ID)
	public static ModMain Instance;
	
	@SidedProxy(clientSide="noah.tpblockmod.proxy.ClientProcessor", 
		        serverSide="noah.tpblockmod.proxy.ServerProcessor")
    public static CProxy Proxy;
	
	public static void DebugOutput(String message) {
		System.out.println("[NOAH_DEBUG]: " + message);
	}
	
	public static final MinecraftServer SERVER = MinecraftServer.getServer();
	
	public static boolean isOp(String playername) {
		String[] arr = SERVER.getConfigurationManager().getOppedPlayerNames();
		
		for (String name : arr) {
			if (name == playername)
				return true;
		}
		
		return false;
	}
	
	public static boolean isTPBlock(World world, BlockPos pos) {
		String name = world.getBlockState(pos.add(0, -1, 0)).getBlock().getUnlocalizedName();
		if (name.equals("tile." + BlockTeleport.BLOCK_UNL_NAME))
			return true;
		else
			return false;
	}
	
	public static Place searchPlaceByPos(BlockPos pos) {
		for (int i=0;i<ServerProcessor.PlaceList.size();i++) {
			Place place = ServerProcessor.PlaceList.get(i);
			
			if (place.isEqualTo(pos))
				return place;
		}
		
		return null;
	}
	
	public static Place searchPlaceByName(String name) {
		for (int i=0;i<ServerProcessor.PlaceList.size();i++) {
			Place place = ServerProcessor.PlaceList.get(i);
			
			if (place.getPlaceName().equals(name))
				return place;
		}
		
		return null;
	}
	
	public static Place searchLinkedPlaceByPos(BlockPos pos) {
		for (int i=0;i<ServerProcessor.LinkedBlockList.size();i++) {
			Place item = ServerProcessor.LinkedBlockList.get(i);
			
			if(item.isEqualTo(pos))
				return searchPlaceByName(item.getPlaceName());
		}
		
		return null;
	}
	
	@EventHandler
	public void Initialize(FMLPreInitializationEvent e) {
		ModMetadata data = e.getModMetadata();
		data.autogenerated = false;
		data.name = (EnumChatFormatting.GREEN + ModInfo.MOD_LOCAL_NAME);
		data.authorList.add(ModInfo.MOD_AUTHOR);
		data.credits = (EnumChatFormatting.YELLOW + ModInfo.PROJECT);
		data.description = (StatCollector.translateToLocal("mod.description"));
		data.logoFile = "assets/teleportblockmod/textures/misc/logo.png";
		
		GameRegistry.registerBlock(new BlockTeleport(), "teleport_block");
	}
	
	@EventHandler
	public void Loaded(FMLInitializationEvent e) {
		Proxy.Loaded(e);
	}
	
	@EventHandler
	public void dd(FMLPostInitializationEvent e) {
		
	}
	
	@EventHandler
	public void ServerStart(FMLServerStartingEvent e) {
		e.registerServerCommand(new RegisterPlaceCommand());
		e.registerServerCommand(new LinkBlockCommand());
	}
	
	@EventHandler
	public void ServerStarted(FMLServerStartedEvent e) {
		WorldServer[] s = MinecraftServer.getServer().worldServers;
		for(int i=0; i<s.length; i++) {
			System.out.println("World[" + i + "] Name=" + s[i].provider.getDimensionName());
		}
	}
}
