package jwk.minecraft.garden.team;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketTeam;
import jwk.minecraft.garden.network.PacketTeam.Action;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.NBTFileManager;
import jwk.minecraft.garden.util.NBTUserProfile;
import jwk.minecraft.garden.util.PlayerUtil;
import jwk.minecraft.garden.util.SoundUtil;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;

public class TeamManager implements ITeamManager {
	
	public static final String TEAM_DEFAULT_PATH = ProjectGarden.getSaveDirectory() + "\\team";
	
	private ITeam teamObj;
	private TeamData data;
	private NBTFileManager fileManager;
	
	public final TeamEventListener EventListener = new TeamEventListener();
	
	public TeamManager(@Nonnull ITeam team) {
		this.teamObj = checkNotNull(team);
		this.data = new TeamData(teamObj);
		this.fileManager = new NBTFileManager(TEAM_DEFAULT_PATH + "\\" + teamObj.getTeamName() + ".dat");
	}
	
	@Override
	public boolean register(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		UUID uid = player.getUniqueID();
		if (data.objects.contains(uid))
			return false;
		
		NBTUserProfile profile = NBTUserProfile.fromGameProfile(player.getGameProfile());
		if (!data.objects.isEmpty())
			sendToAllMembers(new PacketTeam(Action.JOIN, profile, null));
		
		data.objects.offer(uid);
		
		List<NBTUserProfile> list = this.getUserProfileList();
		EntityPlayerMP mp = (EntityPlayerMP) player;
		ProjectGarden.NET_HANDLER.sendTo(new PacketCurrency(PacketCurrency.Action.SET, CurrencyYD.INSTANCE.getManager().get(teamObj)), mp);
		ProjectGarden.NET_HANDLER.sendTo(new PacketTeam(Action.SET, list, teamObj.getTeamName()), mp);
		
		if (data.objects.size() == 1) {
			data.leader = uid;
			ProjectGarden.NET_HANDLER.sendTo(new PacketTeam(Action.LEADER, profile, null), mp);
		}
		
		else if (data.objects.size() >= 2 && data.leader != null) {
			EntityPlayer leader = this.getLeaderAsPlayer();
			
			if (leader != null)
				ProjectGarden.NET_HANDLER.sendTo(new PacketTeam(Action.LEADER, NBTUserProfile.fromGameProfile(leader.getGameProfile()), null), mp);
		}
		
		return true;
	}

	@Override
	public boolean unregister(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		UUID uid = player.getUniqueID();		
		if (!data.objects.contains(uid))
			return false;
		
		data.objects.remove(uid);
		sendToAllMembers(new PacketTeam(Action.LEAVE, NBTUserProfile.fromGameProfile(player.getGameProfile()), null));
		
		EntityPlayerMP mp = (EntityPlayerMP) player;
		ProjectGarden.NET_HANDLER.sendTo(new PacketCurrency(PacketCurrency.Action.SET, 0L), mp);
		ProjectGarden.NET_HANDLER.sendTo(new PacketTeam(Action.RESET, null, null), mp);
		
		if (data.objects.size() == 0)
			data.leader = null;
		else {
			EntityPlayerMP next = this.getNextOnlineMember();
			
			if (next == null) {
				data.leader = null;
				return true;
			}
			
			data.leader = next.getUniqueID();
			sendToAllMembers(new PacketTeam(Action.LEADER, NBTUserProfile.fromGameProfile(next.getGameProfile()), null));
		}
		return true;
	}

	@Override
	public boolean isDataExist(@Nonnull EntityPlayer player) {
		return data.objects.contains(checkNotNull(player).getUniqueID());
	}

	@Override
	public int getDataSize() { return data.objects.size(); }
	
	@Override
	public boolean isDataEmpty() { return data.objects.isEmpty(); }
	
	@Override
	public boolean setLeader(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		if (!isDataExist(player))
			return false;
		
		data.leader = player.getUniqueID();
		sendToAllMembers(new PacketTeam(Action.LEADER, NBTUserProfile.fromGameProfile(player.getGameProfile()), null));
		return true;
	}

	@Override
	public boolean isLeader(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		if (data.leader != null && data.leader.equals(player.getUniqueID()))
			return true;
		
		return false;
	}
	
	@Override
	public UUID getLeader() {
		return data.leader;
	}

	@Override
	public EntityPlayer getLeaderAsPlayer() {
		return data.leader == null? null : PlayerUtil.getPlayerFromUUID(data.leader);
	}
	
