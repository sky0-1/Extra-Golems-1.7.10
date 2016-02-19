package com.golems.entity;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityLapisGolem extends GolemBase 
{			
	private int[] badEffects = {Potion.blindness.id,Potion.moveSlowdown.id,Potion.poison.id,Potion.weakness.id,Potion.wither.id};

	public EntityLapisGolem(World world) 
	{
		super(world, 1.5F, Blocks.lapis_block);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.setTextureType(this.getGolemTexture("lapis"));
	}

	/** Attack by adding potion effect as well */
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			if(Config.ALLOW_LAPIS_SPECIAL && entity instanceof EntityLivingBase)
			{
				int potionID = entity instanceof EntityZombie || entity instanceof EntitySkeleton ? Potion.heal.id : badEffects[rand.nextInt(badEffects.length)];
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(potionID, 20 * (3 + rand.nextInt(10)), 1 + rand.nextInt(3)));
			}
			return true;
		}
		return false;
	}

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		int size = 8 + this.rand.nextInt(8);
		return new ItemStack(Items.dye, size, 4);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
