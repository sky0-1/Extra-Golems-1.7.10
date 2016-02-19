package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHardenedClayGolem extends GolemBase 
{			
	public EntityHardenedClayGolem(World world) 
	{
		super(world, 4.0F, Blocks.hardened_clay);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("hardened_clay"));
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.18D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 1 + this.rand.nextInt(3);
		return new ItemStack(Item.getItemFromBlock(Blocks.hardened_clay), size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
