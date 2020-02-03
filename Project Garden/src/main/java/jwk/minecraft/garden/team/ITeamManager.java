package jwk.minecraft.garden.team;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.IManaged;
import jwk.minecraft.garden.util.NBTUserProfile;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.IChatComponent;

public interface ITeamManager extends IManaged {

	boolean register(@Nonnull EntityPlayer player);
	
	boolean unregister(@Nonnull EntityPlayer player);
	
	boolean isDataExist(@Nonnull EntityPlayer player);
	
	int getDataSize();
	
	boolean isDataEmpty();
	
	boolean setLeader(@Nonnull EntityPlayer player);
	
	boolean isLeader(@Nonnull EntityPlayer player);
	
	UUID getLeader();
	
	EntityPlayer getLeaderAsPlayer();
	
	List<EntityPlayerMP> getOnlineMembers();
	
	List<UUID> getUniqueIDList();
	
	List<NBTUserProfile> getUserProfileList();
	
	List<NBTUserProfile> getUserProfilesExcept(EntityPlayerMP player);
	
	void sendToAllMembers(@Nonnull IMessage message);
	
	void sendToAllMembersExcept(@Nonnull IMessage message, EntityPlayerMP player);
	
	void sendToAllMembers(@Nonnull IChatComponent chatComponent);
	
	void playSoundToAllMembers(@Nonnull SoundType type);
	
	InventoryTeamChest getInventoryTeam();
	
	InventoryWarp getInventoryWarp();
	
	TeamChest getTeamChest();
	
	boolean hasTeamChest();
	
	void setTeamChest(TeamChest target);
	
	TeamEventListener getEventListener();
	
}
