package jwk.minecraft.garden.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.tab.Tab3Kids;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemFlowerBouquet extends Item {
	
	public static final String PREFIX = "flowerBouquet";
	public static final String TEXTURE_PREFIX = ModInfo.ID + ":bouquet";
	
	public static final String[] NAMES = new String[] { "blowBall", "pansyBrown", "gardenia", "houstonia", "oxeyeDaisy", "peony", "tulipPink", "roseRed" };
	public static final String[] TEXTURES = new String[] { "blow_ball", "brown_pansy", "gardenia", "houstonia", "oxeye_daisy", "peony", "pink_tulip", "red_rose" };
	
    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;
	
	public ItemFlowerBouquet() {
		this.setUnlocalizedName(PREFIX)
		    .setTextureName(TEXTURE_PREFIX)
	        .setHasSubtypes(true)
		    .setMaxDamage(0)
		    .setMaxStackSize(1)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
    	
        for (int i = 0; i < 8; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return (damage < 0 || damage > TEXTURES.length - 1)? icons[0] : icons[damage];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icons = new IIcon[TEXTURES.length];

        for (int i = 0; i < TEXTURES.length; i++)
            this.icons[i] = register.registerIcon("pjgarden:bouquet_" + TEXTURES[i]);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 7);
        return super.getUnlocalizedName() + "." + NAMES[i];
    }
    
}
