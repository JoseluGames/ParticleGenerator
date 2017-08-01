package com.jlgm.pgen.item;

import com.jlgm.pgen.lib.PGenConstants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;

public class PGenItem {
	
	public static Item coordSaver_Item;
	
	public static void registerItems(RegistryEvent.Register<Item> event){
		coordSaver_Item = new ItemCoordSaver().setUnlocalizedName("coordsaver").setCreativeTab(CreativeTabs.REDSTONE);;
		event.getRegistry().register(coordSaver_Item.setRegistryName("coordsaver"));
	}
	
	public static void renderItem(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(coordSaver_Item, 0,
				new ModelResourceLocation(PGenConstants.MODID + ":" + "coordsaver", "inventory"));
		
	}
}
