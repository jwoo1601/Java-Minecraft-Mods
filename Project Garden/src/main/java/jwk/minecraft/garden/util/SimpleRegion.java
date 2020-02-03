package jwk.minecraft.garden.util;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class SimpleRegion implements INBTSerializable {

	private BlockPos first;
	private BlockPos last;
	
	public SimpleRegion(@Nonnull BlockPos first, @Nonnull BlockPos last) {
		this.first = checkNotNull(first);
		this.last = checkNotNull(last);
	}
	
	public SimpleRegion setFirst(@Nonnull BlockPos first) {
		this.first = checkNotNull(first);
		
		return this;
	}
	
	public BlockPos getFirst() { return first; }
	
	public SimpleRegion setLast(@Nonnull BlockPos last) {
		this.last = checkNotNull(last);
		
		return this;
	}
	
	public BlockPos getLast() { return last; }
	
	public BlockPos getMinPos() {
		return new BlockPos(Math.min(first.xCoord, last.xCoord), Math.min(first.yCoord, last.yCoord), Math.min(first.zCoord, last.zCoord));
	}
	
	public BlockPos getMaxPos() {
		return new BlockPos(Math.max(first.xCoord, last.xCoord), Math.max(first.yCoord, last.yCoord), Math.max(first.zCoord, last.zCoord));
	}
	
	public boolean isPositionInside(BlockPos pos) {
		BlockPos min = getMinPos(), max = getMaxPos();
		
		return pos.xCoord >= min.xCoord && pos.yCoord >= min.yCoord && pos.zCoord >= min.zCoord && pos.xCoord <= max.xCoord && pos.yCoord <= max.yCoord && pos.zCoord <= max.zCoord;
	}
	
	
	public static final String KEY_REGION = "Region";
	public static final String KEY_FIRST = "First";
	public static final String KEY_LAST = "Last";

	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagRegion = new NBTTagCompound();
		
		NBTTagCompound tagFirst = new NBTTagCompound();
		first.write(tagFirst);
		tagRegion.setTag(KEY_FIRST, tagFirst);
		
		NBTTagCompound tagLast = new NBTTagCompound();
		last.write(tagLast);
		tagRegion.setTag(KEY_LAST, tagLast);
		
		tagCompound.setTag(KEY_REGION, tagRegion);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagRegion = null;
		
		if (tagCompound.hasKey(KEY_REGION, NBT.TAG_COMPOUND))
			tagRegion = tagCompound.getCompoundTag(KEY_REGION);
		
		else
			throw new IllegalArgumentException();
		
		BlockPos first = BlockPos.zero();
		if (tagRegion.hasKey(KEY_FIRST, NBT.TAG_COMPOUND))
			first.read(tagRegion.getCompoundTag(KEY_FIRST));
		
		else
			throw new IllegalArgumentException();
		
		BlockPos last = BlockPos.zero();
		if (tagRegion.hasKey(KEY_LAST, NBT.TAG_COMPOUND))
			last.read(tagRegion.getCompoundTag(KEY_LAST));
		
		else
			throw new IllegalArgumentException();
		
		this.first = first;
		this.last = last;
	}
	
}
