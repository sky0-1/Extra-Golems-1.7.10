package com.golems.content;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPowerProvider extends Block implements ITileEntityProvider
{
	protected final int power;
	
    public BlockPowerProvider(int powerLevel)
    {
        super(Material.air);
        this.power = powerLevel;
        this.setHardness(-1.0F).setTickRandomly(false);
        this.setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z)
    {
        return true;
    }

    @Override
    public int onBlockPlaced(World worldIn, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return meta;
    }

    @Override
    public void onBlockAdded(World worldIn, int x, int y, int z)
    {
        return;
    }
    
    /** Coordinates passed are for this block */
    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighborBlock)
    {
        return;
    }

    @Override
    public void onFallenUpon(World worldIn, int x, int y, int z, Entity entityIn, float fallDistance)
    {
        return;
    }
    
    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
    {
        return this.power;
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int par5) 
    {
        return this.power;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMovingPowerSource();
    }
}