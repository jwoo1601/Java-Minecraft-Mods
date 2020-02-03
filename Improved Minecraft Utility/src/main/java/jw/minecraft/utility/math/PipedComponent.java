package jw.minecraft.utility.math;

import net.minecraft.nbt.NBTBase.NBTPrimitive;

import javax.annotation.Nonnull;

import jw.minecraft.utility.exception.InvalidFormatException;
import jw.minecraft.utility.math.Vec3i;
import jw.minecraft.utility.nbt.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants.NBT;

public class PipedComponent implements IComponent<PipedComponent> {
	
	private Vec3i origin = null;
	private Vec3i end = null;
	
	public PipedComponent() {}
	
	public PipedComponent(Vec3i origin) {
		this.origin = origin;
	}
	
	public PipedComponent(Vec3i origin, Vec3i end) {
		this.origin = origin;
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "Origin: " + (origin == null? "null" : origin.toString()) + " End: " + (end == null? "null" : end.toString());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Vec3i co, ce;
		
		if (origin == null)
			co = null;
		else
			co = (Vec3i) origin.clone();
		
		if (end == null)
			ce = null;
		else
			ce = (Vec3i) end.clone();
		
		return new PipedComponent(co, ce);
	}
	
	public Vec3i getMinPos() {
		if (origin == null || end == null)
			return null;
		
		int x = origin.xCoord < end.xCoord ? origin.xCoord : end.xCoord;
		int y = origin.yCoord < end.yCoord ? origin.yCoord : end.yCoord;
		int z = origin.zCoord < end.zCoord ? origin.zCoord : end.zCoord;
		
		return Vec3i.createVector(x, y, z);
	}
	
	public Vec3i getMaxPos() {
		if (origin == null || end == null)
			return null;
		
		int x = origin.xCoord >= end.xCoord ? origin.xCoord : end.xCoord;
		int y = origin.yCoord >= end.yCoord ? origin.yCoord : end.yCoord;
		int z = origin.zCoord >= end.zCoord ? origin.zCoord : end.zCoord;
		
		return Vec3i.createVector(x, y, z);
	}

	@Override
	public PipedComponent setOrigin(Vec3i pos) {
		origin = pos;		
		return this;
	}

	@Override
	public Vec3i getOrigin() {
		return origin;
	}

	@Override
	public PipedComponent setEnd(Vec3i pos) {
		end = pos;		
		return this;
	}

	@Override
	public Vec3i getEnd() {
		return end;
	}
	
	public int getXLength() {
		return Math.abs(origin.xCoord - end.xCoord);
	}
	
	public int getYLength() {
		return Math.abs(origin.yCoord - end.yCoord);
	}
	
	public int getZLength() {
		return Math.abs(origin.zCoord - end.zCoord);
	}
	
	@Override
	public int getVolume() {
		return getXLength() * getYLength() * getZLength();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof PipedComponent) {
			PipedComponent target = (PipedComponent) o;
			return this.origin.equals(target.origin) && this.end.equals(target.end);
		}
		
		return false;
	}
	
	@Override
	public boolean rangeEquals(@Nonnull PipedComponent target) {
		if (target == null)
			throw new NullPointerException("PipedComponent target");
		
		return origin != null && end != null ? getMinPos().equals(target.getMinPos()) && getMaxPos().equals(target.getMaxPos()) : false;
	}
	
	public static boolean isPoisitionInside(@Nonnull PipedComponent component, @Nonnull Vec3i position) {
		if (component == null)
			throw new NullPointerException("PipedComponent component");
		
		return position.xCoord >= component.getMinPos().xCoord &&
			   position.yCoord >= component.getMinPos().yCoord &&
			   position.zCoord >= component.getMinPos().zCoord &&
			   position.xCoord <= component.getMaxPos().xCoord &&
			   position.yCoord <= component.getMaxPos().yCoord &&
			   position.zCoord <= component.getMaxPos().zCoord;
	}
	
	
	private static final String KEYCOMPONENT = "component";
	private static final String KEYORIGIN = "origin";
	private static final String KEYEND = "end";

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound tag_component = new NBTTagCompound();
		
		if (origin == null)
			tag_component.setString(KEYORIGIN, INBTSerializable.NULL);
		else {
			NBTTagCompound tag_origin = new NBTTagCompound();
			origin.writeToNBT(tag_origin);
			tag_component.setTag(KEYORIGIN, tag_origin);
		}
		
		
		if (end == null)
			tag_component.setString(KEYEND, INBTSerializable.NULL);
		else {
			NBTTagCompound tag_end = new NBTTagCompound();
			end.writeToNBT(tag_end);
			tag_component.setTag(KEYEND, tag_end);
		}
		
		tag.setTag(KEYCOMPONENT, tag_component);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEYCOMPONENT, NBT.TAG_COMPOUND)) {
			NBTTagCompound tag_component = tag.getCompoundTag(KEYCOMPONENT);
			
			if (tag_component.hasKey(KEYORIGIN, NBT.TAG_COMPOUND)) {
				NBTTagCompound tag_origin = tag_component.getCompoundTag(KEYORIGIN);
				
				if (origin == null)
					origin = Vec3i.ZERO();
				
				origin.readFromNBT(tag_origin);				
			}
			else if (tag_component.hasKey(KEYORIGIN, NBT.TAG_STRING) && tag_component.getString(KEYORIGIN).equals(INBTSerializable.NULL))
				origin = null;
			else
				throw new InvalidFormatException("KEYORIGIN");
			
			
			if (tag_component.hasKey(KEYEND, NBT.TAG_COMPOUND)) {
				NBTTagCompound tag_end = tag_component.getCompoundTag(KEYEND);
				
				if (end == null)
					end = Vec3i.ZERO();
				
				end.readFromNBT(tag_end);				
			}
			else if (tag_component.hasKey(KEYEND, NBT.TAG_STRING) && tag_component.getString(KEYEND).equals(INBTSerializable.NULL))
				end = null;
			else
				throw new InvalidFormatException("KEYEND");
			
		}
		
		else
			throw new InvalidFormatException("KEYCOMPONENT");
	}
}
