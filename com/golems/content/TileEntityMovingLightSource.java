package com.golems.content;

import java.util.List;

import com.golems.entity.GolemBase;
import com.golems.entity.GolemLightProvider;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMovingLightSource extends TileEntity
{    
    public TileEntityMovingLightSource() {}

    @Override
    public void updateEntity()
    {
    	List entityList = worldObj.getEntitiesWithinAABB(GolemLightProvider.class, this.getAABBToCheck(this.xCoord, this.yCoord, this.zCoord));
  
        // if no golem was found, delete this tile entity and its block
    	if(entityList.isEmpty())
        {
            if(worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockLightProvider)
            {
            	worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
                worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    } 
    
    private AxisAlignedBB getAABBToCheck(int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)x + 1D, (double)y + 1D, (double)z + 1D);
    }
}

