package jwk.minecraft.garden.network;

import static net.minecraft.util.EnumChatFormatting.*;

import java.io.IOException;
import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.container.ContainerTeamChest;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.util.JUtil;
import jwk.minecraft.garden.util.NBTByteConverter;
import jwk.minecraft.garden.util.PlayerUtil;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class PacketDelivery implements IMessage {
	
	public static enum Action {
		
		DELIVER,
		FAKE_TEAM,
		INV_TEAM,
		
		NOTIFY;
		
	}
	
	private Action action;
	private Object data;
	
	public PacketDelivery() { }
	
	public PacketDelivery(Action action, Object data) {
		
		if (action == Action.DELIVER && !(data instanceof UUID))
			throw new IllegalArgumentException();
		else if (action == Action.FAKE_TEAM && !(data instanceof String))
			throw new IllegalArgumentException();
		else if (action == Action.INV_TEAM && !(data instanceof InventoryTeamChest))
			throw new IllegalArgumentException();
		else if (action == Action.NOTIFY && !(data instanceof UUID))
			throw new IllegalArgumentException();
		
		this.action = action;
		this.data = data;
	} 

	@Override
	public void fromBytes(ByteBuf buf) {
		action = Action.values()[buf.readInt()];
		
		switch (action) {
		
		case DELIVER:
			data = JUtil.readUUID(buf);
			break;
			
		case FAKE_TEAM:
			data = JUtil.readString(buf);
			break;
			
		case INV_TEAM:
			try {
				data = NBTByteConverter.readNBT(buf);
			}
			catch (IOException e) { e.printStackTrace(); }
			
			break;
			
		case NOTIFY:
			data = JUtil.readUUID(buf);
			break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(action.ordinal());
		
		switch (action) {
		
		case DELIVER:
			JUtil.writeUUID(buf, (UUID) data);
			break;
			
		case FAKE_TEAM:
			JUtil.writeString(buf, (String) data);
			break;
			
		case INV_TEAM:
			NBTTagCompound tagInv = new NBTTagCompound();
			InventoryTeamChest invTeam = (InventoryTeamChest) data;
			
			invTeam.write(tagInv);
			try {
				NBTByteConverter.writeNBT(buf, tagInv);
			}
			
			catch (IOException e) {	e.printStackTrace(); }
			break;
			
		case NOTIFY:
			JUtil.writeUUID(buf, (UUID) data);
			break;
		}
	}
	
	
	public static class ClientHandler implements IMessageHandler<PacketDelivery, IMessage> {

		@Override
		public IMessage onMessage(PacketDelivery message, MessageContext ctx) {
			
			switch (message.action) {
			
			case FAKE_TEAM:
				ProjectGarden.proxy.setCurrentTeam(ProjectGarden.proxy.newFakeTeam((String) message.data));
				break;
				
			case INV_TEAM:
				InventoryTeamChest invTeam = new InventoryTeamChest(ProjectGarden.proxy.getCurrentTeam());
				
				invTeam.read((NBTTagCompound) message.data);
				ProjectGarden.proxy.setInventoryTeam(invTeam);
				break;
				
			default:
				throw new RuntimeException("Action: DELIVER is could not handled on Client Side");
				
			}
			return null;
		}
		
	}
	
	public static class ServerHandler implements IMessageHandler<PacketDelivery, IMessage> {

		@Override
		public IMessage onMessage(PacketDelivery message, MessageContext ctx) {
			
			switch (message.action) {
			
			case DELIVER:
				EntityPlayerMP player = PlayerUtil.getPlayerFromUUID((UUID) message.data);
				
				if (player == null) {
					ProjectGarden.logger.error("Cannot Handler PacketDelivery: " + message.action.name());
					ProjectGarden.logger.error("Cause: Cannot Find Player <UUID= " + message.data.toString() + ">");
					return null;
				}
				
				Container container = player.openContainer;
				
				if (container == null || !(container instanceof ContainerTeamChest)) {
					player.addChatComponentMessage(ProjectGarden.toFormatted(EnumChatFormatting.RED + "열려있는 컨테이너가 ContainerTeamChest 가 아닙니다"));
					return null;
				}
				
				InventoryTeamChest invTeam = ((ContainerTeamChest) container).getInventoryTeam();
			    invTeam.deliverContents(player);
				
				break;
				
			case NOTIFY:
				EntityPlayerMP mp = PlayerUtil.getPlayerFromUUID((UUID) message.data);
				
				if (mp == null) {
					ProjectGarden.logger.error("Cannot Handler PacketDelivery: " + message.action.name());
					ProjectGarden.logger.error("Cause: Cannot Find Player <UUID= " + message.data.toString() + ">");
					return null;
				}
				
				Container ct = mp.openContainer;
				
				if (ct == null || !(ct instanceof ContainerTeamChest)) {
					mp.addChatComponentMessage(ProjectGarden.toFormatted(EnumChatFormatting.RED + "열려있는 컨테이너가 ContainerTeamChest 가 아닙니다"));
					return null;
				}
				
				ITeam team = ((ContainerTeamChest) ct).getInventoryTeam().getTeam();
				team.getManager().sendToAllMembers(ProjectGarden.toFormatted(GOLD + "팀 창고" + WHITE + "를 " + RED + "확인" + WHITE + "해 주세요"));
				team.getManager().playSoundToAllMembers(SoundType.DELIVER_SUCCESS);
				
				break;
				
			default:
				throw new RuntimeException("Action: " + message.action.name() + " is could not handled on Server Side");
			}
			
			return null;
		}
		
	}

}
