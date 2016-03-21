package com.golems.entity;

import java.util.List;

import com.golems.main.ExtraGolems;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
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
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(4 + lootingLevel * 4);
		int meta = rand.nextBoolean() ? this.getTextureNum() : 0;
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Item.getItemFromBlock(Blocks.planks), size > 16 ? 16 : size, meta));
		GolemBase.addDropEntry(dropList, Items.stick, 0, 1, 4, 10 + lootingLevel * 4);
		GolemBase.addDropEntry(dropList, Blocks.sapling, 0, 1, 2, 4 + lootingLevel * 4);
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
