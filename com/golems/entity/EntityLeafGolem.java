package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityLeafGolem extends GolemColorized 
{
	private static final ResourceLocation TEXTURE_BASE = GolemBase.getGolemTexture("leaves");
	private static final ResourceLocation TEXTURE_OVERLAY = GolemBase.getGolemTexture("leaves_grayscale");
	
	public EntityLeafGolem(World world)
	{
		super(world, 0.5F, Blocks.leaves, BiomeGenBase.birchForest.color, TEXTURE_BASE, TEXTURE_OVERLAY);
		this.tasks.addTask(0, new EntityAISwimming(this));
	}
	
	@Override
	protected void applyAttributes() 
	{
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
		
		if(this.ticksExisted % 10 == 2 && this.worldObj.isRemote)
		{
			int x = (int)Math.round(this.posX);
			int y = (int)Math.round(this.posY);
	        int z = (int)Math.round(this.posZ);
			BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x, z);
			long color = biome.getBiomeFoliageColor(x, y, z);
			// debug:
			//System.out.print("color is " + color + "\n");
			this.setColor(color);
		}
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.leaves, lootingLevel + 1));
		GolemBase.addDropEntry(dropList, Blocks.sapling, 0, 1, 1, 20 + lootingLevel * 10);
		GolemBase.addDropEntry(dropList, Items.apple, 0, 1, 1, 15 + lootingLevel * 10);
		GolemBase.addDropEntry(dropList, Items.stick, 0, 1, 2, 5 + lootingLevel * 10);
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGrass.soundName;
	}
}
