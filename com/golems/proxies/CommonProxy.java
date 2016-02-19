package com.golems.proxies;

import com.golems.events.GolemEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy 
{
	public void registerRenders() {}
	
	public void registerEvents() 
	{
		MinecraftForge.EVENT_BUS.register(new GolemEventHandler());
	}
}
