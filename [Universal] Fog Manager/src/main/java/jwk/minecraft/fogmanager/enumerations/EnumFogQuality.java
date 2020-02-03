package jwk.minecraft.fogmanager.enumerations;

public enum EnumFogQuality {
	LOW(0x800), MEDIUM(0x801), HIGH(0x2601);
	
	public final int Value;
	
	private EnumFogQuality(int value) {
		Value = value;
	}
	
	public static EnumFogQuality getQuality(int value) {
		
		switch (value) {
		case 0x800:
			return LOW;
		
		case 0x801:
			return MEDIUM;
			
		case 0x2601:
			return HIGH;
			
		default:
			throw new IllegalArgumentException("value is not valid for EnumFogQuality!");
		}
	}
	
}
