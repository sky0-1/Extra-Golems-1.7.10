package com.golems.content;

import com.golems.entity.GolemBase;
import com.golems.events.GolemBuildEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockGolemHead extends BlockDirectional
{
	public BlockGolemHead() 
	{
		super(Material.ground);
		this.setBlockName("golem_head").setCreativeTab(CreativeTabs.tabMisc).setBlockTextureName("pumpkin_side").setStepSound(soundTypeWood);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.pumpkin.getIcon(side, meta);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world_, int x, int y, int z, EntityLivingBase player, ItemStack stack)
	{
		int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		world_.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		if(y > 2 && y < 256)
		{
			this.activate(world, x, y, z);
		}
	}

	public void activate(World world, int x, int y, int z)
	{
		Block blockBelow1 = world.getBlock(x, y - 1, z);
		Block blockBelow2 = world.getBlock(x, y - 2, z);

		if(blockBelow1 == blockBelow2)
		{
			boolean alignedX = world.getBlock(x - 1, y - 1, z) == blockBelow1 && world.getBlock(x + 1, y - 1, z) == blockBelow1;
			boolean alignedZ = world.getBlock(x, y - 1, z - 1) == blockBelow1 && world.getBlock(x, y - 1, z + 1) == blockBelow1;
			int meta = world.getBlockMetadata(x, y - 1, z);

			// hard-coded support for Snow Golem
			if(blockBelow1 == Blocks.snow)
			{
				if(!world.isRemote)
				{
					removeGolemBlocks(world, x, y, z, false, false);
					EntitySnowman entitysnowman = new EntitySnowman(world);
					entitysnowman.setLocationAndAngles((double)x + 0.5D, (double)y - 1.95D, (double)z + 0.5D, 0.0F, 0.0F);
					System.out.print("[Extra Golems]: Building regular boring Snow Golem\n");
					world.spawnEntityInWorld(entitysnowman);
				}
				spawnParticles(world, x, y - 2, z);
			}

			// hard-coded support for Iron Golem
			if(blockBelow1 == Blocks.iron_block && (alignedX || alignedZ))
			{
				if(!world.isRemote)
				{
					removeGolemBlocks(world, x, y, z, alignedX);
					// spawn the golem
					EntityIronGolem golem = new EntityIronGolem(world);
					System.out.print("[Extra Golems]: Building regular boring Iron Golem\n");
					golem.setPlayerCreated(true);
					golem.setLocationAndAngles((double)x + 0.5D, (double)y - 1.95D, (double)z + 0.5D, 0.0F, 0.0F);
					world.spawnEntityInWorld(golem);
				}
				spawnParticles(world, x, y - 2, z);
			}

			if(alignedX || alignedZ)
			{
				if(!world.isRemote)
				{
					GolemBuildEvent event = new GolemBuildEvent(world, x, y, z);
					MinecraftForge.EVENT_BUS.post(event);
					if(event.isGolemNull() || event.isGolemBanned())
					{
						return;
					}

					removeGolemBlocks(world, x, y, z, alignedX);

					// spawn the golem
					GolemBase golem = event.getGolem();
					System.out.print("[Extra Golems]: Building golem " + golem.toString() + "\n");
					golem.setPlayerCreated(true);
					golem.setLocationAndAngles((double)x + 0.5D, (double)y - 1.95D, (double)z + 0.5D, 0.0F, 0.0F);
					world.spawnEntityInWorld(golem);
				}

				spawnParticles(world, x, y - 2, z);
			}
		}
	}

	protected void spawnParticles(World world, int x, int y, int z)
	{
		if(world.isRemote)
		{
			for(int l = 0;l < 80; ++l)
			{
				world.spawnParticle("snowballpoof", (double)x + world.rand.nextDouble(), (double)y + world.rand.nextDouble() * 3.9D, (double)z + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public static void removeGolemBlocks(World world, int headX, int headY, int headZ, boolean isXAligned)
	{
		removeGolemBlocks(world, headX, headY, headZ, isXAligned, true);
	}

	public static void removeGolemBlocks(World world, int headX, int headY, int headZ, boolean isXAligned, boolean removeArms)
	{
		// clear the area where the golem blocks and head were
		world.setBlockToAir(headX, headY, headZ);
		world.setBlockToAir(headX, headY - 1, headZ);
		world.setBlockToAir(headX, headY - 2, headZ);
		if(removeArms)
		{
			if(isXAligned)
			{
				world.setBlockToAir(headX - 1, headY - 1, headZ);
				world.setBlockToAir(headX + 1, headY - 1, headZ);
			}
			else
			{
				world.setBlockToAir(headX, headY - 1, headZ - 1);
				world.setBlockToAir(headX, headY - 1, headZ + 1);
			}
		}
	}
}