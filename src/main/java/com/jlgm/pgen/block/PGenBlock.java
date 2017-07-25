package com.jlgm.pgen.block;

import com.jlgm.pgen.lib.PGenConstants;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;

public class PGenBlock{
	
	public static Block particleGenerator_Block;
	public static ItemBlock particleGenerator_ItemBlock;
	
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		particleGenerator_Block = new BlockParticleGenerator(Material.ROCK).setUnlocalizedName("particlegenerator").setHardness(0.7F).setResistance(0.5F).setCreativeTab(CreativeTabs.REDSTONE);
		event.getRegistry().register(particleGenerator_Block.setRegistryName("particlegenerator"));
	}

	public static void registerItemBlocks(RegistryEvent.Register<Item> event){
		particleGenerator_ItemBlock = new ItemBlock(particleGenerator_Block);
		event.getRegistry().register(particleGenerator_ItemBlock.setRegistryName(particleGenerator_Block.getRegistryName()));
	}
	
	public static void renderBlock(){
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(particleGenerator_ItemBlock, 0,
				new ModelResourceLocation(PGenConstants.MODID + ":" + "particlegenerator", "inventory"));
		
	}
}
