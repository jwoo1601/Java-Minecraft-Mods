package library;

import static library.Reference.WaterLevelTrackerSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mint.seobaragi.creativetab.TabSeoneng;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.player.tracker.PlayerColdZoneTracker;
import mint.seobaragi.player.tracker.PlayerHotZoneTracker;
import mint.seobaragi.player.tracker.PlayerJjimjilZoneTracker;
import mint.seobaragi.player.tracker.PlayerWaterLevelTracker;
import mint.seobaragi.properties.PropertyPlayerStat;
import mint.seobaragi.region.CuboidComponent;
import mint.seobaragi.region.CuboidRegion;
import mint.seobaragi.renderer.RenderNumber.TextureInfo;
import mint.seobaragi.tasks.TaskColdZone;
import mint.seobaragi.tasks.TaskHotZone;
import mint.seobaragi.tasks.TaskJjimJilZone;
import mint.seobaragi.tasks.TaskWaterLevel;
import mint.seobaragi.tasks.TickTimer;
import mint.seobaragi.vecmath.Vec3i;

public class Reference
{
	public static final String MODID = "seobaragi";
	public static final String NAME = "TtaemiriKing";
	public static final String VERSION = "1.7.10-1.4.0.3-stable";
	public static final String CLIENT_PROXY_CLASS = "mint.seobaragi.proxy.ClientProxy";
	public static final String MAIN_PROXY_CLASS = "mint.seobaragi.proxy.MainProxy";
	public static final String SERVER_PROXY_CLASS = "mint.seobaragi.proxy.ServerProxy";
	
	public static final boolean DEBUG = false;
	
	public static final TabSeoneng tabSeoneng = new TabSeoneng();
	
	//GuiFont Texture Size - u v width height
	//Start Coordinate = (u, v)
	//width = 가로
	//height = 세로
	public static final TextureInfo INFO_ZERO	 = new TextureInfo(0, 		 0, 18, 23);
	public static final TextureInfo INFO_ONE	 = new TextureInfo(0.08593F, 0, 11, 23);
	public static final TextureInfo INFO_TWO	 = new TextureInfo(0.14843F, 0, 19, 23);
	public static final TextureInfo INFO_THREE	 = new TextureInfo(0.22656F, 0, 18, 23);
	public static final TextureInfo INFO_FOUR	 = new TextureInfo(0.30468F, 0, 19, 23);
	public static final TextureInfo INFO_FIVE	 = new TextureInfo(0.38281F, 0, 17, 23);
	public static final TextureInfo INFO_SIX	 = new TextureInfo(0.45703F, 0, 18, 23);
	public static final TextureInfo INFO_SEVEN	 = new TextureInfo(0.53515F, 0, 17, 23);
	public static final TextureInfo INFO_EIGHT	 = new TextureInfo(0.60937F, 0, 18, 23);
	public static final TextureInfo INFO_NINE	 = new TextureInfo(0.68359F, 0, 18, 23);
	
	public static final Map<UUID, CuboidRegion> currentRegionMap = new ConcurrentHashMap<UUID, CuboidRegion>();
	
	public static final CuboidRegion HOT_ZONE = new CuboidRegion("온탕", new CuboidComponent(Vec3i.createVector(384, 4, 338), Vec3i.createVector(361, 11, 348)));
	public static final CuboidRegion COLD_ZONE = new CuboidRegion("냉탕", new CuboidComponent(Vec3i.createVector(361, 4, 310), Vec3i.createVector(384, 11, 300)));
	public static final CuboidRegion JJIMJIL_ZONE = new CuboidRegion("찜질방", new CuboidComponent(Vec3i.createVector(388, 4, 314), Vec3i.createVector(403, 9, 334)));
	public static final CuboidRegion DEFAULT_ZONE = new CuboidRegion("목욕탕", new CuboidComponent(null));

	
	public static final Set<PlayerWaterLevelTracker> WaterLevelTrackerSet = Sets.newConcurrentHashSet();
	
	public static final Set<PlayerHotZoneTracker> HotZoneTrackerSet = Sets.newConcurrentHashSet();
	public static final Set<PlayerColdZoneTracker> ColdZoneTrackerSet = Sets.newConcurrentHashSet();
	public static final Set<PlayerJjimjilZoneTracker> JjimJilZoneTrackerSet = Sets.newConcurrentHashSet();
		
