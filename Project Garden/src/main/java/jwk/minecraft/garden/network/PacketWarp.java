package jwk.minecraft.garden.network;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.NBTByteConverter;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.nbt.NBTTagCompound;

public class PacketWarp implements IMessage {

	public static enum Action {
		
		SET_FLOWER_SHOP;
		
	}
	
	private Action action;
	private BlockPos position;
	
	public PacketWarp() { }
	
	public PacketWarp(@Nonnull Action action, InventoryWarp invWarp) {
		checkNotNull(action);
		
		this.action = action;
		this.position = invWarp == null? null : invWarp.getWarpData(6).getPosition();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		action = Action.values()[buf.readInt()];
		
		switch (action) {
		
		case SET_FLOWER_SHOP:
			
			if (buf.readableBytes() != 0) {
				BlockPos pos = BlockPos.zero();
				
				try { pos.read(NBTByteConverter.readNBT(buf)); position = pos; }
				catch (Exception e) { e.printStackTrace(); return; }
			}
			
			break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(action.ordinal());
		
		switch (action) {
		
		case SET_FLOWER_SHOP:
			
			if (position != null) {
				NBTTagCompound tagPos = new NBTTagCompound();
			
				position.write(tagPos);
			
				try { NBTByteConverter.writeNBT(buf, tagPos); }
				catch (Exception e) { e.printStackTrace(); return; }
			
			}
			
			break;
		}
	}
	
	
	public static class ClientHandler implements IMessageHandler<PacketWarp, IMessage> {

		@Override
		public IMessage onMessage(PacketWarp message, MessageContext ctx) {
			
			switch (message.action) {
			
			case SET_FLOWER_SHOP:
				ProjectGarden.proxy.setInventoryWarp(message.position == null? null : new InventoryWarp(message.position));
				break;
				
			}
			
			return null;
		}
		
	}
	
}
