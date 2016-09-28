package com.jlgm.pgen.lib;

public class PGenMath {
	
	public static boolean isInRange(float pointerX, float pointerY, float rangeX1, float rangeX2, float rangeY1, float rangeY2){
		return pointerX > rangeX1 && pointerX < rangeX2 && pointerY > rangeY1 && pointerY < rangeY2;
	}
	
}
