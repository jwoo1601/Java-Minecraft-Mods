package jwk.minecraft.garden.events;

import static jwk.minecraft.garden.ProjectGarden.DEBUG;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketTeam;
import jwk.minecraft.garden.shop.PlayerShopCache;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.util.NBTUserProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;

public class ServerNetworkEventHandler {
		
	@SubscribeEvent
	public void onLogin(PlayerEvent.PlayerLoggedInEvent e) {
		EntityPlayerMP player = (EntityPlayerMP) e.player;
		
		ITeam team = TeamRegistry.getTeamOf(player);
		
		if (team != null)
			team.getManager().getEventListener().onPlayerLogin(team, player);
	}
		
	@SubscribeEvent
	public void onLogout(PlayerEvent.PlayerLoggedOutEvent e) {
		
		if (PlayerShopCache.contains(e.player))
			PlayerShopCache.remove(e.player);
		
		ITeam team = TeamRegistry.getTeamOf(e.player);
		
		if (team != null)
			team.getManager().getEventListener().onPlayerLogout(team, (EntityPlayerMP) e.player);
	}
	
}
