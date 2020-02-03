package jw.minecraft.utility.blockguard.repository;

public enum Mode {
	PROTECT((byte)0x00), UNPROTECT((byte)0x01);
	
	public final byte Flag;
	
	private Mode(byte flag) {
		this.Flag = flag;
	}
	
	public static Mode getMode(byte flag) {
		switch(flag) {
		case 0x00:
			return Mode.PROTECT;
		case 0x01:
			return Mode.UNPROTECT;
		default:
			throw new IllegalArgumentException("byte flag");
		}
	}
	
	public static byte getFlag(Mode mode) {
		switch(mode) {
		case PROTECT:
			return 0x00;
		case UNPROTECT:
			return 0x01;
		default:
			throw new IllegalArgumentException("Mode mode");
		}
	}
}
