package noah.teleport.place;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.teleport.util.Common;
import noah.teleport.util.LocalizationManager;

public class PlaceRegistry {
	
	@Override
	public void finalize() {
		System.out.println("Place Registry FINALIZED!");
	}
	
	public PlaceRegistry(Side side) {
		this.isFake = Side.CLIENT == side ? true : false;
		this.PLACE_GENERIC_NAME = LocalizationManager.localize(LocalizationManager.TYPE_COMMON, "place.generic.name");
		this.storage = new HashMap<Integer, Place>();
	}
	
	private boolean isFake;
	
	public final String PLACE_GENERIC_NAME;
	
	private int ID_INDEX = 0;
	private Map<Integer, Place> storage;
	
	public boolean isClientRegistry() {
		return this.isFake;
	}
	
	public int getSize() {
		return this.storage.size();
	}
	
	/**
	 * 현재 고유 식별자의 인덱스를 가져옵니다.
	 * @return 현재 고유 식별자의 인덱스
	 */
	public int getCurrentIndex() {
		return ID_INDEX;
	}
	
	private void syncIndexofID(int target) {
		this.ID_INDEX = target > this.ID_INDEX ? target : this.ID_INDEX;
	}
	
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	public void registerPlace(Place target) {
		if (isFake)
			System.err.println("Cannot Use this Method on Client Side!");
		
		else {
			ID_INDEX += 1;
			target.setID(ID_INDEX);
			storage.put(ID_INDEX, target);
		}
	}
	
	public void registerPlace(int id, Place target) {
		if (storage.containsKey(id))
			System.err.println("ID is already registered!");
		else {
			storage.put(id, target);
			syncIndexofID(id);
			}
	}
	
	public void deletePlaceByID(int id) {
		if(storage.containsKey(id))
			storage.remove(id);
		
		else
			System.err.println("The Place is not Exist for ID!");
	}
	
	public Place getPlaceByID(int id) {
		if (storage.containsKey(id)) {
			Common.Debug("ID=" + id + "Contains=YES");
			return storage.get(id);
		}
		else {
			Common.Debug("ID=" + id + "Contains=NO");
			return null;
		}
	}
	
	public Place getPlaceByBlockPos(BlockPos pos) {
		if (!this.storage.isEmpty()) {
			Iterator<Place> iter = this.storage.values().iterator();
			
			while(iter.hasNext()) {
				Place next = iter.next();
				
				if (next.getBlockPos().equals(pos))
					return next;
			}
		}
		
		return null;
	}
	
	public Collection<Place> getAllPlaces() {
		if (!storage.isEmpty())
			return storage.values();
		
		return null;
	}
	
	public void removeAllPlaces() {
		if (!storage.isEmpty())
			storage.clear();
	}
	
	public String getNextPlaceGenericName() {
		if (isFake)
			return PLACE_GENERIC_NAME;
		else
			return PLACE_GENERIC_NAME + (ID_INDEX + 1);
	}
}
