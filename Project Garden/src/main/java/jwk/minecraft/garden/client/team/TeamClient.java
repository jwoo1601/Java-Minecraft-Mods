package jwk.minecraft.garden.client.team;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.minecraft.util.EnumChatFormatting.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.sun.media.jfxmedia.logging.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.TaskManager;
import jwk.minecraft.garden.client.font.Fonts;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.SimpleRenderer2D;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.client.timer.PlayerSkinTracker;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import jwk.minecraft.garden.client.util.SimpleSkinTexture;
import jwk.minecraft.garden.client.util.TextureDelegate;
import jwk.minecraft.garden.client.util.TextureDelegate.TextureFilter;
import jwk.minecraft.garden.timer.TickTimer;
import jwk.minecraft.garden.util.IManaged;
import jwk.minecraft.garden.util.JUtil;
import jwk.minecraft.garden.util.NBTUserProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TeamClient implements IManaged {
	
	private static final Color COLOR_LEADER_BACKGROUND = new Color(1.0F, 0.890625F, 0.0F, 0.4F);
	
	private static final int HEAD_SIZE = 10;
	
	private static final float IMAGE_START_X_OFFSET = 1.F;
	private static final float IMAGE_START_Y_OFFSET = -5.F;
	
	private static final float IMAGE_OFFSET = 4.0F;
	
	public boolean isVisible = false;
	
	private String teamName;
	private SimpleRenderer2D renderer = new SimpleRenderer2D();
	private Queue<TeamDataClient> members;
	
	private TeamDataClient leader = null;
	
	private int nextIndex = 0;
	
	public void onJoin(@Nonnull NBTUserProfile profile) {
		checkNotNull(profile);
		
		if (members.contains(profile))
			return;
		
		AbstractClientPlayer player = getPlayerByUUID(profile.getId());
		
		if (player == null) {
			
			TeamDataClient data = null;
			if (TaskManager.instance().hasCache(profile.getId()) && TaskManager.instance().getCache(profile.getId()) != null)
				data = new TeamDataClient(TaskManager.instance().getCache(profile.getId()), profile, HEAD_SIZE, HEAD_SIZE);
			else
				data = new TeamDataClient(profile, HEAD_SIZE, HEAD_SIZE);
			
			members.offer(data);
		}
		
		else {
			ResourceLocation res = player.getLocationSkin();
			
			TeamDataClient data = null;
			
			if (res.equals(AbstractClientPlayer.locationStevePng)) {
				
				if (TaskManager.instance().hasCache(profile.getId()) && TaskManager.instance().getCache(profile.getId()) != null)
					data = new TeamDataClient(TaskManager.instance().getCache(profile.getId()), profile, HEAD_SIZE, HEAD_SIZE);
				else
					data = new TeamDataClient(profile, HEAD_SIZE, HEAD_SIZE);
			}
			else
				data = new TeamDataClient(profile, HEAD_SIZE, HEAD_SIZE, res);
			
			members.offer(data);
		}
	}
	
	public void onLeave(@Nonnull NBTUserProfile profile) {
		checkNotNull(profile);
		
		TeamDataClient target = null;
		for (TeamDataClient data : members) {
			
			if (data.equals(profile)) {
				target = data;
				break;
			}
		}
		
		if (target == null)
			return;
		
		members.remove(target);
		
		if (target == leader)
			leader = null;
		
		if (target.Task != null) {
			
			if (!target.Task.isTerminated())
				target.Task.terminate();
		}
	}
	
	public void onSetTeam(@Nonnull String teamName, @Nonnull List<NBTUserProfile> list) {
		onResetTeam();
		TaskManager.instance().resumeSkinLoader();
		
		this.teamName = checkNotNull(teamName);
		
		checkNotNull(list);
		
		if (list.isEmpty())
			return;
		
		for (NBTUserProfile profile : list)
			onJoin(profile);
		
		isVisible = true;
	}
	
	public void onResetTeam() {
		isVisible = false;
		leader = null;
		teamName = "";
		
		for (TeamDataClient data : members) {
			
			if (data.Task != null) {
				
				if (!data.Task.isTerminated())
					data.Task.terminate();
			}
		}
		
		members.clear();
		nextIndex = 0;
	}
	
	public void onSetLeader(@Nonnull NBTUserProfile profile) {
		checkNotNull(profile);
		
		for (TeamDataClient data : members) {
			
			if (data.equals(profile)) {
				leader = data;
				break;
			}
		}
	}
	
	private AbstractClientPlayer getPlayerByUUID(UUID id) {
		if (id.equals(Minecraft.getMinecraft().thePlayer.getUniqueID()))
			return Minecraft.getMinecraft().thePlayer;
		
		List<AbstractClientPlayer> playerList = Minecraft.getMinecraft().theWorld.playerEntities;
		
		for (AbstractClientPlayer p : playerList) {
			
			if (p.getUniqueID().equals(id))
				return p;
		}
		
		return null;
	}
	
	public void render(ScaledResolution resolution) {
		
		if (!isVisible || teamName == null)
			return;
		
		float sw = resolution.getScaledWidth();
		float sh = resolution.getScaledHeight();
		
		float startX = sw / 2 - 91;
		float startY = sh - 32 + 1;
		
		float fontY = sh - 48;

		float imgX = startX + IMAGE_START_X_OFFSET;
		float imgY = startY + IMAGE_START_Y_OFFSET;
		
		int i=0; for (TeamDataClient data : members) {
			RenderableObject base = data.HeadBase;
			RenderableObject overlay = data.HeadOverlay;
			
			if (i != 0)
				imgX += 10 + IMAGE_OFFSET;
			else
				++i;
			
			if (leader != null && leader.equals(data))
				RenderFrame.drawRect(imgX - IMAGE_START_X_OFFSET, imgY + 7.F, 0.0F, 12.0F, 4.5F, JUtil.convertToColor4f(COLOR_LEADER_BACKGROUND));
			
			glColor4f(1.F, 1.F, 1.F, 1.F);
			
			renderer.enableBlend();
			renderer.disableLighting();
			renderer.disableDepth();
			
			if (data.UseMCTexture)
				Minecraft.getMinecraft().getTextureManager().bindTexture(base.Texture.getTextureLocation());
			else {
				
				if (data.textureObj == null) {
					
					if (data.Task.completedProfile != null) {
						data.textureObj = TextureDelegate.createTexture(data.Task.completedProfile, data.Task.cache, TextureFilter.FASTEST);
						data.textureObj.bindTexture();
						
						TaskManager.instance().putCache((SimpleSkinTexture) data.textureObj);
					}
					
					else
						Minecraft.getMinecraft().getTextureManager().bindTexture(base.Texture.getTextureLocation());
				}
				
				else
					data.textureObj.bindTexture();
			}
			
			renderer.render(imgX, imgY, base.Texture.Width, base.Texture.Height, base.XOffset, base.YOffset, base.TextureWidth, base.TextureHeight, base.getRealWidth(), base.getRealHeight());
			renderer.render(imgX, imgY, overlay.Texture.Width, overlay.Texture.Height, overlay.XOffset, overlay.YOffset, overlay.TextureWidth, overlay.TextureHeight, overlay.getRealWidth(), overlay.getRealHeight());
		}
		
		Fonts.fontDohyeon.drawString(WHITE + "* " + teamName + WHITE + " *", startX, fontY, Color.WHITE);
		
		renderer.enableBlend();
	}

	@Override
	public void onLoad() {
		teamName = "";
		members = Queues.newConcurrentLinkedQueue();
	}

	@Override
	public void onUnload() {
		TaskManager.instance().pauseSkinLoader();
		
		isVisible = false;
		leader = null;
		teamName = "";
		
		members.clear();
		nextIndex = 0;
	}

	@Override
	public void onSave() {	}
	
}
