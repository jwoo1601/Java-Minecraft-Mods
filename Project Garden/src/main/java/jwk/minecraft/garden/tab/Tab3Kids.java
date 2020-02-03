package jwk.minecraft.garden.tab;

import jwk.minecraft.garden.ProjectGarden;
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
		return ProjectGarden.proxy.ITEM_3KIDS;
	}

}