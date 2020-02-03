package jwk.suddensurvival.tab;

import jwk.suddensurvival.block.Blocks;
import jwk.suddensurvival.item.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Tab3Kids extends CreativeTabs {
	
	public static final String NAME = "3kids";
	
	public static final Tab3Kids INSTANCE = new Tab3Kids();

	public Tab3Kids() {
		super(CreativeTabs.getNextID(), NAME);
	}

	@Override
	public Item getTabIconItem() {
		return Items._3KIDS;
	}

}
