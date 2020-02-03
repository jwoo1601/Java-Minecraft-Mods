package jwk.minecraft.garden.item;

import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.tab.Tab3Kids;
import net.minecraft.item.Item;

public class ItemTwine extends Item {
	
	public static final String NAME = "twine";
	public static final String TEXTURE_NAME = ModInfo.ID + ":twine";
	
	public ItemTwine() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(TEXTURE_NAME)
		    .setMaxStackSize(64)
		    .setMaxDamage(0)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
}
