package jw.minecraft.utility.blockguard.region;

import javax.annotation.Nonnull;

import jw.minecraft.utility.exception.InvalidFormatException;
import jw.minecraft.utility.math.PipedComponent;
import jw.minecraft.utility.math.Vec3i;
import jw.minecraft.utility.nbt.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class PipedRegion implements IRegion<PipedComponent> {	
	
	private String id;
	private PipedComponent component;
	
	public PipedRegion() {
		this("temp");
	}
	
	public PipedRegion(@Nonnull String id) {
		if (id == null)
			throw new NullPointerException("String id");
		
		this.id = id;
		this.component = new PipedComponent();
	}	
	
	public PipedRegion(@Nonnull String id, @Nonnull PipedComponent component) {
		if (id == null)
			throw new NullPointerException("String id");
		if (component == null)
			throw new NullPointerException("PipedComponent component");
		
		this.id = id;
		this.component = component;
	}	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new PipedRegion(id, (PipedComponent) component.clone());
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public PipedComponent getComponent() {
		return component;
	}

	@Override
	public void setComponent(@Nonnull PipedComponent component) {
		if (component == null)
			throw new NullPointerException("PipedComponent component");
		
		this.component = component;
	}

	@Override
	public boolean isPoisitionInside(Vec3i position) {
		return position.xCoord >= component.getMinPos().xCoord &&
			   position.yCoord >= component.getMinPos().yCoord &&
			   position.zCoord >= component.getMinPos().zCoord &&
			   position.xCoord <= component.getMaxPos().xCoord &&
			   position.yCoord <= component.getMaxPos().yCoord &&
			   position.zCoord <= component.getMaxPos().zCoord;
	}
	
	@Override
	public boolean equals(Object target) {
		if (target instanceof PipedRegion) {
			PipedRegion pr = (PipedRegion) target;
			
			return id.equals(pr.id) && component.equals(pr.component);
		}
		
		return false;
	}
	
	
	private static final String KEYID = "id";
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setString(KEYID, id);
		component.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEYID, NBT.TAG_STRING))
			id = tag.getString(KEYID);
		else
			throw new InvalidFormatException("KEYID");
		
		component.readFromNBT(tag);
	}
}
