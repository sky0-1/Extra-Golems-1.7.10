package com.golems.entity;

import com.golems.main.ExtraGolems;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityWoodenGolem extends GolemMultiTextured
{		
	private static final String[] woodTypes = {"oak","spruce","birch","jungle","acacia","big_oak"};

	public EntityWoodenGolem(World world) 
	{
		super(world, 3.0F, woodTypes);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.getNavigator().setAvoidsWater(false);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.setTextureNum((byte)0);
		this.setTextureType(this.getSpecialGolemTexture("oak"));
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 3 + this.rand.nextInt(4);
		return new ItemStack(Item.getItemFromBlock(Blocks.planks), size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeWood.soundName;
	}

	@Override
	public String getTexturePrefix() 
	{
		return "wooden";
	}
	
	@Override
	public String getModId()
	{
		return ExtraGolems.MODID;
	}
}
