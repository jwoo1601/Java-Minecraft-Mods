package jwk.minecraft.garden.item;

import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.tab.Tab3Kids;
import net.minecraft.item.Item;

public class ItemWrapper extends Item {
	
	public static final String NAME = "wrapper";
	public static final String TEXTURE_NAME = ModInfo.ID + ":wrapper";
	
	public ItemWrapper() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(TEXTURE_NAME)
		    .setMaxStackSize(64)
		    .setMaxDamage(0)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
}
