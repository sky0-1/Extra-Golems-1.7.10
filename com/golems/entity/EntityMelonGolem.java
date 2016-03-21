package com.golems.entity;

import java.util.List;

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
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityMelonGolem extends GolemBase 
{			
	public EntityMelonGolem(World world) 
	{
		super(world, 1.5F, Blocks.melon_block);
	}

	protected void applyTexture()
	{
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

	protected void setToPlant(World world, int x, int y, int z)
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

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(6 + lootingLevel * 4);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.melon, size));
		GolemBase.addDropEntry(dropList, Items.melon_seeds, 0, 1, 6 + lootingLevel, 20 + lootingLevel * 10);
		GolemBase.addDropEntry(dropList, Items.speckled_melon, 0, 1, 1, 2 + lootingLevel * 10);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
