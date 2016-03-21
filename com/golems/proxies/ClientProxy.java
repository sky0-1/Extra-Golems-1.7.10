package com.golems.proxies;

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
import com.golems.entity.GolemBase;
import com.golems.events.GolemClientEventHandler;
import com.golems.renders.RenderColoredGolem;
import com.golems.renders.RenderGolem;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy 
{	
	@Override
	public void registerRenders()
	{		
		register(EntityBedrockGolem.class);
		register(EntityBookshelfGolem.class);
		register(EntityClayGolem.class);
		register(EntityCoalGolem.class);
		register(EntityDiamondGolem.class);
		register(EntityEmeraldGolem.class);
		register(EntityEndstoneGolem.class);
		register(EntityGlassGolem.class);
		register(EntityGlowstoneGolem.class);
		register(EntityGoldGolem.class);
		register(EntityHardenedClayGolem.class);
		register(EntityIceGolem.class);
		register(EntityLapisGolem.class);
		registerColorized(EntityLeafGolem.class);
		register(EntityMelonGolem.class);
		register(EntityNetherBrickGolem.class);
		register(EntityObsidianGolem.class);
		register(EntityQuartzGolem.class);
		register(EntityRedstoneGolem.class);
		register(EntitySandstoneGolem.class);
		register(EntitySpongeGolem.class);
		registerColorized(EntityStainedClayGolem.class);
		registerColorized(EntityStainedGlassGolem.class);
		register(EntityStrawGolem.class);
		register(EntityTNTGolem.class);
		register(EntityWoodenGolem.class);
		register(EntityWoolGolem.class);	
	}
	
	@Override
	public void registerEvents()
	{
		super.registerEvents();
		MinecraftForge.EVENT_BUS.register(new GolemClientEventHandler());
	}
	
	/**	Registers an entity with the RenderGolem rendering class */
	public static void register(Class<? extends GolemBase> golem)
	{
		RenderingRegistry.registerEntityRenderingHandler(golem, new RenderGolem());
	}
	
	public static void registerColorized(Class<? extends GolemBase> golem)
	{
		RenderingRegistry.registerEntityRenderingHandler(golem, new RenderColoredGolem());
	}
}
