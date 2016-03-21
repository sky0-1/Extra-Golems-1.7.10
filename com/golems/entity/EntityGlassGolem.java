package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityGlassGolem extends GolemBase 
{			
	public EntityGlassGolem(World world) 
	{
		super(world, 13.0F, Blocks.glass);
		this.setCanTakeFallDamage(true);
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("glass"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		GolemBase.addDropEntry(dropList, Blocks.glass, 0, lootingLevel, lootingLevel + 1, 90);
	}
	
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
	
	@Override
	protected String getDeathSound()
	{
		return "random.break";
	}
}
