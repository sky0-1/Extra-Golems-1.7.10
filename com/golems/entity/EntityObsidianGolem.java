package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityObsidianGolem extends GolemBase 
{			
	public EntityObsidianGolem(World world) 
	{
		super(world, 18.0F, Blocks.obsidian);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("obsidian"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 1 + this.rand.nextInt(2);
		return new ItemStack(Item.getItemFromBlock(Blocks.obsidian), size);
	}
   
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
