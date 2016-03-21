package com.golems.main;

import com.golems.entity.EntityBedrockGolem;
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
import com.golems.entity.EntityRedstoneGolem;
import com.golems.entity.EntitySandstoneGolem;
import com.golems.entity.EntitySpongeGolem;
import com.golems.entity.EntityStainedClayGolem;
import com.golems.entity.EntityStainedGlassGolem;
import com.golems.entity.EntityStrawGolem;
import com.golems.entity.EntityTNTGolem;
import com.golems.entity.EntityWoodenGolem;
import com.golems.entity.EntityWoolGolem;

import cpw.mods.fml.common.registry.EntityRegistry;

/** Registers all of the instances of GolemBase for this mod **/
public class GolemEntityRegister 
{
	private static int golemEntityID;

	public static void mainRegistry() 
	{
		registerEntities();
	}
	
	public static void registerEntities()
	{		
		register(EntityBedrockGolem.class, "golem_bedrock");
		register(EntityBookshelfGolem.class, "golem_bookshelf");
		register(EntityClayGolem.class, "golem_clay");
		register(EntityCoalGolem.class, "golem_coal");
		register(EntityDiamondGolem.class, "golem_diamond");
		register(EntityEmeraldGolem.class, "golem_emerald");
		register(EntityEndstoneGolem.class, "golem_end_stone");
		register(EntityGlassGolem.class, "golem_glass");
		register(EntityGlowstoneGolem.class, "golem_glowstone");
		register(EntityGoldGolem.class, "golem_gold");
		register(EntityHardenedClayGolem.class, "golem_hardened_clay");
		register(EntityIceGolem.class, "golem_ice");
		register(EntityLapisGolem.class, "golem_lapis");
		register(EntityLeafGolem.class, "golem_leaf");
		register(EntityMelonGolem.class, "golem_melon");
		register(EntityNetherBrickGolem.class, "golem_nether_brick");
		register(EntityObsidianGolem.class, "golem_obsidian");
		register(EntityQuartzGolem.class, "golem_quartz");
		register(EntityRedstoneGolem.class, "golem_redstone");
		register(EntitySandstoneGolem.class, "golem_sandstone");
		register(EntitySpongeGolem.class, "golem_sponge");
		register(EntityStainedClayGolem.class, "golem_stained_clay");
		register(EntityStainedGlassGolem.class, "golem_stained_glass");
		register(EntityStrawGolem.class, "golem_straw");
		register(EntityTNTGolem.class, "golem_tnt");
		register(EntityWoodenGolem.class, "golem_wooden");
		register(EntityWoolGolem.class, "golem_wool");
	}
	
	private static void register(Class entityClass, String name)
	{
		EntityRegistry.registerModEntity(entityClass, name, ++golemEntityID, ExtraGolems.instance, 48, 3, true);
	}
}
