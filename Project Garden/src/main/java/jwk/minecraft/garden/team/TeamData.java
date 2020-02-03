package jwk.minecraft.garden.team;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.exception.EntryAlreadyExistException;
import jwk.minecraft.garden.exception.NBTLoadException;
import jwk.minecraft.garden.exception.NBTSaveException;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.INBTSerializable;
import jwk.minecraft.garden.util.NBTUUID;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class TeamData implements INBTSerializable {
	
	protected ITeam teamObj;
	Deque<UUID> objects;	
	UUID leader;
	
	InventoryTeamChest inventoryTeam;
	InventoryWarp inventoryWarp;
	
	TeamChest teamChest = null;
	
	public TeamData(@Nonnull ITeam team) {
		this(team, null);
	}
	
	public TeamData(@Nonnull ITeam team, EntityPlayer leader) {
		teamObj = checkNotNull(team);
		objects = Lists.newLinkedList();
		this.leader = leader == null? null : leader.getUniqueID();
		inventoryTeam = new InventoryTeamChest(teamObj);
		inventoryWarp = new InventoryWarp(null);
	}

	
	protected static final String KEY_TEAM = "Team";
	protected static final String KEY_TEAM_NAME = "Team Name";
	protected static final String KEY_LEADER = "Leader";
	protected static final String KEY_TARGET_CHEST = "Target Chest";
	protected static final String KEY_FLOWER_SHOP = "Flower Shop";
	protected static final String KEY_ENTRIES = "Entries";
	protected static final String KEY_SIZE = "Size";
	protected static final String KEY_ENTRY_BASE = "Entry -";

	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagTeam = new NBTTagCompound();
		tagTeam.setString(KEY_TEAM_NAME, teamObj.getTeamName());
		
		if (leader != null) {
			NBTTagCompound tagLeader = new NBTTagCompound();
			NBTUUID uid = NBTUUID.fromUUID(leader);
			
			uid.write(tagLeader);
			tagTeam.setTag(KEY_LEADER, tagLeader);
		}
		
		if (teamChest != null) {
			NBTTagCompound tagChest = new NBTTagCompound();
			
			try { teamChest.write(tagChest); }
			catch (NBTSaveException e) { e.printStackTrace(); return; }
			
			tagTeam.setTag(KEY_TARGET_CHEST, tagChest);
		}
		
		BlockPos pos = inventoryWarp.getWarpData(6).getPosition();
		if (pos != null) {
			NBTTagCompound tagFlowerShop = new NBTTagCompound();
			
			pos.write(tagFlowerShop);
			tagTeam.setTag(KEY_FLOWER_SHOP, tagFlowerShop);
		}
		
		NBTTagCompound tagEntries = new NBTTagCompound();
		int size = objects.size();
		
		tagEntries.setInteger(KEY_SIZE, size);
		
		if (size != 0) {
			Iterator<UUID> iter = objects.iterator();
			
			int i=0; while (i < size && iter.hasNext()) {
				NBTTagCompound tagEntry = new NBTTagCompound();
				
				NBTUUID uid = NBTUUID.fromUUID(iter.next());
				uid.write(tagEntry);
				
				tagEntries.setTag(KEY_ENTRY_BASE + i++, tagEntry);
			}
		}
		
		tagTeam.setTag(KEY_ENTRIES, tagEntries);
		
		tagCompound.setTag(KEY_TEAM, tagTeam);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagTeam = null;
		
		if (tagCompound.hasKey(KEY_TEAM, NBT.TAG_COMPOUND))
			tagTeam = tagCompound.getCompoundTag(KEY_TEAM);
		else
			throw new IllegalArgumentException();
		
		String teamName = null;
		if (tagTeam.hasKey(KEY_TEAM_NAME, NBT.TAG_STRING))
			teamName = tagTeam.getString(KEY_TEAM_NAME);
		else
			throw new IllegalArgumentException();
		
		if (!teamObj.getTeamName().equals(teamName))
			throw new RuntimeException("the target team data file had been extremely damaged!");
		
		if (tagTeam.hasKey(KEY_LEADER, NBT.TAG_COMPOUND)) {
			NBTTagCompound tagLeader = tagTeam.getCompoundTag(KEY_LEADER);
			
			NBTUUID uid = NBTUUID.fromUUID(UUID.randomUUID());
			uid.read(tagLeader);
			
			this.leader = uid.getUniqueID();
		}
		
		if (tagTeam.hasKey(KEY_TARGET_CHEST, NBT.TAG_COMPOUND)) {
			NBTTagCompound tagChest = tagTeam.getCompoundTag(KEY_TARGET_CHEST);
		
			if (teamChest == null)
				teamChest = new TeamChest();
			
			try { teamChest.read(tagChest);	}
			catch (NBTLoadException e) { e.printStackTrace(); }
		}
		
		if (tagTeam.hasKey(KEY_FLOWER_SHOP, NBT.TAG_COMPOUND)) {
			NBTTagCompound tagFlowerShop = tagTeam.getCompoundTag(KEY_FLOWER_SHOP);
			
			BlockPos pos = BlockPos.zero();
			pos.read(tagFlowerShop);
			
			inventoryWarp = new InventoryWarp(pos);
		}
		
		NBTTagCompound tagEntries = null;
		if (tagTeam.hasKey(KEY_ENTRIES, NBT.TAG_COMPOUND))
			tagEntries = tagTeam.getCompoundTag(KEY_ENTRIES);
		else
			throw new IllegalArgumentException();
		
		int size = 0;
		if (tagEntries.hasKey(KEY_SIZE, NBT.TAG_INT))
			size = tagEntries.getInteger(KEY_SIZE);
		else
			throw new IllegalArgumentException();
		
		if (size != 0) {
			
			for (int i=0; i < size; i++) {
				NBTTagCompound tagEntry = null;
				String key = KEY_ENTRY_BASE + i;
				
				if (tagEntries.hasKey(key, NBT.TAG_COMPOUND))
					tagEntry = tagEntries.getCompoundTag(key);
				else
					throw new IllegalArgumentException();
				
				NBTUUID uid = new NBTUUID(UUID.randomUUID());
				uid.read(tagEntry);
				
				if (objects.contains(uid.getUniqueID()))
					throw new EntryAlreadyExistException(uid.getUniqueID());
				
				if (!objects.offerLast(uid.getUniqueID())) {
					ProjectGarden.logger.warn("the team data of " + teamObj.getTeamName() + " is full");
					ProjectGarden.logger.warn("so the left entries are discarded");
				}
			}
		}
	}

}
