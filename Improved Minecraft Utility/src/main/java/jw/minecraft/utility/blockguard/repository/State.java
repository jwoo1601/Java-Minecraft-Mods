package jw.minecraft.utility.blockguard.repository;

public enum State {
	ALL((byte) 0x00), PART((byte) 0x01);
	
	public final byte Flag;
	
	private State(byte flag) {
		this.Flag = flag;
	}
	
	public static State getState(byte flag) {
		switch (flag) {
		case 0x00:
			return ALL;
		case 0x01:
			return PART;
		default:
			throw new IllegalArgumentException("byte flag");
		}
	}
	
	public static byte getFlag(State state) {
		switch(state) {
		case ALL:
			return ALL.Flag;
		case PART:
			return PART.Flag;
		default:
			throw new IllegalArgumentException("State state");
		}
	}
}
