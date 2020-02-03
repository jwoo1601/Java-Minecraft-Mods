package jwk.minecraft.garden.shop;

import jwk.minecraft.garden.item.category.ItemCategory;
import jwk.minecraft.garden.util.INBTSerializable;

public interface ISlot extends IPriceProvider, INBTSerializable {

	ItemCategory getCategory();

	void setPrice(long value);
	
	int getAmount();
	
	void setAmount(int value);
	
}
