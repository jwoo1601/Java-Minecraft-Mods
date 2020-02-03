package jwk.suddensurvival.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.suddensurvival.ModInfo;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockPillarImpl extends BlockRotatedPillar {

	private String topTextureName;
	private String sideTextureName;

	public BlockPillarImpl(Material material, String blockName, String topTextureName, String sideTextureName) {
		super(material);
		
		this.setBlockName(blockName);
		
		this.topTextureName = topTextureName;
		this.sideTextureName = sideTextureName;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int metadata) {
    	return blockIcon;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon(sideTextureName);
        this.field_150164_N = register.registerIcon(topTextureName);
    }
    
	@Override
    protected ItemStack createStackedBlock(int damage) {
        return new ItemStack(GameRegistry.findBlock(ModInfo.ID, getBlockName()), 1, damage);
    }

	public String getBlockName() { return this.getUnlocalizedName().replace("tile.", ""); }
	
}
