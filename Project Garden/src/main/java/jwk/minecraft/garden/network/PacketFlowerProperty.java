package jwk.minecraft.garden.network;

import static com.google.common.base.Preconditions.checkNotNull;
import static jwk.minecraft.garden.ProjectGarden.DEBUG;

import java.io.IOException;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import jwk.minecraft.garden.flower.FlowerProperty;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.NBTByteConverter;
import net.minecraft.nbt.NBTTagCompound;

public class PacketFlowerProperty implements IMessage {

	public static enum Type {
		SINGLE((byte) 0),
		ARRAY((byte) 1),
		DISPLAY((byte) 2);
		
		public final int Value;
		
		private Type(byte value) {
			Value = value;
		}
		
		public static Type fromByte(byte value) {
			
			switch (value) {
			
			case 0:
				return SINGLE;
					
			case 1:
				return ARRAY;
				
			case 2:
				return DISPLAY;
				
			default:
				throw new IllegalArgumentException();
			}
		}
		
	}
	
	private Type dataType;
	private Object data;
	
	public PacketFlowerProperty() { }
	
	public PacketFlowerProperty(@Nonnull Type dataType, @Nonnull Object data) {
		checkNotNull(dataType);
		checkNotNull(data);
		
		if (dataType == Type.SINGLE && !(data instanceof FlowerProperty))
			throw new IllegalArgumentException();
		else if (dataType == Type.ARRAY && !(data instanceof FlowerProperty[]))
			throw new IllegalArgumentException();
		else if (dataType == Type.DISPLAY && !(data instanceof BlockPos))
			throw new IllegalArgumentException();
		
		this.dataType = dataType;
		this.data = data;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		dataType = Type.fromByte(buf.readByte());
		
		switch (dataType) {
		
		case SINGLE:
			try {
				NBTTagCompound tagProperty = NBTByteConverter.readNBT(buf);
				FlowerProperty property = new FlowerProperty();
				
				property.read(tagProperty);
				this.data = property;
			}
			
			catch (IOException e) { throw new RuntimeException(e); }
			
			break;
			
		case ARRAY:
			int size = buf.readInt();
			
			FlowerProperty[] properties = new FlowerProperty[size];
			
			for (int i=0; i < properties.length; i++) {
				
				try {
					NBTTagCompound tagProp = NBTByteConverter.readNBT(buf);
					FlowerProperty prop = new FlowerProperty();
					
					prop.read(tagProp);
					properties[i] = prop;
				}
				
				catch (IOException e) { throw new RuntimeException(e); }
			}
			
			this.data = properties;
			
			break;
			
		case DISPLAY:
			try {
				NBTTagCompound tagMain = NBTByteConverter.readNBT(buf);
				BlockPos pos = BlockPos.zero();
				
				pos.read(tagMain);
				this.data = pos;
			}
			
			catch (IOException e) { e.printStackTrace(); }
			
			break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(dataType.Value);
		
		switch (dataType) {
		
		case SINGLE:
			NBTTagCompound tagProperty = new NBTTagCompound();
			
			FlowerProperty property = (FlowerProperty) data;
			property.write(tagProperty);
			
			try { NBTByteConverter.writeNBT(buf, tagProperty); }
			
			catch (IOException e) {	e.printStackTrace(); }
			
			break;
			
		case ARRAY:
			FlowerProperty[] properties = (FlowerProperty[]) data;
			
			if (properties.length == 0)
				throw new IllegalArgumentException();
			
			buf.writeInt(properties.length);
			
			for (FlowerProperty prop : properties) {
				NBTTagCompound tagProp = new NBTTagCompound();
				
				prop.write(tagProp);
				
				try { NBTByteConverter.writeNBT(buf, tagProp); }
				
				catch (IOException e) { e.printStackTrace(); }
			}
			
			break;
			
		case DISPLAY:
			NBTTagCompound tagMain = new NBTTagCompound();
			
			BlockPos pos = (BlockPos) data;
			pos.write(tagMain);
			
			try { NBTByteConverter.writeNBT(buf, tagMain); }
			
			catch (IOException e) {	e.printStackTrace(); }
			
			break;
		}
	}

	
	public static class ClientHandler implements IMessageHandler<PacketFlowerProperty, IMessage> {

		@Override
		public IMessage onMessage(PacketFlowerProperty message, MessageContext ctx) {
			
			switch (message.dataType) {	
			
			case SINGLE:
				ProjectGarden.proxy.getCurrentCache().inject(new FlowerInfo((FlowerProperty) message.data));
				break;
				
			case ARRAY:
				FlowerProperty[] properties = (FlowerProperty[]) message.data;
				FlowerInfo[] infoArray = new FlowerInfo[properties.length];
				
				for (int i=0; i < infoArray.length; i++)
					infoArray[i] = new FlowerInfo(properties[i]);
				
				ProjectGarden.proxy.getCurrentCache().injectAll(infoArray);
				
				if (DEBUG)
					System.out.println("Handled Properties Length=" + properties.length);
				
				break;
			
			case DISPLAY:
				BlockPos pos = (BlockPos) message.data;
				FlowerInfo info = ProjectGarden.proxy.getCurrentCache().get(pos);
				
				if (DEBUG)
					System.out.println("Handled Display pos=" + pos);
				
				ProjectGarden.proxy.addDisplayFlowerInfo(info);
				
				break;
			}
			
			return null;
		}
		
	}
}
