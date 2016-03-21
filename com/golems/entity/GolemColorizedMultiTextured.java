package com.golems.entity;

import com.golems.entity.GolemBase;
import com.golems.entity.GolemColorized;
import com.golems.main.Config;
import com.golems.main.ContentInit;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class GolemColorizedMultiTextured extends GolemColorized 
{
	private static final int DATA_WATCHER_TEXTURE = 13;
	private static final String NBT_TEXTURE = "GolemTextureData";
	protected final int[] colors;

	/**
	 * Flexible constructor so child classes can "borrow" this class's behavior and customize.
	 * It is fine to pass 'null' for {@link BASE} or {@link OVERLAY}, and null textures will not be rendered.
	 * @param BASE an optional texture that will not be recolored or rendered transparent, to render before {@link OVERLAY}
	 * @param OVERLAY a texture that will be recolored and optionally rendered as transparent.
	 * @param lColors an int[] of color values to use for rendering -- interacting with this golem will go to the next color
	 **/
	public GolemColorizedMultiTextured(World world, float damage, Block pick, final ResourceLocation BASE, final ResourceLocation OVERLAY, int[] lColors)
	{
		super(world, damage, pick, 0L, BASE, OVERLAY);
		colors = lColors;
	}
	
	/**
	 * Flexible constructor so child classes can "borrow" this class's behavior and customize.
	 * It is fine to pass 'null' for {@link BASE} or {@link OVERLAY}, and null textures will not be rendered.
	 * @param BASE an optional texture that will not be recolored or rendered transparent, to render before {@link OVERLAY}
	 * @param OVERLAY a texture that will be recolored and optionally rendered as transparent.
	 * @param lColors an int[] of color values to use for rendering -- interacting with this golem will go to the next color
	 **/
	public GolemColorizedMultiTextured(World world, float damage, final ResourceLocation BASE, final ResourceLocation OVERLAY, int[] lColors)
	{
		this(world, damage, ContentInit.golemHead, BASE, OVERLAY, lColors);
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.getDataWatcher().addObject(DATA_WATCHER_TEXTURE, new Byte((byte)0));
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if(this.ticksExisted == 2)
		{
			this.setColor(this.colors[this.getTextureNum()]);
		}
	}
	
	@Override
	public boolean interact(EntityPlayer player)
	{
		// only change texture when player has empty hand
		if(player.getHeldItem() != null)
		{
			return super.interact(player);
		}
		else
		{
			this.setTextureNum((byte)((this.getTextureNum() + 1) % this.colors.length));
			this.setColor(this.colors[this.getTextureNum()]);
			player.swingItem();
			// debug:
			//System.out.println("texture num is now " + this.getTextureNum() + "; color is " + this.getColor());
			return true;
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
    {
		super.writeEntityToNBT(nbt);
		nbt.setByte(NBT_TEXTURE, (byte)this.getTextureNum());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setTextureNum(nbt.getByte(NBT_TEXTURE));
	}
	
	@Override
	public boolean doesInteractChangeTexture()
	{
		return true;
	}
	
	public void setTextureNum(byte toSet)
	{
		this.dataWatcher.updateObject(DATA_WATCHER_TEXTURE, new Byte(toSet));
	}

	public int getTextureNum() 
	{
		return (int)this.dataWatcher.getWatchableObjectByte(DATA_WATCHER_TEXTURE);
	}
}
