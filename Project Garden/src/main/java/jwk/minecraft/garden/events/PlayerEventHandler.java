package jwk.minecraft.garden.events;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;

import static jwk.minecraft.garden.ProjectGarden.DEBUG;
import static net.minecraft.util.EnumChatFormatting.*;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.FlowerGuiHandler.GuiType;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.flower.FlowerProperty;
import jwk.minecraft.garden.flower.FlowerRetriever;
import jwk.minecraft.garden.flower.FlowerRetriever.FlowerCache;
import jwk.minecraft.garden.flower.Flowers;
import jwk.minecraft.garden.item.ItemUniversalWand;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketFlowerProperty;
import jwk.minecraft.garden.network.PacketTeam;
import jwk.minecraft.garden.network.PacketWarp;
import jwk.minecraft.garden.network.PacketFlowerProperty.Type;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamChest;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.team.container.ContainerTeamChest;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fluids.IFluidBlock;

public class PlayerEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if (!e.world.isRemote && e.action == Action.RIGHT_CLICK_BLOCK) {			
			BlockPos pos = new BlockPos(e.x, e.y, e.z);
			Block targetBlock = e.world.getBlock(e.x, e.y, e.z);

			ItemUniversalWand.onRightClickBlock(e.world, e.entityPlayer, pos);
			
			if (FlowerRetriever.isFlowerBlock(targetBlock)) {
				Block upperBlock = targetBlock;			
				Block lowerBlock = e.world.getBlock(e.x, e.y - 1, e.z);			
				int upperMetadata = e.world.getBlockMetadata(e.x, e.y, e.z);
				int lowerMetadata = e.world.getBlockMetadata(e.x, e.y - 1, e.z);
				
				FlowerRetriever.processFlowerProperty(e.world, e.entityPlayer, pos, new FlowerCache(upperBlock, upperMetadata, lowerBlock, lowerMetadata));
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerJoinWorld(EntityJoinWorldEvent e) {
		
		if (e.entity instanceof EntityPlayer) {

			if (e.world.isRemote)
				ProjectGarden.proxy.onSidedWorldLoad(e.world);
			
			else {
				EntityPlayerMP player = (EntityPlayerMP) e.entity;
				int dId = e.world.provider.dimensionId;
				int idx = Flowers.getIndexFromId(dId);
				
				if (!ProjectGarden.repository[idx].isEmpty()) {
					FlowerProperty[] properties = ProjectGarden.repository[idx].toArray();
					
					ProjectGarden.NET_HANDLER.sendTo(new PacketFlowerProperty(Type.ARRAY, properties), player);
				}				
			}
		}
	}
	
}
