package com.golems.entity;

import java.util.List;

import com.golems.content.BlockPowerProvider;
import com.golems.main.Config;
import com.golems.main.ContentInit;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityRedstoneGolem extends GolemBase 
{			
	public EntityRedstoneGolem(World world) 
	{
		super(world, 2.0F, Blocks.redstone_block);
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("redstone"));
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
		if(Config.ALLOW_REDSTONE_SPECIAL && this.ticksExisted % 2 == 0)
		{
			placePowerNearby();
		}
	}
	
	/** Finds air blocks nearby and replaces them with BlockMovingPowerSource **/
	public boolean placePowerNearby() 
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY - 0.20000000298023224D); // y-pos of block below golem
		int z = MathHelper.floor_double(this.posZ);
		
		// power 3 layers at golem location
		for(int k = -1; k < 3; ++k)
		{	
			int yPos = y + k;
			// if the block is air, make it a power block
			if(this.worldObj.isAirBlock(x, yPos, z))
			{
				return this.worldObj.setBlock(x, yPos, z, ContentInit.blockPowerProviderFull);
			}
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
		int size = 8 + rand.nextInt(14 + lootingLevel * 4);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.redstone, size > 36 ? 36 : size));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
