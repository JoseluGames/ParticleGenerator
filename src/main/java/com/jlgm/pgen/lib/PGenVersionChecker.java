package com.jlgm.pgen.lib;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;

public class PGenVersionChecker implements Runnable{

	private static ArrayList<String> latestVersionInfo = new ArrayList<String>();
	private static boolean isLatestVersion = false;
	private static String latestVersion = "";

	@Override
	public void run(){
		InputStream in = null;
		try{
			in = new URL("https://raw.githubusercontent.com/JoseluGames/ParticleGenerator/mc-1.10.2/versionCheck.txt").openStream();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			Iterator itr = IOUtils.readLines(in).iterator();
			int lineNum = 0;
			while(itr.hasNext()){
				String line = (String) itr.next();
				if(!line.equals("##")){
					latestVersionInfo.add(line);
					lineNum++;
				}else{
					break;
				}
			}
			latestVersion = latestVersionInfo.get(0);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(in);
		}
		isLatestVersion = PGenConstants.VERSION.equals(latestVersion);
	}

	public boolean isLatestVersion(){
		return isLatestVersion;
	}

	public String getLatestVersion(){
		return latestVersion;
	}

	public ArrayList<String> getLatestVersionInfo(){
		return latestVersionInfo;
	}
}
