package mint.seobaragi.properties;

import static library.ColorCode.Cb;
import static library.ColorCode.Cf;
import static library.ColorCode.Cd;
import mint.seobaragi.exceptions.InvalidFormatException;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.packet.SeonengPacket.Type;
import mint.seobaragi.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class PropertyPlayerStat implements IExtendedEntityProperties
{
	public static final String ID = "PlayerStat";
	
	public int dirtLevel = 0;
	public int waterLevel = 50;
	public int dirtGauge = 0;
	public int cellPoint = 0;
	
	public static final String KEY_STAT = "stat";
	public static final String KEY_DIRT_LEVEL = "dirt_level";
	public static final String KEY_WATER_LEVEL = "water_level";
	public static final String KEY_DIRT_GAUGE = "dirt_gauge";
	public static final String KEY_CELL_POINT = "cell_point";
	
	protected EntityPlayer thePlayer;
	protected World theWorld;
	
	// 1. int ->> 플레이어가 로그인하면 1200   // 
	// 2. timer 를 플레이 어 객체만큼 만들고 실행
	
	public PropertyPlayerStat set(PropertyPlayerStat object)
	{
		this.dirtLevel = object.dirtLevel;
		this.waterLevel = object.waterLevel;
		this.dirtGauge = object.dirtGauge;
		this.cellPoint = object.cellPoint;
		
		return this;
	}
	
	
	public PropertyPlayerStat setDirtLevel(int value)
	{
		this.dirtLevel = value;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.DIRT_LEVEL, dirtLevel), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat setWaterLevel(int value)
	{
		this.waterLevel = value;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.WATER_LEVEL, waterLevel), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat setDirtGauge(int value)
	{
		this.dirtGauge = value;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.DIRT_GAUGE, dirtGauge), theWorld.provider.dimensionId);
		
		return this;
	}
	
	public PropertyPlayerStat setSellPoint(int value)
	{
		this.cellPoint = value;
		CommonProxy.INSTANCE.sendToDimension(new SeonengPacket(thePlayer.getUniqueID(), Type.CELL_POINT, cellPoint), theWorld.provider.dimensionId);
		
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
		tag.setInteger(KEY_CELL_POINT, cellPoint);
		
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
			
			
			if(tag_stat.hasKey(KEY_CELL_POINT, NBT.TAG_INT))
				cellPoint = tag_stat.getInteger(KEY_CELL_POINT);
			else
				throw new InvalidFormatException("KEY_SELL_POINT");
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
	
	//NBT데이터 백업
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return new PropertyPlayerStat()
		.setDirtLevel(this.dirtLevel)
		.setWaterLevel(this.waterLevel)
		.setDirtGauge(this.dirtGauge)
		.setSellPoint(this.cellPoint);
	}
	
	//String 메세지
	@Override
	public String toString()
	{
		return
				Cb + "DirtLevel: " + Cf + dirtLevel +
				Cb + " WaterLevel: " + Cf + waterLevel +
				Cb + " DirtGauge: " + Cf + dirtGauge +
				Cb + " CellPoint: " + Cf + cellPoint;
	}
}
