package com.golems.entity;

import com.golems.main.Config;
import com.golems.main.ContentInit;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBedrockGolem extends GolemBase 
{			
	public EntityBedrockGolem(World world) 
	{
		super(world, 14.0F, Blocks.bedrock);
	}

	protected void entityInit()
	{
		super.entityInit();
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

		return true;
	}

	@Override
	protected void damageEntity(DamageSource source, float amount) { }

	//THE FOLLOWING USE @Override AND SHOULD BE SET FOR EACH GOLEM

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(999.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	@Override
	public ItemStack getGolemDrops() 
	{
		return new ItemStack(Item.getItemFromBlock(Blocks.air), 1);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
