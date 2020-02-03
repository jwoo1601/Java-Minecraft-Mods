package noah.tpblockmod.proxy;

import java.util.ArrayList;
import java.util.Iterator;

public class ServerProcessor extends CProxy {
	public static ArrayList<Place> PlaceList = new ArrayList();
	public static ArrayList<Place> LinkedBlockList = new ArrayList();
	
	public static boolean isAlreadyExist(String name) {		
		for (int i=0; i<PlaceList.size(); i++) {
			if (PlaceList.get(i).getPlaceName().equals(name))
				return true;
		}
		
		return false;
	}
}
