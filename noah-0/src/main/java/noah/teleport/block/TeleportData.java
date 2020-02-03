package noah.teleport.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.teleport.Main;
import noah.teleport.network.TeleportPacketHandler;
import noah.teleport.place.Place;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;
import noah.teleport.util.ServerType;

public class TeleportData extends TileEntity {
	private final String KEY_OWN_PLACE = "ownPlace";
	private final String KEY_LINKED_PLACE = "linkedPlace";	
	
	private Place ownplace;
	private Place linkedplace;
	
	//SERVER SIDE ONLY
	public boolean isRegistered = false;
	//CLIENT SIDE ONLY
	public boolean isFRegistered = false;
	
	public TeleportData(String name, Place linked) {
		this.ownplace = new Place(name, 0, 0, 0);		
		this.linkedplace = linked;
	}
	
	public TeleportData() {
		this.ownplace = new Place(null, 0, 0, 0);
	}
	
	public int getOwnPlaceID() {
		return this.ownplace.getID();
	}
	
	public void setOwnPlaceName(String name) {
		if (name != null)
			this.ownplace.setName(name);
		else
			System.err.println("Cannot Set Own Place name to Null by using TeleportData.setOwnPlaceName()!");
	}
	
	public Place getOwnPlace() {
		return this.ownplace;
	}
	
	public Place getLinkedPlace() {
		return this.linkedplace;
	}
	
	public void setLinkedPlace(Place place) {
		this.linkedplace = place;
	}
	
	//SERVER SIDE
	@Override
	public Packet getDescriptionPacket() { 
		NBTTagCompound nbtdata = new NBTTagCompound();
		this.writeToNBT(nbtdata);
		return new S35PacketUpdateTileEntity(this.pos, this.getBlockMetadata(), nbtdata);
	}
	
	//CLIENT SIDE
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());

		if (!isFRegistered && Common.isClientThread()) {
			Main.Proxy.FakePlaceReg.registerPlace(this.ownplace.getID(), this.ownplace);
			this.isFRegistered = true;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound);
		
		NBTTagCompound ownplacenbt = new NBTTagCompound();
		this.ownplace.writeToNBTData(ownplacenbt);
		parentNBTTagCompound.setTag(KEY_OWN_PLACE, ownplacenbt);
		
		if (this.linkedplace != null) {
			NBTTagCompound linkedplacenbt = new NBTTagCompound();
			this.ownplace.writeToNBTData(linkedplacenbt);
			parentNBTTagCompound.setTag(KEY_LINKED_PLACE, linkedplacenbt);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
		super.readFromNBT(parentNBTTagCompound);
		
		if (!parentNBTTagCompound.hasKey(KEY_OWN_PLACE))
			System.err.println("A Critical Error has occured during Reading NBT Data!");
		else {
			NBTTagCompound ownplacenbt = parentNBTTagCompound.getCompoundTag(KEY_OWN_PLACE);
			this.ownplace.readFromNBTData(ownplacenbt);
		}
		
		if (parentNBTTagCompound.hasKey(KEY_LINKED_PLACE)) {
			NBTTagCompound linkedplacenbt = parentNBTTagCompound.getCompoundTag(KEY_LINKED_PLACE);
			Place readPlace = new Place(Place.INVALID_ID, null, 0, 0, 0);
			readPlace.readFromNBTData(linkedplacenbt);
			this.linkedplace = readPlace;
		}
		else
			this.linkedplace = null;
	}
	
	@Override
	public void onLoad() {
		try { this.ownplace.setCoordinates(this.pos); }
		catch (Exception e) { System.err.println(e.getMessage()); }
		
		if (!isRegistered && Common.isServerThread()) {
			if (this.ownplace.getID() == Place.INVALID_ID) {
				int id = this.worldObj.provider.getDimensionId();
				
				switch (id) {
				case Common.OVERWORLD_ID:
					Main.Proxy.PlaceReg[0].registerPlace(this.ownplace);
					break;
				case Common.NETHER_ID:
					Main.Proxy.PlaceReg[1].registerPlace(this.ownplace);
					break;
				case Common.THEEND_ID:
					Main.Proxy.PlaceReg[2].registerPlace(this.ownplace);
					break;
					default:
						Main.Proxy.PlaceReg[id].registerPlace(this.ownplace);
						break;
				}
			}
			
			else if (this.ownplace.getID() > Place.INVALID_ID) {
				int id = this.worldObj.provider.getDimensionId();
				
				switch (id) {
				case Common.OVERWORLD_ID:
					Main.Proxy.PlaceReg[0].registerPlace(this.ownplace.getID(), this.ownplace);
					break;
				case Common.NETHER_ID:
					Main.Proxy.PlaceReg[1].registerPlace(this.ownplace.getID(), this.ownplace);
					break;
				case Common.THEEND_ID:
					Main.Proxy.PlaceReg[2].registerPlace(this.ownplace.getID(), this.ownplace);
					break;
					default:
						Main.Proxy.PlaceReg[id].registerPlace(this.ownplace.getID(), this.ownplace);
						break;
				}
			}
			
			else
				System.err.println("Place ID cannot be less than INVALID_ID!");
			
			this.isRegistered = true;
		}
	}
	
	public static TeleportData searchTeleportDataByID(World world, int id) {
		List<TileEntity> l = world.loadedTileEntityList;
		
		if (l != null) {
			Iterator<TileEntity> iter = l.iterator();
			
			while(iter.hasNext()) {
				TileEntity tmp = iter.next();
				if (tmp instanceof TeleportData) {
					TeleportData data = (TeleportData)tmp;
					
					if (data.ownplace.getID() == id)
						return data;
					}
				}
			}
		
		return null;
	}
}
