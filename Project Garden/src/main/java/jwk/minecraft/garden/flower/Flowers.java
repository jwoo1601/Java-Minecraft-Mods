package jwk.minecraft.garden.flower;

import static com.google.common.base.Preconditions.checkNotNull;
import static jwk.minecraft.garden.flower.Flowers.DOUBLE_PLANT;
import static jwk.minecraft.garden.flower.Flowers.FCFLOWER;
import static jwk.minecraft.garden.flower.Flowers.FLOWER1;
import static jwk.minecraft.garden.flower.Flowers.FLOWER2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.vecmath.Color4f;

import cpw.mods.fml.common.registry.GameRegistry;
//import flowercraftmod.Modflowercraft;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;

public class Flowers {
	
	public static final Block FLOWER1 = Blocks.yellow_flower;
	public static final Block FLOWER2 = Blocks.red_flower;
	public static final Block DOUBLE_PLANT = Blocks.double_plant;
	public static final Block FCFLOWER = null;// Modflowercraft.FCFlower; //fromName("fcflower"); MODID: flowercraftmod
	
	public static final Item Item_Grass = Item.getItemFromBlock(Blocks.tallgrass);
	public static final Item Item_Flower1 = Item.getItemFromBlock(Blocks.yellow_flower);
	public static final Item Item_Flower2 = Item.getItemFromBlock(Blocks.red_flower);
	public static final Item Item_Double_Plant = Item.getItemFromBlock(Blocks.double_plant);
	public static final Item Item_FCFlower = GameRegistry.findItem("flowercraftmod", "fcflower");
	public static final Item Item_Stamen = GameRegistry.findItem("flowercraftmod", "fcstamen"); // 수술
	public static final Item Item_Pollen = null; //Modflowercraft.FCPollen; // 꽃가루
	public static final Item Item_Petal = GameRegistry.findItem("flowercraftmod", "fcpetal"); // 꽃잎
	public static final Item Item_Stem = GameRegistry.findItem("flowercraftmod", "fcstem"); // 줄기
	public static final Item Item_Leaves = GameRegistry.findItem("flowercraftmod", "fcleaves"); // 잎
	public static final Item Item_Thorn = GameRegistry.findItem("flowercraftmod", "fcthorn"); // 가시
	public static final Item Item_OldLog = Item.getItemFromBlock(Blocks.log); // 원목1
	public static final Item Item_NewLog = Item.getItemFromBlock(Blocks.log2); // 원목 2
	public static final Item Item_Hedge = GameRegistry.findItem("flowercraftmod", "fchedge");
	public static final Item Item_Flower_Vine_Black = GameRegistry.findItem("flowercraftmod", "fcvineblack");
	public static final Item Item_Flower_Vine_Cyan = GameRegistry.findItem("flowercraftmod", "fcvinecyan");
	public static final Item Item_Flower_Vine_Gray = GameRegistry.findItem("flowercraftmod", "fcvinegray");
	public static final Item Item_Flower_Vine_LightBlue = GameRegistry.findItem("flowercraftmod", "fcvinelightblue");
	public static final Item Item_Flower_Vine_LightGray = GameRegistry.findItem("flowercraftmod", "fcvinelightgray");
	public static final Item Item_Flower_Vine_Purple = GameRegistry.findItem("flowercraftmod", "fcvinepurple");
	public static final Item Item_Flower_Vine_Magenta = GameRegistry.findItem("flowercraftmod", "fcvinemagenta");
	public static final Item Item_Flower_Vine_Orange = GameRegistry.findItem("flowercraftmod", "fcvineorange");
	public static final Item Item_Flower_Vine_Pink = GameRegistry.findItem("flowercraftmod", "fcvinepink");
	public static final Item Item_Flower_Vine_Red = GameRegistry.findItem("flowercraftmod", "fcvinered");
	public static final Item Item_Flower_Vine_Yellow = GameRegistry.findItem("flowercraftmod", "fcvineyellow");
	public static final Item Item_Flower_Vine_Lime = GameRegistry.findItem("flowercraftmod", "fcvinelime");
	public static final Item Item_Flower_Vine_Blue = GameRegistry.findItem("flowercraftmod", "fcvineblue");
	public static final Item Item_Flower_Vine_Brown = GameRegistry.findItem("flowercraftmod", "fcvinebrown");
	public static final Item Item_Flower_Vine_White = GameRegistry.findItem("flowercraftmod", "fcvinewhite");
	public static final Item Item_Hedge_Carpet = GameRegistry.findItem("flowercraftmod", "fchedgecarpet");
	public static final Item Item_Sapling = Item.getItemFromBlock(Blocks.sapling);
	
	private static int currentIndex = 0;
	
	private static final Map<Integer, Integer> indexMap = new ConcurrentHashMap<Integer, Integer>();
	
	public static final int getIndexFromId(int dimensionId) {

		if (indexMap.containsKey(dimensionId))
			return indexMap.get(dimensionId);
		
		return -1;
	}
	
	public static final int nextIndex(int dimensionId) {

		if (!indexMap.containsKey(dimensionId)) {
			indexMap.put(dimensionId, currentIndex);
			return currentIndex++;
		}
		
		return -1;
	}
	
}
