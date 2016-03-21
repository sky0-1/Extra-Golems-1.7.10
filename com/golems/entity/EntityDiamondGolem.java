package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityDiamondGolem extends GolemBase 
{			
	public EntityDiamondGolem(World world) 
	{
		super(world, 20.0F, Blocks.diamond_block);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("diamond_block"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(220.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.diamond, size));
	}
	
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
