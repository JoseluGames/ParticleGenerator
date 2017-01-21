package com.jlgm.pgen.main;

import java.io.File;

import com.jlgm.pgen.block.PGenBlock;
import com.jlgm.pgen.client.gui.PGenGuiHandler;
import com.jlgm.pgen.event.PGenEventHandler;
import com.jlgm.pgen.lib.PGenConfigStorage;
import com.jlgm.pgen.lib.PGenConstants;
import com.jlgm.pgen.network.PGenPacketHandler;
import com.jlgm.pgen.tileentity.PGenTileEntity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class PGenCommonProxy{

	public static Configuration config;
	
	public void preInit(FMLPreInitializationEvent preInitEvent){
		PGenPacketHandler.registerMessage();
		PGenBlock.main();
		
		// Config file setup
        File directory = preInitEvent.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), PGenConstants.MODID + ".cfg"));
        PGenConfigStorage.readConfig();
	}

	public void init(FMLInitializationEvent initEvent){
		PGenTileEntity.main();
		PGenBlock.registerBlock();
		NetworkRegistry.INSTANCE.registerGuiHandler(PGenMain.instance, new PGenGuiHandler());
		GameRegistry.addRecipe(new ShapedOreRecipe(PGenBlock.particleGenerator_Block, "CGC", "CRC", "CCC", 'C', "cobblestone", 'R', "dustRedstone", 'G', "gunpowder"));
		MinecraftForge.EVENT_BUS.register(new PGenEventHandler());
	}

	public void postInit(FMLPostInitializationEvent postInitEvent){
		// Config file wrap up
        if (config.hasChanged()) {
            config.save();
        }
	}
}
