package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityEndstoneGolem extends GolemBase 
{			
	private int teleportDelay;

	public EntityEndstoneGolem(World world) 
	{
		super(world, 8.0F, Blocks.end_stone);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("end_stone"));
	}

	private boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0);
		if(MinecraftForge.EVENT_BUS.post(event) || !Config.ALLOW_ENDSTONE_SPECIAL)
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

			for (int l = 0; l < short1; ++l)
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

			if(Config.ALLOW_ENDSTONE_SPECIAL)
			{
				this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
				this.playSound("mob.endermen.portal", 1.0F, 1.0F);
			}

			return true;
		}
	}

	private boolean teleportRandomly()
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 32.0D; //was * 64.0D but Endstone golems should have smaller range
		double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
		return this.teleportTo(d0, d1, d2);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		int i;
		int j;
		int k;

		for (k = 0; k < 2; ++k)
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
		else if(rand.nextInt(200) == 0)
		{
			this.teleportRandomly();
		}

		if (!this.worldObj.isRemote && this.isEntityAlive())
		{
			if (this.entityToAttack != null)
			{
				if (this.entityToAttack instanceof EntityMob)
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
				if(rand.nextInt(8) == 0 || (this.entityToAttack != null && rand.nextInt(2) == 0))
				{
					this.teleportRandomly();
				}

				return super.attackEntityFrom(source, amount);
			}
		}
	}

	/**
	 * Teleport the enderman to another entity
	 */
	private boolean teleportToEntity(Entity entity)
	{
		Vec3 vec3 = Vec3.createVectorHelper(this.posX - entity.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - entity.posY + (double)entity.getEyeHeight(), this.posZ - entity.posZ);
		vec3 = vec3.normalize();
		double d0 = 16.0D;
		double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
		double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
		double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
		return this.teleportTo(d1, d2, d3);
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int i = rand.nextInt(3);
		switch(i)
		{
		case 0:	return new ItemStack(Item.getItemFromBlock(Blocks.end_stone), 2 + this.rand.nextInt(2));
		case 1:	return new ItemStack(Items.ender_pearl, 2 + this.rand.nextInt(3));
		case 2:	return new ItemStack(Items.ender_eye, 1 + this.rand.nextInt(2));
		}

		return new ItemStack(Item.getItemFromBlock(Blocks.end_stone));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
