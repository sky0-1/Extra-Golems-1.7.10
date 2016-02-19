package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpongeGolem extends GolemBase 
{			
	public EntitySpongeGolem(World world) 
	{
		super(world, 2.0F, Blocks.sponge);
		this.getNavigator().setAvoidsWater(false);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("sponge"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(Config.ALLOW_SPONGE_SPECIAL && this.ticksExisted % 4 == 0)
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int z = MathHelper.floor_double(this.posZ);
			int[][] blockCoords = 
				{{x,y,z},{x+1,y,z},{x-1,y,z},{x,y,z+1},{x,y,z-1},
				{x+1,y,z+1},{x-1,y,z+1},{x+1,y,z-1},{x-1,y,z-1}};

			// affect 3 layers of blocks around the golem
			for(int i = -1; i < 3; i++)
			{
				for(int[] coord : blockCoords)
				{
					int xCoord = coord[0];
					int yCoord = coord[1] + i;
					int zCoord = coord[2];
					Block b1 = this.worldObj.getBlock(xCoord, yCoord, zCoord);

					if(b1 == Blocks.water || b1 == Blocks.flowing_water || b1.getMaterial() == Material.water)
					{
						this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
					}
				}	
			}
		}

		if(Math.abs(this.motionX) < 0.03D && Math.abs(this.motionZ) < 0.03D)
		{
			double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 0.75D;
			double d1 = this.posY + this.rand.nextDouble() * (double)this.height - 0.5D;
			double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 0.75D;
			this.worldObj.spawnParticle("dripWater", d0, d1, d2, 0.0D, -4.0D, 0.0D);
		}
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 1 + this.rand.nextInt(3);
		return new ItemStack(Item.getItemFromBlock(Blocks.sponge), size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
