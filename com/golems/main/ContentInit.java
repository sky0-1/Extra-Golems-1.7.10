package com.golems.main;

import com.golems.content.BlockGolemHead;
import com.golems.content.BlockLightProvider;
import com.golems.content.BlockPowerProvider;
import com.golems.content.ItemBedrockGolem;
import com.golems.content.ItemGolemPaper;
import com.golems.content.TileEntityMovingLightSource;
import com.golems.content.TileEntityMovingPowerSource;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/** Initializes instances of all items AND blocks for this mod */
public class ContentInit 
{
	public static Item golemPaper;			// golem spell instance (crafting item only)
	public static Item spawnBedrockGolem; 	// creative-only item to create & destroy bedrock golems
	public static Block golemHead;			// golem head instance
	public static Block blockLightProviderFull;	// full light source for lightprovider golems
	public static Block blockLightProviderHalf;	// full light source for lightprovider golems
	public static Block blockPowerProviderFull; // full power source for redstone golem
	
	/** Initializes and registers an instance of this mods items and blocks */
	public static void mainRegistry()
	{		
		golemPaper = new ItemGolemPaper();
		spawnBedrockGolem = new ItemBedrockGolem();
		golemHead = new BlockGolemHead();
		blockLightProviderFull = new BlockLightProvider(1.0F).setBlockName("movinglightsource");
		blockLightProviderHalf = new BlockLightProvider(0.5F).setBlockName("movinglightsource2");
		blockPowerProviderFull = new BlockPowerProvider(Config.TWEAK_REDSTONE).setBlockName("movingpowerprovider");
				
		GameRegistry.registerTileEntity(TileEntityMovingLightSource.class, ExtraGolems.MODID + "_TileEntityMovingLightSource");
		GameRegistry.registerTileEntity(TileEntityMovingPowerSource.class, ExtraGolems.MODID + "_TileEntityMovingPowerSource");
		
		register(golemPaper);
		register(spawnBedrockGolem);
		register(golemHead);
		register(blockLightProviderFull);
		register(blockLightProviderHalf);
		register(blockPowerProviderFull);
	}
	
	private static void register(Item in)
	{
		GameRegistry.registerItem(in, in.getUnlocalizedName());
	}
	
	private static void register(Block in)
	{
		GameRegistry.registerBlock(in, in.getUnlocalizedName());
	}
}
