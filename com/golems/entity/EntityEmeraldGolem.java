package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityEmeraldGolem extends GolemBase 
{			
	public EntityEmeraldGolem(World world) 
	{
		super(world, 18.0F, Blocks.emerald_block);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("emerald_block"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(190.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 4 + this.rand.nextInt(8);
		return new ItemStack(Items.emerald, size);
	}
	
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
