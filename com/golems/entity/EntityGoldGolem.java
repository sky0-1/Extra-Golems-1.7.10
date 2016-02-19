package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGoldGolem extends GolemBase 
{			
	public EntityGoldGolem(World world) 
	{
		super(world, 8.0F, Blocks.gold_block);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("gold"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.19D);
	  	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.9D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 6 + this.rand.nextInt(4);
		return new ItemStack(Items.gold_ingot, size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeMetal.soundName;
	}
}
