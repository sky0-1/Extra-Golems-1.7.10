package com.golems.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityGlowstoneGolem extends GolemLightProvider
{			
	public EntityGlowstoneGolem(World world) 
	{
		super(world, 12.0F, Blocks.glowstone, EnumLightLevel.FULL);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("glowstone"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.glowstone_dust, size));
	}
 
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
