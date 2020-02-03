package jwk.minecraft.garden.shop;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.item.category.Categories;
import jwk.minecraft.garden.util.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class ShopData implements INBTSerializable {
	
	public static enum ShopType {
		
		SALE,
		PURCHASE;
		
	}
	
	ShopType type;
	
	final ISlot[] Slots = new ISlot[12];

	public ShopData() {
		type = null;
	}
	
	public ShopData(@Nonnull ShopType type) {
		checkNotNull(type);
		
		this.type = type;
		
		if (type == ShopType.SALE)
			initSaleShop();
		else if (type == ShopType.PURCHASE)
			initPurchaseShop();
	}
	
	private void initSaleShop() {
		Slots[0] = new SimpleSlot(Categories.Seeds, 1, 500L);
		Slots[1] = new SimpleSlot(Categories.Grass, 1, 50L);
		Slots[2] = new SimpleSlot(Categories.Flowers, 2, 100L);
		Slots[3] = new SimpleSlot(Categories.Stamen, 1, 800L);
		Slots[4] = new SimpleSlot(Categories.Pollen, 1, 400L);
		Slots[5] = new SimpleSlot(Categories.Petal, 1, 200L);
		Slots[6] = new SimpleSlot(Categories.Stem, 1, 200L);
		Slots[7] = new SimpleSlot(Categories.Leaves, 1, 300L);
		Slots[8] = new SimpleSlot(Categories.Woods, 1, 200L);
		Slots[9] = null;
		Slots[10] = null;
		Slots[11] = null;
	}
	
	private void initPurchaseShop() {
		Slots[0] = new SimpleSlot(Categories.Shears, 1, 1000L);
		Slots[1] = new SimpleSlot(Categories.BoneMeal, 1, 300L);
		Slots[2] = new SimpleSlot(Categories.WaterBucket, 1, 1500L);
		Slots[3] = new SimpleSlot(Categories.Wrapper, 1, 500L);
		Slots[4] = new SimpleSlot(Categories.Twine, 1, 300L);
		Slots[5] = null;
		Slots[6] = null;
		Slots[7] = null;
		Slots[8] = null;
		Slots[9] = null;
		Slots[10] = null;
		Slots[11] = null;
	}
	
	
	private static final String KEY_SHOP = "Shop Data";
	private static final String KEY_SHOP_TYPE = "Shop Type";
	private static final String KEY_SLOT_BASE = "Slot -";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagShop = new NBTTagCompound();
		
		tagShop.setInteger(KEY_SHOP_TYPE, type.ordinal());
		
		for (int i=0; i < Slots.length; i++) {
			
			if (Slots[i] == null)
				continue;
			
			NBTTagCompound tagSlot = new NBTTagCompound();
			
			Slots[i].write(tagSlot);
			tagShop.setTag(KEY_SLOT_BASE + i, tagSlot);
		}
		
		tagCompound.setTag(KEY_SHOP, tagShop);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		
		NBTTagCompound tagShop = null;
		if (tagCompound.hasKey(KEY_SHOP, NBT.TAG_COMPOUND))
			tagShop = tagCompound.getCompoundTag(KEY_SHOP);
		else
			throw new IllegalArgumentException();
		
		ShopType type = null;
		if (tagShop.hasKey(KEY_SHOP_TYPE, NBT.TAG_INT)) {
			int ordinal = tagShop.getInteger(KEY_SHOP_TYPE);
			
			if (ordinal < 0 || ordinal > ShopType.values().length -1)
				throw new RuntimeException("Invalid Shop Type: " + ordinal);
			
			type = ShopType.values()[ordinal];
		}
		else
			throw new IllegalArgumentException();
		
		this.type = type;
		
		if (type == ShopType.SALE)
			initSaleShop();
		else if (type == ShopType.PURCHASE)
			initPurchaseShop();
		else
			throw new IllegalStateException();
		
		for (int i=0; i < Slots.length; i++) {
			
			if (Slots[i] == null)
				continue;
			
			String key = KEY_SLOT_BASE + i;
			NBTTagCompound tagSlot = null;
			if (tagShop.hasKey(key, NBT.TAG_COMPOUND))
				tagSlot = tagShop.getCompoundTag(key);
			else
				throw new IllegalArgumentException();
			
			Slots[i].read(tagSlot);
		}
	}
	
	public ISlot getSlot(int index) {
		return Slots[index];
	}
	
	public ShopType getShopType() { return type; }
	
}
