package jw.minecraft.utility.blockguard.event;

import java.util.Iterator;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jw.minecraft.utility.blockguard.BlockGuard;
import jw.minecraft.utility.blockguard.region.IRegion;
import jw.minecraft.utility.blockguard.region.PipedRegion;
import jw.minecraft.utility.blockguard.repository.Mode;
import jw.minecraft.utility.blockguard.repository.RangeData;
import jw.minecraft.utility.blockguard.repository.RegionMap;
import jw.minecraft.utility.blockguard.repository.State;
import jw.minecraft.utility.event.EventHandler;
import jw.minecraft.utility.math.PipedComponent;
import jw.minecraft.utility.math.Vec3i;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class BlockEventHandler extends EventHandler {
	
	public boolean shouldCancel(Vec3i position) {
		boolean cancel;
		
		if (BlockGuard.overworld().isEnabled()) {
			
			RangeData range = BlockGuard.overworld().getRange();
			
			if (range.getState() == State.PART) {
				PipedComponent activatedRange = range.getComponent();
				
				if (PipedComponent.isPoisitionInside(activatedRange, position))
					cancel = processBlockGuard(position);
				else
					cancel = false;
			}
			
			else
				cancel = processBlockGuard(position);
		}
		
		else
			cancel = false;
		
		return cancel;
	}
	
	public boolean processBlockGuard(Vec3i position) {
		boolean cancel;
		
		RegionMap map = BlockGuard.overworld().getMatchedValues(BlockGuard.currentOverworldMode).get(0);
		
		if (BlockGuard.currentOverworldMode == Mode.PROTECT)
			cancel = !map.isPositionWithinAnyRegion(position);
		else
			cancel = map.isPositionWithinAnyRegion(position);
		
		return cancel;
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onBreaked(BlockEvent.BreakEvent e) {
		if (e.getPlayer() != null && e.getPlayer().getHeldItem() != null && BlockGuard.toolEquals(e.getPlayer().getHeldItem()))
			e.setCanceled(true);
		else if (shouldCancel(Vec3i.createVector(e.x, e.y, e.z)))
			e.setCanceled(true);
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onPlaced(BlockEvent.PlaceEvent e) {
		if (shouldCancel(Vec3i.createVector(e.x, e.y, e.z)))
			e.setCanceled(true);
	}

	@Override
	public EventBus getCurrentBus() {
		return MinecraftForge.EVENT_BUS;
	}
}
