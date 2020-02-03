package jwk.minecraft.fogmanager.animation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import jwk.minecraft.fogmanager.config.Configuration.FogConfig;

public class AnimatedFog {
	
	private float speed;
	
	// tick
	private long duration;
	
	private FogConfig currentConfig;
	
	public AnimatedFog(float speed, long duration, @Nonnull FogConfig config) {
		checkArgument(speed != 0 && duration != 0);
		
		this.speed = speed;
		this.duration = duration;
		this.currentConfig = checkNotNull(config);
	}
	
	public FogConfig getCurrentConfig() {
		return currentConfig;
	}
	
	public float getPositionAt(long targetTicks) {
		float currentTime = targetTicks * 0.05f;
		return speed * currentTime;
	}
	
	public long getDuration() { return duration; }
	
	public float getSpeed() { return speed; }
	
}
