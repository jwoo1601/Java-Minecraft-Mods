package jwk.minecraft.garden.client.flower;

import static jwk.minecraft.garden.client.util.FlowerClientUtil.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.renderer.RenderableObject;

@SideOnly(Side.CLIENT)
public enum EnumFlowerType {
	
	// ----------------- Flowers of Vanilla ------------------ //
	// flower1
	DANDELION(1, "dandelion", false, OBJ_DANDELION),  // 민들레
	
	// flower2
	POPPY(2, "poppy", false, OBJ_POPPY),  // 양귀비  0
	BLUE_ORCHID(3, "blueOrchid", false, OBJ_BLUE_ORCHID),  // 파란 난초  1
	ALLIUM(4, "allium", false, OBJ_ALLIUM),  // 파꽃  2
	HOUSTONIA(5, "houstonia", false, OBJ_HOUSTONIA),  // 푸른 삼백초  3
	RED_TULIP(6, "tulipRed", false, OBJ_RED_TULIP),  // 빨간색 튤립  4
	ORANGE_TULIP(7, "tulipOrange", false, OBJ_ORANGE_TULIP),  // 주황색 튤립  5
	WHITE_TULIP(8, "tulipWhite", false, OBJ_WHITE_TULIP),  // 하얀색 튤립  6
	PINK_TULIP(9, "tulipPink", false, OBJ_PINK_TULIP),  // 분홍색 튤립  7
	OXEYE_DAISY(10, "oxeyeDaisy", false, OBJ_OXEYE_DAISY),  // 데이지  8
	
	// doublePlant
	DOUBLE_SUNFLOWER(11, "sunflower", true, OBJ_DOUBLE_SUNFLOWER),  // 해바라기 (아래 0 위 11)
	DOUBLE_LILAC(12, "syringa", true, OBJ_DOUBLE_LILAC),  // 라일락 (아래 1 위 11)
	DOUBLE_ROSE_BUSH(13, "rose", true, OBJ_DOUBLE_ROSE_BUSH),  // 장미 덤불  (아래 4 위 9)
	DOUBLE_PEONY(14, "paeonia", true, OBJ_DOUBLE_PEONY),  // 모란 (아래 5 위 11)
	
	// ----------------- Flowers of FlowerCraft ------------------ //
	BLACK_ROSE(15, "blackrose", false, OBJ_BLACK_ROSE),  // 흑장미
	GRAY_PANSY(16, "pansy_gray", false, OBJ_GRAY_PANSY),  // 회색 팬지꽃
	BROWN_PANSY(17, "pansy_brown", false, OBJ_BROWN_PANSY), // 갈색 팬지꽃
	BLUE_BELL(18, "bluebell", false, OBJ_BLUE_BELL),  // 스킬라
	IRIS(19, "iris", false, OBJ_IRIS),  // 붓꽃
	GARDENIA(20, "gardenia", false, OBJ_GARDENIA),  // 치자꽃
	BLOWBALL(21, "blowball", false, OBJ_BLOWBALL),  // 하얀색 민들레
	THISTLE(22, "thistle", false, OBJ_THISTLE),  // 엉겅퀴
	ENZIAN(23, "enzian", false, OBJ_ENZIAN),  // 용담
	ORCHID(24, "orchid", false, OBJ_ORCHID),  // 용담
	FOXGLOVES(25, "foxgloves", false, OBJ_FOXGLOVES),  // 디기탈리스
	LILY(26, "lily", false, OBJ_LILY),  // 백합
	PEONY(27, "peony", false, OBJ_PEONY),  // 작약
	RED_ROSE(28, "redrose", false, OBJ_RED_ROSE),  // 빨간 장미
	CHRYSANTHEME(29, "chrysantheme", false, OBJ_CHRYSANTHEME);  // 국화
	
	public final int TypeInt;
	public final String Name;
	public final boolean IsDoublePlant;
	public final RenderableObject FlowerObj;
	
	private EnumFlowerType(int typeInt, String name, boolean isDoublePlant, RenderableObject flowerObj) {
		TypeInt = typeInt;
		Name = name;
		IsDoublePlant = isDoublePlant;
		FlowerObj = flowerObj;
	}
	
	public static final EnumFlowerType fromInteger(int typeInt) {
		
		switch (typeInt) {
		
		case 1:
			return DANDELION;
			
		case 2:
			return POPPY;
			
		case 3:
			return BLUE_ORCHID;
			
		case 4:
			return ALLIUM;
			
		case 5:
			return HOUSTONIA;
			
		case 6:
			return RED_TULIP;
			
		case 7:
			return ORANGE_TULIP;
			
		case 8:
			return WHITE_TULIP;
			
		case 9:
			return PINK_TULIP;
			
		case 10:
			return OXEYE_DAISY;
			
		case 11:
			return DOUBLE_SUNFLOWER;
			
		case 12:
			return DOUBLE_LILAC;
			
		case 13:
			return DOUBLE_ROSE_BUSH;
			
		case 14:
			return DOUBLE_PEONY;
			
		case 15:
			return BLACK_ROSE;
			
		case 16:
			return GRAY_PANSY;
			
		case 17:
			return BROWN_PANSY;
			
		case 18:
			return BLUE_BELL;
			
		case 19:
			return IRIS;
			
		case 20:
			return GARDENIA;
			
		case 21:
			return BLOWBALL;
			
		case 22:
			return THISTLE;
			
		case 23:
			return ENZIAN;
			
		case 24:
			return ORCHID;
			
		case 25:
			return FOXGLOVES;
			
		case 26:
			return LILY;
			
		case 27:
			return PEONY;
			
		case 28:
			return RED_ROSE;
			
		case 29:
			return CHRYSANTHEME;
			
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
