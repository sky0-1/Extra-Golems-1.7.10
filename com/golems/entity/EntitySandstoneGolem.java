package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntitySandstoneGolem extends GolemBase 
{			
	public EntitySandstoneGolem(World world) 
	{
		super(world, 4.0F, Blocks.sandstone);
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("sandstone"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 4 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.sand, size));
	}
   
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
