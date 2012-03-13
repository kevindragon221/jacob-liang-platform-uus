/**
 *功能说明：
 * @author jacobliang
 * @time @Jan 19, 2012 @10:57:32 AM
 */
package com.bhtec.common.util;

import java.io.IOException;

public class JmxJconsoleMonitor {
	public void startJconsole(){
		Runtime runtime = Runtime.getRuntime();
		try { 
			runtime.exec("cmd.exe /c jconsole ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
