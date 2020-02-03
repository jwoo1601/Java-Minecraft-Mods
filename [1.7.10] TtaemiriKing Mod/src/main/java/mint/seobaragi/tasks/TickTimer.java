package mint.seobaragi.tasks;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import library.ITaskable;

public class TickTimer
{
	private boolean isStarted = false;
	private long currentTicks = 0L;
	private ITaskable target = null;
	
	
	
	public TickTimer(@Nonnull ITaskable target)
	{
		checkArgument(target.getDuration() > 0, "duration must be greater than 0");
		this.target = checkNotNull(target);
	}
	
	public TickTimer update(long value)
	{
		if(isStarted)
		{
			currentTicks += value;
			
			if(currentTicks % target.getDuration() == 0)
				target.uptate();
		}
		return this;
	}
	
	public long getCurrentTicks()
	{
		return currentTicks;
	}
	
	public long getCurrentSeconds()
	{
		return (long) (currentTicks * 0.05);
	}
	
	public TickTimer start()
	{
		if(!isStarted)
			isStarted = true;
		
		return this;
	}
	
	public TickTimer stop()
	{
		if(isStarted)
			isStarted = false;
		
		return this;
	}
	
	public TickTimer reset()
	{
		currentTicks = 0L;
		
		return this;
	}
	
	public boolean isStarted() { return isStarted; }
}
