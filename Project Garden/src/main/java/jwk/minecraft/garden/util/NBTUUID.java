package jwk.minecraft.garden.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;

/**
 * A Wrapper Class for {@link UUID}
 * @author jwoo__
 *
 */
public class NBTUUID implements INBTConvertable, INBTSerializable {

	private UUID uniqueID;
	
	public NBTUUID(@Nonnull UUID uuid) {
		uniqueID = checkNotNull(uuid);
	}
	
	@Override
	public NBTBase toNBT() {
		return new NBTTagString(uniqueID.toString());
	}

	@Override
	public void fromNBT(NBTBase nbtbase) {
		
		if (nbtbase instanceof NBTTagString)
			uniqueID = UUID.fromString(((NBTTagString) nbtbase).func_150285_a_());
		
		else
			throw new IllegalArgumentException();
	}
	
	public UUID getUniqueID() { return uniqueID; }
	
	@Override
	public int hashCode() { return uniqueID.hashCode(); }
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof UUID)
			return uniqueID.equals(obj);
		
		else if (obj instanceof NBTUUID)
			return uniqueID.equals(((NBTUUID) obj).uniqueID);
		
		return false;
	}
	
	public static NBTUUID fromUUID(@Nonnull UUID uid) {
		return new NBTUUID(uid);
	}
	
	public static NBTUUID fromString(@Nonnull String str) {
		return new NBTUUID(UUID.fromString(str));
	}
	
	public static NBTUUID randomUUID() {
		return new NBTUUID(UUID.randomUUID());
	}
	
	
	private static final String KEY_UUID = "UUID";
	private static final String KEY_LEAST_BITS = "LBits";
	private static final String KEY_MOST_BITS = "MBits";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagUUID = new NBTTagCompound();
		
		tagUUID.setLong(KEY_LEAST_BITS, uniqueID.getLeastSignificantBits());
		tagUUID.setLong(KEY_MOST_BITS, uniqueID.getMostSignificantBits());
		
		tagCompound.setTag(KEY_UUID, tagUUID);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagUUID = null;
		
		if (tagCompound.hasKey(KEY_UUID, NBT.TAG_COMPOUND))
			tagUUID = tagCompound.getCompoundTag(KEY_UUID);
		else
			throw new IllegalArgumentException();
		
		long leastBits = 0L;
		if (tagUUID.hasKey(KEY_LEAST_BITS, NBT.TAG_LONG))
			leastBits = tagUUID.getLong(KEY_LEAST_BITS);
		else
			throw new IllegalArgumentException();
		
		long mostBits = 0L;
		if (tagUUID.hasKey(KEY_MOST_BITS, NBT.TAG_LONG))
			mostBits = tagUUID.getLong(KEY_MOST_BITS);
		else
			throw new IllegalArgumentException();
		
		uniqueID = new UUID(mostBits, leastBits);
	}

}
