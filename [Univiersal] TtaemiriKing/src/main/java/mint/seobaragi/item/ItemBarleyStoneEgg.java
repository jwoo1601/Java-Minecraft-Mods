package mint.seobaragi.item;

import library.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemBarleyStoneEgg extends ItemFood
{
	public ItemBarleyStoneEgg()
	{
		super(6, true);
		this.setUnlocalizedName("barleyStoneEgg");
		this.setTextureName("seobaragi:BarleyStoneEgg");
		this.setMaxStackSize(32);
		this.setCreativeTab(Reference.tabSeoneng);
	}
}
