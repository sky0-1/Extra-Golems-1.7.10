package com.golems.main;

import net.minecraftforge.common.config.Configuration;

/** Registers the config settings to adjust aspects of this mod **/
public class Config 
{
	public static boolean ALLOW_BEDROCK_GOLEM;
	public static boolean ALLOW_BOOKSHELF_GOLEM;
	public static boolean ALLOW_CLAY_GOLEM;
	public static boolean ALLOW_COAL_GOLEM;
	public static boolean ALLOW_DIAMOND_GOLEM;
	public static boolean ALLOW_EMERALD_GOLEM;
	public static boolean ALLOW_ENDSTONE_GOLEM;
	public static boolean ALLOW_GLASS_GOLEM;
	public static boolean ALLOW_GLOWSTONE_GOLEM;
	public static boolean ALLOW_GOLD_GOLEM;
	public static boolean ALLOW_HARD_CLAY_GOLEM;
	public static boolean ALLOW_ICE_GOLEM;
	public static boolean ALLOW_LAPIS_GOLEM;
	public static boolean ALLOW_LEAF_GOLEM;
	public static boolean ALLOW_MELON_GOLEM;
	public static boolean ALLOW_NETHERBRICK_GOLEM;
	public static boolean ALLOW_OBSIDIAN_GOLEM;
	public static boolean ALLOW_QUARTZ_GOLEM;
	public static boolean ALLOW_REDSTONE_GOLEM;
	public static boolean ALLOW_SANDSTONE_GOLEM;
	public static boolean ALLOW_STAINED_CLAY_GOLEM;
	public static boolean ALLOW_STAINED_GLASS_GOLEM;
	public static boolean ALLOW_SPONGE_GOLEM;
	public static boolean ALLOW_STRAW_GOLEM;
	public static boolean ALLOW_TNT_GOLEM;
	public static boolean ALLOW_WOODEN_GOLEM;
	public static boolean ALLOW_WOOL_GOLEM;
	
	public static boolean ALLOW_BOOKSHELF_SPECIAL;
	public static boolean ALLOW_COAL_SPECIAL;
	public static boolean ALLOW_ENDSTONE_SPECIAL;
	public static boolean ALLOW_ICE_SPECIAL;
	public static boolean ALLOW_LAPIS_SPECIAL;
	public static boolean ALLOW_LEAF_SPECIAL;	
	public static boolean ALLOW_MELON_SPECIAL;
	public static boolean ALLOW_NETHERBRICK_SPECIAL_FIRE;
	public static boolean ALLOW_NETHERBRICK_SPECIAL_LAVA;
	public static boolean ALLOW_REDSTONE_SPECIAL;
	public static boolean ALLOW_SPONGE_SPECIAL;
	public static boolean ALLOW_TNT_SPECIAL;
	
	public static boolean CAN_USE_REGULAR_ICE;
	
	public static int TWEAK_NETHERBRICK;
	public static int TWEAK_MELON;
	public static int TWEAK_REDSTONE;
	public static int TWEAK_SPONGE_INTERVAL;
	public static int TWEAK_SPONGE;
	public static int TWEAK_STAINED_GLASS;
	public static int TWEAK_STAINED_CLAY;

	private static final String CATEGORY_SPAWNS = "spawns";
	private static final String CATEGORY_ABILITY = "abilities";
	private static final String CATEGORY_TWEAKS = "tweaks";
	
