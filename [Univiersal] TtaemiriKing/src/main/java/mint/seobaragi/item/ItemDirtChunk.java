package mint.seobaragi.item;

import library.Reference;
import net.minecraft.item.Item;

public class ItemDirtChunk extends Item
{
	public ItemDirtChunk()
	{
		super();
		this.setUnlocalizedName("dirtChunk");
		this.setTextureName("seobaragi:DirtChunk");
		this.setCreativeTab(Reference.tabSeoneng);
	}
}
