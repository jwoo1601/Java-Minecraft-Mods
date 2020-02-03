package jwk.minecraft.garden.flower;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants.NBT;

public class FlowerProperty implements INBTSerializable {
	
	public static final int FIRST_NUM = 1;
	public static final int LAST_NUM = 29;
	
	private boolean isVisible;
	private BlockPos pos;
	private int flowerType;
	private EnumFacing facing;
	
	public FlowerProperty() {
		this(true, BlockPos.zero(), FIRST_NUM, EnumFacing.NORTH);
	}
	
	public FlowerProperty(@Nonnull BlockPos pos, int flowerType) {
		this(true, pos, flowerType, EnumFacing.NORTH);
	}
	
	public FlowerProperty(@Nonnull BlockPos pos, int flowerType, @Nonnull EnumFacing facing) {
		this(true, pos, flowerType, facing);
	}
	
	public FlowerProperty(boolean isVisible, @Nonnull BlockPos pos, int flowerType, @Nonnull EnumFacing facing) {
		this.isVisible = isVisible;
		this.pos = checkNotNull(pos);
		setFlowerType(flowerType);
		this.facing = checkNotNull(facing);
	}
	
	public FlowerProperty setVisible(boolean value) {
		this.isVisible = value;
		
		return this;
	}
	
	public boolean getVisible() { return isVisible; }
	
	public FlowerProperty setFacing(@Nonnull EnumFacing facing) {
		this.facing = checkNotNull(facing);
		
		return this;
	}
	
	public EnumFacing getFacing() { return facing; }
	
	private FlowerProperty setPosition(@Nonnull BlockPos pos) {
		this.pos = checkNotNull(pos);
		
		return this;
	}
	
	public BlockPos getPosition() { return pos; }
	
	private FlowerProperty setFlowerType(int typeInt) {
		checkArgument(typeInt >= FIRST_NUM && typeInt <= LAST_NUM);
		
		this.flowerType = typeInt;
		
		return this;
	}
	
	public int getFlowerType() { return flowerType; }
	
	@Override
	public String toString() {
		return "isVisible= " + isVisible + " pos= " + pos + " flowerType= " + flowerType + " facing= " + facing;
	}
	
	private static final String KEY_PROPERTY = "Flower Property";
	private static final String KEY_VISIBLE = "Visible";
	private static final String KEY_TARGET_POS = "Position";
	private static final String KEY_FLOWER_TYPE = "FlowerType";
	private static final String KEY_FACING = "Facing";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagProperty = new NBTTagCompound();
		
		tagProperty.setBoolean(KEY_VISIBLE, isVisible);
		
		NBTTagCompound tagPos = new NBTTagCompound();
		pos.write(tagPos);
		tagProperty.setTag(KEY_TARGET_POS, tagPos);
		
		tagProperty.setInteger(KEY_FLOWER_TYPE, flowerType);
		tagProperty.setInteger(KEY_FACING, convertToInteger(facing));
		
		tagCompound.setTag(KEY_PROPERTY, tagProperty);
	}
	
	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagProperty = null;
		
		if (tagCompound.hasKey(KEY_PROPERTY, NBT.TAG_COMPOUND))
			tagProperty = tagCompound.getCompoundTag(KEY_PROPERTY);
		else
			throw new IllegalArgumentException();
		
		boolean isVisible = false;		
		if (tagProperty.hasKey(KEY_VISIBLE, NBT.TAG_BYTE))
			isVisible = tagProperty.getBoolean(KEY_VISIBLE);
		else
			throw new IllegalArgumentException();
		
		BlockPos pos = BlockPos.zero();
		if (tagProperty.hasKey(KEY_TARGET_POS, NBT.TAG_COMPOUND))
			pos.read(tagProperty.getCompoundTag(KEY_TARGET_POS));
		else
			throw new IllegalArgumentException();
		
		int flowerType = 0;
		if (tagProperty.hasKey(KEY_FLOWER_TYPE, NBT.TAG_INT))
			flowerType = tagProperty.getInteger(KEY_FLOWER_TYPE);
		else
			throw new IllegalArgumentException();
		
		EnumFacing facing = null;
		if (tagProperty.hasKey(KEY_FACING, NBT.TAG_INT))
			facing = convertToFacing(tagProperty.getInteger(KEY_FACING));
		else
			throw new IllegalArgumentException();
		
		this.isVisible = isVisible;
		this.setPosition(pos);
		this.setFlowerType(flowerType);
		this.setFacing(facing);
	}
	
	private static final EnumFacing convertToFacing(int value) {
		
		switch (value) {
		
		case 0:
			return EnumFacing.NORTH;
			
		case 1:
			return EnumFacing.SOUTH;
			
		case 2:
			return EnumFacing.EAST;
			
		case 3:
			return EnumFacing.WEST;
			
		default:
			throw new IllegalArgumentException();
		}
	}
	
	private static final int convertToInteger(EnumFacing facing) {
	
		switch (facing) {
		
		case NORTH:
			return 0;
			
		case SOUTH:
			return 1;
			
		case EAST:
			return 2;
			
		case WEST:
			return 3;
			
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
