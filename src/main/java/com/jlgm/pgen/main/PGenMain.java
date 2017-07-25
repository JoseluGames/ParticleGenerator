package com.jlgm.pgen.main;

import com.jlgm.pgen.lib.PGenConstants;
import com.jlgm.pgen.lib.PGenVersionChecker;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PGenConstants.MODID,
	name = PGenConstants.NAME,
	version = PGenConstants.VERSION,
	acceptedMinecraftVersions = PGenConstants.ACCEPTEDMINECRAFTVERSIONS)

public class PGenMain{

	public static PGenVersionChecker versionChecker;
	public static boolean haveWarnedVersionOutOfDate = false;

	@SidedProxy(clientSide = PGenConstants.CLIENT_PROXY, serverSide = PGenConstants.SERVER_PROXY)
	public static PGenCommonProxy proxy;
	@Mod.Instance(PGenConstants.MODID)
	public static PGenMain instance;

	@Mod.EventHandler
	public static void PreInit(FMLPreInitializationEvent preInitEvent){
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
