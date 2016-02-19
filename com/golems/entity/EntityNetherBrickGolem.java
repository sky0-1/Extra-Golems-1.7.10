package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNetherBrickGolem extends GolemBase 
{			
	/** Golem should stand in one spot for 20 ticks before affecting the block below it */
	private int ticksStandingStill;

	public EntityNetherBrickGolem(World world) 
	{
		super(world, 6.5F, Blocks.nether_brick);
		this.ticksStandingStill = 0;
		this.isImmuneToFire = true;
		this.stepHeight = 1.0F;
		this.tasks.addTask(0, new EntityAISwimming(this));
	}

	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("nether_brick"));
	}

	/** Attack by lighting on fire as well */
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			if(Config.ALLOW_NETHERBRICK_SPECIAL_FIRE)
			{
				entity.setFire(2 + rand.nextInt(5));
			}
			return true;
		}
		return false;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(Config.ALLOW_NETHERBRICK_SPECIAL_LAVA)
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int k = MathHelper.floor_double(this.posZ);
			Block blockBelow = this.worldObj.getBlock(i,j,k);

			int roundedLastX = MathHelper.floor_double(this.lastTickPosX);
			int roundedLastZ = MathHelper.floor_double(this.lastTickPosZ);

			if(i == roundedLastX && k == roundedLastZ)
			{
				if(this.ticksStandingStill++ >= Config.TWEAK_NETHERBRICK && blockBelow == Blocks.cobblestone)
				{
					this.worldObj.setBlock(i, j, k, Blocks.lava);
					this.ticksStandingStill = 0;
				}
			}
			else
			{
				this.ticksStandingStill = 0;
			}
		}
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 4 + this.rand.nextInt(8);
		return new ItemStack(Items.netherbrick, size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
