package com.jlgm.pgen.main;

import com.jlgm.pgen.block.PGenBlock;
import com.jlgm.pgen.client.gui.PGenGuiHandler;
import com.jlgm.pgen.item.PGenItem;
import com.jlgm.pgen.lib.PGenConfigStorage;
import com.jlgm.pgen.network.PGenPacketHandler;
import com.jlgm.pgen.tileentity.PGenTileEntity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class PGenCommonProxy{

	public void preInit(FMLPreInitializationEvent preInitEvent){
		PGenConfigStorage configStorage = PGenMain.instance.configStorage;
		Configuration config = new Configuration(preInitEvent.getSuggestedConfigurationFile());
		config.load();
		configStorage.relativeCoords = config.getBoolean("Force the use of relative coordinates", config.CATEGORY_GENERAL, false, "");
		config.save();
		
		PGenPacketHandler.registerMessage();
		PGenTileEntity.registerTileEntity();
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		PGenBlock.registerBlocks(event);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		PGenBlock.registerItemBlocks(event);
		PGenItem.registerItems(event);
	}
	

	public void init(FMLInitializationEvent initEvent){
		NetworkRegistry.INSTANCE.registerGuiHandler(PGenMain.instance, new PGenGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent postInitEvent){

	}
}
