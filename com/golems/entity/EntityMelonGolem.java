package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMelonGolem extends GolemBase 
{			
	public EntityMelonGolem(World world) 
	{
		super(world, 1.5F, Blocks.melon_block);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("melon"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(Config.ALLOW_MELON_SPECIAL)
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
			int z = MathHelper.floor_double(this.posZ);
			Block blockBelow = this.worldObj.getBlock(x, y, z);

			if(blockBelow == Blocks.grass && this.worldObj.isAirBlock(x, y + 1, z))
			{
				final int PLANT_CHANCE = 160;
				if(rand.nextInt(PLANT_CHANCE) == 0)
				{
					setToPlant(this.worldObj, x, y + 1, z);
				}
			}
		}
	}

	private void setToPlant(World world, int x, int y, int z)
	{
		int ranInt = rand.nextInt(5);
		Block b = null;
		int meta = 0;
		switch(ranInt)
		{
		case 0: 
			b = Blocks.red_flower;
			meta = rand.nextInt(9);
			break;
		case 1: 
			b = Blocks.yellow_flower;
			break;
		case 2: 
			b = Blocks.tallgrass;
			meta = rand.nextInt(3);
		case 3:
			b = Blocks.brown_mushroom;
			break;
		case 4:
			b = Blocks.red_mushroom;
			break;
		}

		if(b != null)
		{
			world.setBlock(x, y, z, b, meta, 3);
		}
	}

	//THE FOLLOWING SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 6 + this.rand.nextInt(4);
		return new ItemStack(Items.melon, size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
