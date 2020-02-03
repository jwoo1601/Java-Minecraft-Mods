package jwk.minecraft.garden.network;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.shop.PlayerShopCache;
import jwk.minecraft.garden.shop.PortableSlot;
import jwk.minecraft.garden.shop.ShopData;
import jwk.minecraft.garden.shop.ShopEventListener;
import jwk.minecraft.garden.shop.ShopManager;
import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.util.JUtil;
import jwk.minecraft.garden.util.NBTByteConverter;
import jwk.minecraft.garden.util.PlayerUtil;
import jwk.minecraft.garden.util.SoundUtil;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class PacketShop implements IMessage {

	public static enum Action {
		
		SET_SHOP_DATA, // Shop data
		OPEN_SHOP, // SERVER -> CLIENT
		OPEN_SHOP_SETUP,
		CLOSE_SHOP,
		CLOSE_SHOP_SETUP,
		
		SLOT_CLICK, // CLIENT -> SERVER  : UUID, Slot Number
		SLOT_RIGHT_CLICK,
		CLOSE, // C -> S : UUID
		
		CHANGE_PRICE;
		
	}
	
	private Action action;
	private Object data;
	
	private UUID id;
	
	public PacketShop() { }
	
	public PacketShop(Action action, Object data, UUID id) {
		this.action = action;
		this.data = data;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		action = Action.values()[buf.readInt()];
		
		switch (action) {
		
		case SET_SHOP_DATA:
			try {
				data = NBTByteConverter.readNBT(buf);
			}
			catch (IOException e) { e.printStackTrace(); }
			
			break;
			
		case OPEN_SHOP:
			break;
			
		case OPEN_SHOP_SETUP:
			break;
			
		case CLOSE_SHOP:
			break;
			
		case CLOSE_SHOP_SETUP:
			break;
			
		case SLOT_CLICK:
			id = JUtil.readUUID(buf);
			data = buf.readInt();
			break;
			
		case SLOT_RIGHT_CLICK:
			id = JUtil.readUUID(buf);
			data = buf.readInt();
			break;
			
		case CLOSE:
			id = JUtil.readUUID(buf);
			break;
			
		case CHANGE_PRICE:
			int size = buf.readInt();
			List<PortableSlot> list = Lists.newArrayListWithCapacity(size);
			
			for (int i=0; i < size; i++) {
				PortableSlot slot = new PortableSlot();
			
				slot.deserialize(buf);
				list.add(slot);
			}
			
			data = list;
			
			id = JUtil.readUUID(buf);
			
			break;
			
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(action.ordinal());
		
		switch (action) {
		
		case SET_SHOP_DATA:
			NBTTagCompound tagShop = new NBTTagCompound();
			ShopData dataS = (ShopData) data;
			
			dataS.write(tagShop);
			try {
				NBTByteConverter.writeNBT(buf, tagShop);
			}
			catch (IOException e) { e.printStackTrace(); }
			
			break;
			
		case OPEN_SHOP:
			break;
			
		case OPEN_SHOP_SETUP:
			break;
			
		case CLOSE_SHOP:
			break;
			
		case CLOSE_SHOP_SETUP:
			break;
			
		case SLOT_CLICK:
			JUtil.writeUUID(buf, id);
			buf.writeInt((Integer) data);
			break;
			
		case SLOT_RIGHT_CLICK:
			JUtil.writeUUID(buf, id);
			buf.writeInt((Integer) data);
			break;
			
		case CLOSE:
			JUtil.writeUUID(buf, id);
			break;
			
		case CHANGE_PRICE:
			List<PortableSlot> list = (List<PortableSlot>) data;
			
			if (list.isEmpty())
				return;
			
			buf.writeInt(list.size());
			
			for (PortableSlot slot : list)
				slot.serialize(buf);
			
			JUtil.writeUUID(buf, id);
			break;
			
		}
	}
	
	
	public static class ClientHandler implements IMessageHandler<PacketShop, IMessage> {

		@Override
		public IMessage onMessage(PacketShop message, MessageContext ctx) {
			
			switch (message.action) {
			
			case SET_SHOP_DATA:
				ProjectGarden.proxy.getCurrentShopData().read((NBTTagCompound) message.data);
				break;
				
			case OPEN_SHOP:
				ProjectGarden.proxy.openGuiShop();
				break;
				
			case OPEN_SHOP_SETUP:
				ProjectGarden.proxy.openGuiShopSetup();
				break;
				
			case CLOSE_SHOP:
				ProjectGarden.proxy.closeGuiShop();
				break;
				
			case CLOSE_SHOP_SETUP:
				ProjectGarden.proxy.closeGuiShopSetup();
				break;
				
			default:
				System.err.println("Invalid Packet Type: " + message.action.name());
				break;
			}
			
			return null;
		}
		
	}
	
	public static class ServerHandler implements IMessageHandler<PacketShop, IMessage> {

		@Override
		public IMessage onMessage(PacketShop message, MessageContext ctx) {
			
			switch (message.action) {
			
			case SLOT_CLICK:
				int idx = (Integer) message.data;
				EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(message.id);
				
				if (player == null) {
					System.err.println("Could not find player -uuid: " + message.id);
					break;
				}
				
				else if (!PlayerShopCache.contains(player)) {
					System.err.println("No Current Player ShopData Cache -uuid: " + message.id);
					break;
				}
				
				ShopManager manager = PlayerShopCache.get(player);
				
				ShopEventListener.onSlotClicked(manager, idx, player);
				
				break;
				
			case SLOT_RIGHT_CLICK:
				int index = (Integer) message.data;
				EntityPlayerMP mp = PlayerUtil.getPlayerFromUUID(message.id);
				
				if (mp == null) {
					System.err.println("Could not find player -uuid: " + message.id);
					break;
				}
				
				else if (!PlayerShopCache.contains(mp)) {
					System.err.println("No Current Player ShopData Cache -uuid: " + message.id);
					break;
				}
				
				ShopManager man = PlayerShopCache.get(mp);
				
				ShopEventListener.onSlotRightClicked(man, index, mp);
				
				break;
				
			case CLOSE:
				EntityPlayerMP p = PlayerUtil.getPlayerFromUUID(message.id);
				
				if (p == null) {
					System.err.println("Could not find player -uuid: " + message.id);
					break;
				}
				
				ShopManager m = PlayerShopCache.get(p);
				
				if (m == null)
					break;
				
				PlayerShopCache.remove(p);
				
				if (m.getShopType() == ShopType.PURCHASE)
					SoundUtil.playSoundTo(p, SoundType.CLOSE_PURCHASE_SHOP_GUI);
				else
					SoundUtil.playSoundTo(p, SoundType.CLOSE_SALE_SHOP_GUI);
				
				break;
				
			case CHANGE_PRICE:
				EntityPlayerMP mp2 = PlayerUtil.getPlayerFromUUID(message.id);
				
				if (mp2 == null) {
					System.err.println("Could not find player -uuid: " + message.id);
					break;
				}
				
				else if (!PlayerShopCache.contains(mp2)) {
					System.err.println("No Current Player ShopData Cache -uuid: " + message.id);
					break;
				}
				
				ShopManager man2 = PlayerShopCache.get(mp2);
				List<PortableSlot> list = (List<PortableSlot>) message.data;
				
				for (PortableSlot slot : list)
					man2.setSlotPrice(slot.getIndex(), slot.getValue());
				
				mp2.addChatComponentMessage(ProjectGarden.toFormatted("설정이 변경되었습니다."));
				
				List<EntityPlayerMP> playerList = PlayerShopCache.getAllCustomers(man2);
				if (playerList.isEmpty())
					break;
				
				for (EntityPlayerMP current : playerList)
					ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.CLOSE_SHOP, null, null), current);
				
				break;
				
			default:
				System.err.println("Invalid Packet Type: " + message.action.name());
				break;
			}
			
			return null;
		}
		
	}

}
