package jw.minecraft.utility.blockguard.catchable;

import jw.minecraft.utility.catchable.InformationBase;

public class BlockGuardInfo extends InformationBase {
	
	public static final String ID = "blockguard";

	public BlockGuardInfo(int code, String defaultMessage, Object[] args) {
		super(ID, code, defaultMessage, args);
	}
	
	public static final GetBlockGuardToolInfo GetBlockGuardTool = new GetBlockGuardToolInfo();
	
	public static final EnableBlockGuardInfo EnableBlockGuard = new EnableBlockGuardInfo();

	public static final DisableBlockGuardInfo DisableBlockGuard = new DisableBlockGuardInfo();

	public static final RegionAddInfo RegionAdd = new RegionAddInfo();

	public static final RegionRemoveInfo RegionRemove = new RegionRemoveInfo();

	public static final ModeSetInfo ModeSet = new ModeSetInfo();

	public static final RangeSetInfo RangeSet = new RangeSetInfo();
	
	
	public static class GetBlockGuardToolInfo extends BlockGuardInfo { GetBlockGuardToolInfo() { super(0, "Got The Tool", null); }	}
	
	public static class EnableBlockGuardInfo extends BlockGuardInfo { EnableBlockGuardInfo() { super(1, "Block Guard is &bEnabled", null); } }
	
	public static class DisableBlockGuardInfo extends BlockGuardInfo { DisableBlockGuardInfo() { super(2, "Block Guard is &7Disabled", null); } }
	
	public static class RegionAddInfo extends BlockGuardInfo { RegionAddInfo() { super(3, "The region &6%s &fis successfully &aAdded", null); } }
	
	public static class RegionRemoveInfo extends BlockGuardInfo { RegionRemoveInfo() { super(4, "The Region &6%s &fis successfully &9Removed", null); } }
	
	public static class ModeSetInfo extends BlockGuardInfo { ModeSetInfo() { super(5, "Mode has been set to &6%s", null); } }
	
	public static class RangeSetInfo extends BlockGuardInfo { RangeSetInfo() { super(6, "Range has been set to &6%s", null); } }
}
