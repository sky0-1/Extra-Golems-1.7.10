package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntitySpongeGolem extends GolemBase 
{			
	public EntitySpongeGolem(World world) 
	{
		super(world, 2.0F, Blocks.sponge);
		this.getNavigator().setAvoidsWater(false);
	}

	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("sponge"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(Config.ALLOW_SPONGE_SPECIAL && (Config.TWEAK_SPONGE_INTERVAL == 1 || this.ticksExisted % Config.TWEAK_SPONGE_INTERVAL == 0))
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int z = MathHelper.floor_double(this.posZ);
			
			// affect sphere around the golem
			for(int i = -Config.TWEAK_SPONGE; i <= Config.TWEAK_SPONGE; i++)
			{
				for(int j = -Config.TWEAK_SPONGE; j <= Config.TWEAK_SPONGE; j++)
				{
					for(int k = -Config.TWEAK_SPONGE; k <= Config.TWEAK_SPONGE; k++)
					{
						if(this.getDistance(x + i, y + j, z + k) <= Config.TWEAK_SPONGE)
						{
							Block b1 = this.worldObj.getBlock(x + i, y + j, z + k);

							if(b1 == Blocks.water || b1 == Blocks.flowing_water || b1.getMaterial() == Material.water)
							{
								this.worldObj.setBlockToAir(x + i, y + j, z + k);
							}
						}
					}
				}	
			}
		}

		if(Math.abs(this.motionX) < 0.03D && Math.abs(this.motionZ) < 0.03D)
		{
			double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 0.75D;
			double d1 = this.posY + this.rand.nextDouble() * (double)this.height - 0.5D;
			double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 0.75D;
			this.worldObj.spawnParticle("dripWater", d0, d1, d2, 0.0D, -4.0D, 0.0D);
		}
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)	
	{
		int size = 1 + this.rand.nextInt(3 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Item.getItemFromBlock(Blocks.sponge), size > 4 ? 4 : size));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
