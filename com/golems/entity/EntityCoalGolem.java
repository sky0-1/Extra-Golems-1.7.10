package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityCoalGolem extends GolemBase
{		
	public EntityCoalGolem(World world) 
	{
		super(world, 2.5F, Blocks.coal_block);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("coal"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}
	
	/** Attack by adding potion effect as well */
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			final int BLIND_CHANCE = 4;
			if(Config.ALLOW_COAL_SPECIAL && entity instanceof EntityLivingBase && this.rand.nextInt(BLIND_CHANCE) == 0)
			{
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 20 * (1 + rand.nextInt(5)), 1));
			}
			return true;
		}
		return false;
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.coal, size));
		GolemBase.addDropEntry(dropList, Items.coal, 1, 1, size / 4, 40);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
