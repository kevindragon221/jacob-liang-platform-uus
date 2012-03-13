/**
 *功能说明：
 * @author jacobliang
 * @time @Jan 21, 2011 @11:02:18 AM
 */
package com.bhtec.common.util;

import static com.bhtec.common.tools.UtilTools.getClusterResourcePath;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class PostgresqlDbBackup {
	private Logger log = Logger.getLogger(this.getClass());
	public PostgresqlDbBackup(){
		Runtime runtime = Runtime.getRuntime();
		String fileName = getClusterResourcePath("builder"+File.separator+"postgresqlDdBackup.bat");
		log.info("bat path :"+fileName);
		try { 
			runtime.exec("cmd.exe /c start " +fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**功能说明：
	 * @author jacobliang
	 * @param args
	 * @time Jan 21, 2011 11:02:18 AM
	 */
	public static void main(String[] args) {
//		new PostgresqlDbBackup();
		Runtime runtime = Runtime.getRuntime();
		try { 
			runtime.exec("cmd.exe /c jconsole ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
