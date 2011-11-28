/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 16, 2010 @1:35:27 PM
 */
package com.bhtec.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class OnlineCounter {
	Logger log = Logger.getLogger(this.getClass());
	public static Map sessionMap = new HashMap();
	private static OnlineCounter onlineCounter = new OnlineCounter();
	private OnlineCounter(){
		
	}
	
	public static OnlineCounter getInstance(){
		return onlineCounter;
	}
	
	private long online = 0; 
	public long getOnline(){
		log.debug("online---> " + online);
		return online; 
	} 
	public void raise(){ 
		online++; 
		log.debug("---> raise");
	} 
	public void reduce(){ 
		online--; 
		log.debug("--->reduce");
	} 
	/**
	 * 功能说明：为用户添加session
	 * @author jacobliang
	 * @param userCode
	 * @param session
	 * @time Nov 3, 2010 1:16:03 PM
	 */
	public void addUserSession(String userCode,HttpSession session){
		sessionMap.put(userCode, session);
	}
	
}
