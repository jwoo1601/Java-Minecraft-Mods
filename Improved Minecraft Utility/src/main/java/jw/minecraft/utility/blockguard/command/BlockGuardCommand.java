package jw.minecraft.utility.blockguard.command;

import static jw.minecraft.utility.LogHelper.info;

import java.lang.reflect.Field;
import java.util.List;

import jw.minecraft.utility.blockguard.BlockGuard;
import jw.minecraft.utility.blockguard.catchable.BlockGuardError;
import jw.minecraft.utility.blockguard.catchable.BlockGuardInfo;
import jw.minecraft.utility.blockguard.player.PlayerCache;
import jw.minecraft.utility.blockguard.region.PipedRegion;
import jw.minecraft.utility.blockguard.repository.Mode;
import jw.minecraft.utility.blockguard.repository.RangeData;
import jw.minecraft.utility.blockguard.repository.RegionChunk;
import jw.minecraft.utility.blockguard.repository.RegionMap;
import jw.minecraft.utility.blockguard.repository.State;
import jw.minecraft.utility.callback.Callback;
import jw.minecraft.utility.callback.CallbackType;
import jw.minecraft.utility.catchable.CommonError;
import jw.minecraft.utility.catchable.ErrorBase;
import jw.minecraft.utility.command.SubCommand;
import jw.minecraft.utility.command.AddonCommand;
import jw.minecraft.utility.command.AddonCommand.*;
import jw.minecraft.utility.localization.Parser;
import jw.minecraft.utility.localization.Parser.ParseType;
import jw.minecraft.utility.command.IOutputHandler;
import jw.minecraft.utility.math.PipedComponent;
import jw.minecraft.utility.permissions.IPermission;
import jw.minecraft.utility.permissions.Permissions;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

@AddonCommand(addonId = "blockguard", name = "#translate(imu.blockguard.command.name)", usage = "#translate(imu.blockguard.command.usage)", permissions = "default", allowCallback = true)
public class BlockGuardCommand {
	
	@OutputHandler(prefix = "[§6#translate(imu.blockguard.name)§f] ")
	public IOutputHandler out;
	
	@Callback(type = CallbackType.ERROR)
	public void onErrorCallback(ErrorBase e) {
		out.bind(e.getCauseObject().getCause()).printc(e);
	}
	
	private String getFullState(boolean isEnabled, Mode mode, State rangeState) {		
		String s = isEnabled? "§a#translate(imu.blockguard.command.sub[0])§f: §b#translate(imu.blockguard.command.sub[2])\\n":
                              "§a#translate(imu.blockguard.command.sub[0])§f: §9#translate(imu.blockguard.command.sub[3])\\n";

		String m = (mode == Mode.PROTECT? "§6#translate(imu.blockguard.command.sub[6])§f: #translate(imu.blockguard.command.sub[6].args[0])\\n":
			                              "§6#translate(imu.blockguard.command.sub[6])§f: #translate(imu.blockguard.command.sub[6].args[1])\\n");

		String r = (rangeState == State.ALL? "§d#translate(imu.blockguard.command.sub[7])§f: #translate(imu.blockguard.command.sub[7].args[0])\\n":
			                                 "§d#translate(imu.blockguard.command.sub[7])§f: #translate(imu.blockguard.command.sub[7].args[1])\\n");
		
		return s +  m + r;
	}
	
	@SubCommand(usage = "#sub(0)", desc = "/bg state")
	public void executeState(EntityPlayerMP player, String[] args) {
		out.bind(player);
		
		boolean isEnabled = BlockGuard.overworld().isEnabled();
		Mode mode = BlockGuard.currentOverworldMode;
		State state = BlockGuard.overworld().getRange().getState();
		
		out.printp(getFullState(isEnabled, mode, state));
	}
	
	@SubCommand(usage = "#sub(1)", desc = "/bg tool")
	public void executeTool(EntityPlayerMP player, String[] args) {
		out.bind(player);
		
		if (player.inventory.addItemStackToInventory(BlockGuard.Tool))
			out.printc(BlockGuardInfo.GetBlockGuardTool);
		else
			out.printc(BlockGuardError.NoEmptySlot);
		
		BlockGuard.Tool.stackSize = 1;
	}
	
