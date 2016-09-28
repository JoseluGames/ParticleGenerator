package com.jlgm.pgen.block;

import com.jlgm.pgen.lib.PGenConstants;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PGenBlock{
	
	public static Block particleGenerator_Block;
	public static ItemBlock particleGenerator_ItemBlock;
	
	public static void main(){
		initialiseBlock();
	}

	public static void initialiseBlock(){
		particleGenerator_Block = new BlockParticleGenerator(Material.ROCK).setUnlocalizedName("particleGenerator").setHardness(0.7F).setResistance(0.5F).setCreativeTab(CreativeTabs.REDSTONE);
		particleGenerator_ItemBlock = new ItemBlock(particleGenerator_Block);
	}

	public static void registerBlock(){
		GameRegistry.register(particleGenerator_Block.setRegistryName("particleGenerator"));
		GameRegistry.register(particleGenerator_ItemBlock.setRegistryName(particleGenerator_Block.getRegistryName()));
	}

	public static void renderBlock(){
		ItemModelMesher modelMesherItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		modelMesherItem.register(particleGenerator_ItemBlock, 0, new ModelResourceLocation(PGenConstants.MODID + ":" + "test", "inventory"));
	}
}
