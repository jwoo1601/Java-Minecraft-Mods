package mint.seobaragi.event;

import static library.Message.COLDWATERJOIN;
import static library.Message.GAMEOVER;
import static library.Message.GETDIRT;
import static library.Message.HOTWATERJOIN;
import static library.Message.JJIMJILBANDJOIN;
import static library.Message.NAME;
import static library.Message.ZONEOUT;
import static library.Reference.COLD_ZONE;
import static library.Reference.ColdZoneTrackerSet;
import static library.Reference.DEBUG;
import static library.Reference.DEFAULT_ZONE;
import static library.Reference.HOT_ZONE;
import static library.Reference.HotZoneTrackerSet;
import static library.Reference.JJIMJIL_ZONE;
import static library.Reference.JjimJilZoneTrackerSet;
import static library.Reference.currentRegionMap;
import static library.Reference.getColdZoneTracker;
import static library.Reference.getHotZoneTracker;
import static library.Reference.getJjimjilZoneTracker;
import static library.Reference.getTrackerFromPlayer;
import static library.Reference.safeRemoveColdZoneTracker;
import static library.Reference.safeRemoveHotZoneTracker;
import static library.Reference.safeRemoveJjimjilZoneTracker;
import static library.Reference.safeStartWaterLevelTracker;
import static library.Reference.safeStopWaterLevelTracker;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.UUID;

import library.Reference;
import mint.seobaragi.player.tracker.PlayerColdZoneTracker;
import mint.seobaragi.player.tracker.PlayerHotZoneTracker;
import mint.seobaragi.player.tracker.PlayerJjimjilZoneTracker;
import mint.seobaragi.properties.PropertyPlayerStat;
import mint.seobaragi.region.CuboidRegion;
import mint.seobaragi.vecmath.Vec3i;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ServerPlayerEventHandler
{
	private void updatePlayerRegionData(EntityPlayer player)
	{
		Vec3i pos = Vec3i.createVector((int) player.posX, (int) player.posY, (int) player.posZ);
		UUID uid = player.getUniqueID();
		CuboidRegion currentRegion = currentRegionMap.get(uid);
		
		
		if (HOT_ZONE.isPoisitionInside(pos))
		{	
			if (currentRegion != HOT_ZONE)
			{
				currentRegionMap.put(uid, HOT_ZONE);
				HotZoneTrackerSet.add(new PlayerHotZoneTracker(player).startTracking());
				player.addChatMessage(new ChatComponentText(NAME + HOTWATERJOIN));
			}
		}

		else if (COLD_ZONE.isPoisitionInside(pos))
		{
			
			if (currentRegion != COLD_ZONE)
			{
				currentRegionMap.put(uid, COLD_ZONE);
				ColdZoneTrackerSet.add(new PlayerColdZoneTracker(player).startTracking());
				player.addChatMessage(new ChatComponentText(NAME + COLDWATERJOIN));
			}
		}
		
		else if (JJIMJIL_ZONE.isPoisitionInside(pos))
		{
			if (currentRegion != JJIMJIL_ZONE)
			{
				currentRegionMap.put(uid, JJIMJIL_ZONE);
				JjimJilZoneTrackerSet.add(new PlayerJjimjilZoneTracker(player).startTracking());
				player.addChatMessage(new ChatComponentText(NAME + JJIMJILBANDJOIN));
			}
		}
		
		else
		{	
			if (currentRegion != DEFAULT_ZONE)
			{
				CuboidRegion previousRegion = currentRegionMap.put(uid, DEFAULT_ZONE);
				
				if (previousRegion == HOT_ZONE)
					safeRemoveHotZoneTracker(player);
				
				else if (previousRegion == COLD_ZONE)
					safeRemoveColdZoneTracker(player);
				
				else if (previousRegion == JJIMJIL_ZONE)
					safeRemoveJjimjilZoneTracker(player);
				
				else
					throw new IllegalArgumentException("previousRegion is not a valid Region!");
				
				player.addChatMessage(new ChatComponentText(NAME + ZONEOUT));
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;		
			PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
		
			//Water Event
			if(stat.waterLevel <= 7)
			{
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10, 1));
			}
			
			//Not Found Number Error
			if(stat.waterLevel < 0)
			{
				stat.setWaterLevel(PropertyPlayerStat.MIN_WATER_LEVEL);
			}
			
			updatePlayerRegionData(player);
			
			
			//Mining Event
			if(stat.dirtGauge >= 200)
			{
				player.addChatMessage(new ChatComponentText(GETDIRT));
				stat.resetDirtGaugeAndSendPacket().setDirtLevel(stat.dirtLevel + 1);
			}
		}
	}
	
	@SubscribeEvent
	public void onBreak(BreakEvent event)
	{
		if (!event.world.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) event.getPlayer();
			PropertyPlayerStat stat = ((PropertyPlayerStat) player.getExtendedProperties(ID));
		
			if(event.block.equals(Blocks.stone))
			{
				stat.setDirtGauge(stat.dirtGauge + 1);
			}
			
			else if(event.block.equals(Blocks.iron_ore))
			{
				stat.setDirtGauge(stat.dirtGauge + 5);
			}
		}
	}
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;		
			
			safeStopWaterLevelTracker(player);
		}
	}
}
