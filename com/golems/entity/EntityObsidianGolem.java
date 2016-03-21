package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityObsidianGolem extends GolemBase 
{			
	public EntityObsidianGolem(World world) 
	{
		super(world, 18.0F, Blocks.obsidian);
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("obsidian"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 1 + this.rand.nextInt(2 + (lootingLevel > 2 ? 2 : lootingLevel));
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.obsidian, size));
	}
   
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
