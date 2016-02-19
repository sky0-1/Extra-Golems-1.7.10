package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTNTGolem extends GolemBase 
{	
	private final int MIN_EXPLOSION_RAD = 3;
	private final int FUSE_LEN = 50;

	private boolean isIgnited;
	private boolean willExplode;
	private int fuseTimer;

	public EntityTNTGolem(World world) 
	{
		super(world, 2.5F, Blocks.tnt);
		this.resetIgnite();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("tnt"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(this.getHealth() <= 1)
		{
			this.ignite();
			this.fuseTimer = 1;
		}

		if(this.isBurning())
		{
			this.ignite();
		}

		if(this.isWet() || (this.entityToAttack != null && this.getDistanceSqToEntity(this.entityToAttack) > 16))
		{
			this.resetIgnite();
		}

		if(this.isIgnited)
		{
			this.motionX = this.motionZ = 0;
			this.fuseTimer--;
			for (int i = 0; i < 2; i++)
			{
				this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 2.0D, this.posZ, 0.0D, 0.0D, 0.0D);
				this.worldObj.spawnParticle("largesmoke", this.posX + 0.75D, this.posY + 1.0D + rand.nextDouble() * 2, this.posZ + 0.75D, 0.5 * (0.5D - rand.nextDouble()), 0.0D, 0.5 * (0.5D - rand.nextDouble()));
				this.worldObj.spawnParticle("largesmoke", this.posX + 0.75D, this.posY + 1.0D + rand.nextDouble() * 2, this.posZ - 0.75D, 0.5 * (0.5D - rand.nextDouble()), 0.0D, 0.5 * (0.5D - rand.nextDouble()));
				this.worldObj.spawnParticle("largesmoke", this.posX - 0.75D, this.posY + 1.0D + rand.nextDouble() * 2, this.posZ + 0.75D, 0.5 * (0.5D - rand.nextDouble()), 0.0D, 0.5 * (0.5D - rand.nextDouble()));
				this.worldObj.spawnParticle("largesmoke", this.posX - 0.75D, this.posY + 1.0D + rand.nextDouble() * 2, this.posZ - 0.75D, 0.5 * (0.5D - rand.nextDouble()), 0.0D, 0.5 * (0.5D - rand.nextDouble()));
			}

			if(this.fuseTimer <= 0)
			{
				this.willExplode = true;
			}
		}

		if(this.willExplode)
		{
			this.explode();
		}
	}
	
	@Override
	public void onDeath(DamageSource source)
    {
		super.onDeath(source);
		this.explode();
    }

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		boolean flag = super.attackEntityAsMob(entity);

		if (flag && !entity.isDead && rand.nextInt(10) == 0 && this.getDistanceSqToEntity(entity) < 16)
		{
			this.ignite();
		}

		return flag;
	}

	@Override
	protected boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null && itemstack.getItem() == Items.flint_and_steel)
		{		
			player.swingItem();

			if(!this.worldObj.isRemote)
			{
				this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
				this.setFire(FUSE_LEN / 20);
				this.ignite();
				itemstack.damageItem(1, player);
				return true;
			}
		}

		return true;
	}

	private void ignite()
	{
		if(!this.isIgnited)
		{
			// update info
			this.isIgnited = true;
			this.fuseTimer = this.FUSE_LEN;
			// play sounds
			if(!this.isWet())
			{
				this.worldObj.playSoundAtEntity(this, "creeper.primed", 1.0F, 0.5F);
			}
		}
	}

	private void resetIgnite()
	{
		this.isIgnited = false;
		this.fuseTimer = this.FUSE_LEN;
		this.willExplode = false;
	}

	private void explode()
	{
		if(Config.ALLOW_TNT_SPECIAL)
		{
			if(!this.worldObj.isRemote)
			{
				boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.MIN_EXPLOSION_RAD + rand.nextInt(3), flag);
				this.setDead();
			}
		}
		else
		{
			resetIgnite();
		}
	}


	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 2 + this.rand.nextInt(4);
		return new ItemStack(Items.gunpowder, size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
