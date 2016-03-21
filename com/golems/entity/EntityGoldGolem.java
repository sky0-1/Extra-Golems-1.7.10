package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityGoldGolem extends GolemBase 
{			
	public EntityGoldGolem(World world) 
	{
		super(world, 8.0F, Blocks.gold_block);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("gold"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.19D);
	  	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.9D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(8 + lootingLevel * 4);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.gold_ingot, size));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeMetal.soundName;
	}
}
