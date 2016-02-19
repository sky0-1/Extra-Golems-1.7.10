package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityStrawGolem extends GolemBase 
{			
	public EntityStrawGolem(World world) 
	{
		super(world, 1.0F, Blocks.hay_block);
		this.tasks.addTask(0, new EntityAISwimming(this));
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("straw"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 4 + this.rand.nextInt(8);
		return new ItemStack(Items.wheat, size);
	}
	
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}

}