	//타이머
	public static TickTimer TimerHotZone = new TickTimer(new TaskHotZone());
	public static TickTimer TimerColdZone = new TickTimer(new TaskColdZone());
	public static TickTimer TimerJimJilZone = new TickTimer(new TaskJjimJilZone());
	public static TickTimer TimerWaterLevel = new TickTimer(new TaskWaterLevel());
	

	public static EntityPlayerMP getPlayerByUUID(UUID uid)
	{
		List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		for(EntityPlayerMP player : list)
		{
			if(player.getUniqueID().equals(uid))
				return player;
		}
		return null;
	}
	
	public static EntityPlayerMP getPlayerByName(String name)
	{
		List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		for(EntityPlayerMP player : list)
		{
			if(player.getDisplayName().equals(name))
				return player;
		}
		return null;
	}	
	
	public static CuboidRegion getCurrentRegion(EntityPlayer player) {
		Vec3i pos = Vec3i.createVector((int) player.posX, (int) player.posY, (int) player.posZ);
		
		if (HOT_ZONE.isPoisitionInside(pos))
			return HOT_ZONE;
		
		else if (COLD_ZONE.isPoisitionInside(pos))
			return COLD_ZONE;
		
		else if (JJIMJIL_ZONE.isPoisitionInside(pos))
			return JJIMJIL_ZONE;
		
		else
			return DEFAULT_ZONE;
	}
	
	public static PlayerWaterLevelTracker getTrackerFromPlayer(EntityPlayer player) {
		for (PlayerWaterLevelTracker tracker : WaterLevelTrackerSet) {
			
			if (tracker.getPlayer().getUniqueID().equals(player.getUniqueID()))
				return tracker;
		}
		
		return null;
	}
	
	public static void safeStopWaterLevelTracker(EntityPlayer player) {
		PlayerWaterLevelTracker tracker = getTrackerFromPlayer(player);
		
		if (tracker != null)
			tracker.stopTracking();
	}
	
	public static void safeStartWaterLevelTracker(EntityPlayer player) {
		PlayerWaterLevelTracker tracker = getTrackerFromPlayer(player);
		
		if (tracker != null)
			tracker.startTracking();
	}
	
	public static PlayerHotZoneTracker getHotZoneTracker(EntityPlayer player) {
		for (PlayerHotZoneTracker tracker : HotZoneTrackerSet) {
			
			if (tracker.getPlayer().getUniqueID().equals(player.getUniqueID()))
				return tracker;
		}
		
		return null;
	}
	
	public static void safeRemoveHotZoneTracker(EntityPlayer player) {
		PlayerHotZoneTracker tracker = getHotZoneTracker(player);
		
		if (tracker != null)
			HotZoneTrackerSet.remove(tracker.stopTracking());
	}
	
	public static PlayerColdZoneTracker getColdZoneTracker(EntityPlayer player) {
		for (PlayerColdZoneTracker tracker : ColdZoneTrackerSet) {
			
			if (tracker.getPlayer().getUniqueID().equals(player.getUniqueID()))
				return tracker;
		}
		
		return null;
	}
	
	public static void safeRemoveColdZoneTracker(EntityPlayer player) {
		PlayerColdZoneTracker tracker = getColdZoneTracker(player);
		
		if (tracker != null)
			ColdZoneTrackerSet.remove(tracker.stopTracking());
	}
	
	public static PlayerJjimjilZoneTracker getJjimjilZoneTracker(EntityPlayer player) {
		for (PlayerJjimjilZoneTracker tracker : JjimJilZoneTrackerSet) {
			
			if (tracker.getPlayer().getUniqueID().equals(player.getUniqueID()))
				return tracker;
		}
		
		return null;
	}
	
	public static void safeRemoveJjimjilZoneTracker(EntityPlayer player) {
		PlayerJjimjilZoneTracker tracker = getJjimjilZoneTracker(player);
		
		if (tracker != null)
			JjimJilZoneTrackerSet.remove(tracker.stopTracking());
	}
	
	public static PropertyPlayerStat getStatFromPlayer(@Nonnull EntityPlayer player) {
		IExtendedEntityProperties property = player.getExtendedProperties(PropertyPlayerStat.ID);
		return property instanceof PropertyPlayerStat? (PropertyPlayerStat) property : null;
	}
	
}
