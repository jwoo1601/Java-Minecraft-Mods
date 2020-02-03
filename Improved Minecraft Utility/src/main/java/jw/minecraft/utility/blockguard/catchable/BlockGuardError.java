package jw.minecraft.utility.blockguard.catchable;

import jw.minecraft.utility.catchable.CommonError;
import jw.minecraft.utility.catchable.ErrorBase;

public class BlockGuardError extends ErrorBase {
	
	public static final String ID = "blockguard";
	
	public BlockGuardError(int code, String defaultMessage, Object[] args) {
		super(ID, code, defaultMessage, args);
	}
	
	public static final NoSelectedRegionError NoSelectedRegion = new NoSelectedRegionError();
	
	public static final NoEmptySlotError NoEmptySlot = new NoEmptySlotError();
	
	public static final StateAlreadySetError StateAlreadySet = new StateAlreadySetError();

	public static final RegionAlreadyExistError RegionAlreadyExist = new RegionAlreadyExistError();

	public static final NoSuchRegionError NoSuchRegion = new NoSuchRegionError();

	public static final NoMatchedRegionError NoMatchedRegion = new NoMatchedRegionError(); 
	
	public static final ModeAlreadySetError ModeAlreadySet = new ModeAlreadySetError();

	public static final RangeAlreadySetError RangeAlreadySet = new RangeAlreadySetError();
  
	
	public static class NoSelectedRegionError extends BlockGuardError { private NoSelectedRegionError() { super(0, "Set Region before Executing Command!", null); }	}
	
	public static class NoEmptySlotError extends BlockGuardError { private NoEmptySlotError() { super(1, "There is no Empty Slot in your inventory!", null); }	}
	
	public static class StateAlreadySetError extends BlockGuardError { private StateAlreadySetError() { super(2, "Block Guard is already %s!", null); } }
	
	public static class RegionAlreadyExistError extends BlockGuardError { private RegionAlreadyExistError() { super(3, "The Region already Exists!", null); } }
	
	public static class NoSuchRegionError extends BlockGuardError { private NoSuchRegionError() { super(4, "The Region does not Exist!", null); } }
	
	public static class NoMatchedRegionError extends BlockGuardError { private NoMatchedRegionError() { super(5, "There is no Region matches What you've Set!", null); } }
	
	public static class ModeAlreadySetError extends BlockGuardError { private ModeAlreadySetError() { super(6, "Mode has already been set to %s!", null); }	}
	
	public static class RangeAlreadySetError extends BlockGuardError { private RangeAlreadySetError() { super(7, "Range has already been set to %s!", null); }	}
	
}
