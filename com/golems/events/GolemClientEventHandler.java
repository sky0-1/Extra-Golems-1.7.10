package com.golems.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GolemClientEventHandler 
{
	@SubscribeEvent
	public void onAddInfo(GolemPaperAddInfoEvent event)
	{
		// debug:
		//event.infoList.add("test");
	}
}
