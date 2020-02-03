package jwk.minecraft.kongagent.items;

import net.minecraft.item.Item;

public class ItemKongIcon extends Item {

	public static final String ITEM_NAME = "kong_icon";
	
	public ItemKongIcon() {
		this.setUnlocalizedName(ITEM_NAME);
		this.setTextureName("kongagent:kong_icon");
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}
	
}
