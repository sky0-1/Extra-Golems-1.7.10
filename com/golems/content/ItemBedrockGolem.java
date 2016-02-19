package com.golems.content;

import java.util.List;

import com.golems.entity.EntityBedrockGolem;
import com.golems.entity.GolemBase;
import com.golems.main.Config;
import com.golems.main.ExtraGolems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBedrockGolem extends Item 
{
	public ItemBedrockGolem()
	{
		this.setUnlocalizedName("spawn_bedrock_golem").setTextureName(ExtraGolems.MODID + ":spawn_bedrock").setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World worldIn, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		// creative players can use this item to spawn a bedrock golem
		if(Config.ALLOW_BEDROCK_GOLEM && !worldIn.isRemote && player.capabilities.isCreativeMode) 
		{
			Block block = worldIn.getBlock(x, y, z);
			GolemBase golem = new EntityBedrockGolem(worldIn);

			if (block == Blocks.snow_layer && (worldIn.getBlockMetadata(x, y, z) & 7) < 1)
			{
				side = 1;
			}
			else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(worldIn, x, y, z))
			{
				switch(side)
				{
				case 0: --y;
				break;
				case 1: ++y;
				break;
				case 2: --z;
				break;
				case 3: ++z;
				break;
				case 4: --x;
				break;
				case 5: ++x;
				break;
				}
			}

			golem.setPlayerCreated(true);
			golem.setLocationAndAngles((double)x + 0.5D, (double)y + 0.05D, (double)z + 0.5D, 0.0F, 0.0F);
			worldIn.spawnEntityInWorld(golem);

			return true;
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		String loreCreativeOnly = new ChatComponentTranslation(EnumChatFormatting.RED + trans("tooltip.creative_only_item")).getFormattedText(); 
		par3List.add(loreCreativeOnly);

		if(GuiScreen.isShiftKeyDown())
		{
			par3List.add(StatCollector.translateToLocalFormatted("tooltip.use_to_spawn", new Object[] {trans("entity.golems.golem_bedrock.name")}));
			par3List.add(StatCollector.translateToLocalFormatted("tooltip.use_on_existing", new Object[] {trans("entity.golems.golem_bedrock.name")}));
			par3List.add(trans("tooltip.to_remove_it") + ".");
		}
		else
		{	
			String lorePressShift =
			new ChatComponentTranslation(EnumChatFormatting.GRAY + trans("tooltip.press") + " ").getFormattedText() + 
			new ChatComponentTranslation(EnumChatFormatting.YELLOW + trans("tooltip.shift") + " ").getFormattedText() + 
			new ChatComponentTranslation(EnumChatFormatting.GRAY + trans("tooltip.for_more_details")).getFormattedText();
			par3List.add(lorePressShift);
		}
	}
	
	private String trans(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
