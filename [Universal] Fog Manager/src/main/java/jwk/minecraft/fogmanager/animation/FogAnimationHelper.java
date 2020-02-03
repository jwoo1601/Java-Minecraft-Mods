package jwk.minecraft.fogmanager.animation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import javax.annotation.Nonnull;

public class FogAnimationHelper {

	private Queue<AnimatedFog> animQueue = new LinkedList<AnimatedFog>();
	
	private AnimatedFog currentAnimation = null;
	
	private boolean isStarted = false;
	
	private long elapsedTicks = 0L;
	
	public boolean push(@Nonnull AnimatedFog target) {		
		return animQueue.add(checkNotNull(target));
	}
	
	public AnimatedFog pop() {
		return animQueue.poll();
	}
	
	public boolean isQueueEmpty() {
		return animQueue.isEmpty();
	}
	
	public int getQueueSize() {
		return animQueue.size();
	}
	
	public void clear() {
		animQueue.clear();
	}
	
	public AnimatedFog getCurrentAnimation() {
		return currentAnimation;
	}
	
	public float getCurrentPosition() {
		return currentAnimation.getPositionAt(elapsedTicks);
	}
	
	public boolean getState() { return isStarted; }
	
	public long getElapsedTicks() { return elapsedTicks; }
	
	public long getElapsedSeconds() { return (long) (elapsedTicks * 0.05); }
	
	public FogAnimationHelper start() {
		if (!isStarted) {
			reset();
			
			if (animQueue.isEmpty())
				throw new NoSuchElementException();
			
			currentAnimation = animQueue.element();
			isStarted = true;
		}
		
		return this;
	}
	
	public FogAnimationHelper update(long value) {		
		if (isStarted) {
			
			if (currentAnimation != null && currentAnimation.getDuration() == elapsedTicks) {
				stop();
				pop();
				currentAnimation = animQueue.peek();
			}
			
			else
				elapsedTicks += value;
		}
		
		return this;
	}
	
	public FogAnimationHelper stop() {
		if (isStarted) 
			isStarted = false;
		
		return this;
	}
	
	public FogAnimationHelper resume() {
		if (!isStarted)
			isStarted = true;
		
		return this;
	}
	
	public FogAnimationHelper reset() {
		currentAnimation = null;
		elapsedTicks = 0L;
		return this;
	}
}
