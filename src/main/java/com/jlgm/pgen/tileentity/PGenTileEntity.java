package com.jlgm.pgen.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class PGenTileEntity{
	
	public static void main(){
		registerTileEntity();
	}
	
	public static void registerTileEntity(){
		GameRegistry.registerTileEntity(TileEntityParticleGenerator.class, "particleGenerator_tileEntity");
	}
	
	public static void registerTESR(){
		
	}
}
