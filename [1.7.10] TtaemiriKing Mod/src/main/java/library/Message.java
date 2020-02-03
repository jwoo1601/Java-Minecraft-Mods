package library;

import static library.ColorCode.Ce;
import static library.ColorCode.Cf;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Message
{
	//영문어
	
	//한국어
	//컨텐츠 메세지
	public static final String TITLE = LanguageRegistry.instance().getStringLocalization("Title.SeoBaragi", "ko_KR"); //모드 메세지
	public static final String NAME = Ce + "[" + TITLE +  "] " + Cf;
	
	//경고 메세지
	public static final String HUNGRY = LanguageRegistry.instance().getStringLocalization("message.Hungry", "ko_KR");					//배고픔 경고			
	public static final String THIRSTY = LanguageRegistry.instance().getStringLocalization("message.Thirsty", "ko_KR");					//갈증 경고			
	
	public static final String ERROR_1 = LanguageRegistry.instance().getStringLocalization("message.Error1", "ko_KR");					//에러코드1 = 잘못된 조건
	
	//시스템 메세지
	public static final String KICKOUT = LanguageRegistry.instance().getStringLocalization("message.KickOut", "ko_KR");					//더러움으로 퇴출	
	public static final String NEEDFOOD = LanguageRegistry.instance().getStringLocalization("message.NeedFood", "ko_KR");				//배고파서 때를 밀 수 없음	
	public static final String CAUSE_DIRTY = LanguageRegistry.instance().getStringLocalization("message.CauseDirty", "ko_KR");			//타인을 더럽힘			
	public static final String SUFFER_DIRTY = LanguageRegistry.instance().getStringLocalization("message.SufferDirty", "ko_KR");		//타인으로 인해 더러워짐	
	public static final String NOTCLEAR = LanguageRegistry.instance().getStringLocalization("message.NotClear", "ko_KR");				//때를 밀 수 없음
	public static final String NOTDIRT = LanguageRegistry.instance().getStringLocalization("message.NotDirt", "ko_KR");					//밀 수 있는 때가 없음
	public static final String GETDIRT = LanguageRegistry.instance().getStringLocalization("message.GetDirt", "ko_KR");					//땀으로 인해 더러워짐
	
	public static final String HOTWATERJOIN = LanguageRegistry.instance().getStringLocalization("message.HotWaterJoin", "ko_KR");		//온탕 입장			
	public static final String COLDWATERJOIN = LanguageRegistry.instance().getStringLocalization("message.ColdWaterJoin", "ko_KR");		//냉탕 입장			
	public static final String JJIMJILBANDJOIN = LanguageRegistry.instance().getStringLocalization("message.JjimjilbangJoin", "ko_KR");	//찜질방 입장			
	public static final String ZONEOUT = LanguageRegistry.instance().getStringLocalization("message.ZoneOut", "ko_KR");					//사우나 퇴장		
	public static final String CANCLEAR = LanguageRegistry.instance().getStringLocalization("message.CanClear", "ko_KR");				//때를 밀 수 있음
	public static final String GAMEOVER = LanguageRegistry.instance().getStringLocalization("message.GameOver", "ko_KR");				//게임오버
	
	//아이템 사용 메세지
	public static final String USE_TOWEL = LanguageRegistry.instance().getStringLocalization("message.UseTowel", "ko_KR");				//때밀이수건 사용		
	
	public static final String RESET_DIRT = LanguageRegistry.instance().getStringLocalization("message.ResetDirt", "ko_KR");			//더러움수치 초기화
	public static final String RESET_WATER = LanguageRegistry.instance().getStringLocalization("message.ResetWater", "ko_KR");			//갈증 초기화
	public static final String RESET_DIRTGAUGE = LanguageRegistry.instance().getStringLocalization("message.ResetGauge", "ko_KR");		//피로 초기화
	
	public static final String EAT = LanguageRegistry.instance().getStringLocalization("message.Eat", "ko_KR");							//배고픔 해소			
	public static final String DRINK = LanguageRegistry.instance().getStringLocalization("message.Drink", "ko_KR");						//갈증 해소			
	
	
	
	//아이템 정보
	public static final String DUST_TYPE_A = LanguageRegistry.instance().getStringLocalization("info.Dust_TypeA", "ko_KR");
	public static final String DUST_TYPE_B = LanguageRegistry.instance().getStringLocalization("info.Dust_TypeB", "ko_KR");
	public static final String DIRTCHUNK = LanguageRegistry.instance().getStringLocalization("info.DirtChunk", "ko_KR");
	public static final String RESET_TYPE_A = LanguageRegistry.instance().getStringLocalization("info.Reset_TypeA", "ko_KR");
	public static final String RESET_TYPE_B = LanguageRegistry.instance().getStringLocalization("info.Reset_TypeB", "ko_KR");
	public static final String RESET_TYPE_C = LanguageRegistry.instance().getStringLocalization("info.Reset_TypeC", "ko_KR");
	public static final String RESET_TYPE_C1 = LanguageRegistry.instance().getStringLocalization("info.Reset_TypeC1", "ko_KR");

}
