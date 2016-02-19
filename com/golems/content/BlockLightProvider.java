package com.golems.content;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockLightProvider extends Block implements ITileEntityProvider
{
    public BlockLightProvider(float lightLevel)
    {
        super(Material.air);
        this.setHardness(-1.0F).setTickRandomly(false).setLightLevel(lightLevel);
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
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMovingLightSource();
    }
}