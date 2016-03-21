package com.golems.entity;

import com.golems.content.BlockLightProvider;
import com.golems.main.ContentInit;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class GolemLightProvider extends GolemBase 
{
	private EnumLightLevel lightLevel;
	
	public GolemLightProvider(World world, float attack, Block pick, EnumLightLevel light)
	{
		super(world, attack, pick);
		this.lightLevel = light;
	}
	
	public GolemLightProvider(World world, float attack, EnumLightLevel light)
	{
		this(world, attack, ContentInit.golemHead, light);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float f)
    {
        return 15728880;
    }

    /** Gets how bright this entity is **/
	@Override
    public float getBrightness(float f)
    {
        return 1.0F;
    }
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		// only do this every other tick -- reduces lag by 50%
		if(this.ticksExisted % 2 == 0)
		{
			placeLightNearby();
		}
	}

	/** Finds an air block nearby and replaces it with a BlockMovingLightSource **/
	public boolean placeLightNearby() 
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY - 0.20000000298023224D); // y-pos of block below golem
		int z = MathHelper.floor_double(this.posZ);
		int[][] validCoords = {{x,z},{x+1,z},{x-1,z},{x,z+1},{x,z-1},{x+1,z+1},{x-1,z+1},{x+1,z-1},{x-1,z-1}};
		// for each coordinate in the list
		for(int[] coord : validCoords)
		{
			int xPos = coord[0];
			int zPos = coord[1];
			// for each y
			for(int k = 0; k < 3; ++k)
			{	
				int yPos = y + k + 1;
				// if the block is air, make a light block. else if it's already a light block, return
				if(this.worldObj.getBlock(xPos, yPos, zPos) == Blocks.air)
				{
					return this.worldObj.setBlock(xPos, yPos, zPos, this.lightLevel.getLightBlock());
				}
				else if(this.worldObj.getBlock(xPos, yPos, zPos) instanceof BlockLightProvider) 
				{
					return false;
				}
			}
		}
		return false;
	}
	
	/** Allows the golem to emit different levels of light **/
	public static enum EnumLightLevel
	{
		HALF(0.5F),
		FULL(1.0F);
		
		private final float light;
		
		private EnumLightLevel(float brightness)
		{
			this.light = brightness;
		}
		
		public Block getLightBlock()
		{
			switch(this)
			{
			case FULL:	return ContentInit.blockLightProviderFull;
			case HALF:	return ContentInit.blockLightProviderHalf;
			default:	return Blocks.air;
			}
		}
		
		public float getBrightness()
		{
			return this.light;
		}
	}
}