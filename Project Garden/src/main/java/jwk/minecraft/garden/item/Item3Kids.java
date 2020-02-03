package jwk.minecraft.garden.item;

import jwk.minecraft.garden.ModInfo;
import net.minecraft.item.Item;

public class Item3Kids extends Item {
	
	public static final String NAME = "3kids";
	public static final String TEXTURE_NAME = "tab_3kids";
	
	public Item3Kids() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(ModInfo.ID + ":" + TEXTURE_NAME)
		    .setMaxStackSize(1);
	}
	
}