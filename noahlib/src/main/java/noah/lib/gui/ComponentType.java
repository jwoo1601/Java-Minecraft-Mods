package noah.lib.gui;

public enum ComponentType {
	
	BUTTON(0x00, "Button"), 
	LABEL(0x01, "Label"), 
	TEXTBOX(0x02, "Textbox"), 
	SCROLLBAR(0x03,"Scrollbar"),
	LISTVIEW(0x04, "Listview"),
	TREEVIEW(0x05, "Treeview"),
	SELECTIONBOX(0x06, "Selectionbox");
	
	private final int componentid;
	private final String name;
	
	ComponentType(int cid, String cname) {
		this.componentid = cid;
		this.name = cname;
	}
	
	public int getComponentID() {
		return this.componentid;
	}
	
	public String getComponentName() {
		return this.name;
	}
}
