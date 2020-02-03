package jw.minecraft.utility.permissions;

import net.minecraft.util.StatCollector;

public class PermissionDefault implements IPermission {
	
	PermissionDefault() {}
	
	@Override
	public String getId() {
		return "default";
	}

	@Override
	public String getName() {
		return StatCollector.translateToLocal("imu.permission.default");
	}

}
