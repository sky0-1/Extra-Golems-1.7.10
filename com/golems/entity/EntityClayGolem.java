package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityClayGolem extends GolemBase 
{		
	public EntityClayGolem(World world) 
	{
		super(world, 2.0F, Blocks.clay);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("clay"));
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
		int size = 8 + this.rand.nextInt(8);
		return new ItemStack(Items.clay_ball, size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeSand.soundName;
	}
}
