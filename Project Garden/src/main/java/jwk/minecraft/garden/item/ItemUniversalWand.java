package jwk.minecraft.garden.item;

import static net.minecraft.util.EnumChatFormatting.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.network.PacketDelivery;
import jwk.minecraft.garden.tab.Tab3Kids;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamChest;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.FlowerGuiHandler.GuiType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemUniversalWand extends Item {
	
	public static final String NAME = "universalWand";
	public static final String TEXTURE_NAME = "universal_wand";
	
	public ItemUniversalWand() {
		this.setUnlocalizedName(NAME)
		    .setTextureName(ModInfo.ID + ":" + TEXTURE_NAME)
		    .setMaxStackSize(1)
		    .setMaxDamage(0)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
	@Override
    public String getUnlocalizedName(ItemStack stack) {
        Action action = this.getActionFromDamage(stack.getItemDamage());
        
        return super.getUnlocalizedName() + "." + action.NAME;
    }
	
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        String s = this.getUnlocalizedName(stack);
        
        if (s == null)
        	return "";
        
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        	return StatCollector.translateToLocal(s);
        else
        	return LanguageRegistry.instance().getStringLocalization(s + ".name", ProjectGarden.LOCALE.Code);
    }
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
    }
	
	@Override
	public boolean hasEffect(ItemStack stack) {
    	Action action = this.getActionFromDamage(stack.getItemDamage());
    	return action != Action.NONE;
	}
    
	@Override
    public EnumRarity getRarity(ItemStack stack) {
    	Action action = this.getActionFromDamage(stack.getItemDamage());
    	return action == Action.NONE? EnumRarity.common : EnumRarity.rare;
    }
	
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase living) {
    	
		if (!player.worldObj.isRemote && living instanceof EntityPlayerMP) {
			Action action = getActionFromDamage(stack.getItemDamage());
			EntityPlayerMP target = (EntityPlayerMP) living;
			
			switch (action) {
			
			case SETUP_TEAM_MEMBER:
				
				if (!stack.hasDisplayName())
					return false;
				
				String teamName = getTeamName(stack.getDisplayName());
				
				if (teamName == null)
					return false;
				
				ITeam team = TeamRegistry.get(teamName);
				if (team == null)
					return false;
				
				ITeam teamTarget = TeamRegistry.getTeamOf(target);
				
				if (teamTarget == null) {
					team.getManager().register(target);
					player.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + target.getDisplayName() + WHITE + " 이(가) 팀 " + AQUA + team.getTeamName() + WHITE + " 에 가입되었습니다."));
					return true;
				}
				
				else if (teamTarget.equals(team)) {
					team.getManager().unregister(target);
					player.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + target.getDisplayName() + WHITE + " 이(가) 팀 " + AQUA + team.getTeamName() + WHITE + " 에서 탈퇴되었습니다."));
					return true;
				}
				
				player.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + target.getDisplayName() + WHITE + " 은(는) " + RED + "이미 " + AQUA + teamTarget.getTeamName() + WHITE + " 팀에 소속되어 있습니다."));				
				return true;
				
			default:
				return false;
			}
		}
		
		return false;
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (!world.isRemote) {
			Action action = getActionFromDamage(stack.getItemDamage());
			
			switch (action) {
			
			case OPEN_TEAM_CHEST:
				
				if (!stack.hasDisplayName())
					return stack;
				
				String teamName = getTeamName(stack.getDisplayName());
				
				if (teamName == null)
					return stack;
				
				ITeam team = TeamRegistry.get(teamName);
				if (team == null)
					return stack;
				
				EntityPlayerMP mp = (EntityPlayerMP) player;
				
				ProjectGarden.NET_HANDLER.sendTo(new PacketDelivery(jwk.minecraft.garden.network.PacketDelivery.Action.FAKE_TEAM, teamName), mp);
				ProjectGarden.NET_HANDLER.sendTo(new PacketDelivery(jwk.minecraft.garden.network.PacketDelivery.Action.INV_TEAM, team.getManager().getInventoryTeam()), mp);
				player.openGui(ProjectGarden.instance, GuiType.TEAMCHEST.ordinal(), world, 0, 0, 0);
				break;
				
			default:
				break;
			}
		}
		
        return stack;
    }
	
	public static void onRightClickBlock(World world, EntityPlayer player, BlockPos pos) {
		ItemStack heldItem = player.getHeldItem();
		
		if (heldItem != null && heldItem.getItem() instanceof ItemUniversalWand && heldItem.hasDisplayName()) {
			String teamName = getTeamName(heldItem.getDisplayName());
			ITeam team = TeamRegistry.get(teamName);
			
			if (team == null) {
				player.addChatComponentMessage(ProjectGarden.toFormatted("해당 팀이 존재하지 않습니다"));
				return;
			}
			
			
			if (isTeamChestSetupWand(heldItem)) {				
				TileEntity tileentity = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
				
				if (tileentity != null && tileentity instanceof TileEntityChest) {
				
					if (TeamChest.validate(world, pos)) {
						team.getManager().setTeamChest(TeamChest.newTeamChest(world, pos));
						player.addChatComponentMessage(ProjectGarden.toFormatted("해당 창고가 팀 " + EnumChatFormatting.AQUA + teamName + EnumChatFormatting.WHITE + " 의 창고로 등록되었습니다"));
					}
				
					else
						player.addChatComponentMessage(ProjectGarden.toFormatted(AQUA + "팀 창고" + WHITE + "는 " + RED + "두 칸 이상" + WHITE + "이어야 합니다"));
				}
			}
			
			else if (isWarpDataSetupWand(heldItem)) {
				BlockPos target = pos.add(0, 1, 0);
				
				team.getManager().getInventoryWarp().setFlowerShopPosition(target);
				player.addChatComponentMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 의 " + RED + "꽃 가게" + WHITE + "가 " + GOLD + target + WHITE + " (으)로 설정되었습니다"));
			}
		}
	}
	
	public static String getTeamName(String displayName) {
		if (displayName.contains("Team's Chest Open Wand"))
			return displayName.replace("Team's Chest Open Wand", "");
		
		else if (displayName.contains("Team's Member Setup Wand"))
			return displayName.replace("Team's Member Setup Wand", "");
		
		else if (displayName.contains("Team's Chest Setting Wand"))
			return displayName.replace("Team's Chest Setting Wand", "");
		
		else if (displayName.contains("Team's Flower Shop Setting Wand.name"))
			return displayName.replace("Team's Flower Shop Setting Wand.name", "");
		
		else if (displayName.contains("팀 창고 열기 완드.name"))
			return displayName.replace("팀 창고 열기 완드.name", "");
		
		else if (displayName.contains("팀 멤버 설정 완드.name"))
			return displayName.replace("팀 멤버 설정 완드.name", "");
		
		else if (displayName.contains("팀 창고 설정 완드.name"))
			return displayName.replace("팀 창고 설정 완드.name", "");
		
		else if (displayName.contains("팀 꽃가게 설정 완드.name"))
			return displayName.replace("팀 꽃가게 설정 완드.name", "");
		
		return null;
	}
	
    public static Action getActionFromDamage(int damage) {
        int i = MathHelper.clamp_int(damage, 0, 5);
        return Action.values()[i];
    }
    
    public static boolean isDefaultWand(ItemStack stack) {
    	Action action = getActionFromDamage(stack.getItemDamage());
    	return action == Action.NONE;
    }
    
    public static boolean isDirectionWand(ItemStack stack) {
    	Action action = getActionFromDamage(stack.getItemDamage());
    	return action == Action.SET_DIRECTION;
    }
    
    public static boolean isTeamSetupWand(ItemStack stack) {
    	Action action = getActionFromDamage(stack.getItemDamage());
    	return action == Action.SETUP_TEAM_MEMBER;
    }
    
    public static boolean isTeamChestWand(ItemStack stack) {
    	Action action = getActionFromDamage(stack.getItemDamage());
    	return action == Action.OPEN_TEAM_CHEST;
    }
    
    public static boolean isTeamChestSetupWand(ItemStack stack) {
    	Action action = getActionFromDamage(stack.getItemDamage());
    	return action == Action.SET_TEAM_CHEST;
    }
    
    public static boolean isWarpDataSetupWand(ItemStack stack) {
    	Action action = getActionFromDamage(stack.getItemDamage());
    	return action == Action.SET_WARP_DATA;
    }
    
    public static void setWandAs(ItemStack stack, Action action) {
    	
    	if (!(stack.getItem() instanceof ItemUniversalWand))
    		return;
    	
    	stack.setItemDamage(action.ordinal());
    }
    
    public static enum Action {
    	
    	NONE("none"),
    	SET_DIRECTION("direction"),
    	SETUP_TEAM_MEMBER("setup"),
    	OPEN_TEAM_CHEST("teamChest"),
    	SET_TEAM_CHEST("setTeamChest"),
    	SET_WARP_DATA("setWarpData");
    	
    	public final String NAME;
    	
    	private Action(String name) {
    		NAME = name;
    	}
    	
    }

}
