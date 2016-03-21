package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityHardenedClayGolem extends GolemBase 
{			
	public EntityHardenedClayGolem(World world) 
	{
		super(world, 4.0F, Blocks.hardened_clay);
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("hardened_clay"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.18D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 1 + this.rand.nextInt(2 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.hardened_clay, size));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
