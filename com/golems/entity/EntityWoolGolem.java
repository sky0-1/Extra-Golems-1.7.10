package com.golems.entity;

import com.golems.main.ExtraGolems;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityWoolGolem extends GolemMultiTextured
{	
	private static final String[] coloredWoolTypes = {"black","orange","magenta","light_blue","yellow","lime","pink","gray","silver","cyan","purple","blue","brown","green","red"};

	public EntityWoolGolem(World world) 
	{
		super(world, 1.0F, coloredWoolTypes);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.getNavigator().setAvoidsWater(false);
	}
	
	@Override
	public String getTexturePrefix() 
	{
		return "wool";
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 1 + this.rand.nextInt(3);
		return new ItemStack(Item.getItemFromBlock(Blocks.wool), size);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeCloth.soundName;
	}
	
	@Override
	public String getModId()
	{
		return ExtraGolems.MODID;
	}
}
