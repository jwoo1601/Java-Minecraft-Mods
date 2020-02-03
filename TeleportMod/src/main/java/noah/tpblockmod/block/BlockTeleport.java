package noah.tpblockmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.tpblockmod.ModInfo;
import noah.tpblockmod.ModMain;
import noah.tpblockmod.proxy.Place;
import noah.tpblockmod.proxy.ServerProcessor;

public class BlockTeleport extends Block implements ITileEntityProvider {
	public static final String BLOCK_UNL_NAME = "teleport_block";

	public BlockTeleport() {
		super(Material.iron);
		this.setUnlocalizedName(BLOCK_UNL_NAME);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(30.0f);
		this.setStepSound(soundTypeMetal);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.SOLID;
	}
	
	public void onBlockPlaceBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		
		System.out.println("01 World.isRemote? = " + world.isRemote);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {			
			Place tmp = ModMain.searchLinkedPlaceByPos(pos.add(0, 1, 0));
			
			if (tmp != null) {
				BlockPos tmp2 = tmp.getPlacePosition();
				
				player.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + ModInfo.MSG_TP_PRE));
				player.setPositionAndUpdate((tmp2.getX() + 1), tmp2.getY(), tmp2.getZ());
				player.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + ModInfo.MSG_TP_POST));
				}
			}
		
		return true;
		}
	
	@Override
	public void onLanded(World world, Entity entity) {
		if (world.getPlayerEntityByName(entity.getName()) != null) {
			if (!world.isRemote) {
				Place tmp = ModMain.searchLinkedPlaceByPos(entity.getPosition());
				
				if (tmp != null) {
					entity.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + ModInfo.MSG_TP_PRE));
					this.teleportToTargetPlace(entity, tmp);
					entity.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + ModInfo.MSG_TP_POST));
					}
				}
			}
	} 
	
	private void teleportToTargetPlace(Entity player, Place p) {
		BlockPos pos = p.getPlacePosition();
		player.setPositionAndUpdate(pos.getX() + 2, pos.getY(), pos.getZ());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		System.out.println("00 World.isRemote? = " + worldIn.isRemote);
		
		return null;
	}
	
	/*@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing face, float hitX, float hitY, float hitZ) {
		return true;
	} */
}
