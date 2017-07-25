package com.jlgm.pgen.main;

import com.jlgm.pgen.block.PGenBlock;
import com.jlgm.pgen.lib.PGenVersionChecker;
import com.jlgm.pgen.tileentity.PGenTileEntity;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PGenClientProxy extends PGenCommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent preInitEvent){
		super.preInit(preInitEvent);
	}

	@Override
	public void init(FMLInitializationEvent initEvent){
		super.init(initEvent);
		PGenBlock.renderBlock();
	}

	@Override
	public void postInit(FMLPostInitializationEvent postInitEvent){
		super.postInit(postInitEvent);
		PGenMain.versionChecker = new PGenVersionChecker();
		Thread versionCheckThread = new Thread(PGenMain.versionChecker, "VersionCheck");
		versionCheckThread.start();
	}
}
