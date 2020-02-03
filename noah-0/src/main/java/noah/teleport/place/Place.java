package noah.teleport.place;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class Place implements INBTData {
	
	private static final String KEY_ID = "placeID";
	private static final String KEY_NAME = "placeName";
	private static final String KEY_XCOORD = "x";
	private static final String KEY_YCOORD = "y";
	private static final String KEY_ZCOORD = "z";
	private static final String KEY_COORDS = "placePos";
	
	public static final int INVALID_ID = -1;
	
	private int id;
	private String name;
	private int xcoord;
	private int ycoord;
	private int zcoord;

	public Place(String name, int x, int y, int z) {
		this.id = INVALID_ID;
		this.name = name;
		this.setCoordinates(x, y, z);
	}
	
	public Place(int id, String name, int x, int y, int z) {
		this.id = id;
		this.name = name;
		this.setCoordinates(x, y, z);
	}
	
	public Place(String name, BlockPos pos) throws Exception {
		this.id = INVALID_ID;
		this.name = name;
		this.setCoordinates(pos);
	}
	
	public Place(int id, String name, BlockPos pos) throws Exception {
		this.id = id;
		this.name = name;
		this.setCoordinates(pos);
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BlockPos getBlockPos() {
		return new BlockPos(this.xcoord, this.ycoord, this.zcoord);
	}
	
	public void setCoordinates(int x, int y, int z) {
		this.xcoord = x;
		this.ycoord = y;
		this.zcoord = z;
	}
	
	public void setCoordinates(BlockPos pos) throws Exception {
		if (pos == null)
			throw new NullPointerException("Cannot get place Coordinates from Null Reference!");
		else {
			this.xcoord = pos.getX();
			this.ycoord = pos.getY();
			this.zcoord = pos.getZ();
		}			
	}
	
	public int getX() {
		return this.xcoord;
	}
	
	public void setX(int x) {
		this.xcoord = x;
	}
	
	public int getY() {
		return this.ycoord;
	}
	
	public void setY(int y) {
		this.ycoord = y;
	}
	
	public int getZ() {
		return this.zcoord;
	}
	
	public void setZ(int z) {
		this.zcoord = z;
	}
	
	@Override
	public void writeToNBTData(NBTTagCompound nbtdata) {
		nbtdata.setInteger(KEY_ID, this.id);
		nbtdata.setString(KEY_NAME, this.name);
		
		NBTTagCompound coordsdata = new NBTTagCompound();
		coordsdata.setInteger(KEY_XCOORD, this.xcoord);
		coordsdata.setInteger(KEY_YCOORD, this.ycoord);
		coordsdata.setInteger(KEY_ZCOORD, this.zcoord);
		
		nbtdata.setTag(KEY_COORDS, coordsdata);
	}

	@Override
	public void readFromNBTData(NBTTagCompound nbtdata) {
		final int NBT_INT_ID = 3;		
		final int NBT_STRING_ID = 8;
		
		if (nbtdata.hasKey(KEY_ID, NBT_INT_ID)) {
			int readID = nbtdata.getInteger(KEY_ID);
			
			if (readID > INVALID_ID)
				this.id = readID;
			else
				System.err.println("Place ID is cannot be less than value of INVALID_ID");
		}
		
		String readName = null;
		if (nbtdata.hasKey(KEY_NAME, NBT_STRING_ID))
			readName = nbtdata.getString(KEY_NAME);
		
		this.setName(readName);		

		NBTTagCompound coordsdata = nbtdata.getCompoundTag(KEY_COORDS);
		BlockPos readPos = null;
		if (coordsdata.hasKey(KEY_XCOORD, NBT_INT_ID) && coordsdata.hasKey(KEY_YCOORD, NBT_INT_ID) && coordsdata.hasKey(KEY_ZCOORD, NBT_INT_ID))
			readPos = new BlockPos(coordsdata.getInteger(KEY_XCOORD), coordsdata.getInteger(KEY_YCOORD), coordsdata.getInteger(KEY_ZCOORD));
		
		try { this.setCoordinates(readPos);	}
		catch (Exception e) { System.err.println(e.getMessage());; }
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Place) {
			Place objplace = (Place)obj;
			if (this.id == objplace.id && this.name.equals(objplace.name) && this.xcoord == objplace.xcoord && this.ycoord == objplace.ycoord && this.zcoord == objplace.zcoord)
				return true;
		}
		
		return false;
	}
}
