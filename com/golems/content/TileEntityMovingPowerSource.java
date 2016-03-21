package com.golems.content;

import java.util.List;

import com.golems.entity.EntityRedstoneGolem;
import com.golems.entity.GolemBase;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMovingPowerSource extends TileEntity
{    
	private AxisAlignedBB aabb = (AxisAlignedBB)null;
    public TileEntityMovingPowerSource() { }

    @Override
    public void updateEntity()
    {
    	List entityList = worldObj.getEntitiesWithinAABB(GolemBase.class, this.getAABBToCheck(this.xCoord, this.yCoord, this.zCoord));
    	boolean foundGolem = false;
    	// check each entity in the list looking for a glowable golem
        for (int i = 0, j = entityList.size(); i < j; ++i)
        {
            Entity entity1 = (Entity)entityList.get(i);
            if (entity1 instanceof EntityRedstoneGolem)
            {
                foundGolem = true;
				break;
            }
        }
        // if no golem was found, delete this tile entity and its block
    	if(!foundGolem)
        {
            if(worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockPowerProvider)
            {
            	worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
                worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    } 
    
    private AxisAlignedBB getAABBToCheck(int x, int y, int z)
    {
    	if(this.aabb == (AxisAlignedBB)null)
    	{
    		this.aabb = AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)x + 1D, (double)y + 1D, (double)z + 1D);
    	}
    	return this.aabb;	
    }
}

