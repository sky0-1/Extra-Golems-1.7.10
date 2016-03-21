package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityStainedClayGolem extends GolemColorizedMultiTextured
{
	private static final ResourceLocation TEXTURE_BASE = GolemBase.getGolemTexture("stained_clay");
	private static final ResourceLocation TEXTURE_OVERLAY = GolemBase.getGolemTexture("stained_clay_grayscale");
	private static final int[] dyeColors = ItemDye.field_150922_c;
	
	public EntityStainedClayGolem(World world)
	{
		super(world, 3.0F, Blocks.stained_hardened_clay, TEXTURE_BASE, TEXTURE_OVERLAY, dyeColors);
	}
	
	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int meta = Config.TWEAK_STAINED_CLAY < 0 ? 15 - this.getTextureNum() : Config.TWEAK_STAINED_CLAY;
		int size = 1 + lootingLevel + rand.nextInt(3);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.stained_hardened_clay, size > 4 ? 4 : size, meta));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeStone.soundName;
	}
}
