package com.golems.entity;

import java.util.List;

import com.golems.main.Config;
import com.golems.main.ContentInit;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityEndstoneGolem extends GolemBase 
{			
	/** countdown timer for next teleport **/
	protected int teleportDelay;
	/** Max distance for one teleport; range is 32.0 for endstone golem **/
	protected double range;
	protected boolean canTeleport;
	protected boolean hasParticles;
	
	protected int ticksBetweenIdleTeleports;
	/** Percent chance to teleport away when hurt by non-projectile **/
	protected int chanceToTeleportWhenHurt;
	
	/** Default constructor **/
	public EntityEndstoneGolem(World world) 
	{
		this(world, 8.0F, Blocks.end_stone, 32.0D, Config.ALLOW_ENDSTONE_SPECIAL, true);
	}
	
	/**
	 * Flexible constructor to allow child classes to customize.
	 * 
	 * @param world the worldObj
	 * @param attack base attack damage
	 * @param pick Creative pick-block return
	 * @param teleportRange 64.0 for enderman, 32.0 for endstone golem
	 * @param teleportingAllowed usually set by the config, checked here
	 * @param particles whether to display "portal" particles 
	 **/
	public EntityEndstoneGolem(World world, float attack, Block pick, double teleportRange, boolean teleportingAllowed, boolean particles)
	{
		super(world, attack, pick);
		this.ticksBetweenIdleTeleports = 200;
		this.chanceToTeleportWhenHurt = 15;
		this.range = teleportRange;
		this.canTeleport = teleportingAllowed;
		this.hasParticles = particles;
	}

	/**
	 * Flexible contructor to allow child classes to customize.
	 * 
	 * @param world the worldObj
	 * @param attack base attack damage
	 * @param teleportRange 64.0 for enderman, 32.0 for endstone golem
	 * @param teleportingAllowed usually set by the config, checked here
	 * @param particles whether to display "portal" particles 
	 **/
	public EntityEndstoneGolem(World world, float attack, double teleportRange, boolean teleportingAllowed, boolean particles) 
	{
		this(world, attack, ContentInit.golemHead, teleportRange, teleportingAllowed, particles);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("end_stone"));
	}
	
	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		GolemBase.addDropEntry(dropList, Blocks.end_stone, 0, 2, 2 + lootingLevel, 90);
		GolemBase.addDropEntry(dropList, Items.ender_pearl, 0, 2, 4 + lootingLevel, 40);
		GolemBase.addDropEntry(dropList, Items.ender_eye, 0, 1, 1 + lootingLevel, 6);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}

	protected boolean teleportTo(double x, double y, double z)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, x, y, z, 0);
		if(MinecraftForge.EVENT_BUS.post(event) || !canTeleport)
		{
			return false;
		}
		double d3 = this.posX;
		double d4 = this.posY;
		double d5 = this.posZ;
		this.posX = event.targetX;
		this.posY = event.targetY;
		this.posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);

		if(this.worldObj.blockExists(i, j, k))
		{
			boolean flag1 = false;

			while (!flag1 && j > 0)
			{
				Block block = this.worldObj.getBlock(i, j - 1, k);

				if (block.getMaterial().blocksMovement())
				{
					flag1 = true;
				}
				else
				{
					--this.posY;
					--j;
				}
			}

			if (flag1)
			{
				this.setPosition(this.posX, this.posY, this.posZ);

				if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
				{
					flag = true;
				}
			}
		}

		if (!flag)
		{
			this.setPosition(d3, d4, d5);
			return false;
		}
		else
		{
			short short1 = 128;

			for (int l = 0; this.hasParticles && l < short1; ++l)
			{
				double d6 = (double)l / ((double)short1 - 1.0D);
				float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
				double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
				double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
				this.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
			}

			if(canTeleport)
			{
				this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
				this.playSound("mob.endermen.portal", 1.0F, 1.0F);
			}

			return true;
		}
	}

	protected boolean teleportRandomly()
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * range;
		double d1 = this.posY + (this.rand.nextDouble() - 0.5D) * range * 0.5D;
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * range;
		return this.teleportTo(d0, d1, d2);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		int i, j, k;

		for (k = 0; this.hasParticles && k < 2; ++k)
		{
			this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
		}

		if (this.entityToAttack != null)
		{
			this.faceEntity(this.entityToAttack, 100.0F, 100.0F);
			if(rand.nextInt(5) == 0)
			{
				this.teleportToEntity(this.entityToAttack);
			}
		}
		else if(rand.nextInt(ticksBetweenIdleTeleports) == 0)
		{
			this.teleportRandomly();
		}

		if (!this.worldObj.isRemote && this.isEntityAlive())
		{
			if (this.entityToAttack != null)
			{
				if (this.entityToAttack instanceof IMob)
				{
					if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0D)
					{
						this.teleportRandomly();
					}

					this.teleportDelay = 0;
				}
				else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && this.teleportToEntity(this.entityToAttack))
				{
					this.teleportDelay = 0;
				}
			}
			else
			{           
				this.teleportDelay = 0;
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			// if it's a projectile, try to teleport away
			if (source instanceof EntityDamageSourceIndirect)
			{
				for (int i = 0; i < 32; ++i)
				{
					if (this.teleportRandomly())
					{
						return true;
					}
				}

				return super.attackEntityFrom(source, amount);
			}
			else
			{
				if(rand.nextInt(100) < this.chanceToTeleportWhenHurt || (this.entityToAttack != null && rand.nextBoolean()))
				{
					this.teleportRandomly();
				}

				return super.attackEntityFrom(source, amount);
			}
		}
	}

	protected boolean teleportToEntity(Entity entity)
	{
		Vec3 vec3 = Vec3.createVectorHelper(this.posX - entity.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - entity.posY + (double)entity.getEyeHeight(), this.posZ - entity.posZ);
		vec3 = vec3.normalize();
		double d0 = 16.0D;
		double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
		double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
		double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
		return this.teleportTo(d1, d2, d3);
	}
}
