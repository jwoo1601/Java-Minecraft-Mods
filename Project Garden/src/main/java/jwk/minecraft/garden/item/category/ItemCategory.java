package jwk.minecraft.garden.item.category;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemCategory {

	private String displayName;
	private List<ItemStack> items = Lists.newArrayList();

	private ItemStack icon;
	
	public ItemCategory(@Nonnull String displayName, @Nonnull Item[] items) {
		this (displayName, items, null);
	}
	
	public ItemCategory(@Nonnull String displayName, @Nonnull Item[] items, ItemStack icon) {
		checkNotNull(displayName); checkNotNull(items);
		
		this.displayName = displayName;
		
		for (int i=0; i < items.length; i++) {
			
			if (items[i] != null)
				this.items.add(new ItemStack(items[i], 1));
		}
		
		this.icon = icon;
	}
	
	public ItemCategory(@Nonnull String displayName, @Nonnull ItemInfo[] infos, ItemStack icon) {
		checkNotNull(displayName); checkNotNull(infos);
		
		this.displayName = displayName;
		
		for (int i=0; i < infos.length; i++) {
			int length = infos[i].MostDamage - infos[i].LeastDamage + 1;
			
			for (int j=0; j < length; j++) {
				
				if (infos[i].ExceptNumbers.length == 0) {
					this.items.add(new ItemStack(infos[i].ItemObj, 1, j));
					continue;
				}
				
				for (int k=0; k < infos[i].ExceptNumbers.length; k++) {
					
					if (j == infos[i].ExceptNumbers[k])
						continue;
					
					this.items.add(new ItemStack(infos[i].ItemObj, 1, j));
				}
			}
		}
		
		this.icon = icon;
	}
	
	public ItemCategory(@Nonnull String displayName, @Nonnull ItemStack[] items) {
		this(displayName, items, null);
	}
	
	public ItemCategory(@Nonnull String displayName, @Nonnull ItemStack[] items, ItemStack icon) {
		checkNotNull(displayName); checkNotNull(items);
		
		this.displayName = displayName;
		
		for (ItemStack stack : items) {
			
			if (stack != null)
				this.items.add(stack);
		}

		this.icon = icon;
	}
	
	public boolean isItemInCategory(@Nonnull ItemStack target) {
		checkNotNull(target);

		for (ItemStack i : items) {
			
			if (i != null && i.isItemEqual(target))
				return true;
		}
		
		return false;
	}
	
	public boolean isItemInCategory(@Nonnull Item target) {
		return isItemInCategory(new ItemStack(target, 1));
	}
	
	public String getDisplayName() { return displayName; }
	
	public boolean isSingleItem() { return items.size() == 1; }
	
	public ItemStack[] getItems() { return items.toArray(new ItemStack[0]); }
	
	@SideOnly(Side.CLIENT)
	public ItemCategory setIconFromItemStack(ItemStack icon) {
		this.icon = icon;
		
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	public ItemStack getIcon() { return icon; }
	
	@SideOnly(Side.CLIENT)
	public boolean hasIcon() { return icon != null; }
	
}