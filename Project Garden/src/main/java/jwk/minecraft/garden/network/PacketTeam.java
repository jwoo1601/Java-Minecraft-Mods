package jwk.minecraft.garden.network;

import static com.google.common.base.Preconditions.checkNotNull;
import static jwk.minecraft.garden.ProjectGarden.DEBUG;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.team.container.ContainerTeamChest;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.util.JUtil;
import jwk.minecraft.garden.util.NBTByteConverter;
import jwk.minecraft.garden.util.NBTUserProfile;
import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class PacketTeam implements IMessage {

	public static enum Action {
		
		SET,
		JOIN,
		LEAVE,
		RESET,
		LEADER;
		
	}
	
	public PacketTeam() { }
	
	public PacketTeam(@Nonnull Action action, @Nullable Object data, @Nullable String teamName) {
		checkNotNull(action);
		
		if (action == Action.SET && (!(data instanceof List) || teamName == null))
			throw new IllegalArgumentException();
		else if (action == Action.JOIN && !(data instanceof NBTUserProfile))
			throw new IllegalArgumentException();
		else if (action == Action.LEAVE && !(data instanceof NBTUserProfile))
			throw new IllegalArgumentException();
		else if (action == Action.LEADER && !(data instanceof NBTUserProfile))
			throw new IllegalArgumentException();
		
		this.action = action;
		this.data = data;
		this.teamName = teamName;
	}
	
	private Action action;
	private Object data;
	
	// Only used for Action.SET
	private String teamName = null;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		action = Action.values()[buf.readInt()];
		
		switch (action) {
		
		case SET:
			int size = buf.readInt();
			
			List<NBTUserProfile> list = Lists.newArrayListWithCapacity(size);
			
			for (int i=0; i < size; i++) {
				
				try {
					NBTTagCompound tagProfile = NBTByteConverter.readNBT(buf);
					
					NBTUserProfile profile = NBTUserProfile.newTempProfile();
					profile.read(tagProfile);
					
					list.add(profile);
				}
				
				catch (Exception e) { e.printStackTrace(); return; }
			}
			
			data = list;			
			teamName = JUtil.readString(buf);
			
			break;
			
		case JOIN:
			try {
				NBTTagCompound tagProfile = NBTByteConverter.readNBT(buf);
				
				NBTUserProfile profile = NBTUserProfile.newTempProfile();
				profile.read(tagProfile);
				
				data = profile;
			}
			
			catch (Exception e) { e.printStackTrace(); return; }
			
			break;
			
		case LEAVE:
			try {
				NBTTagCompound tagProfile = NBTByteConverter.readNBT(buf);
				
				NBTUserProfile profile = NBTUserProfile.newTempProfile();
				profile.read(tagProfile);
				
				data = profile;
			}
			
			catch (Exception e) { e.printStackTrace(); return; }
			
			break;
			
		case RESET:
			break;
			
		case LEADER:
			try {
				NBTTagCompound tagProfile = NBTByteConverter.readNBT(buf);
				
				NBTUserProfile profile = NBTUserProfile.newTempProfile();
				profile.read(tagProfile);
				
				data = profile;
			}
			
			catch (Exception e) { e.printStackTrace(); return; }
			
			break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(action.ordinal());
		
		switch (action) {
		
		case SET:
			List<NBTUserProfile> list = (List<NBTUserProfile>) data;
			buf.writeInt(list.size());
			
			for (NBTUserProfile profile : list) {
				NBTTagCompound tagProfile = new NBTTagCompound();
				profile.write(tagProfile);
				
				try { NBTByteConverter.writeNBT(buf, tagProfile); }
				catch (Exception e) { e.printStackTrace(); return; }
			}
			
			JUtil.writeString(buf, teamName);
			
			break;
			
		case JOIN:
			NBTTagCompound tagProfile = new NBTTagCompound();
			((NBTUserProfile) data).write(tagProfile);
			
			try { NBTByteConverter.writeNBT(buf, tagProfile); }
			catch (Exception e) { e.printStackTrace(); return; }
			
			break;
			
		case LEAVE:
			NBTTagCompound tagProfile2 = new NBTTagCompound();
			((NBTUserProfile) data).write(tagProfile2);
			
			try { NBTByteConverter.writeNBT(buf, tagProfile2); }
			catch (Exception e) { e.printStackTrace(); return; }
			break;
			
		case RESET:
			break;
			
		case LEADER:
			NBTTagCompound tagProfile3 = new NBTTagCompound();
			((NBTUserProfile) data).write(tagProfile3);
			
			try { NBTByteConverter.writeNBT(buf, tagProfile3); }
			catch (Exception e) { e.printStackTrace(); return; }
			
			break;
		}
	}

	
	public static class ClientHandler implements IMessageHandler<PacketTeam, IMessage> {

		@Override
		public IMessage onMessage(PacketTeam message, MessageContext ctx) {
			
			if (DEBUG)
				System.out.println("PacketTeamClient Received <action= " + message.action.name() + ">");
			
			switch (message.action) {
			
			case SET:
				List<NBTUserProfile> list = (List<NBTUserProfile>) message.data;
				
				ProjectGarden.proxy.getTeamClient().onSetTeam(message.teamName, list);
				System.out.println("Handled PacketTeam SET : member size=" + list.size());
				break;
				
			case JOIN:
				ProjectGarden.proxy.getTeamClient().onJoin((NBTUserProfile) message.data); 
				break;
				
			case LEAVE:
				ProjectGarden.proxy.getTeamClient().onLeave((NBTUserProfile) message.data);
				break;
				
			case RESET:
				ProjectGarden.proxy.getTeamClient().onResetTeam();
				break;
				
			case LEADER:
				ProjectGarden.proxy.getTeamClient().onSetLeader((NBTUserProfile) message.data);
				break;
			}
			
			return null;
		}
		
	}
	
}
