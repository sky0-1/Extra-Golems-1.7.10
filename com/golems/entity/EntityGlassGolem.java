package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityGlassGolem extends GolemBase 
{			
	public EntityGlassGolem(World world) 
	{
		super(world, 13.0F, Blocks.glass);
		this.setAllowFallDamage(true);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("glass"));
	}
	
	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM
	
	@Override
	protected void applyEntityAttributes() 
	{
	 	super.applyEntityAttributes();
	 	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		int size = this.rand.nextInt(2);
		return new ItemStack(Item.getItemFromBlock(Blocks.glass));
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
