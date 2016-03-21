package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityStainedGlassGolem extends GolemColorizedMultiTextured
{
	private static final ResourceLocation TEXTURE_BASE = GolemBase.getGolemTexture("stained_glass");
	private static final ResourceLocation TEXTURE_OVERLAY = GolemBase.getGolemTexture("stained_glass_grayscale");
	private static final int[] dyeColors = ItemDye.field_150922_c;
	
	public EntityStainedGlassGolem(World world)
	{
		super(world, 12.0F, Blocks.stained_glass, TEXTURE_BASE, TEXTURE_OVERLAY, dyeColors);
		this.setCanTakeFallDamage(true);
	}
	
	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(9.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30D);
	}
	
	/**
     * Whether {@link overlay} should be rendered as transparent.
     * Is not called for rendering {@link base}
     **/
    @SideOnly(Side.CLIENT)
    public boolean hasTransparency()
    {
    	return true;
    }
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int meta = Config.TWEAK_STAINED_GLASS < 0 ? 15 - this.getTextureNum() : Config.TWEAK_STAINED_GLASS;
		int size = lootingLevel + rand.nextInt(3 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.stained_glass, size > 4 ? 4 : size, meta));
	}

	@Override
	public String getGolemSound() 
	{
		return Block.soundTypeGlass.soundName;
	}
}
