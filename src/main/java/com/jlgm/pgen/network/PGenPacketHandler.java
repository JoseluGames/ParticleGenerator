package com.jlgm.pgen.network;

import com.jlgm.pgen.lib.PGenConstants;
import com.jlgm.pgen.network.ParticleGeneratorMessage.ParticleGeneratorMessageHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PGenPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(PGenConstants.MODID);
	
	public static void registerMessage(){
		INSTANCE.registerMessage(ParticleGeneratorMessageHandler.class, ParticleGeneratorMessage.class, 0, Side.SERVER);
	}
}