package jwk.minecraft.garden.item;

import jwk.minecraft.garden.ModInfo;
import net.minecraft.item.Item;

public class ItemFlowerShop extends Item {
	
	public static final String NAME = "flowerShop";
	public static final String TEXTURE_NAME = "flower_shop";
	
	public ItemFlowerShop() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(ModInfo.ID + ":" + TEXTURE_NAME)
		    .setMaxStackSize(1);
	}
	
}