	@Override
	public List<EntityPlayerMP> getOnlineMembers() {
		List<EntityPlayerMP> list = Lists.newArrayList();
		
		for (UUID id : data.objects) {
			EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(id);
			
			if (player != null)
				list.add(player);
		}
		
		return list;
	}
	
	@Override
	public List<NBTUserProfile> getUserProfilesExcept(EntityPlayerMP player) {
		List<NBTUserProfile> list = Lists.newArrayList();
		
		for (UUID id : data.objects) {
			
			if (!PlayerUtil.isPlayerOnline(id))
				continue;
			else if (player.getUniqueID().equals(id))
				continue;
			
			String username = UsernameCache.getLastKnownUsername(id);
			if (username == null) {
				EntityPlayerMP mp = PlayerUtil.getPlayerFromUUID(id);
				
				if (mp == null)
					continue;
				
				username = mp.getCommandSenderName();
			}
			
			list.add(new NBTUserProfile(id, username));
		}
		
		return list;
	}
	
	private EntityPlayerMP getNextOnlineMember() {
		
		for (UUID id : data.objects) {
			EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(id);
			
			if (player != null)
				return player;
		}
		
		return null;
	}
	
	@Override
	public List<UUID> getUniqueIDList() {
		List<UUID> list = Lists.newArrayList();
		
		for (UUID id : data.objects) {
			EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(id);
			
			if (player != null)
				list.add(player.getUniqueID());
		}
		
		return list;
	}
	
	@Override
	public List<NBTUserProfile> getUserProfileList() {
		List<NBTUserProfile> list = Lists.newArrayList();
		
		for (UUID id : data.objects) {
			
			if (!PlayerUtil.isPlayerOnline(id))
				continue;
			
			String username = UsernameCache.getLastKnownUsername(id);
			if (username == null) {
				EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(id);
				
				if (player == null)
					continue;
				
				username = player.getCommandSenderName();
			}
			
			list.add(new NBTUserProfile(id, username));
		}
		
		return list;
	}

	@Override
	public void sendToAllMembers(@Nonnull IMessage message) {
		checkNotNull(message);
		
		List<EntityPlayerMP> members = getOnlineMembers();
		
		for (EntityPlayerMP player : members)
			ProjectGarden.NET_HANDLER.sendTo(message, player);
	}
	
	@Override
	public void sendToAllMembersExcept(IMessage message, EntityPlayerMP player) {
		checkNotNull(message);
		
		List<EntityPlayerMP> members = getOnlineMembers();
		
		for (EntityPlayerMP mp : members) {
			
			if (mp.getUniqueID().equals(player.getUniqueID()))
				continue;
			
			ProjectGarden.NET_HANDLER.sendTo(message, mp);
		}
	}
	
	@Override
	public void sendToAllMembers(@Nonnull IChatComponent chatComponent) {
		checkNotNull(chatComponent);
		
		List<EntityPlayerMP> members = getOnlineMembers();
		
		for (EntityPlayerMP player : members)
			player.addChatComponentMessage(chatComponent);			
	}
	
	@Override
	public void onSave() {
		try {
			NBTTagCompound compound = new NBTTagCompound();
			
			data.write(compound);
			fileManager.writeToFile(true, compound);
		}		
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLoad() {		
		try {
			data.read(fileManager.readFromFile());
		}
		
		catch (FileNotFoundException e) {
			ProjectGarden.logger.info("no previous team file detected <path=" + e.getMessage() + ">");
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUnload() {
		teamObj = null;
		data.leader = null;
		data.teamObj = null;
		data.objects.clear();
		data.objects = null;
		data = null;
		fileManager = null;
	}

	@Override
	public InventoryWarp getInventoryWarp() {
		return data.inventoryWarp;
	}
	
	@Override
	public InventoryTeamChest getInventoryTeam() {
		return data.inventoryTeam;
	}

	@Override
	public TeamChest getTeamChest() {
		
		if (data.teamChest == null)
			return null;
		
		else if (data.teamChest.checkSelf())
			return data.teamChest;
		
		data.teamChest = null;
		return null;
	}
	
	@Override
	public boolean hasTeamChest() {
		return data.teamChest != null;
	}
	
	@Override
	public void setTeamChest(TeamChest target) {
		data.teamChest = target;
	}

	@Override
	public TeamEventListener getEventListener() {
		return EventListener;
	}

	@Override
	public void playSoundToAllMembers(SoundType type) {
		checkNotNull(type);
		
		List<EntityPlayerMP> members = getOnlineMembers();
		
		for (EntityPlayerMP player : members)
			SoundUtil.playSoundTo(player, type);
	}

}
