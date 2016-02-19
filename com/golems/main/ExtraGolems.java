package com.golems.main;

import com.golems.events.GolemEventHandler;
import com.golems.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = ExtraGolems.MODID, name = ExtraGolems.NAME, version = ExtraGolems.VERSION, acceptedMinecraftVersions = ExtraGolems.MCVERSION)
public class ExtraGolems 
{
	public static final String MODID = "golems";
	public static final String NAME = "Extra Golems";
	public static final String VERSION = "1.19";
	public static final String MCVERSION = "1.7.10";
	public static final String CLIENT = "com." + MODID + ".proxies.ClientProxy";
	public static final String SERVER = "com." + MODID + ".proxies.CommonProxy";
	
	@Mod.Instance(ExtraGolems.MODID)
	public static ExtraGolems instance;
	
	@SidedProxy(clientSide = ExtraGolems.CLIENT, serverSide = ExtraGolems.SERVER)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) 
	{	
		Config.mainRegistry(new Configuration(event.getSuggestedConfigurationFile()));
		ContentInit.mainRegistry();			// registers items AND blocks
		GolemEntityRegister.mainRegistry();	// registers entities with their IDs
		registerRecipes();
		proxy.registerRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) 
	{
		proxy.registerEvents();
	}
	
	/** Registers the 2 crafting recipes for this mod :) **/
	private static void registerRecipes() 
	{
		//shapeless
		GameRegistry.addShapelessRecipe(new ItemStack(ContentInit.golemPaper, 1), Items.feather,Items.redstone,new ItemStack(Items.dye, 1, 0),Items.paper);
		GameRegistry.addShapelessRecipe(new ItemStack(ContentInit.golemHead, 1), ContentInit.golemPaper,Blocks.pumpkin);
	}	
}