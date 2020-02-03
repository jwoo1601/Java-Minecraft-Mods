package jwk.minecraft.garden.item.category;

import static com.google.common.base.Preconditions.checkArgument;

import net.minecraft.item.Item;

public class ItemInfo {

	public final Item ItemObj;
	public final int LeastDamage;
	public final int MostDamage;
	
	public final int[] ExceptNumbers;
	
	public ItemInfo(Item item, int mostDmg) {
		this(item, 0, mostDmg);
	}
	
	public ItemInfo(Item item, int leastDmg, int mostDmg) {
		checkArgument(leastDmg <= mostDmg);
		
		ItemObj = item;
		LeastDamage = leastDmg;
		MostDamage = mostDmg;
		ExceptNumbers = new int[0];
	}
	
	public ItemInfo(Item item, int leastDmg, int mostDmg, int...exceptNumbers) {
		checkArgument(leastDmg <= mostDmg);
		
		ItemObj = item;
		LeastDamage = leastDmg;
		MostDamage = mostDmg;
		ExceptNumbers = exceptNumbers.length == 0? new int[0] : exceptNumbers;
	}
	
}
