package com.acolos.acolostracker;

import com.acolos.acolostracker.proxy.CommonProxy;
import com.acolos.acolostracker.util.Reference;
import com.acolos.acolostracker.util.LoadDriver;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static LoadDriver driver;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) { }
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
	@EventHandler
	public static void PostInt(FMLPostInitializationEvent event) { }

}