	@SubCommand(usage = "#sub(2)", desc = "/bg enable")
	public void executeEnable(EntityPlayerMP player, String[] args) {

		if (BlockGuard.overworld().isEnabled())
			out.bind(player).printc(BlockGuardError.StateAlreadySet.args(Parser.parse("#sub(2)", ParseType.SUBCOMMAND)));
		else {
			BlockGuard.overworld().enable();
			out.bind(null).printc(BlockGuardInfo.EnableBlockGuard);
		}
	}
	
	@SubCommand(usage = "#sub(3)", desc = "/bg disable")
	public void executeDisable(EntityPlayerMP player, String[] args) {
		
		if (BlockGuard.overworld().isEnabled()) {
			BlockGuard.overworld().disable();
			out.bind(null).printc(BlockGuardInfo.DisableBlockGuard);
		}
		else
			out.bind(player).printc(BlockGuardError.StateAlreadySet.args(Parser.parse("#sub(3)", ParseType.SUBCOMMAND)));
	}
	
	@SubCommand(usage = "#sub(4)", desc = "/bg add ID", minArgsLength = 1, maxArgsLength = 1)
	public void executeAdd(EntityPlayerMP player, String[] args) {
		out.bind(player);
		PipedComponent cache = PlayerCache.getPlayerCache(player.getUniqueID());
		
		if (cache.getOrigin() == null || cache.getEnd() == null)
			out.printc(BlockGuardError.NoSelectedRegion);
		
		else {
			List<RegionMap> list = BlockGuard.overworld().getMatchedValues(BlockGuard.currentOverworldMode);
			
			if (list.isEmpty())
				throw new RuntimeException("RegionMap does not exist!");
			
			RegionMap map = list.get(0);
			String id = args[0];
			
			try {
				if (map.putIfAbsent(new PipedRegion(id, (PipedComponent) cache.clone())))
					out.printc(BlockGuardInfo.RegionAdd.args(id));		
				else
					out.printc(BlockGuardError.RegionAlreadyExist);
			}
			
			catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}			
	}
	
	@SubCommand(usage = "#sub(5)", desc = "/bg remove [ID]", minArgsLength = 0, maxArgsLength = 1)
	public void executeRemove(EntityPlayerMP player, String[] args) {
		out.bind(player);
		
		switch (args.length) {
		
		case 0:
			PipedComponent cache = PlayerCache.getPlayerCache(player.getUniqueID());
			
			if (cache.getOrigin() == null || cache.getEnd() == null)
				out.printc(BlockGuardError.NoSelectedRegion);
			
			else {
				List<RegionMap> list = BlockGuard.overworld().getMatchedValues(BlockGuard.currentOverworldMode);
				
				if (list.isEmpty())
					throw new RuntimeException("RegionMap does not exist!");
				
				RegionMap map = list.get(0);
				String id = map.getIdRangeEquals(cache);
				
				if (id == null)
					out.printc(BlockGuardError.NoMatchedRegion);
				
				else {
					map.safeRemoveIfExist(id);
					out.printc(BlockGuardInfo.RegionRemove.args(id));
				}
			}
			break;
		
		case 1:
			List<RegionMap> list = BlockGuard.overworld().getMatchedValues(BlockGuard.currentOverworldMode);
			
			if (list.isEmpty())
				throw new RuntimeException("RegionMap does not exist!");
			
			RegionMap map = list.get(0);
			String id = args[0];
			
			if (map.containsId(id)) {
				map.safeRemoveIfExist(id);
				out.printc(BlockGuardInfo.RegionRemove.args(id));
			}
			
			else
				out.printc(BlockGuardError.NoSuchRegion);
			break;
		}
	}
	
