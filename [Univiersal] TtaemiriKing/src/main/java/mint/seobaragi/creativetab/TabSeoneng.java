package mint.seobaragi.creativetab;

import mint.seobaragi.SeonengItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabSeoneng extends CreativeTabs
{
	public TabSeoneng()
	{
		super("tabSeoneng");
		this.setBackgroundImageName("items.png");
	}

	@Override
	public Item getTabIconItem()
	{
		return SeonengItems.seonengIcon;
	}
}