	public static void mainRegistry(Configuration config)
	{
		config.load();
		
		ALLOW_BEDROCK_GOLEM = config.getBoolean("Allow Bedrock Golem", CATEGORY_SPAWNS, true,
				"Whether the Bedrock Golem can be spawned in using the item.");
		ALLOW_BOOKSHELF_GOLEM = config.getBoolean("Allow Bookshelf Golem", CATEGORY_SPAWNS, true,
				"Whether the Librarian Golem can be built.");
		ALLOW_CLAY_GOLEM = config.getBoolean("Allow Clay Golem", CATEGORY_SPAWNS, true,
				"Whether the Clay Golem can be built.");
		ALLOW_COAL_GOLEM = config.getBoolean("Allow Coal Golem", CATEGORY_SPAWNS, true,
				"Whether the Coal Golem can be built.");
		ALLOW_DIAMOND_GOLEM = config.getBoolean("Allow Diamond Golem", CATEGORY_SPAWNS, true,
				"Whether the Diamond Golem can be built.");
		ALLOW_EMERALD_GOLEM = config.getBoolean("Allow Emerald Golem", CATEGORY_SPAWNS, true,
				"Whether the Emerald Golem can be built.");
		ALLOW_ENDSTONE_GOLEM = config.getBoolean("Allow Endstone Golem", CATEGORY_SPAWNS, true,
				"Whether the Endstone Golem can be built.");
		ALLOW_GLASS_GOLEM = config.getBoolean("Allow Glass Golem", CATEGORY_SPAWNS, true,
				"Whether the Glass Golem can be built.");
		ALLOW_GLOWSTONE_GOLEM = config.getBoolean("Allow Glowstone Golem", CATEGORY_SPAWNS, true,
				"Whether the Glowstone Golem can be built.");
		ALLOW_GOLD_GOLEM = config.getBoolean("Allow Gold Golem", CATEGORY_SPAWNS, true,
				"Whether the Gold Golem can be built.");
		ALLOW_HARD_CLAY_GOLEM = config.getBoolean("Allow Hardended Clay Golem", CATEGORY_SPAWNS, true,
				"Whether the Hardended Clay Golem can be built.");
		ALLOW_ICE_GOLEM = config.getBoolean("Allow Ice Golem", CATEGORY_SPAWNS, true,
				"Whether the Ice Golem can be built.");
		ALLOW_LAPIS_GOLEM = config.getBoolean("Allow Lapis Golem", CATEGORY_SPAWNS, true,
				"Whether the Lapis Lazuli Golem can be built.");
		ALLOW_LEAF_GOLEM = config.getBoolean("Allow Leaf Golem", CATEGORY_SPAWNS, true,
				"Whether the Leaf Golem can be built.");
		ALLOW_MELON_GOLEM = config.getBoolean("Allow Melon Golem", CATEGORY_SPAWNS, true,
				"Whether the Melon Golem can be built.");
		ALLOW_NETHERBRICK_GOLEM = config.getBoolean("Allow NetherBrick Golem", CATEGORY_SPAWNS, true,
				"Whether the Nether Brick Golem can be built.");
		ALLOW_OBSIDIAN_GOLEM = config.getBoolean("Allow Obsidian Golem", CATEGORY_SPAWNS, true,
				"Whether the Obsidian Golem can be built.");
		ALLOW_QUARTZ_GOLEM = config.getBoolean("Allow Quartz Golem", CATEGORY_SPAWNS, true,
				"Whether the Quartz Golem can be built.");
		ALLOW_REDSTONE_GOLEM = config.getBoolean("Allow Redstone Golem", CATEGORY_SPAWNS, true,
				"Whether the Redstone Golem can be built.");
		ALLOW_SANDSTONE_GOLEM = config.getBoolean("Allow Sandstone Golem", CATEGORY_SPAWNS, true,
				"Whether the Sandstone Golem can be built.");
		ALLOW_STAINED_CLAY_GOLEM = config.getBoolean("Allow Stained Clay Golem", CATEGORY_SPAWNS, true,
				"Whether the Stained Clay Golem can be built.");
		ALLOW_STAINED_GLASS_GOLEM = config.getBoolean("Allow Stained Glass Golem", CATEGORY_SPAWNS, true,
				"Whether the Stained Glass Golem can be built.");
		ALLOW_SPONGE_GOLEM = config.getBoolean("Allow Sponge Golem", CATEGORY_SPAWNS, true,
				"Whether the Sponge Golem can be built.");
		ALLOW_STRAW_GOLEM = config.getBoolean("Allow Straw Golem", CATEGORY_SPAWNS, true,
				"Whether the Straw Golem can be built.");
		ALLOW_TNT_GOLEM = config.getBoolean("Allow TNT Golem", CATEGORY_SPAWNS, true,
				"Whether the TNT Golem can be built.");
		ALLOW_WOODEN_GOLEM = config.getBoolean("Allow Wooden Golem", CATEGORY_SPAWNS, true,
				"Whether the Wooden Golem can be built.");
		ALLOW_WOOL_GOLEM = config.getBoolean("Allow Wool Golem", CATEGORY_SPAWNS, true,
				"Whether the Wool Golem can be built.");
		CAN_USE_REGULAR_ICE = config.getBoolean("Can use regular Ice", CATEGORY_SPAWNS, true,
				"When true, the Ice Golem can be built with regular ice as well as packed ice");
		
		/////////////////////////////////////////////////////////////////////////////////////
		
		ALLOW_BOOKSHELF_SPECIAL = config.getBoolean("Allow Bookshelf Special", CATEGORY_ABILITY, true, 
				"Whether the Bookshelf Golem can give itself potion effects");		
		ALLOW_COAL_SPECIAL = config.getBoolean("Allow Coal Golem Special", CATEGORY_ABILITY, false, 
				"Whether the Coal Golem can give temporary blindness effect");	
		ALLOW_ENDSTONE_SPECIAL = config.getBoolean("Allow Endstone Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Endstone Golem can teleport");
		ALLOW_ICE_SPECIAL = config.getBoolean("Allow Ice Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Ice Golem can freeze water and cool lava nearby");
		ALLOW_LAPIS_SPECIAL = config.getBoolean("Allow Lapis Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Lapis Golem can give enemies potion effects");
		ALLOW_LEAF_SPECIAL = config.getBoolean("Allow Leaf Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Leaf Golem can give itself Regeneration I");	
		ALLOW_MELON_SPECIAL = config.getBoolean("Allow Melon Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Melon Golem can occasionally plant flowers");
		ALLOW_NETHERBRICK_SPECIAL_FIRE = config.getBoolean("Allow NetherBrick Golem Fire Special", CATEGORY_ABILITY, true, 
				"Whether the NetherBrick Golem can light enemies on fire");
		ALLOW_NETHERBRICK_SPECIAL_LAVA = config.getBoolean("Allow NetherBrick Golem Lava Special", CATEGORY_ABILITY, true, 
				"Whether the NetherBrick Golem can melt cobblestone when in place for too long");
		ALLOW_REDSTONE_SPECIAL = config.getBoolean("Allow Redstone Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Redstone Golem can power nearby blocks");
		ALLOW_SPONGE_SPECIAL = config.getBoolean("Allow Sponge Golem Special", CATEGORY_ABILITY, true, 
				"Whether the Sponge Golem can soak up water nearby");
		ALLOW_TNT_SPECIAL = config.getBoolean("Allow TNT Golem Special", CATEGORY_ABILITY, true, 
				"Whether the TNT Golem can explode (randomly or upon death)");
		
		/////////////////////////////////////////////////////////////////////////////////////
		
		TWEAK_MELON = config.getInt("Melon Golem Interval", CATEGORY_TWEAKS, 320, 1, 24000, 
				"Average number of ticks between uses of Melon Golem special. (16 sec * 20 t/sec = 400 t)");
		TWEAK_NETHERBRICK = config.getInt("Netherbrick Golem Interval", CATEGORY_TWEAKS, 300, 1, 240000, 
				"Number of ticks the NetherBrick Golem must stand on cobblestone to melt it. (15 sec * 20 t/sec = 300 t)");
		TWEAK_REDSTONE = config.getInt("Redstone Golem Power", CATEGORY_TWEAKS, 15, 1, 15, 
				"Redstone power level emitted by Redstone Golem");
		TWEAK_SPONGE_INTERVAL = config.getInt("Sponge Golem Interval", CATEGORY_TWEAKS, 2, 1, 24000, 
				"Number of ticks in between water-checks. Increase to reduce lag.");
		TWEAK_SPONGE = config.getInt("Sponge Golem Range", CATEGORY_TWEAKS, 2, 2, 8, 
				"Radial distance at which Sponge Golem can absorb water (Warning: larger values cause lag)");
		TWEAK_STAINED_CLAY = config.getInt("Stained Clay Golem drop metadata", CATEGORY_TWEAKS, -1, -1, 15, 
				"The metadata of stained clay dropped by Stained Clay golems. Set to -1 to let it be based on current texture.");
		TWEAK_STAINED_GLASS = config.getInt("Stained Glass Golem drop metadata", CATEGORY_TWEAKS, -1, -1, 15, 
				"The metadata of stained glass dropped by Stained Glass golems. Set to -1 to let it be based on current texture.");

		config.save();
	}
}
