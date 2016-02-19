package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityGlowstoneGolem extends GolemLightProvider
{			
	public EntityGlowstoneGolem(World world) 
	{
		super(world, 12.0F, Blocks.glowstone, EnumLightLevel.FULL);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("glowstone"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 6 + this.rand.nextInt(8);
		return new ItemStack(Items.glowstone_dust, size);
	}
 
	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
