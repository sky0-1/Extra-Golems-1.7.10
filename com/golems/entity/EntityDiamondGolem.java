package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityDiamondGolem extends GolemBase 
{			
	public EntityDiamondGolem(World world) 
	{
		super(world, 20.0F, Blocks.diamond_block);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("diamond_block"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(220.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 4 + this.rand.nextInt(8);
		return new ItemStack(Items.diamond, size);
	}
	
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
