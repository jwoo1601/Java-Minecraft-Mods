package mint.seobaragi;

import cpw.mods.fml.common.registry.GameRegistry;
import mint.seobaragi.item.ItemBarleyStoneEgg;
import mint.seobaragi.item.ItemDirtChunk;
import mint.seobaragi.item.ItemDirtReset;
import mint.seobaragi.item.ItemGaugeReset;
import mint.seobaragi.item.ItemIcon;
import mint.seobaragi.item.ItemNenglonaminC;
import mint.seobaragi.item.ItemSeonengMilk;
import mint.seobaragi.item.ItemSikhye;
import mint.seobaragi.item.ItemSoap;
import mint.seobaragi.item.ItemTowel;
import mint.seobaragi.item.ItemTtaemiriTowel;
import mint.seobaragi.item.ItemWand;
import mint.seobaragi.item.ItemWaterReset;
import mint.seobaragi.item.ProximityOnlyDust;
import mint.seobaragi.item.ThrowableDust;
import net.minecraft.item.Item;

public class SeonengItems
{
	public static Item seonengIcon;			//아이콘
	public static Item seonengWand;			//문어스틱
	
	public static Item dirtResetItem;		//더러움 수치 리셋 아이템
	public static Item gaugeResetItem;		//더러움 게이지 리셋 아이템
	public static Item waterResetItem;		//갈증 리셋 아이템
	
	public static Item ttaemiriTowel;		//때밀이 수건
	public static Item soap;				//비누
	public static Item towel;				//수건
	public static Item dirtChunk;			//때
	
	public static Item barleyStoneEgg;		//맥반석 계란
	public static Item seonengMilk;			//우유
	public static Item sikhye;				//식혜
	public static Item nenglonaminC;		//넹로나민C
	
	public static Item proximityOnlyDust;	//근접용 먼지
	public static Item throwableDust;		//투척용 먼지
	
	
	
	public static void init()
	{
		seonengIcon = new ItemIcon();
		seonengWand = new ItemWand();
		
		dirtResetItem = new ItemDirtReset();
		gaugeResetItem = new ItemGaugeReset();
		waterResetItem = new ItemWaterReset();
		
		ttaemiriTowel = new ItemTtaemiriTowel();
		soap = new ItemSoap();
		towel = new ItemTowel();
		dirtChunk = new ItemDirtChunk();
		
		barleyStoneEgg = new ItemBarleyStoneEgg();
		seonengMilk = new ItemSeonengMilk();
		sikhye = new ItemSikhye();
		nenglonaminC = new ItemNenglonaminC();
		
		proximityOnlyDust = new ProximityOnlyDust();
		throwableDust = new ThrowableDust();
	}
	
	public static void register()
	{
		GameRegistry.registerItem(seonengIcon, "seonengIcon");
		GameRegistry.registerItem(seonengWand, "seonengWand");
		
		GameRegistry.registerItem(dirtResetItem, "dirtResetItem");
		GameRegistry.registerItem(gaugeResetItem, "gaugeResetItem");
		GameRegistry.registerItem(waterResetItem, "waterResetItem");
		
		GameRegistry.registerItem(ttaemiriTowel, "ttaemiriTowel");
		GameRegistry.registerItem(soap, "soap");
		GameRegistry.registerItem(towel, "towel");
		GameRegistry.registerItem(dirtChunk, "dirtChunk");
		
		GameRegistry.registerItem(barleyStoneEgg, "barleyStoneEgg");
		GameRegistry.registerItem(seonengMilk, "seonengMilk");
		GameRegistry.registerItem(sikhye, "sikhye");
		GameRegistry.registerItem(nenglonaminC, "nenglonaminC");
		
		GameRegistry.registerItem(proximityOnlyDust, "proximityOnlyDust");
		GameRegistry.registerItem(throwableDust, "throwableDust");
	}
}
