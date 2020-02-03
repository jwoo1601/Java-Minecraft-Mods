package mint.seobaragi.player.tracker;

import static com.google.common.base.Preconditions.checkNotNull;
import static library.Reference.DEBUG;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import javax.annotation.Nonnull;

import library.ITaskable;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class PlayerWaterLevelTracker implements ITracker {
	
	public static final int DURATION = 1200;
	
	private boolean isStarted = false;
	private int leftTicks = DURATION;
	private EntityPlayer thePlayer = null;

	public PlayerWaterLevelTracker(@Nonnull EntityPlayer target) {
		thePlayer = checkNotNull(target);
	}
	
	public EntityPlayer attach(@Nonnull EntityPlayer target) {
		return (thePlayer = checkNotNull(target));
	}
	
	@Override
	public PlayerWaterLevelTracker startTracking() {
		if (!isStarted)
			isStarted = true;
		
		return this;
	}

	@Override
	public PlayerWaterLevelTracker update() {
		if (!isStarted)
			return this;
		
		if (DEBUG && leftTicks % 100 == 0)
			thePlayer.addChatComponentMessage(new ChatComponentText("Left Ticks = " + leftTicks));
		
		if (leftTicks == 0) {
			PropertyPlayerStat stat = (PropertyPlayerStat) thePlayer.getExtendedProperties(ID);			
			stat.setWaterLevel(stat.waterLevel - 1);
			reset();
		}
		
		else
			--leftTicks;
		
		return this;
	}

	@Override
	public PlayerWaterLevelTracker stopTracking() {
		if (isStarted)
			isStarted = false;
		
		return this;
	}
	
	@Override
	public PlayerWaterLevelTracker reset() {
		leftTicks = DURATION;
		return this;
	}
	
	public EntityPlayer getPlayer() {
		return thePlayer;
	}
	
	@Override
	public int hashCode() {
		return thePlayer.getUniqueID().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlayerWaterLevelTracker) {
			PlayerWaterLevelTracker tracker = (PlayerWaterLevelTracker) obj;
			return thePlayer.getUniqueID().equals(tracker.thePlayer.getUniqueID());
		}
		
		return false;
	}
	
}
