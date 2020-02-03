package jwk.minecraft.garden.item.category;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Categories {

	public static final ItemCategory Seeds = new ItemCategory("씨앗", new Item[] { Items.wheat_seeds }, new ItemStack(Items.wheat_seeds, 1));
	
	public static final ItemCategory Grass = new ItemCategory("잔디", new ItemStack[] { new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Grass, 1, 1) }, new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Grass, 1, 1));
	
	public static final ItemCategory Flowers = new ItemCategory("꽃", new ItemInfo[] { new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_Flower1, 0),
			                                                                          new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_Flower2, 0, 8),
			                                                                          new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_Double_Plant, 0, 5, 2, 3),
			                                                                          new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_FCFlower, 0, 14) },
	                                                                  new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Flower2, 1));
	
	public static final ItemCategory Stamen = new ItemCategory("수술", new Item[] { jwk.minecraft.garden.flower.Flowers.Item_Stamen }, 
                                                                      new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Stamen, 1));
	
	public static final ItemCategory Pollen = new ItemCategory("꽃가루", new Item[] { jwk.minecraft.garden.flower.Flowers.Item_Pollen }, 
			                                                           new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Pollen, 1));
	
	public static final ItemCategory Petal = new ItemCategory("꽃잎", new ItemInfo[] { new ItemInfo( jwk.minecraft.garden.flower.Flowers.Item_Petal, 0, 14) },
			                                                         new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Petal, 1, 10));
	
	public static final ItemCategory Stem = new ItemCategory("줄기", new Item[] { jwk.minecraft.garden.flower.Flowers.Item_Stem },
                                                                    new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Stem, 1));
	
	public static final ItemCategory Leaves = new ItemCategory("잎", new Item[] { jwk.minecraft.garden.flower.Flowers.Item_Leaves,
			                                                                     jwk.minecraft.garden.flower.Flowers.Item_Thorn },
                                                                      new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Leaves, 1));
	
	public static final ItemCategory Woods = new ItemCategory("나무", new ItemInfo[] { new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_OldLog, 0, 3),
			                                                                          new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_NewLog, 0, 1) },
			                                                         new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_OldLog, 1));
	
	public static final ItemCategory Shears = new ItemCategory("가위", new Item[] { Items.shears }, new ItemStack(Items.shears, 1));
	
	public static final ItemCategory BoneMeal = new ItemCategory("뼛가루", new ItemStack[] { new ItemStack(Items.dye, 1, 15) }, new ItemStack(Items.dye, 1, 15));
	
	public static final ItemCategory WaterBucket = new ItemCategory("물양동이", new Item[] { Items.water_bucket }, new ItemStack(Items.water_bucket, 1));
	
	public static final ItemCategory Hedge = new ItemCategory("울타리", new ItemInfo[] { new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_Hedge, 0, 14) }, new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Hedge, 1) );
	
	public static final ItemCategory HedgeCarpet = new ItemCategory("꽃 카펫", new ItemInfo[] { new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_Hedge_Carpet, 0, 14) }, new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Hedge_Carpet, 1) );
	
	public static final ItemCategory FlowerVine = new ItemCategory("덩쿨", new Item[] { jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Black,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Cyan,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Gray,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_LightBlue,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_LightGray,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Purple,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Magenta,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Orange,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Pink,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Red,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Yellow,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Lime,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Blue,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_Brown,
			                                                                           jwk.minecraft.garden.flower.Flowers.Item_Flower_Vine_White } );
	
	public static final ItemCategory Sapling = new ItemCategory("묘목", new ItemInfo[] { new ItemInfo(jwk.minecraft.garden.flower.Flowers.Item_Sapling, 0, 5) }, new ItemStack(jwk.minecraft.garden.flower.Flowers.Item_Sapling, 1));
	
	public static final ItemCategory Wrapper = new ItemCategory("포장지", new Item[] { ProjectGarden.proxy.WRAPPER }, new ItemStack(ProjectGarden.proxy.WRAPPER, 1));
	
	public static final ItemCategory Twine = new ItemCategory("끈", new Item[] { ProjectGarden.proxy.TWINE }, new ItemStack(ProjectGarden.proxy.TWINE, 1));
	
	
	public static final ItemCategory fromItem(@Nonnull Item item) {
		
		if (Seeds.isItemInCategory(item))
			return Seeds;
		else if (Grass.isItemInCategory(item))
			return Grass;
		else if (Flowers.isItemInCategory(item))
			return Flowers;
		else if (Stamen.isItemInCategory(item))
			return Stamen;
		else if (Pollen.isItemInCategory(item))
			return Pollen;
		else if (Petal.isItemInCategory(item))
			return Petal;
		else if (Stem.isItemInCategory(item))
			return Stem;
		else if (Leaves.isItemInCategory(item))
			return Leaves;
		else if (Woods.isItemInCategory(item))
			return Woods;
		else if (Shears.isItemInCategory(item))
			return Shears;
		else if (BoneMeal.isItemInCategory(item))
			return BoneMeal;
		else if (WaterBucket.isItemInCategory(item))
			return WaterBucket;
		else if (Hedge.isItemInCategory(item))
			return Hedge;
		else if (HedgeCarpet.isItemInCategory(item))
			return HedgeCarpet;
		else if (FlowerVine.isItemInCategory(item))
			return FlowerVine;
		else if (Sapling.isItemInCategory(item))
			return Sapling;
		else if (Wrapper.isItemInCategory(item))
			return Wrapper;
		else if (Twine.isItemInCategory(item))
			return Twine;
		else
			return null;
	}
	
	public static final ItemCategory fromItemStack(@Nonnull ItemStack item) {
		
		if (Seeds.isItemInCategory(item))
			return Seeds;
		else if (Grass.isItemInCategory(item))
			return Grass;
		else if (Flowers.isItemInCategory(item))
			return Flowers;
		else if (Stamen.isItemInCategory(item))
			return Stamen;
		else if (Pollen.isItemInCategory(item))
			return Pollen;
		else if (Petal.isItemInCategory(item))
			return Petal;
		else if (Stem.isItemInCategory(item))
			return Stem;
		else if (Leaves.isItemInCategory(item))
			return Leaves;
		else if (Woods.isItemInCategory(item))
			return Woods;
		else if (Shears.isItemInCategory(item))
			return Shears;
		else if (BoneMeal.isItemInCategory(item))
			return BoneMeal;
		else if (WaterBucket.isItemInCategory(item))
			return WaterBucket;
		else if (Hedge.isItemInCategory(item))
			return Hedge;
		else if (HedgeCarpet.isItemInCategory(item))
			return HedgeCarpet;
		else if (FlowerVine.isItemInCategory(item))
			return FlowerVine;
		else if (Sapling.isItemInCategory(item))
			return Sapling;
		else if (Wrapper.isItemInCategory(item))
			return Wrapper;
		else if (Twine.isItemInCategory(item))
			return Twine;
		else
			return null;
	}
	
	public static final boolean hasCategory(@Nonnull Item item) {
		return fromItem(item) != null;
	}
	
	public static final boolean hasCategory(@Nonnull ItemStack item) {
		return fromItemStack(item) != null;
	}
	
}
