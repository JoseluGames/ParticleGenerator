package com.jlgm.pgen.lib;

import org.apache.logging.log4j.Level;

import com.jlgm.pgen.main.PGenCommonProxy;
import com.jlgm.pgen.main.PGenMain;

import net.minecraftforge.common.config.Configuration;

public class PGenConfigStorage{
	// This class populated by trevorjd based on McJty's tutorial for config files
	
		// define catgories for config file
		private static final String CATEGORY_GENERAL = "general";

	    // define values to store in config file
	    public static boolean versionChecking = true;

	    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
	    // exist yet and read the values if it does exist.
	    public static void readConfig() {
	        Configuration cfg = PGenCommonProxy.config;
	        try {
	            cfg.load();
	            initGeneralConfig(cfg);
	        } catch (Exception e1) {
	            PGenMain.logger.log(Level.ERROR, "Problem loading config file!", e1);
	        } finally {
	            if (cfg.hasChanged()) {
	                cfg.save();
	            }
	        }
	    }

	    private static void initGeneralConfig(Configuration cfg) {
	        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
	        // cfg.getBoolean() will get the value in the config if it is already specified there. If not it will create the value.
	        versionChecking = cfg.getBoolean("versionChecking", CATEGORY_GENERAL, versionChecking, "Set to false to disable version checking.");
	    }
}
