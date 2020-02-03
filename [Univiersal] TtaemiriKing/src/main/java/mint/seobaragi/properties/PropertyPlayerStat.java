package mint.seobaragi.properties;

import static library.ColorCode.Cb;
import static library.ColorCode.Cf;
import static library.Message.GAMEOVER;
import static library.ColorCode.Cd;
import mint.seobaragi.exceptions.InvalidFormatException;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.packet.SeonengPacket.Type;
import mint.seobaragi.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class PropertyPlayerStat implements IExtendedEntityProperties
{
	public static final String ID = "PlayerStat";
	
	public int dirtLevel = 0;
	public int waterLevel = 50;
	public int dirtGauge = 0;
	public boolean canClean = false;
	
	public static final int DEFAULT_DIRT_LEVEL = 0;
	public static final int MIN_DIRT_LEVEL = 0;
	public static final int MAX_DIRT_LEVEL = 5;
	
	public static final int DEFAULT_WATER_LEVEL = 50;
	public static final int MIN_WATER_LEVEL = 0;
	public static final int MAX_WATER_LEVEL = 50;
	
	public static final int DEFAULT_DIRT_GAUGE = 0;
	public static final int MIN_DIRT_GAUGE = 0;
	public static final int MAX_DIRT_GAUGE = 200;
	
	public static final boolean DEFAULT_CAN_CLEAN = false;
	
	public static final String KEY_STAT = "PlayerStat";
	public static final String KEY_DIRT_LEVEL = "DirtLevel";
	public static final String KEY_WATER_LEVEL = "WaterLevel";
	public static final String KEY_DIRT_GAUGE = "DirtGauge";
	public static final String KEY_CAN_CLEAN = "CanClean";
	
	protected EntityPlayer thePlayer;
	protected World theWorld;
	
	// 1. int ->> 플레이어가 로그인하면 1200   // 
	// 2. timer 를 플레이 어 객체만큼 만들고 실행
	
	public PropertyPlayerStat set(PropertyPlayerStat stat)
	{
		this.dirtLevel = checkDirtLevel(stat.dirtLevel);
		this.waterLevel = checkWaterLevel(stat.waterLevel);
		this.dirtGauge = checkDirtGauge(stat.dirtGauge);
		this.canClean = stat.canClean;
		
		return this;
	}
	
	public PropertyPlayerStat resetAll() {
		this.dirtLevel = DEFAULT_DIRT_LEVEL;
		this.waterLevel = DEFAULT_WATER_LEVEL;
		this.dirtGauge = DEFAULT_DIRT_GAUGE;
		this.canClean = DEFAULT_CAN_CLEAN;
		
		return this;
	}
	
	public PropertyPlayerStat resetAllAndSendPacket() {
		this.dirtLevel = DEFAULT_DIRT_LEVEL;
		this.waterLevel = DEFAULT_WATER_LEVEL;
		this.dirtGauge = DEFAULT_DIRT_GAUGE;
		this.canClean = DEFAULT_CAN_CLEAN;
		
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.ALL, this), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat resetDirtLevelAndSendPacket() {
		this.dirtLevel = DEFAULT_DIRT_LEVEL;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.DIRT_LEVEL, DEFAULT_DIRT_LEVEL), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat setDirtLevel(int value)
	{
		this.dirtLevel = checkDirtLevel(value);
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.DIRT_LEVEL, dirtLevel), theWorld.provider.dimensionId);
		
		return this;
	}
	
	private int checkDirtLevel (int value) {
		int tmp = value < MIN_DIRT_LEVEL? MIN_DIRT_LEVEL : value;
		
		if (!theWorld.isRemote && tmp > MAX_DIRT_LEVEL) {
			thePlayer.addChatMessage(new ChatComponentText(GAMEOVER));
			thePlayer.setPositionAndUpdate(388, 4, 306);
		}
		
		return tmp > MAX_DIRT_LEVEL? DEFAULT_DIRT_LEVEL : tmp;
	}
	
	public PropertyPlayerStat setWaterLevel(int value)
	{
		this.waterLevel = checkWaterLevel(value);
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.WATER_LEVEL, waterLevel), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat resetWaterLevelAndSendPacket()
	{
		this.waterLevel = DEFAULT_WATER_LEVEL;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.WATER_LEVEL, DEFAULT_WATER_LEVEL), theWorld.provider.dimensionId);
		
		return this;
	}
	
	private int checkWaterLevel (int value) {
		int tmp = value < MIN_WATER_LEVEL? MIN_WATER_LEVEL : value;
		return tmp > MAX_WATER_LEVEL? MAX_WATER_LEVEL : tmp;
	}
	
	public PropertyPlayerStat setDirtGauge(int value)
	{
		this.dirtGauge = checkDirtGauge(value);
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.DIRT_GAUGE, dirtGauge), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat resetDirtGaugeAndSendPacket()
	{
		this.dirtGauge = DEFAULT_DIRT_GAUGE;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.DIRT_GAUGE, DEFAULT_DIRT_GAUGE), theWorld.provider.dimensionId);
		
		return this;
	}
	
	private int checkDirtGauge (int value) {
		int tmp = value < MIN_DIRT_GAUGE? MIN_DIRT_GAUGE : value;
		return tmp > MAX_DIRT_GAUGE? MAX_DIRT_GAUGE : tmp;
	}
	
	public PropertyPlayerStat setCanClean(boolean value) {
		this.canClean = value;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.CAN_CLEAN, canClean), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat resetCanCleanAndSendPacket() {
		this.canClean = DEFAULT_CAN_CLEAN;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.CAN_CLEAN, DEFAULT_CAN_CLEAN), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat setAndSendPacket(PropertyPlayerStat stat) {
		this.dirtLevel = checkDirtLevel(stat.dirtLevel);
		this.waterLevel = checkWaterLevel(stat.waterLevel);
		this.dirtGauge = checkDirtGauge(stat.dirtGauge);
		this.canClean = stat.canClean;
		
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.ALL, this), theWorld.provider.dimensionId);
		
		return this;
	}
	
	
	//NBT데이터 생성
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(KEY_DIRT_LEVEL, dirtLevel);
		tag.setInteger(KEY_WATER_LEVEL, waterLevel);
		tag.setInteger(KEY_DIRT_GAUGE, dirtGauge);
		tag.setBoolean(KEY_CAN_CLEAN, canClean);
		
		compound.setTag(KEY_STAT, tag);
	}
	
	//NBT데이터 불러오기
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		if(compound.hasKey(KEY_STAT, NBT.TAG_COMPOUND))
		{
			NBTTagCompound tag_stat = compound.getCompoundTag(KEY_STAT);
			
			if(tag_stat.hasKey(KEY_DIRT_LEVEL, NBT.TAG_INT))
				dirtLevel = tag_stat.getInteger(KEY_DIRT_LEVEL);
			else
				throw new InvalidFormatException("KEY_DIRT");
			
			
			if(tag_stat.hasKey(KEY_WATER_LEVEL, NBT.TAG_INT))
				waterLevel = tag_stat.getInteger(KEY_WATER_LEVEL);
			else
				throw new InvalidFormatException("KEY_WATER");
			
			
			if(tag_stat.hasKey(KEY_DIRT_GAUGE, NBT.TAG_INT))
				dirtGauge = tag_stat.getInteger(KEY_DIRT_GAUGE);
			else
				throw new InvalidFormatException("KEY_DIRT_GAUGE");
			
			
			if(tag_stat.hasKey(KEY_CAN_CLEAN, NBT.TAG_BYTE))
				canClean = tag_stat.getBoolean(KEY_CAN_CLEAN);
			else
				throw new InvalidFormatException("KEY_CAN_CLEAN");
		}
		
		else
			throw new InvalidFormatException("KEY_STAT");
		
	}
	
	//NBT데이터 생성시
	@Override
	public void init(Entity entity, World world)
	{
		thePlayer = (EntityPlayer) entity;
		theWorld = world;
	}
	
	//String 메세지
	@Override
	public String toString()
	{
		return
				Cb + "더러움: " + Cf + dirtLevel +
				Cb + " 수분: " + Cf + waterLevel +
				Cb + " 더러움 게이지: " + Cf + dirtGauge +
				Cb + " 때밀이: " + Cf + (canClean? "가능" : "불가능");
	}
}
