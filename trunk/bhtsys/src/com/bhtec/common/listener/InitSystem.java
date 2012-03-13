/**
 *功能说明：
 * @author jacobliang
 * @time @Dec 26, 2011 @4:04:49 PM
 */
package com.bhtec.common.listener;

import static com.bhtec.common.constant.Common.BHT_SYS_FILE_DIR;
import static com.bhtec.common.constant.Common.SYS_CONFIGFILE_XML;
import static com.bhtec.common.tools.UtilTools.getResourcePath;

import com.bhtec.common.util.SystemConfigBean;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.exception.ApplicationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class InitSystem implements ServletContextListener {
	private static Logger log = Logger.getLogger("InitSystemCommonDir");
	
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		//初始化系统配置文件
		SystemConifgXmlParse.getInstance();
		//创建配置文件
		createSystemCommonDir();
	}
	
	/**
	 * 功能说明：在公共目录创建系统目录及配置文件
	 * @author jacobliang
	 * @time Dec 26, 2011 4:05:53 PM
	 */
	private void createSystemCommonDir(){
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			SystemConfigBean systemConfigBean = 
							SystemConifgXmlParse.getSystemConfigBean();
			String cluster = systemConfigBean.getCluster();
			if("false".equalsIgnoreCase(cluster)){
				return;
			}else if("true".equalsIgnoreCase(cluster)){
				String separate = File.separator;
				String path = getResourcePath();
				String rootDir = separate;//默认为分隔符 linux unix
				if("\\".equals(separate)){//windows系统
					rootDir = path.substring(0, 2);
				}
				log.info("system separator : "+ separate);
				log.info("root : "+ rootDir);
				rootDir = rootDir+separate+BHT_SYS_FILE_DIR+separate;
				File rootDirFile = new File(rootDir);				
				File sysConfigFile = new File(rootDir+SYS_CONFIGFILE_XML);
				File originalConfigFile = new File(path+SYS_CONFIGFILE_XML);//原配置文件
				if(!rootDirFile.exists())rootDirFile.mkdir();//创建目录
				if(!sysConfigFile.exists()){
					sysConfigFile.createNewFile();//创建文件
				}else{
					return;
				}
					
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(originalConfigFile), "UTF-8"));
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(sysConfigFile),"UTF-8"));
				String str;
				while ((str = br.readLine()) != null) {
					bw.write(str+"\n\r");
					bw.flush();
				}
			}
			
		} catch (IOException e) {
			new ApplicationException("系统启动时初始化创建目录出错!",e);			
		}finally{
			try {
				if (bw != null)
					bw.close();
				if (br != null)
					br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[]args){
		String separate = File.separator;
		String rootDir = "d:"+separate+BHT_SYS_FILE_DIR+separate;
		File dir = new File(rootDir);
		File sysConfigFile = new File(rootDir+SYS_CONFIGFILE_XML);
			
			try {
				dir.mkdir();
				sysConfigFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
