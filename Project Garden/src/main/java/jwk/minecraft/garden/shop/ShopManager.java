package jwk.minecraft.garden.shop;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.item.category.ItemCategory;
import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.util.IManaged;
import jwk.minecraft.garden.util.NBTFileManager;
import net.minecraft.nbt.NBTTagCompound;

public class ShopManager implements IManaged {
	
	public static final String SHOP_DEFAULT_PATH = ProjectGarden.getSaveDirectory() + "\\shop";

	private ShopData data;
	private NBTFileManager fileManager;
	
	public ShopManager(String name) {
		data = new ShopData();
		fileManager = new NBTFileManager(SHOP_DEFAULT_PATH + "\\" + "shop_" + name + ".dat");
	}
	
	public ShopManager(String name, @Nonnull ShopType type) {
		data = new ShopData(type);
		fileManager = new NBTFileManager(SHOP_DEFAULT_PATH + "\\" + "shop_" + name + ".dat");
	}
	
	public void setSlotPrice(int index, long value) {
		ISlot slot = getSlot(index);
		
		if (slot == null)
			return;
		
		slot.setPrice(value);
	}
	
	public long getSlotPrice(int index) {
		ISlot slot = getSlot(index);
		
		if (slot == null)
			return -1;
		
		return slot.getPrice();
	}
	
	public void setSlotAmount(int index, int value) {		
		ISlot slot = getSlot(index);
		
		if (slot == null)
			return;
		
		slot.setAmount(value);
	}
	
	public int getSlotAmount(int index) {		
		ISlot slot = getSlot(index);
		
		if (slot == null)
			return -1;
		
		return slot.getAmount();
	}
	
	public ISlot getSlot(int index) {
		if (index < 0 || index > data.Slots.length - 1)
			return null;
		
		return data.Slots[index];
	}
	
	public ItemCategory getCategoryOfSlot(int index) {
		ISlot slot = getSlot(index);
		
		if (slot == null)
			return null;
		
		return slot.getCategory();
	}

	@Override
	public void onUnload() { }
	
	@Override
	public void onLoad() {
		try {
			data.read(fileManager.readFromFile());
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSave() {
		try {
			NBTTagCompound compound = new NBTTagCompound();
			
			data.write(compound);
			fileManager.writeToFile(true, compound);
		}		
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ShopData getData() { return data; }
	
	public ShopType getShopType() { return data.type; }

}
