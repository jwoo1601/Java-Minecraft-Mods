package jwk.suddensurvival.item;

import jwk.suddensurvival.ModInfo;
import net.minecraft.item.Item;

public class Item3Kids extends Item {
	
	public static final String NAME = "3kids";
	public static final String TEXTURE_NAME = ModInfo.ID + ":tab_3kids";
	
	public Item3Kids() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(TEXTURE_NAME)
		    .setMaxStackSize(1);
	}
	
}
