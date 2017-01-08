package com.jlgm.pgen.lib;

public class PGenConstants{
	//The name "behind the scenes"
	public static final String MODID = "jlgm_pgen";
	//The public name
	public static final String NAME = "Particle Generator";
	
	//Version parts ( https://mcforge.readthedocs.io/en/latest/conventions/versioning/ )
	public static final String MCVERSION = "1.11.2";
	public static final String MAJOR = "1";
	public static final String MINOR = "0";
	public static final String PATCH = "0";
	public static final String RELEASETYPE = "";
	
	//The version of the mod
	public static final String VERSION = MCVERSION + "-" + MAJOR + "." + MINOR + "." + PATCH + RELEASETYPE;
	//The Minecraft verstion this mod is focused to work with
	public static final String ACCEPTEDMINECRAFTVERSIONS = "[1.11.2]";
	
	//The package route of the proxys
	public static final String CLIENT_PROXY = "com.jlgm.pgen.main.PGenClientProxy";
	public static final String SERVER_PROXY = "com.jlgm.pgen.main.PGenServerProxy";
}
