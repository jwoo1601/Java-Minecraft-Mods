package jwk.minecraft.garden.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTUserProfile implements INBTSerializable {
	
	private NBTUUID id;
	private String name;
	
	private NBTUserProfile() {
		this.id = null;
		this.name = null;
	}
	
	public NBTUserProfile(@Nonnull UUID id, @Nonnull String name) {
		this.id = NBTUUID.fromUUID(checkNotNull(id));
		this.name = checkNotNull(name);
	}

	public UUID getId() { return id.getUniqueID(); }
	
	public String getUsername() { return name; }
	
	@Override
	public int hashCode() {
		return getId().hashCode() ^ name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj != null && obj instanceof NBTUserProfile) {
			NBTUserProfile profile = (NBTUserProfile) obj;
			
			return profile.id.equals(id) && profile.name.equals(name);
		}
		
		return false;
	}
	
	public GameProfile toGameProfile() {
		return new GameProfile(id.getUniqueID(), name);
	}
	
	public static NBTUserProfile fromGameProfile(GameProfile profile) {
		return new NBTUserProfile(profile.getId(), profile.getName());
	}
	
	public static NBTUserProfile newTempProfile() {
		return new NBTUserProfile();
	}
	
	
	private static final String KEY_USER_PROFILE = "User Profile";
	private static final String KEY_USER_NAME = "Username";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagProfile = new NBTTagCompound();
		
		id.write(tagProfile);
		tagProfile.setString(KEY_USER_NAME, name);
		
		tagCompound.setTag(KEY_USER_PROFILE, tagProfile);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagProfile = null;
		
		if (tagCompound.hasKey(KEY_USER_PROFILE, NBT.TAG_COMPOUND))
			tagProfile = tagCompound.getCompoundTag(KEY_USER_PROFILE);
		else
			throw new IllegalArgumentException();
		
		NBTUUID id = NBTUUID.randomUUID();
		id.read(tagProfile);
		
		String name = null;
		if (tagProfile.hasKey(KEY_USER_NAME, NBT.TAG_STRING))
			name = tagProfile.getString(KEY_USER_NAME);
		else
			throw new IllegalArgumentException();
		
		this.id = id;
		this.name = name;
	}

}
