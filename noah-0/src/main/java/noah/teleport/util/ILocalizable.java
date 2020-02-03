package noah.teleport.util;

public interface ILocalizable {
	public static enum Type {
		COMMON("common"), BlOCK("tile"), ITEM("item"), COMMAND("command"), MESSAGE("message");
		
		private final String tString;
		
		Type(String tstring) {
			this.tString = tstring;
		}
		
		String getTypeString() {
			return this.tString;
		}
	}
	
	void setUniqueKey(String key);	
	String getLocalizedString(Type type, String property);
}
