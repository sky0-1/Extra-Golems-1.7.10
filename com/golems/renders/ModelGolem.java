package com.golems.renders;

import com.golems.entity.GolemBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@SideOnly(Side.CLIENT)
public class ModelGolem extends ModelBase
{
    /** The head model for the golem. */
    public ModelRenderer golemHead;
    /** The body model for the golem. */
    public ModelRenderer golemBody;
    /** The right arm model for the golem. */
    public ModelRenderer golemRightArm;
    /** The left arm model for the golem. */
    public ModelRenderer golemLeftArm;
    /** The left leg model for the Golem. */
    public ModelRenderer golemLeftLeg;
    /** The right leg model for the Golem. */
    public ModelRenderer golemRightLeg;

    public ModelGolem()
    {
        this(0.0F);
    }

    public ModelGolem(float f)
    {
        this(f, -7.0F);
    }

    public ModelGolem(float f1, float addRotationY)
    {
        short width = 128;
        short height = 128;
        this.golemHead = (new ModelRenderer(this)).setTextureSize(width, height);
        this.golemHead.setRotationPoint(0.0F, 0.0F + addRotationY, -2.0F);
        this.golemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, f1);
        this.golemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, f1);
        this.golemBody = (new ModelRenderer(this)).setTextureSize(width, height);
        this.golemBody.setRotationPoint(0.0F, 0.0F + addRotationY, 0.0F);
        this.golemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, f1);
        this.golemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, f1 + 0.5F);
        this.golemRightArm = (new ModelRenderer(this)).setTextureSize(width, height);
        this.golemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.golemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, f1);
        this.golemLeftArm = (new ModelRenderer(this)).setTextureSize(width, height);
        this.golemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.golemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, f1);
        this.golemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(width, height);
        this.golemLeftLeg.setRotationPoint(-4.0F, 18.0F + addRotationY, 0.0F);
        this.golemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f1);
        this.golemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(width, height);
        this.golemRightLeg.mirror = true;
        this.golemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + addRotationY, 0.0F);
        this.golemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, f1);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float swingSuppress, float f1, float f2, float f3, float f4, float SCALE)
    {
        this.setRotationAngles(swingSuppress, f1, f2, f3, f4, SCALE, entity);
        this.golemHead.render(SCALE);
        this.golemBody.render(SCALE);
        this.golemLeftLeg.render(SCALE);
        this.golemRightLeg.render(SCALE);
        this.golemRightArm.render(SCALE);
        this.golemLeftArm.render(SCALE);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float swingSuppress, float motion, float f2, float lookingYawY, float lookingYawX, float f5, Entity entity)
    {
        this.golemHead.rotateAngleY = lookingYawY / (180F / (float)Math.PI);
        this.golemHead.rotateAngleX = lookingYawX / (180F / (float)Math.PI);
        this.golemLeftLeg.rotateAngleX = -1.5F * this.calculateRotation(swingSuppress, 13.0F) * motion;
        this.golemRightLeg.rotateAngleX = 1.5F * this.calculateRotation(swingSuppress, 13.0F) * motion;
        this.golemLeftLeg.rotateAngleY = 0.0F;
        this.golemRightLeg.rotateAngleY = 0.0F;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entity, float swingSuppress, float f2, float f3)
    {
        GolemBase entityirongolem = (GolemBase)entity;
        int i = entityirongolem.getAttackTimer();

        if (i > 0)
        {
            this.golemRightArm.rotateAngleX = -2.0F + 1.5F * this.calculateRotation((float)i - f3, 10.0F);
            this.golemLeftArm.rotateAngleX = -2.0F + 1.5F * this.calculateRotation((float)i - f3, 10.0F);
        }
        else
        {
            this.golemRightArm.rotateAngleX = (-0.2F + 1.5F * this.calculateRotation(swingSuppress, 13.0F)) * f2;
            this.golemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.calculateRotation(swingSuppress, 13.0F)) * f2;
        }
    }

    private float calculateRotation(float f1, float f2)
    {
        return (Math.abs(f1 % f2 - f2 * 0.5F) - f2 * 0.25F) / (f2 * 0.25F);
    }
}
    