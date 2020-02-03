package jw.minecraft.utility.blockguard.repository;

import javax.annotation.Nonnull;

import jw.minecraft.utility.exception.InvalidFormatException;
import jw.minecraft.utility.math.PipedComponent;
import jw.minecraft.utility.nbt.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class RangeData implements INBTSerializable {
	
	private State currentState = State.ALL;
	
	private PipedComponent component;
	
	public RangeData() {
		this(State.ALL, null);
	}
	
	public RangeData(@Nonnull State state, PipedComponent component) {
		if (state == null)
			throw new NullPointerException("State state");
		
		this.currentState = state;
		this.component = component;
	}
	
	public RangeData setState(@Nonnull State state) {
		if (state == null)
			throw new NullPointerException("State state");
		else {
			synchronized(this) {
				currentState = state;
			}
		}
		return this;
	}
	
	public State getState() { return currentState; }
	
	public synchronized RangeData setComponent(PipedComponent component) {
		this.component = component;
		return this;
	}
	
	public PipedComponent getComponent() { return this.component; }

	
	private static final String KEYSTATE = "state";	
	private static final String KEYRANGE = "range";
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound tag_range = new NBTTagCompound();
		tag_range.setByte(KEYSTATE, currentState.Flag);
		
		switch (currentState) {
		case ALL:
			if (component != null)
				throw new IllegalArgumentException("component must be null if State is set to ALL");
			break;
		case PART:
			if (component == null)
				throw new NullPointerException("PipedComponent component");
			else
				component.writeToNBT(tag_range);
			break;
		default:
			throw new NullPointerException("State currentState");
		}
		
		tag.setTag(KEYRANGE, tag_range);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEYRANGE, NBT.TAG_COMPOUND)) {
			NBTTagCompound tag_range = tag.getCompoundTag(KEYRANGE);
			
			if (tag_range.hasKey(KEYSTATE, NBT.TAG_BYTE)) {
				currentState = State.getState(tag_range.getByte(KEYSTATE));
				
				switch (currentState) {
				case ALL:
					component = null;
					break;
				case PART:
					
					if (component == null)
						component = new PipedComponent();
					
					component.readFromNBT(tag_range);
					break;
				}
			}
			
			else
				throw new InvalidFormatException("KEYSTATE");
		}
		
		else
			throw new InvalidFormatException("KEYRANGE");
	}
}
