package com.golems.events;

import com.golems.entity.GolemBase;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GolemBuildEvent extends Event 
{
	/** The world in which theGolem was built **/
	public final World worldObj;
	/** The X,Y,Z coordinates of the Golem Head block **/
	public final int headX, headY, headZ;
	/** The Block type being used to build the golem **/
	public final Block blockBelow;
	/** The metadata of blockBelow **/
	public final int blockMeta;
	/** Whether the golem's arms are aligned on the x-axis **/
	public final boolean isXAligned;
	/** Whether all 4 construction blocks are identical metadata **/
	public final boolean areBlocksSameMeta;

	/** The GolemBase to spawn when the event is finished **/
	private GolemBase theGolem;
	/** Whether theGolem is not allowed to be spawned **/
	private boolean isGolemBanned;

	public GolemBuildEvent(World world, final int x, final int y, final int z)
	{
		this.worldObj = world;
		this.headX = x;
		this.headY = y;
		this.headZ = z;
		this.blockBelow = world.getBlock(x, y - 1, z);
		this.blockMeta = world.getBlockMetadata(x, y - 1, z);
		this.isXAligned = world.getBlock(x - 1, y - 1, z) == this.blockBelow && this.worldObj.getBlock(x + 1, y - 1, z) == this.blockBelow;
		this.areBlocksSameMeta = this.areGolemBlocksSameMeta();
		this.theGolem = (GolemBase)null;
		this.isGolemBanned = false;
	}

	/** Assign this event a new GolemBase to spawn and its spawn permission **/
	public void setGolem(GolemBase golem, boolean isAllowedByConfig)
	{
		this.theGolem = golem;
		this.isGolemBanned = !isAllowedByConfig;
	}

	/** Assign this event a new GolemBase to spawn **/
	public void setGolem(GolemBase golem)
	{
		this.setGolem(golem, true);
	}

	public void setIsGolemBanned(boolean toSet)
	{
		this.isGolemBanned = toSet;
	}

	/** @return the GolemBase to spawn (may be null) **/
	public GolemBase getGolem()
	{
		return this.theGolem;
	}

	/** @return true if theGolem has not been initialized **/
	public boolean isGolemNull()
	{
		return this.theGolem == (GolemBase)null;
	}

	/** @return true if theGolem should not be spawned (even if it is not null) **/
	public boolean isGolemBanned()
	{
		return this.isGolemBanned;
	}
	
	/** @return true if all 4 construction blocks have the same metadata **/
	protected boolean areGolemBlocksSameMeta() 
	{
		int x = this.headX;
		int y = this.headY;
		int z = this.headZ;
		int metaBelow2 = this.worldObj.getBlockMetadata(x, y - 2, z);	
		int metaArm1 = this.isXAligned ? this.worldObj.getBlockMetadata(x - 1, y - 1, z) : this.worldObj.getBlockMetadata(x, y - 1, z - 1);
		int metaArm2 = this.isXAligned ? this.worldObj.getBlockMetadata(x + 1, y - 1, z) : this.worldObj.getBlockMetadata(x, y - 1, z + 1);
		
		return this.blockMeta == metaBelow2 && metaBelow2 == metaArm1 && metaArm1 == metaArm2;
	}
}
