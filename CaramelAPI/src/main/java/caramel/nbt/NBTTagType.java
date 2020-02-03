package caramel.nbt;

public enum NBTTagType {
	
	TAG_BYTE((byte) 1),
	TAG_SHORT((byte) 2),
	TAG_INT((byte) 3),
	TAG_LONG((byte) 4),
	TAG_FLOAT((byte) 5),
	TAG_DOUBLE((byte) 6),
	TAG_BYTE_ARRAY((byte) 7),
	TAG_STRING((byte) 8),
	TAG_LIST((byte) 9),
	TAG_COMPOUND((byte) 10),
	TAG_INT_ARRAY((byte) 11);
	
	public final byte Value;
	
	private NBTTagType(byte value) {
		Value = value;
	}
	
}
