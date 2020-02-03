package jwk.minecraft.garden.item;

import jwk.minecraft.garden.ModInfo;
import net.minecraft.item.Item;

public class ItemFlowerFactory extends Item {
	
	public static final String NAME = "flowerFactory";
	public static final String TEXTURE_NAME = "flower_factory";
	
	public ItemFlowerFactory() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(ModInfo.ID + ":" + TEXTURE_NAME)
		    .setMaxStackSize(1);
	}
	
}