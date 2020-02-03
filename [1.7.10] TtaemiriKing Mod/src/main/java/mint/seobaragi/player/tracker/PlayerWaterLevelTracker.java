package mint.seobaragi.player.tracker;

import static com.google.common.base.Preconditions.checkNotNull;
import static library.Reference.DEBUG;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import javax.annotation.Nonnull;

import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class PlayerWaterLevelTracker implements ITracker {
	
	public static final int DURATION = 1200;
	
	private boolean isStarted = false;
	private int elapsedTicks = 0;
	private EntityPlayer thePlayer = null;

	public PlayerWaterLevelTracker(@Nonnull EntityPlayer target) {
		thePlayer = checkNotNull(target);
	}
	
	@Override
	public void startTracking() {
		if (!isStarted)
			isStarted = true;
	}

	@Override
	public void update() {
		if (!isStarted)
			return;
		
		if (DEBUG)
			thePlayer.addChatComponentMessage(new ChatComponentText("Elapsed Ticks = " + elapsedTicks));
		
		if (elapsedTicks <= 0) {
			PropertyPlayerStat stat = (PropertyPlayerStat) thePlayer.getExtendedProperties(ID);			
			stat.setWaterLevel(stat.waterLevel - 1);
			reset();
		}
		
		else
			--elapsedTicks;
	}

	@Override
	public void stopTracking() {
		if (isStarted)
			isStarted = false;
	}

	@Override
	public int getDuration() {
		return DURATION;
	}

	@Override
	public void reset() {
		elapsedTicks = DURATION;
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
		if (obj instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) obj;
			return thePlayer.getUniqueID().equals(player.getUniqueID());
		}
		
		return false;
	}
	
}
