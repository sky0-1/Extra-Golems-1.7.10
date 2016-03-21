package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityQuartzGolem extends GolemBase 
{			
	public EntityQuartzGolem(World world) 
	{
		super(world, 8.5F, Blocks.quartz_block);
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("quartz"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(85.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.29D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 4 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.quartz, size > 16 ? 16 : size));
	}
   
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
