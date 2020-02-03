package mint.seobaragi.entity;

import static library.Message.CAUSE_DIRTY;
import static library.Message.DUST_TYPE_B;
import static library.Message.NAME;
import static library.Message.SUFFER_DIRTY;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.packet.SeonengPacket.Type;
import mint.seobaragi.properties.PropertyPlayerStat;
import mint.seobaragi.proxy.CommonProxy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDust extends EntityThrowable
{
	public EntityDust(World world)
	{
		super(world);
	}
	
	public EntityDust(World world, EntityLivingBase entityBase)
	{
		super(world, entityBase);
	}
	
	public EntityDust(World world, double par2, double par4, double par6)
	{
		super(world, par2, par4, par6);
	}
	
	
	
	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		if(mop.entityHit != null)
		{
			byte b0 = 4;
			
			if(mop.entityHit instanceof EntityPlayerMP)
			{
				b0 = 1;
				
				EntityPlayerMP playerMP = (EntityPlayerMP) mop.entityHit;
				PropertyPlayerStat stat = (PropertyPlayerStat) playerMP.getExtendedProperties(ID);
				
				playerMP.addChatMessage(new ChatComponentText(NAME + SUFFER_DIRTY));
				stat.setDirtLevel(stat.dirtLevel + 1);
			}
			
			mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) b0);		

		}
		
		for(int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle("cloud", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
		
		if(!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}
