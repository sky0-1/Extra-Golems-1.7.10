package com.golems.entity;

import java.awt.Color;

import com.golems.main.ContentInit;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class GolemColorized extends GolemBase
{
	protected long color;
	protected ResourceLocation base;
	protected ResourceLocation overlay;
	protected boolean hasBase;
	
	public GolemColorized(World world, float attack, Block pickBlock, long initial, ResourceLocation rBase, ResourceLocation rOverlay)
	{
		super(world, attack, pickBlock);
		this.color = initial;
		this.base = rBase;
		this.overlay = rOverlay;
		this.hasBase = this.base != null;
	}
	
	public GolemColorized(World world, float attack, long initial, ResourceLocation rBase, ResourceLocation rOverlay) 
	{
		this(world, attack, ContentInit.golemHead, initial, rBase, rOverlay);
	}
	
	@Override
	protected void applyTexture() {}
	
	/** An optional texture to render as-is, without coloring **/
	public ResourceLocation getTextureBase()
	{
		return base;
	}
	
	/** The texture that will be colored **/
	public ResourceLocation getTextureToColor()
	{
		return overlay;
	}
	
	/** Whether this golem has a sub-texture that should not be colored **/
	public boolean hasBase()
	{
		return this.hasBase;
	}
	
	public void setColor(long toSet)
	{
		this.color = toSet;
	}
	
	public long getColor()
	{
		return this.color;
	}
    
    /**
     * Whether {@link overlay} should be rendered as transparent.
     * This is not called for rendering {@link base},
     * only for rendering the colorized layer.
     **/
    @SideOnly(Side.CLIENT)
    public boolean hasTransparency()
    {
    	return false;
    }
}
