package jwk.suddensurvival.item;

import jwk.suddensurvival.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockWool extends ItemCloth {

	public ItemBlockWool(Block block) {
		super(block);
	}

	@Override
    public String getUnlocalizedName(ItemStack stack) {
        return StatCollector.translateToLocal("tile.cloth." + ItemDye.field_150923_a[BlockColored.func_150032_b(stack.getItemDamage())]);
    }
	
}
