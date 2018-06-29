package net.blurblah.notiboy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonpUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonpUtil.class);

	public static String checkJsonP(String callback, String result){
		if(callback != null)
			result = callback + "(" + result + ")";
		
		log.debug("result", result);
		return result;
	}
	
}
