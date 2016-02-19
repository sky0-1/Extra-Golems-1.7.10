package com.golems.events;

import com.golems.entity.EntityBookshelfGolem;
import com.golems.entity.EntityClayGolem;
import com.golems.entity.EntityCoalGolem;
import com.golems.entity.EntityDiamondGolem;
import com.golems.entity.EntityEmeraldGolem;
import com.golems.entity.EntityEndstoneGolem;
import com.golems.entity.EntityGlassGolem;
import com.golems.entity.EntityGlowstoneGolem;
import com.golems.entity.EntityGoldGolem;
import com.golems.entity.EntityHardenedClayGolem;
import com.golems.entity.EntityIceGolem;
import com.golems.entity.EntityLapisGolem;
import com.golems.entity.EntityLeafGolem;
import com.golems.entity.EntityMelonGolem;
import com.golems.entity.EntityNetherBrickGolem;
import com.golems.entity.EntityObsidianGolem;
import com.golems.entity.EntityQuartzGolem;
import com.golems.entity.EntitySandstoneGolem;
import com.golems.entity.EntitySpongeGolem;
import com.golems.entity.EntityStrawGolem;
import com.golems.entity.EntityTNTGolem;
import com.golems.entity.EntityWoodenGolem;
import com.golems.entity.EntityWoolGolem;
import com.golems.entity.GolemBase;
import com.golems.main.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** Handles events added specifically from this mod **/
public class GolemEventHandler 
{
	@SubscribeEvent
	public void onBuildGolem(GolemBuildEvent event)
	{
		// if it has not already been set
		if(event.isGolemNull())
		{
			if(event.blockBelow == Blocks.diamond_block)
			{
				event.setGolem(new EntityDiamondGolem(event.worldObj), Config.ALLOW_DIAMOND_GOLEM);
			}
			else if(event.blockBelow == Blocks.emerald_block)
			{
				event.setGolem(new EntityEmeraldGolem(event.worldObj), Config.ALLOW_EMERALD_GOLEM);	
			}
			else if(event.blockBelow == Blocks.gold_block)
			{
				event.setGolem(new EntityGoldGolem(event.worldObj), Config.ALLOW_GOLD_GOLEM);	
			}
			else if(event.blockBelow == Blocks.lapis_block)
			{
				event.setGolem(new EntityLapisGolem(event.worldObj), Config.ALLOW_LAPIS_GOLEM);	
			}
			else if(event.blockBelow == Blocks.tnt)
			{
				event.setGolem(new EntityTNTGolem(event.worldObj), Config.ALLOW_TNT_GOLEM);	
			}
			else if(event.blockBelow == Blocks.sponge)
			{
				event.setGolem(new EntitySpongeGolem(event.worldObj), Config.ALLOW_SPONGE_GOLEM);
			}
			else if(event.blockBelow == Blocks.sandstone)
			{
				event.setGolem(new EntitySandstoneGolem(event.worldObj), Config.ALLOW_SANDSTONE_GOLEM);
			}
			else if(event.blockBelow == Blocks.wool)
			{
				event.setGolem(new EntityWoolGolem(event.worldObj), Config.ALLOW_WOOL_GOLEM);
			}
			else if(event.blockBelow == Blocks.hardened_clay)
			{
				event.setGolem(new EntityHardenedClayGolem(event.worldObj), Config.ALLOW_HARD_CLAY_GOLEM);
			}
			else if(event.blockBelow == Blocks.obsidian)
			{
				event.setGolem(new EntityObsidianGolem(event.worldObj), Config.ALLOW_OBSIDIAN_GOLEM);	
			}
			else if(event.blockBelow == Blocks.bookshelf)
			{
				event.setGolem(new EntityBookshelfGolem(event.worldObj), Config.ALLOW_BOOKSHELF_GOLEM);	
			}
			else if(event.blockBelow == Blocks.glass)
			{
				event.setGolem(new EntityGlassGolem(event.worldObj), Config.ALLOW_GLASS_GOLEM);
			}
			else if(event.blockBelow == Blocks.packed_ice)
			{
				event.setGolem(new EntityIceGolem(event.worldObj), Config.ALLOW_ICE_GOLEM);
			}
			else if(event.blockBelow == Blocks.log || event.blockBelow == Blocks.log2)
			{
				event.setGolem(new EntityWoodenGolem(event.worldObj), Config.ALLOW_WOODEN_GOLEM);
			}
			else if(event.blockBelow == Blocks.clay)
			{
				event.setGolem(new EntityClayGolem(event.worldObj), Config.ALLOW_CLAY_GOLEM);	
			}
			else if(event.blockBelow == Blocks.hay_block)
			{
				event.setGolem(new EntityStrawGolem(event.worldObj), Config.ALLOW_STRAW_GOLEM);	
			}
			else if(event.blockBelow == Blocks.nether_brick)
			{
				event.setGolem(new EntityNetherBrickGolem(event.worldObj), Config.ALLOW_NETHERBRICK_GOLEM);	
			}
			else if(event.blockBelow == Blocks.glowstone)
			{
				event.setGolem(new EntityGlowstoneGolem(event.worldObj), Config.ALLOW_GLOWSTONE_GOLEM);	
			}
			else if(event.blockBelow == Blocks.end_stone)
			{
				event.setGolem(new EntityEndstoneGolem(event.worldObj), Config.ALLOW_ENDSTONE_GOLEM);	
			}
			else if(event.blockBelow == Blocks.quartz_block)
			{
				event.setGolem(new EntityQuartzGolem(event.worldObj), Config.ALLOW_QUARTZ_GOLEM);	
			}
			else if(event.blockBelow == Blocks.coal_block)
			{
				event.setGolem(new EntityCoalGolem(event.worldObj), Config.ALLOW_COAL_GOLEM);
			}
			else if(event.blockBelow == Blocks.melon_block)
			{
				event.setGolem(new EntityMelonGolem(event.worldObj), Config.ALLOW_MELON_GOLEM);
			}
			else if(event.blockBelow instanceof BlockLeaves)
			{
				event.setGolem(new EntityLeafGolem(event.worldObj), Config.ALLOW_LEAF_GOLEM);
			}
		}
	}
}
