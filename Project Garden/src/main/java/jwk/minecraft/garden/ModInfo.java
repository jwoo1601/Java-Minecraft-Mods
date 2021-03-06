package jwk.minecraft.garden;

import cpw.mods.fml.common.ModMetadata;

public class ModInfo {

	public static final String ID = "pjgarden";	
	public static final String NAME = "정원의 화인";
	public static final String VERSION = "1.7.10-1.3.6.3-final";
	public static final String MC_VERSION = "1.7.10";
	public static final String DESCRIPTION = "양띵TV 릴레이 컨텐츠 삼식편 정원의 화인을 위해 제작된 모드입니다.";
	public static final String AUTHOR = "jwoo__";
	public static final String CREDITS = "Team. 삼꼬맹이";
	public static final String URL = "http://jwoop.tistory.com";
	public static final String UPDATE_URL = "";
	public static final String LOGO_FILE = "assets/" + ID + "/textures/misc/logo.png";
	
	public static final void setupMetadata(ModMetadata data) {
		data.autogenerated = false;
		data.modId = ID;
		data.name = NAME;
		data.version = VERSION;
		data.description = DESCRIPTION;
		data.authorList.add(AUTHOR);
		data.credits = CREDITS;
		data.url = URL;
		data.updateUrl = UPDATE_URL;
		data.logoFile = LOGO_FILE;
	}
	
	public static final int NETHER_ID = -1;
	public static final String NETHER_NAME = "Nether";
	public static final int OVERWORLD_ID = 0;
	public static final String OVERWORLD_NAME = "Overworld";
	public static final int THEEND_ID = 1;
	public static final String THEEND_NAME = "The End";
	
}
