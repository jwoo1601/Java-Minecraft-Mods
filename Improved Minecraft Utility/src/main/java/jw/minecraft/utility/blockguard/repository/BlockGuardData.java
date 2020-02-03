package jw.minecraft.utility.blockguard.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import jw.minecraft.utility.LogHelper;
import jw.minecraft.utility.blockguard.region.IRegion;
import jw.minecraft.utility.blockguard.region.PipedRegion;
import jw.minecraft.utility.exception.InvalidFormatException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class BlockGuardData extends WorldSavedData {
	
	/*  !-- FILE HEADER --!
	 *  Magic Number [4]
	 *  Version [2]
	 *  World Id [1] (per World)
	 *  State Flag [1] (per World)
	 *  ExtensionFlag [1]
	 *  Chunk Size [4] (per World)
	 *  Reserved0 [1]
	 *  Reserved1 [1]
	 *  Reserved2 [1]
	 */
	
	public static final short MAX_COMPATIBLE_VERSION = 100;
	
	// --------------------------------------- DO NOT MODIFY THE FOLLOWING VALUES ----------------------------------------
	// ----------------------------------------------------HEADER---------------------------------------------------------
	public static final String Name = "BlockGuard";
	public static byte[] MagicNumbers = { 0x42, 0x4C, 0x47, 0x44 };  // BLGD to ASCII
	public static final short Version = 100;
	public static final byte ExtensionFlag = 0x01;  // 001
	public static final byte Reserved0 = 0;
	public static final byte Reserved1 = 0;
	public static final byte Reserved2 = 0;
	
	private byte WorldId = -1;
	private byte StateFlag = -1; // 0: Disabled 1: Enabled
	// -------------------------------------------------------------------------------------------------------------------
	
	// ----------------------------------------------------SOURCE---------------------------------------------------------
	private static RangeData Range = new RangeData();
	private static Map<Long, RegionChunk> ChunkMap = new HashMap<Long, RegionChunk>();
	// -------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------

	
	public static BlockGuardData get(byte wid, World world) {
		MapStorage storage = world.perWorldStorage;
		BlockGuardData instance = (BlockGuardData) storage.loadData(BlockGuardData.class, Name + "_" + wid);
		
		if (instance == null) {
			instance = new BlockGuardData(wid).enable();
			instance.register(RegionChunk.newInstance(PipedRegion.class)
					          .add(new RegionMap<PipedRegion>(true, PipedRegion.class, Mode.PROTECT))
					          .add(new RegionMap<PipedRegion>(true, PipedRegion.class, Mode.UNPROTECT)));
			storage.setData(Name, instance);
		}
		
		return instance;
	}
	
	
	public BlockGuardData() {
		super(Name);
	}
	
	public BlockGuardData(byte wid) {
		super(Name);
		
		WorldId = wid;
	}
	
	public BlockGuardData enable() {
		if (StateFlag != 1)
			StateFlag = 1;
		
		return this;
	}
	
	public BlockGuardData disable() {
		if (StateFlag != 0)
			StateFlag = 0;
		
		return this;
	}
	
	public boolean isEnabled() {
		if (StateFlag == 1)
			return true;
		else
			return false;
	}
	
	public RangeData getRange() { return this.Range; }
	
	public int chunkSize() { return ChunkMap.size(); }
	
	public boolean contains(@Nonnull RegionChunk target) {
		if (target == null)
			throw new NullPointerException("RegionChunk target");
		
		return ChunkMap.containsKey(target.handle());
	}
	
	public boolean contains(long handle) {
		return ChunkMap.containsKey(handle);
	}
	
	public boolean register(@Nonnull RegionChunk target) {
		if (target == null)
			throw new NullPointerException("RegionChunk target");
		
		if (ChunkMap.containsKey(target.handle()))
			return false;
		
		ChunkMap.put(target.handle(), target);
		return true;
	}
	
	public boolean unregister(@Nonnull RegionChunk target) {
		if (target == null)
			throw new NullPointerException("RegionChunk target");
		
		if (ChunkMap.containsKey(target.handle())) {
			ChunkMap.remove(target.handle());
			return true;
		}
		
		return false;
	}
	
	public RegionChunk getChunk(long handle) {
		return ChunkMap.get(handle);
	}
	
	public Collection<RegionChunk> getAllChunks() {
		return ChunkMap.values();
	}
	
	public List<RegionMap> getMatchedValues(Mode mode) {
		List<RegionMap> result = new ArrayList<RegionMap>();
		
		if (!ChunkMap.isEmpty()) {
			for (RegionChunk chunk : ChunkMap.values()) {
				List<RegionMap> list = chunk.getList();
				
				if (!list.isEmpty()) {
					for (RegionMap map : list) {		
						if (mode == map.getMode())
							result.add(map);
					}
				}
			}
		}
		
		return result;
	}
	
	
	private static final String KEYHEADER = "header";
	private static final String KEYMAGICNUMBER = "mn";
	private static final String KEYVERSION = "version";
	private static final String KEYWORLDID = "wid";
	private static final String KEYSTATE = "state";
	private static final String KEYEXTENSION = "extension";
	private static final String KEYCHUNKSIZE = "chunksize";
	private static final String KEYRESERVED0 = "reserved0";
	private static final String KEYRESERVED1 = "reserved1";
	private static final String KEYRESERVED2 = "reserved2";
	
	private static final String KEYSOURCE = "source";
	private static final String KEYCHUNK = "chunk -";
	
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound tag_header = new NBTTagCompound();
		tag_header.setByteArray(KEYMAGICNUMBER, MagicNumbers);
		tag_header.setShort(KEYVERSION, Version);
		tag_header.setByte(KEYWORLDID, WorldId);
		tag_header.setByte(KEYSTATE, StateFlag);
		tag_header.setByte(KEYEXTENSION, ExtensionFlag);
		
		int size = chunkSize();		
		if (size < 0)
			throw new IllegalArgumentException("ChunkSize must be bigger than -1!");
		
		tag_header.setInteger(KEYCHUNKSIZE, size);
		tag_header.setByte(KEYRESERVED0, Reserved0);
		tag_header.setByte(KEYRESERVED1, Reserved1);
		tag_header.setByte(KEYRESERVED2, Reserved2);
		tag.setTag(KEYHEADER, tag_header);
		
		NBTTagCompound tag_source = new NBTTagCompound();
		Range.writeToNBT(tag_source);
		
		if (size != 0) {
			RegionChunk[] array = ChunkMap.values().toArray(new RegionChunk[size]);
			for (int i=0; i < size; i++) {
				NBTTagCompound tag_chunk = new NBTTagCompound();
				array[i].writeToNBT(tag_chunk);
				tag_source.setTag(KEYCHUNK + i, tag_chunk);
			}
		}
		tag.setTag(KEYSOURCE, tag_source);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEYHEADER, NBT.TAG_COMPOUND)) {
			NBTTagCompound tag_header = tag.getCompoundTag(KEYHEADER);
			
			if (tag_header.hasKey(KEYMAGICNUMBER, NBT.TAG_BYTE_ARRAY)) {
				byte[] numbers = tag_header.getByteArray(KEYMAGICNUMBER);
				
				if (numbers.length == MagicNumbers.length) {
					for (int i=0; i < MagicNumbers.length; i++) {
						if (MagicNumbers[i] != numbers[i])
							throw new InvalidFormatException("Magic Numbers");
					}
					
					if (tag_header.hasKey(KEYVERSION, NBT.TAG_SHORT)) {
						short version = tag_header.getShort(KEYVERSION);
						int tv_f = (int) (version * 0.01);
						int cv_f = (int) (Version * 0.01);
						
						if (tv_f > cv_f || version < MAX_COMPATIBLE_VERSION)
							throw new InvalidFormatException("Cannot Convert v" + version + " to v" + Version);
						
						if (tag_header.hasKey(KEYWORLDID, NBT.TAG_BYTE)) {
							WorldId = tag_header.getByte(KEYWORLDID);
							
							if (tag_header.hasKey(KEYSTATE, NBT.TAG_BYTE)) {
								byte state = tag_header.getByte(KEYSTATE);
								
								if (state != 0 && state != 1)
									throw new InvalidFormatException("state");
								
								StateFlag = state;
								
								if (tag_header.hasKey(KEYEXTENSION, NBT.TAG_BYTE)) {
									byte ext = tag_header.getByte(KEYEXTENSION);
									
									if (ext != ExtensionFlag)
										LogHelper.warn("Different Extension Flag (Block Guard might not work well)");
									
									if (tag_header.hasKey(KEYCHUNKSIZE, NBT.TAG_INT)) {
										int csize = tag_header.getInteger(KEYCHUNKSIZE);
										
										if (tag.hasKey(KEYSOURCE, NBT.TAG_COMPOUND)) {
											NBTTagCompound tag_source = tag.getCompoundTag(KEYSOURCE);
											Range.readFromNBT(tag_source);
											
											if (csize > 0) {
												for (int j=0; j<csize; j++) {
													String key = KEYCHUNK + j;
													
													if (tag_source.hasKey(key, NBT.TAG_COMPOUND)) {
														NBTTagCompound tag_chunk = tag_source.getCompoundTag(key);
														
														if (tag_chunk.hasKey(RegionChunk.KEYTYPE, NBT.TAG_BYTE)) {
															Class<? extends IRegion> type = RegionChunk.ClassType.getTypeFromMask(tag_chunk.getByte(RegionChunk.KEYTYPE));
														
															RegionChunk chunk = RegionChunk.newInstance(type);
															chunk.readFromNBT(tag_chunk);
															register(chunk);
														}
														
														else
															throw new InvalidFormatException("RegionChunk.KEYTYPE");
													}
													
													else
														throw new InvalidFormatException(key);
												}
											}
											else if (csize < 0)
												throw new InvalidFormatException("ChunkSize must be bigger than -1!");
										}
										
										else
											throw new InvalidFormatException("KEYSOURCE");
									}
									
									else
										throw new InvalidFormatException("KEYCHUNKSIZE");
								}
								
								else
									throw new InvalidFormatException("KEYEXTENSION");
							}
							
							else
								throw new InvalidFormatException("KEYSTATE");
						}
						
						else
							throw new InvalidFormatException("KEYWORLDID");
					}
					
					else
						throw new InvalidFormatException("KEYVERSION");
				}
				
				else
					throw new InvalidFormatException("Magic Numbers' Length");
			}
			
			else
				throw new InvalidFormatException("KEYMAGICNUMBER");
		}
		
		else
			throw new InvalidFormatException("KEYHEADER");
	}
}
