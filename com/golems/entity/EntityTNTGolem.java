package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityTNTGolem extends GolemBase 
{	
	protected final int MIN_EXPLOSION_RAD;
	protected final int MAX_EXPLOSION_RAD;
	protected final int FUSE_LEN;
	/** Percent chance to explode while attacking a mob **/
	protected final int CHANCE_TO_EXPLODE_WHEN_ATTACKING;
	protected boolean canExplode;

	protected boolean isIgnited;
	protected boolean willExplode;
	protected int fuseTimer;

	/** Default constructor for TNT golem **/
	public EntityTNTGolem(World world) 
	{
		this(world, 2.5F, Blocks.tnt, 3, 6, 50, 10, Config.ALLOW_TNT_SPECIAL);
	}
	
	/**
	 * Flexible constructor to allow child classes to customize.
	 **/
	public EntityTNTGolem(World world, float attack, Block pick, int minExplosionRange, int maxExplosionRange, int minFuseLength, int randomExplosionChance, boolean configAllowsExplode)
	{
		super(world, attack, pick);
		this.MIN_EXPLOSION_RAD = minExplosionRange;
		this.MAX_EXPLOSION_RAD = maxExplosionRange > minExplosionRange ? maxExplosionRange : minExplosionRange + 1;
		this.FUSE_LEN = minFuseLength;
		this.CHANCE_TO_EXPLODE_WHEN_ATTACKING = randomExplosionChance;
		this.canExplode = configAllowsExplode;
		this.resetIgnite();	
	}

	@Override
	protected void applyTexture()
	{
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

		if(this.isWet() || (this.entityToAttack != null && this.getDistanceSqToEntity(this.entityToAttack) > this.MIN_EXPLOSION_RAD * this.MIN_EXPLOSION_RAD))
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

		if (flag && !entity.isDead && rand.nextInt(100) < this.CHANCE_TO_EXPLODE_WHEN_ATTACKING && this.getDistanceSqToEntity(entity) <= this.MIN_EXPLOSION_RAD * this.MIN_EXPLOSION_RAD)
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
				this.setFire(Math.floorDiv(this.FUSE_LEN, 20));
				this.ignite();
				itemstack.damageItem(1, player);
				return true;
			}
		}

		return true;
	}

	protected void ignite()
	{
		if(!this.isIgnited)
		{
			// update info
			this.isIgnited = true;
			this.fuseTimer = this.FUSE_LEN + rand.nextInt(Math.floorDiv(FUSE_LEN, 2) + 1);
			// play sounds
			if(!this.isWet())
			{
				this.worldObj.playSoundAtEntity(this, "creeper.primed", 1.0F, 0.5F);
			}
		}
	}

	protected void resetIgnite()
	{
		this.isIgnited = false;
		this.fuseTimer = this.FUSE_LEN + rand.nextInt(Math.floorDiv(FUSE_LEN, 2) + 1);
		this.willExplode = false;
	}

	protected void explode()
	{
		if(this.canExplode)
		{
			if(!this.worldObj.isRemote)
			{
				boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.MIN_EXPLOSION_RAD + rand.nextInt(MAX_EXPLOSION_RAD - MIN_EXPLOSION_RAD), flag);
				this.setDead();
			}
		}
		else
		{
			resetIgnite();
		}
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 2 + this.rand.nextInt(4);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.gunpowder, size));
		GolemBase.addDropEntry(dropList, Blocks.tnt, 0, 1, 1, lootingLevel * 30);
		GolemBase.addDropEntry(dropList, Blocks.sand, 0, 0, 4, 5 + lootingLevel * 10);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
