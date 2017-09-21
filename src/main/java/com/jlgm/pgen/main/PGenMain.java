package com.jlgm.pgen.main;

import com.jlgm.pgen.lib.PGenConfigStorage;
import com.jlgm.pgen.lib.PGenConstants;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PGenConstants.MODID,
	name = PGenConstants.NAME,
	version = PGenConstants.VERSION,
	acceptedMinecraftVersions = PGenConstants.ACCEPTEDMINECRAFTVERSIONS,
	updateJSON = PGenConstants.UPDATEJSON)

public class PGenMain{

	@SidedProxy(clientSide = PGenConstants.CLIENT_PROXY, serverSide = PGenConstants.SERVER_PROXY)
	public static PGenCommonProxy proxy;
	@Mod.Instance(PGenConstants.MODID)
	public static PGenMain instance;
	
	public static PGenConfigStorage configStorage;

	@Mod.EventHandler
	public static void PreInit(FMLPreInitializationEvent preInitEvent){
		configStorage = new PGenConfigStorage();
		proxy.preInit(preInitEvent);
	}

	@Mod.EventHandler
	public static void Init(FMLInitializationEvent initEvent){
		proxy.init(initEvent);
	}

	@Mod.EventHandler
	public static void PostInit(FMLPostInitializationEvent postInitEvent){
		proxy.postInit(postInitEvent);
	}
}
