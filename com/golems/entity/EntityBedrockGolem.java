package com.golems.entity;

import java.util.List;

import com.golems.main.ContentInit;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityBedrockGolem extends GolemBase 
{			
	public EntityBedrockGolem(World world) 
	{
		super(world, 32.0F, Blocks.bedrock);
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("bedrock"));
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		return super.attackEntityAsMob(entity);
	}

	@Override
	public boolean isEntityInvulnerable()
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

	@Override
	protected boolean interact(EntityPlayer player)
	{
		// creative players can "despawn" by using spawnBedrockGolem on this entity
		if(player.capabilities.isCreativeMode)
		{
			ItemStack itemstack = player.inventory.getCurrentItem();

			if (itemstack != null && itemstack.getItem() == ContentInit.spawnBedrockGolem)
			{		
				player.swingItem();
				if(!this.worldObj.isRemote)
				{
					this.setDead();
				}
			}
		}

		return super.interact(player);
	}

	@Override
	protected void damageEntity(DamageSource source, float amount) { }

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(999.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel) {}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
