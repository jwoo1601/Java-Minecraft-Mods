package jwk.minecraft.garden.timer;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;

public abstract class TickTimer implements ITickListener {

	private boolean isStarted = false;
	private long elapsedTicks = 0L;
	
	protected final long Duration;
	
	protected TickTimer(long duration) {
		Duration = duration < 1L? 1L : duration;
	}
	
	@Override
	public final void onUpdate() {
		
		if(isStarted) {
			
			if (elapsedTicks == Long.MAX_VALUE)
				elapsedTicks = 0L;
			
			++elapsedTicks;
			
			if(elapsedTicks % Duration == 0)
				update();
		}
	}
	
	protected abstract void update();
	
	public long elapsedTicks() { return elapsedTicks; }
	
	public long elapsedTicksToSec() { return (long) (elapsedTicks * 0.05); }
	
	public void start() {
		
		if(!isStarted) {
			isStarted = true;
			
			if (elapsedTicks != 0L)
				elapsedTicks = 0L;
		}
	}
	
	public void stop() {
		
		if(isStarted) {
			isStarted = false;
			elapsedTicks = 0L;
		}
	}
	
	public void pause() {
		
		if (isStarted)
			isStarted = false;
	}
	
	public void resume() {
		
		if (!isStarted)
			isStarted = true;
	}
	
	public boolean isStarted() { return isStarted; }

}
