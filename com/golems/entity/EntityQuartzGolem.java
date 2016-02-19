package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityQuartzGolem extends GolemBase 
{			
	public EntityQuartzGolem(World world) 
	{
		super(world, 8.5F, Blocks.quartz_block);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("quartz"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(85.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.29D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 4 + this.rand.nextInt(8);
		return new ItemStack(Items.quartz, size);
	}
   
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
