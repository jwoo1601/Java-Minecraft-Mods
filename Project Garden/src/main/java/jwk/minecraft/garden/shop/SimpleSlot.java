package jwk.minecraft.garden.shop;

import jwk.minecraft.garden.item.category.Categories;
import jwk.minecraft.garden.item.category.ItemCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class SimpleSlot implements ISlot {
	
	private final ItemCategory Category;
	
	private int amount;
	private long price;
	
	public SimpleSlot(ItemCategory category) {
		this(category, 1, 0L);
	}
	
	public SimpleSlot(ItemCategory category, int amount) {
		this(category, amount, 0L);
	}
	
	public SimpleSlot(ItemCategory category, int amount, long price) {
		Category = category;
		this.amount = amount < 1? 1 : amount;
		this.price = price < 0? 0L : price;
	}

	@Override
	public boolean hasPrice() {
		return price != 0L;
	}

	@Override
	public long getPrice() {
		return price;
	}

	@Override
	public ItemCategory getCategory() {
		return Category;
	}

	@Override
	public void setPrice(long value) {
		this.price = value < 0? 0L : value;
	}

	
	private static final String KEY_CATEGORY_NAME = "Category";
	private static final String KEY_AMOUNT = "Amount";
	private static final String KEY_PRICE = "Price";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		tagCompound.setString(KEY_CATEGORY_NAME, Category.getDisplayName());
		tagCompound.setInteger(KEY_AMOUNT, amount);
		tagCompound.setLong(KEY_PRICE, price);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {		
		String category = null;
		if (tagCompound.hasKey(KEY_CATEGORY_NAME, NBT.TAG_STRING))
			category = tagCompound.getString(KEY_CATEGORY_NAME);
		else
			throw new IllegalArgumentException();
		
		if (!category.equals(Category.getDisplayName()))
			throw new RuntimeException("Category Mistmatch: " + category + " , " + Category.getDisplayName());
		
		int amount = 1;
		if (tagCompound.hasKey(KEY_AMOUNT, NBT.TAG_INT))
			amount = tagCompound.getInteger(KEY_AMOUNT);
		else
			throw new IllegalArgumentException();
		
		long price = 0L;
		if (tagCompound.hasKey(KEY_PRICE, NBT.TAG_LONG))
			price = tagCompound.getLong(KEY_PRICE);
		else
			throw new IllegalArgumentException();
		
		this.amount = amount < 1? 1 : amount;
		this.price = price < 0L? 0L : price;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int value) {
		this.amount = value < 1? 1 : value;
	}

}
