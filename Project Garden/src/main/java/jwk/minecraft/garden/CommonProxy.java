package jwk.minecraft.garden;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.currency.CurrencyClient;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.client.flower.FlowerInfoCache;
import jwk.minecraft.garden.client.team.FakeTeam;
import jwk.minecraft.garden.client.team.TeamClient;
import jwk.minecraft.garden.client.timer.FlowerInfoTracker;
import jwk.minecraft.garden.command.CommandEnchant;
import jwk.minecraft.garden.command.CommandSave;
import jwk.minecraft.garden.command.CommandShop;
import jwk.minecraft.garden.command.CommandTeam;
import jwk.minecraft.garden.command.CommandTeamCurrency;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.events.PlayerEventHandler;
import jwk.minecraft.garden.events.ServerEventHandler;
import jwk.minecraft.garden.events.ServerNetworkEventHandler;
import jwk.minecraft.garden.events.WorldEventHandler;
import jwk.minecraft.garden.flower.Flowers;
import jwk.minecraft.garden.item.Item3Kids;
import jwk.minecraft.garden.item.ItemAttackTicket;
import jwk.minecraft.garden.item.ItemFlowerBouquet;
import jwk.minecraft.garden.item.ItemFlowerFactory;
import jwk.minecraft.garden.item.ItemFlowerShop;
import jwk.minecraft.garden.item.ItemTwine;
import jwk.minecraft.garden.item.ItemUniversalWand;
import jwk.minecraft.garden.item.ItemWarpClock;
import jwk.minecraft.garden.item.ItemWrapper;
import jwk.minecraft.garden.repository.FlowerPropsRepository;
import jwk.minecraft.garden.shop.ShopData;
import jwk.minecraft.garden.tab.Tab3Kids;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.timer.SidedTickListenerList;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy {
	
	public final InventoryWarp DEFAULT_INV_WARP = new InventoryWarp(null);

	public void onFMLLifeCycleEvent(FMLPreInitializationEvent e) {
		ModInfo.setupMetadata(e.getModMetadata());
		
		ProjectGarden.logger = e.getModLog();
		
		registerItems();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(ProjectGarden.instance, new FlowerGuiHandler());
		
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		FMLCommonHandler.instance().bus().register(new ServerEventHandler());
		FMLCommonHandler.instance().bus().register(new ServerNetworkEventHandler());
		
		loadClientResources();
	}
	
	public static final Item3Kids ITEM_3KIDS = new Item3Kids();
	public static final ItemFlowerShop ITEM_FLOWER_SHOP = new ItemFlowerShop();
	public static final ItemFlowerFactory ITEM_FLOWER_FACTORY = new ItemFlowerFactory();
	
	public static final ItemUniversalWand UNIVERSAL_WAND = new ItemUniversalWand();
	
	public static final ItemFlowerBouquet FLOWER_BOUQUET = new ItemFlowerBouquet();
	public static final ItemTwine TWINE = new ItemTwine();
	public static final ItemWrapper WRAPPER = new ItemWrapper();
	
	public static final ItemWarpClock WARP_CLOCK = new ItemWarpClock();
	
	public static final ItemAttackTicket ATTACK_TICKET = new ItemAttackTicket();
	
	private void registerItems() {
		GameRegistry.registerItem(ITEM_3KIDS, ITEM_3KIDS.NAME);
		GameRegistry.registerItem(ITEM_FLOWER_SHOP, ITEM_FLOWER_SHOP.NAME);
		GameRegistry.registerItem(ITEM_FLOWER_FACTORY, ITEM_FLOWER_FACTORY.NAME);
		
		GameRegistry.registerItem(UNIVERSAL_WAND, UNIVERSAL_WAND.NAME);
		
		GameRegistry.registerItem(FLOWER_BOUQUET, FLOWER_BOUQUET.PREFIX);
		GameRegistry.registerItem(TWINE, TWINE.NAME);
		GameRegistry.registerItem(WRAPPER, WRAPPER.NAME);
		
		GameRegistry.registerItem(WARP_CLOCK, WARP_CLOCK.NAME);
		GameRegistry.registerItem(ATTACK_TICKET, ATTACK_TICKET.NAME);
	}
	
	private void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 0), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_FCFlower, 1, 6), 'W', WRAPPER, 'T', TWINE);
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 1), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_FCFlower, 1, 2), 'W', WRAPPER, 'T', TWINE);
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 2), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_FCFlower, 1, 5), 'W', WRAPPER, 'T', TWINE);
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 3), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_Flower2, 1, 3), 'W', WRAPPER, 'T', TWINE);
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 4), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_Flower2, 1, 8), 'W', WRAPPER, 'T', TWINE);
    	GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 5), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_FCFlower, 1, 12), 'W', WRAPPER, 'T', TWINE);
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 6), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_Flower2, 1, 7), 'W', WRAPPER, 'T', TWINE);
		GameRegistry.addShapedRecipe(new ItemStack(FLOWER_BOUQUET, 1, 7), "FFF", "WTW", " W ", 'F', new ItemStack(Flowers.Item_FCFlower, 1, 13), 'W', WRAPPER, 'T', TWINE);
	}
	
	public void onFMLLifeCycleEvent(FMLInitializationEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLPostInitializationEvent e) {
		registerRecipes();
	}
	
	public void onFMLLifeCycleEvent(FMLServerAboutToStartEvent e) {
		loadServerResources();
	}
	
	public void onFMLLifeCycleEvent(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandTeam());
		e.registerServerCommand(new CommandTeamCurrency());
		e.registerServerCommand(new CommandSave());
		e.registerServerCommand(new CommandEnchant());
		e.registerServerCommand(new CommandShop());
	}
	
	public void onFMLLifeCycleEvent(FMLServerStartedEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLServerStoppingEvent e) {
		saveServerResources();
	}
	
	public void onFMLLifeCycleEvent(FMLServerStoppedEvent e) {
		unloadServerResources();
	}
	
	public void loadClientResources() { }
	public void unloadClientResources() { }
	
	public void loadServerResources() {		
		CurrencyYD.INSTANCE.getManager().onLoad();
		TeamRegistry.onLoad();
		
		serverTickListenerList = new SidedTickListenerList(Side.SERVER);
	}
	
	public void unloadServerResources() {
		releaseServerTickListeners();
		
		CurrencyYD.INSTANCE.getManager().onUnload();
		TeamRegistry.onUnload();
	}
	
	public void saveServerResources() {
		CurrencyYD.INSTANCE.getManager().onSave();
		TeamRegistry.onSave();
	}
	
	// Client Only Variables
	public void addDisplayFlowerInfo(FlowerInfo info) { }
	public void removeDisplayFlowerInfo(FlowerInfo info) { }
	public Iterator<FlowerInfo> getDisplayFlowerInfoIterator() { return null; }
	
	public void setCurrentCache(FlowerInfoCache cache) { }
	public FlowerInfoCache getCurrentCache() { return null; }
	
	public TeamClient getTeamClient() { return null; }
	public CurrencyClient getCurrencyClient() { return null; }
	
	public FakeTeam getCurrentTeam() { return null; }
	public void setCurrentTeam(FakeTeam team) { }
	
	public InventoryTeamChest getInventoryTeam() { return null; }
	public void setInventoryTeam(InventoryTeamChest invTeam) { }
	
	public InventoryWarp getInventoryWarp() { return null; }
	public void setInventoryWarp(InventoryWarp invWarp) { }
	
	public FakeTeam newFakeTeam(String teamName) { return null; }
	
	public ShopData getCurrentShopData() { return null; }
	
	public void openGuiShop() {	}
	public void openGuiShopSetup() { }
	public void closeGuiShop() { }
	public void closeGuiShopSetup() { }
	
	public int[] getVanillaColorCode() { return null; }
	
	public SidedTickListenerList getSidedTickListenerList() {
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return getClientTickListenerList();
		
		return getServerTickListenerList();
	}
	
	public SidedTickListenerList getClientTickListenerList() { return null; }
	public SidedTickListenerList getServerTickListenerList() { return serverTickListenerList; }
	
	private SidedTickListenerList serverTickListenerList;
	
	private void releaseServerTickListeners() {
		
		if (serverTickListenerList != null) {
			serverTickListenerList.removeAll();
			serverTickListenerList = null;
		}
	}
	
	// World Event
	
	public void onSidedWorldLoad(World world) { 
		
		if (!world.isRemote) {
			
			if (ProjectGarden.repository == null)
				ProjectGarden.repository = new FlowerPropsRepository[1];
			
			int dId = world.provider.dimensionId;			
			int index = Flowers.getIndexFromId(dId);
			
			if (index == -1)
				index = Flowers.nextIndex(dId);
			
			if (ProjectGarden.repository.length < index + 1) {
				FlowerPropsRepository[] tmp = new FlowerPropsRepository[index + 2];
				
				for(int i=0; i < ProjectGarden.repository.length; i++)
					tmp[i] = ProjectGarden.repository[i];
				
				ProjectGarden.repository = tmp;
			}
			
			ProjectGarden.repository[index] = FlowerPropsRepository.get(world);
		}		
	}
	
	public void onSidedWorldSave(World world) { 
		
		if (!world.isRemote) {
			
			for (FlowerPropsRepository repository : ProjectGarden.repository)
				repository.markDirty();
			
			if (world.provider.dimensionId == 0)
				saveServerResources();
		}		
	}
	
	public void onSidedWorldUnload(World world) {
		
		if (!world.isRemote) {
			
			if (ProjectGarden.repository != null) {
				
				for (FlowerPropsRepository repository : ProjectGarden.repository) {
				
					if (repository != null)
						repository = null;
				}
			
				ProjectGarden.repository = null;
			}
		}
	}
	
}
