package jwk.minecraft.fogmanager.enumerations;

public enum EnumFogRenderOption {	
	FASTEST(0x1101), DONT_CARE(0x1100), NICEST(0x1102);
	
	public final int Value;
	
	private EnumFogRenderOption(int value) {
		Value = value;
	}
	
	public static EnumFogRenderOption getRenderOption(int value) {
		
		switch (value) {
		case 0x1100:
			return DONT_CARE;
		
		case 0x1101:
			return FASTEST;
			
		case 0x1102:
			return NICEST;
			
		default:
			throw new IllegalArgumentException("value is not valid for EnumFogRenderOption!");
		}
	}
	
}
