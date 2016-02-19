package com.golems.content;

import java.util.List;

import com.golems.events.GolemPaperAddInfoEvent;
import com.golems.main.ExtraGolems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

public class ItemGolemPaper extends Item 
{	
	public ItemGolemPaper()
	{
		super();
		this.setUnlocalizedName("golem_paper").setTextureName(ExtraGolems.MODID + ":golem_paper").setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(GuiScreen.isShiftKeyDown())
		{
			String loreListIntro= new ChatComponentTranslation(EnumChatFormatting.WHITE + trans("tooltip.in_order_of_attack")).getFormattedText() + ":";
			par3List.add(loreListIntro);
			par3List.add(trans("tile.blockDiamond.name") + ", " + trans("tile.blockEmerald.name") + ",");
			par3List.add(trans("tile.obsidian.name") + ", " + trans("material.glass") + ", " + trans("tile.lightgem.name") + ", " + trans("tile.whiteStone.name") + ",");
			par3List.add(trans("tile.quartzBlock.default.name") + ", " + trans("tile.blockGold.name") + ",");
			par3List.add(trans("tile.icePacked.name") + ", " + trans("tile.netherBrick.name") + ", ");
			par3List.add(trans("tile.blockIron.name") + ", " + trans("tile.sandStone.name") + ",");
			par3List.add(trans("tile.clayHardened.name") + ", " + trans("material.log") + ", " + trans("tile.tnt.name") + ",");
			par3List.add(trans("tile.blockCoal.name") + ", " + trans("tile.blockLapis.name") + ",");
			par3List.add(trans("tile.clay.name") + ", " + trans("tile.bookshelf.name") + ", " + trans("tile.sponge.name") + ", " + trans("tile.melon.name") + ",");
			par3List.add(trans("tile.cloth.name") + ", " + trans("tile.hayBlock.name") + ", " + trans("material.leaf_block"));		
			GolemPaperAddInfoEvent event = new GolemPaperAddInfoEvent(par1ItemStack, par2EntityPlayer, par3List);
			MinecraftForge.EVENT_BUS.post(event);
		}
		else
		{		
			String lorePressShift = 
					new ChatComponentTranslation(EnumChatFormatting.GRAY + trans("tooltip.press") + " ").getFormattedText() + 
					new ChatComponentTranslation(EnumChatFormatting.YELLOW + trans("tooltip.shift").toUpperCase() + " ").getFormattedText() + 
					new ChatComponentTranslation(EnumChatFormatting.GRAY + trans("tooltip.for_golem_materials")).getFormattedText();
			par3List.add(lorePressShift);
		}
	}

	private String trans(String s)
	{
		return StatCollector.translateToLocal(s);
	}
}
