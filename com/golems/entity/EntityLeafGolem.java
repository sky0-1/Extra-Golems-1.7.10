package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLeafGolem extends GolemBase 
{
	public EntityLeafGolem(World world)
	{
		super(world, 0.5F, Blocks.leaves);
		this.tasks.addTask(0, new EntityAISwimming(this));
	}
	
	@Override
	public void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("leaves"));
	}
	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.31D);
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(Config.ALLOW_LEAF_SPECIAL && this.getActivePotionEffects().isEmpty() && rand.nextInt(40) == 0)
		{
			this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200 + 20 * (1 + rand.nextInt(8)), 1));
		}
	}
	
	@Override
	public ItemStack getGolemDrops() 
	{
		return new ItemStack(Item.getItemFromBlock(Blocks.leaves), 1);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}

}
