package jwk.minecraft.garden.client;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.Font;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.CommonProxy;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.currency.CurrencyClient;
import jwk.minecraft.garden.client.event.ClientEventHandler;
import jwk.minecraft.garden.client.event.ClientNetworkEventHandler;
import jwk.minecraft.garden.client.event.InputEventHandler;
import jwk.minecraft.garden.client.event.RenderOverlayEventHandler;
import jwk.minecraft.garden.client.event.RenderWorldEventHandler;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.client.flower.FlowerInfoCache;
import jwk.minecraft.garden.client.font.Fonts;
import jwk.minecraft.garden.client.gui.GuiPurchaseShop;
import jwk.minecraft.garden.client.gui.GuiPurchaseShopSetup;
import jwk.minecraft.garden.client.gui.GuiSaleShop;
import jwk.minecraft.garden.client.gui.GuiSaleShopSetup;
import jwk.minecraft.garden.client.gui.GuiShop;
import jwk.minecraft.garden.client.gui.GuiShopSetup;
import jwk.minecraft.garden.client.team.FakeTeam;
import jwk.minecraft.garden.client.team.TeamClient;
import jwk.minecraft.garden.client.timer.FlowerInfoTracker;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.events.PlayerEventHandler;
import jwk.minecraft.garden.shop.ShopData;
import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.timer.SidedTickListenerList;
import jwk.minecraft.garden.timer.TickTimer;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.ReflectionHelper;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.MinecraftForge;

public class Client extends CommonProxy {
	
	private Queue<FlowerInfo> displayFlowerInfoList = Queues.newConcurrentLinkedQueue();
	
	private FlowerInfoCache clientCache = new FlowerInfoCache();
	
	private TeamClient TeamClientObj = null;
	private CurrencyClient CurrencyClientObj = new CurrencyClient(CurrencyYD.INSTANCE);
	
	@Override
	public void onFMLLifeCycleEvent(FMLPreInitializationEvent e) {
		super.onFMLLifeCycleEvent(e);
		
		FMLCommonHandler.instance().bus().register(new ClientNetworkEventHandler());
		FMLCommonHandler.instance().bus().register(new ClientEventHandler());
		FMLCommonHandler.instance().bus().register(new InputEventHandler());
		MinecraftForge.EVENT_BUS.register(new RenderWorldEventHandler());
		MinecraftForge.EVENT_BUS.register(new RenderOverlayEventHandler());
	}
	
	@Override
	public void onFMLLifeCycleEvent(FMLPostInitializationEvent e) {
		super.onFMLLifeCycleEvent(e);
		
		colorCode = ReflectionHelper.getValue(FontRenderer.class, Minecraft.getMinecraft().fontRenderer, "colorCode");
		
		if (colorCode == null)
			colorCode = ReflectionHelper.getValue(FontRenderer.class, Minecraft.getMinecraft().fontRenderer, "field_78285_g");
		
		Fonts.initializeFonts();
		CurrencyClientObj.init();
		
		TaskManager.instance().startSkinLoader();
	}
	
	@Override
	public void onSidedWorldLoad(World world) {
		super.onSidedWorldLoad(world);
		
		clientCache.onLoad();
	}
	
	@Override
	public void onSidedWorldUnload(World world) {
		super.onSidedWorldUnload(world);
		
		clientCache.onUnload();
		
		if (!displayFlowerInfoList.isEmpty())
			displayFlowerInfoList.clear();
	}
	
	@Override
	public void addDisplayFlowerInfo(@Nonnull FlowerInfo info) {
		if (info == null) {
			System.err.println("Display Flower Info: null");
			return;
		}
		
		displayFlowerInfoList.add(info);
		
		FlowerInfoTracker tracker = new FlowerInfoTracker(info);
		clientTickListenerList.addListener(tracker);
		tracker.start();
	}
	
	@Override
	public void removeDisplayFlowerInfo(@Nonnull FlowerInfo info) {
		checkNotNull(info);
		
		displayFlowerInfoList.remove(info);
	}
	
	@Override
	public Iterator<FlowerInfo> getDisplayFlowerInfoIterator() { return displayFlowerInfoList.iterator(); }
	
	@Override
	public void setCurrentCache(FlowerInfoCache cache) { clientCache = cache; }
	
	@Override
	public FlowerInfoCache getCurrentCache() { return clientCache; }
	
	@Override
	public TeamClient getTeamClient() { return TeamClientObj; }
	
	@Override
	public CurrencyClient getCurrencyClient() { return CurrencyClientObj; }
	
	@Override
	public void loadClientResources() {		
		TeamClientObj = new TeamClient();
		TeamClientObj.onLoad();
		
		CurrencyClientObj = new CurrencyClient(CurrencyYD.INSTANCE);
		CurrencyClientObj.onLoad();
	}
	
	@Override
	public void unloadClientResources() {
		TeamClientObj.onUnload();
		CurrencyClientObj.onUnload();
	}
	
	private SidedTickListenerList clientTickListenerList = new SidedTickListenerList(Side.CLIENT);
	
	@Override
	public SidedTickListenerList getClientTickListenerList() {
		return clientTickListenerList;
	}
	
	private FakeTeam currentTeam = null;
	
	@Override
	public FakeTeam getCurrentTeam() {
		return currentTeam;
	}
	
	@Override
	public void setCurrentTeam(FakeTeam team) {
		currentTeam = team;
	}
	
	private InventoryTeamChest invTeam = null;
	
	@Override
	public InventoryTeamChest getInventoryTeam() {
		return invTeam;
	}
	
	@Override
	public void setInventoryTeam(InventoryTeamChest invTeam) {
		this.invTeam = invTeam;
	}
	
	@Override
	public FakeTeam newFakeTeam(String teamName) {
		return new FakeTeam(teamName);
	}
	
	private ShopData shopData = new ShopData();
	
	@Override
	public ShopData getCurrentShopData() { return shopData; }
	
	@Override
	public void openGuiShop() {
		
		if (shopData.getShopType() == ShopType.PURCHASE)
			Minecraft.getMinecraft().displayGuiScreen(new GuiPurchaseShop());
		
		else if (shopData.getShopType() == ShopType.SALE)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSaleShop());
	}
	
	@Override
	public void openGuiShopSetup() {
		
		if (shopData.getShopType() == ShopType.PURCHASE)
			Minecraft.getMinecraft().displayGuiScreen(new GuiPurchaseShopSetup());
		
		else if (shopData.getShopType() == ShopType.SALE)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSaleShopSetup());
	}
	
	@Override
	public void closeGuiShop() {
		
		if (Minecraft.getMinecraft().currentScreen instanceof GuiShop)
			Minecraft.getMinecraft().displayGuiScreen(null);
	}
	
	@Override
	public void closeGuiShopSetup() {
		
		if (Minecraft.getMinecraft().currentScreen instanceof GuiShopSetup)
			Minecraft.getMinecraft().displayGuiScreen(null);
	}
	
	private int[] colorCode = null;
	
	@Override
	public int[] getVanillaColorCode() { return colorCode; }
	
	private InventoryWarp invWarp = this.DEFAULT_INV_WARP;
	
	@Override
	public InventoryWarp getInventoryWarp() { return invWarp; }
	
	@Override
	public void setInventoryWarp(InventoryWarp invWarp) {
		this.invWarp = invWarp == null? DEFAULT_INV_WARP : invWarp;
	}
	
}
