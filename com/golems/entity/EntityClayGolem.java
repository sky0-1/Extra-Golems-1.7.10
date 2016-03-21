package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityClayGolem extends GolemBase 
{		
	public EntityClayGolem(World world) 
	{
		super(world, 2.0F, Blocks.clay);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("clay"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + this.rand.nextInt(8 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.clay_ball, size));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeSand.soundName;
	}
}
