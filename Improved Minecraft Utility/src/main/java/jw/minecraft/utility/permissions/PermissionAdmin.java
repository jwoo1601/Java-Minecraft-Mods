package jw.minecraft.utility.permissions;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.StatCollector;

public class PermissionAdmin implements IPermission {
	
	PermissionAdmin() {}	
	
	@Override
	public String getId() {
		return "administrator";
	}

	@Override
	public String getName() {
		return StatCollector.translateToLocal("imu.permission.default");
	}
}
