package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityIceGolem extends GolemBase 
{			
	public EntityIceGolem(World world) 
	{
		super(world, 6.0F, Blocks.packed_ice);
		this.getNavigator().setAvoidsWater(false);
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("ice"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		// calling every other tick reduces lag by 50%
		if(this.ticksExisted % 2 == 0)
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
			int z = MathHelper.floor_double(this.posZ);
			// positions of block below golem and surrounding blocks, including diagonals
			int[][] blockBelowCoords = 
				{{x,y-1,z},{x+1,y,z},{x-1,y,z},{x,y,z+1},
						{x,y,z-1},{x+1,y,z+1},{x-1,y,z+1},{x+1,y,z-1},
						{x-1,y,z-1},{x+2,y,z},{x-2,y,z},{x,y,z-2},{x,y,z+2}};

			if(this.worldObj.getBiomeGenForCoords(x, z).getFloatTemperature(x, y, z) > 1.0F)
			{
				this.attackEntityFrom(DamageSource.onFire, (float)Math.random() + 0.1F);
			}

			if(Config.ALLOW_ICE_SPECIAL)
			{
				Block b1;
				// affect 2 layers of blocks below the golem
				for(int m = 0; m < 3; m++)
				{
					for(int[] coord : blockBelowCoords)
					{
						x = coord[0];
						y = coord[1] - m + 1;
						z = coord[2];
						b1 = this.worldObj.getBlock(x, y, z);
						boolean shouldBeThinIce = false;

						if(b1 == Blocks.water)
						{
							shouldBeThinIce = rand.nextBoolean();
							if(!shouldBeThinIce)
							{
								this.worldObj.setBlock(x, y, z, Blocks.packed_ice);
							}
						}
						if(shouldBeThinIce || b1 == Blocks.flowing_water)
						{
							this.worldObj.setBlock(x, y, z, Blocks.ice);	    		
						}
						else if(b1 == Blocks.lava)
						{
							this.worldObj.setBlock(x, y, z, Blocks.obsidian);	    		
						}
						else if(b1 == Blocks.flowing_lava)
						{
							this.worldObj.setBlock(x, y, z, Blocks.cobblestone);	    		
						}
					}	
				}
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			if(entity.isBurning())
			{
				this.attackEntityFrom(DamageSource.generic, 0.5F);
			}
			return true;
		}

		return false;  
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 1 + lootingLevel;
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.ice, size > 4 ? 4 : size));
		if(lootingLevel > 0 || !Config.CAN_USE_REGULAR_ICE)
		{
			GolemBase.addDropEntry(dropList, Blocks.packed_ice, 0, 0, size > 2 ? 2 : size, 80);
		}
	}

	@Override
	protected String getDeathSound()
	{
		return "random.break";
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