	@SubCommand(usage = "#sub(6)", desc = "/bg mode protect|unprotect", minArgsLength = 1, maxArgsLength = 1)
	public void executeMode(EntityPlayerMP player, String[] args) {
		out.bind(player);
		Mode mode = getModeFromString(args[0]);
		
		switch (mode) {
		
		case PROTECT:
			
			switch (BlockGuard.currentOverworldMode) {
			
			case UNPROTECT:
				BlockGuard.currentOverworldMode = Mode.PROTECT;
				out.bind(null).printc(BlockGuardInfo.ModeSet.args(args[0]));
				break;
				
			default:
				out.bind(player).printc(BlockGuardError.ModeAlreadySet.args(args[0]));
				break;
			}
		break;
		
		case UNPROTECT:
			
			switch (BlockGuard.currentOverworldMode) {
			
			case PROTECT:
				BlockGuard.currentOverworldMode = Mode.UNPROTECT;
				out.bind(null).printc(BlockGuardInfo.ModeSet.args(args[0]));
				break;
				
			default:
				out.bind(player).printc(BlockGuardError.ModeAlreadySet.args(args[0]));
				break;
			}
			break;
		
		default:
			out.bind(player).printc(CommonError.WrongCommand);
			break;
		}
	}
	
	@SubCommand(usage = "#sub(7)", desc = "/bg range all|part", minArgsLength = 1, maxArgsLength = 1)
	public void executeRange(EntityPlayerMP player, String[] args) {
		State state = getStateFromString(args[0]);
		
		switch (state) {
		
		case ALL:
			RangeData data = BlockGuard.overworld().getRange();
			
			switch (data.getState()) {

			case PART:
				data.setState(State.ALL).setComponent(null);
				out.bind(null).printc(BlockGuardInfo.RangeSet.args(args[0]));
				break;	
				
			default:
				out.bind(player).printc(BlockGuardError.RangeAlreadySet.args(args[0]));
				break;
		    }				
			break;
		
		case PART:
			RangeData _data = BlockGuard.overworld().getRange();
			
			switch (_data.getState()) {
			
			case ALL:
				PipedComponent cache = PlayerCache.getPlayerCache(player.getUniqueID());
				
				if (cache.getOrigin() == null || cache.getEnd() == null)
					out.printc(BlockGuardError.NoSelectedRegion);
				
				else {
					try {
						_data.setComponent((PipedComponent) cache.clone()).setState(State.PART);
					}
					
					catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					out.bind(null).printc(BlockGuardInfo.RangeSet.args(args[0]));
				}
				break;
				
			default:
				out.bind(player).printc(BlockGuardError.RangeAlreadySet.args(args[0]));
				break;
			}
		
		default:
			out.bind(player).printc(CommonError.WrongCommand);
			break;
		}
	}

	@SubCommand(usage = "#sub(8)", desc = "/bg list")
	public void executeList(EntityPlayerMP player, String[] args) {
		out.bind(player);
	}
	
	@SubCommand(usage = "#sub(9)", desc = "/bg cache")
	public void executeCache(EntityPlayerMP player, String[] args) {
		out.bind(player);
		
		PipedComponent cache = PlayerCache.getPlayerCache(player.getUniqueID());
		out.printf("Player §3Cache: §f{name=%s, uuid=%s, %s}", player.getDisplayName(), player.getUniqueID().toString(), cache.toString());
	}
	
	private Mode getModeFromString(String str) {
		if (str.equals(Parser.parse("#translate(imu.blockguard.command.sub[6].args[0])", ParseType.TRANSLATE)))
			return Mode.PROTECT;
		else if (str.equals(Parser.parse("#translate(imu.blockguard.command.sub[6].args[1])", ParseType.TRANSLATE)))
			return Mode.UNPROTECT;
		else
			return null;
	}
	
	private State getStateFromString(String str) {
		if (str.equals(Parser.parse("#translate(imu.blockguard.command.sub[7].args[0])", ParseType.TRANSLATE)))
			return State.ALL;
		else if (str.equals(Parser.parse("#translate(imu.blockguard.command.sub[7].args[1])", ParseType.TRANSLATE)))
			return State.PART;
		else
			return null;
	}
	
}