package com.golems.renders;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.golems.entity.GolemColorized;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * RenderGolem is the same as RenderIronGolem but with casting to GolemColorized instead of EntityIronGolem
 */
public class RenderColoredGolem extends RenderLiving 
{
	/** Golem's Model. */
    private final ModelGolem golemModel;
    
    private ResourceLocation texture;
    
    public RenderColoredGolem()
    {
        super(new ModelGolem(), 0.5F);
        this.golemModel = (ModelGolem)this.mainModel;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(GolemColorized golem, double x, double y, double z, float f0, float f1)
    {
    	long color = golem.getColor();
    	if((color & -67108864) == 0) 
    	{
    	    color |= -16777216;
    	}

    	float colorRed = (float) (color >> 16 & 255) / 255.0F;
    	float colorGreen = (float) (color >> 8 & 255) / 255.0F;
    	float colorBlue = (float) (color & 255) / 255.0F;
    	float colorAlpha = (float) (color >> 24 & 255) / 255.0F;
    	
    	GL11.glPushMatrix();
    	// enable transparency if needed
    	   	
    	// debug:
    	//if(golem.ticksExisted % 20 == 0) System.out.print("golem.getColor() returns " + golem.getColor() + "; colorRed=" + colorRed + "; colorGreen=" + colorGreen + "; colorBlue=" + colorBlue + "\n");
    	
    	// render first pass of golem texture (usually eyes and other opaque, pre-colored features)
    	if(golem.hasBase())
    	{
	    	this.texture = golem.getTextureBase();
	    	if(this.texture != null)
	    	{
	    		super.doRender((EntityLiving)golem, x, y, z, f0, f1);
	    	}
    	}

    	// prepare to render the complicated layer
    	GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
    	if(golem.hasTransparency())
    	{
    		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
    		GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	}
    		
    	// render second pass of golem texture, enabling colorizing and transparency if specified
    	this.texture = golem.getTextureToColor();
    	if(this.texture != null)
    	{
    		super.doRender((EntityLiving)golem, x, y, z, f0, f1);
    	}
        
    	// return GL11 settings to normal
    	if(golem.hasTransparency())
    	{
    		GL11.glPopAttrib();
    		GL11.glDisable(GL11.GL_NORMALIZE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	}
        GL11.glPopMatrix();      
    }

    protected void rotateCorpse(GolemColorized p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);

        if ((double)p_77043_1_.limbSwingAmount >= 0.01D)
        {
            float f3 = 13.0F;
            float f4 = p_77043_1_.limbSwing - p_77043_1_.limbSwingAmount * (1.0F - p_77043_4_) + 6.0F;
            float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
            GL11.glRotatef(6.5F * f5, 0.0F, 0.0F, 1.0F);
        }
    }

    protected void renderEquippedItems(GolemColorized p_77029_1_, float p_77029_2_)
    {
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((GolemColorized)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
    {
        this.renderEquippedItems((GolemColorized)p_77029_1_, p_77029_2_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        this.rotateCorpse((GolemColorized)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((GolemColorized)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
    	GolemColorized thisentity = (GolemColorized) entity;
        return this.texture != null ? this.texture : thisentity.getTextureBase();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((GolemColorized)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
