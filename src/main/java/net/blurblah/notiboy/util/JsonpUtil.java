package net.blurblah.notiboy.util;

import net.blurblah.notiboy.log.SLog;


public class JsonpUtil {

	public static String checkJsonP(String callback, String result){
		if(callback != null)
			result = callback + "(" + result + ")";
		
		SLog.d("result", result);
		return result;
	}
	
}
