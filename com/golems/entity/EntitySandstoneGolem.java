package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySandstoneGolem extends GolemBase 
{			
	public EntitySandstoneGolem(World world) 
	{
		super(world, 4.0F, Blocks.sandstone);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("sandstone"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 4 + this.rand.nextInt(8);
		return new ItemStack(Item.getItemFromBlock(Blocks.sand), size);
	}
   
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
