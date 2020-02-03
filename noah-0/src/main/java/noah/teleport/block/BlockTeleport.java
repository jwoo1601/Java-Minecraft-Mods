package noah.teleport.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noah.teleport.Main;
import noah.teleport.gui.GuiTeleport;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;

public class BlockTeleport extends Block implements ITileEntityProvider {
	public static final String REG_NAME = "teleportblock";
	
	public BlockTeleport() {
		super(Material.iron);
		this.setUnlocalizedName(REG_NAME);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
		this.setResistance(30.0f);
		this.setStepSound(soundTypeMetal);
	}
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
			TileEntity tmp = world.getTileEntity(pos);	
			
			if (tmp != null && tmp instanceof TeleportData) {
				TeleportData data = (TeleportData) tmp;			
				
				if (data.getOwnPlace() != null) {
					if (world.isRemote)
						Main.Proxy.FakePlaceReg.deletePlaceByID(data.getOwnPlaceID());
					
					else {
						int id = world.provider.getDimensionId();
						
						switch (id) {
						case Common.OVERWORLD_ID:
							Main.Proxy.PlaceReg[0].deletePlaceByID(data.getOwnPlaceID());
							
							break;
						case Common.NETHER_ID:
							Main.Proxy.PlaceReg[1].deletePlaceByID(data.getOwnPlaceID());
							
							break;
						case Common.THEEND_ID:
							Main.Proxy.PlaceReg[2].deletePlaceByID(data.getOwnPlaceID());
							
							break;
						default:
							Main.Proxy.PlaceReg[id].deletePlaceByID(data.getOwnPlaceID());
							
							break;
							}
						}
					}
				}
		
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	@Override
	public void onLanded(World world, Entity entity) {
		super.onLanded(world, entity);
		Common.Debug("0");
		
		if (entity instanceof EntityPlayer && !world.isRemote) {
			Common.Debug("1");
			BlockPos pos = new BlockPos(entity.getPosition());
			
			TileEntity tmp = world.getTileEntity(pos.add(0, -1, 0));
			
			if (tmp instanceof TeleportData) {
				Common.Debug("2");
				TeleportData data = (TeleportData) tmp;
				
				if (data.getLinkedPlace() != null) {
					Common.Debug("3");
					if (world.isRemote) {
						Common.Debug("4");
						entity.setPositionAndUpdate(data.getLinkedPlace().getX() + 1, data.getLinkedPlace().getY(), data.getLinkedPlace().getZ());
					}
					else if (!world.isRemote && entity instanceof EntityPlayerMP) {
						Common.Debug("5");
						EntityPlayerMP player = (EntityPlayerMP) entity;
						player.setPositionAndUpdate(data.getLinkedPlace().getX() + 1, data.getLinkedPlace().getY(), data.getLinkedPlace().getZ());
						Common.Debug("setposandupdate");
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + data.getOwnPlace().getName() + EnumChatFormatting.WHITE +" >> " + EnumChatFormatting.AQUA + data.getLinkedPlace().getName()));
					}
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			TileEntity tmp = world.getTileEntity(pos);
			
			if (tmp instanceof TeleportData) {
				Main.Proxy.openTeleportGui((TeleportData)tmp);
			}
		}
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {		
		TeleportData data;
		
		if (world.isRemote)
			data = new TeleportData(Main.Proxy.FakePlaceReg.getNextPlaceGenericName(), null);
		else {
			int id = world.provider.getDimensionId();
			
			switch (id) {
			case Common.OVERWORLD_ID:
				data = new TeleportData(Main.Proxy.PlaceReg[0].getNextPlaceGenericName(), null);
				break;
			case Common.NETHER_ID:
				data = new TeleportData(Main.Proxy.PlaceReg[1].getNextPlaceGenericName(), null);
				break;
			case Common.THEEND_ID:
				data = new TeleportData(Main.Proxy.PlaceReg[2].getNextPlaceGenericName(), null);
				break;
				default:
					data = new TeleportData(Main.Proxy.PlaceReg[id].getNextPlaceGenericName(), null);
					break;
			}
		}
		
		return data;
	}
	
	  @SideOnly(Side.CLIENT)
	  public EnumWorldBlockLayer getBlockLayer()
	  {
	    return EnumWorldBlockLayer.TRANSLUCENT;
	  }

	  // used by the renderer to control lighting and visibility of other blocks.
	  // set to true because this block is opaque and occupies the entire 1x1x1 space
	  // not strictly required because the default (super method) is true
	  @Override
	  public boolean isOpaqueCube() {
	    return true;
	  }

	  // used by the renderer to control lighting and visibility of other blocks, also by
	  // (eg) wall or fence to control whether the fence joins itself to this block
	  // set to true because this block occupies the entire 1x1x1 space
	  // not strictly required because the default (super method) is true
	  @Override
	  public boolean isFullCube() {
	    return true;
	  }

	  // render using a BakedModel (mbe01_block_simple.json --> mbe01_block_simple_model.json)
	  // not strictly required because the default (super method) is 3.
	  @Override
	  public int getRenderType() {
	    return 3;
	  }
}
