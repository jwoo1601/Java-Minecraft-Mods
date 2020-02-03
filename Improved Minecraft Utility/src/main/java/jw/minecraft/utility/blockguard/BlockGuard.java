package jw.minecraft.utility.blockguard;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import jw.minecraft.utility.Common;
import jw.minecraft.utility.addon.Addon;
import jw.minecraft.utility.blockguard.command.BlockGuardCommand;
import jw.minecraft.utility.blockguard.repository.BlockGuardData;
import jw.minecraft.utility.blockguard.repository.Mode;
import jw.minecraft.utility.command.AddonCommand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

@Addon(id = "blockguard", name = "#translate(imu.bg.name)", version = "1.1.8", desc = "#translate(imu.bg.desc)", unit = BlockGuardUnit.class, command = BlockGuardCommand.class)
public class BlockGuard {
	
	public static final String LOCALIZEDNAME = StatCollector.translateToLocal("imu.bg.name");
	
	public static final String PREFIX = "[" + EnumChatFormatting.GOLD + LOCALIZEDNAME + EnumChatFormatting.WHITE + "] ";
	
	public static ChatComponentText printf(String msg) {
		return new ChatComponentText(PREFIX + msg);
	}
	
	public static ChatComponentText printf(String msg, EnumChatFormatting... style) {
		String result = null;
		
		for (int i=0; i < style.length; i++)
			result = msg.replaceFirst("%cs", style[i].toString());
		
		return new ChatComponentText(PREFIX + result);
	}
	
	public static ChatComponentText tprintf(String key) {
		return new ChatComponentText(PREFIX + Common.translateToLocal(key));
	}
	
	public static ChatComponentText tprintf(String key, Object...args) {
		return new ChatComponentText(PREFIX + Common.translateToLocalFormatted(key, args));
	}
	
	public static final String ToolName;
	public static final ItemStack Tool;
	
	static {
		ToolName = EnumChatFormatting.BOLD.toString() + EnumChatFormatting.GOLD + StatCollector.translateToLocal("imu.bg.tool.name");
		Tool = new ItemStack(Items.diamond_pickaxe).setStackDisplayName(ToolName);
		Tool.setItemDamage(1);
		Tool.setRepairCost(0);
	}
	
	public static final boolean toolEquals(@Nonnull ItemStack target) {
		if (target == null)
			throw new NullPointerException("ItemStack target");
		
		return target.hasDisplayName() && target.getDisplayName().equals(ToolName);
	}
	
	public static final BlockGuardData[] DATA = new BlockGuardData[3];
	
	/**
	 * Overworld dimension Id : 0, Index: 0
	 * @return BlockGuardData of Overworld
	 */
	public static BlockGuardData overworld() { return DATA[0]; }
	
	public static Mode currentOverworldMode = Mode.PROTECT;
	
	/**
	 * Nether dimension Id : -1, Index: 1
	 * @return BlockGuardData of Nether
	 */
	public static BlockGuardData nether() { return DATA[1]; }
	
	public static Mode currentNetherMode = Mode.PROTECT;
	
	/**
	 * The End dimension Id : 1, Index: 2
	 * @return BlockGuardData of The End
	 */
	public static BlockGuardData theEnd() { return DATA[2]; }
	
	public static Mode currentTheEndMode = Mode.PROTECT;
}
