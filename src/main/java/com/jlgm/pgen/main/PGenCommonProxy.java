package com.jlgm.pgen.main;

import com.jlgm.pgen.block.PGenBlock;
import com.jlgm.pgen.client.gui.PGenGuiHandler;
import com.jlgm.pgen.event.PGenEventHandler;
import com.jlgm.pgen.network.PGenPacketHandler;
import com.jlgm.pgen.tileentity.PGenTileEntity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class PGenCommonProxy{

	public void preInit(FMLPreInitializationEvent preInitEvent){
		PGenPacketHandler.registerMessage();
		PGenBlock.main();
	}

	public void init(FMLInitializationEvent initEvent){
		PGenTileEntity.main();
		PGenBlock.registerBlock();
		NetworkRegistry.INSTANCE.registerGuiHandler(PGenMain.instance, new PGenGuiHandler());
		//MinecraftForge.EVENT_BUS.register(new PGenEventHandler());
	}

	public void postInit(FMLPostInitializationEvent postInitEvent){

	}
}
