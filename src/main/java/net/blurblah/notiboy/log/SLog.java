package net.blurblah.notiboy.log;

import net.blurblah.notiboy.config.Config;
import net.blurblah.notiboy.util.DateUtil;

public class SLog {

	/**
	 * debug log
	 * @param msg
	 */
	public static void d(String msg){
		if(Config.isDebug == true)
			System.out.println("[DEBUG] " + DateUtil.currentTime() + " | " + msg );
	}
	
	/**
	 * debug log 
	 * @param title
	 * @param msg
	 */
	public static void d(String title, String msg){
		if(Config.isDebug == true)
			System.out.println("[DEBUG] " + DateUtil.currentTime() + " | " + title + " | " + msg );
	}
	
	/**
	 * debug log 
	 * @param title
	 * @param number
	 */
	public static void d(String title, int number){
		if(Config.isDebug == true)
			System.out.println("[DEBUG] " + DateUtil.currentTime() + " | " + title + " | " + number );
	}
	
	/**
	 * debug log
	 * @param obj
	 * @param msg
	 */
	public static void d(Object obj, String msg){
		if(Config.isDebug == true)
			System.out.println("[DEBUG] " + DateUtil.currentTime() + " | " + obj.getClass().getSimpleName() +" | " + msg );
	}
	
	/**
	 * error log
	 * @param msg
	 */
	public static void e(String msg){
		System.out.println("[ERROR] " + DateUtil.currentTime() + " | " + msg );
	}
	
	/**
	 * information log 
	 * @param msg
	 */
	public static void i(String msg){
		System.out.println("[INFO] " + DateUtil.currentTime() + " | " + msg );
	}
	
	
}
