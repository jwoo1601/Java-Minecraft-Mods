package jwk.suddensurvival.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class BlockWool extends Block {

	@SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockWool() {
        super(Material.cloth);
        
        this.setHardness(0.8F)
            .setStepSound(Block.soundTypeCloth)
            .setBlockName("wool")
            .setBlockTextureName(ModInfo.ID + ":wool")
            .setCreativeTab(Tab3Kids.INSTANCE);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return icons[metadata % icons.length];
    }

    public int damageDropped(int damage) {
        return damage;
    }

    private static int getIndex(int par) {
        return ~par & 15;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    	
        for (int i = 0; i < 16; ++i)
            list.add(new ItemStack(item, 1, i));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
    	icons  = new IIcon[16];
    	
        for (int i = 0; i < icons.length; ++i)
            icons[i] = register.registerIcon(this.getTextureName() + "_" + ItemDye.field_150921_b[getIndex(i)]);
    }

    @Override
    public MapColor getMapColor(int par) {
        return MapColor.getMapColorForBlockColored(par);
    }
    
}
