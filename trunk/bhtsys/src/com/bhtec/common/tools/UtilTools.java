package com.bhtec.common.tools;

import static com.bhtec.common.constant.Common.BHT_SYS_FILE_DIR;
import static com.bhtec.common.constant.Common.WEB_APP_ROOT_KEY;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.bhtec.common.tools.FirstLetter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bhtec.exception.ApplicationException;

/**
 *功能描述：常用工具方法的集合
 *创建时间：Mar 9, 2009 11:46:22 AM 
 *@author lianglp
 *@version 1.0
 */

public class UtilTools {
	private static Logger log = Logger.getLogger("UtilTools");
	/**
     * 加密操作
     * @param data
     * @return
     */
    public static String encrypt(String data){
    	return PwdCrypt.getInstance().encrypt(data);
    }
    
    /**
     * 解密操作
     * @param data
     * @return
     * @throws IOException
     */
    public static String decrypt(String data){
    	return PwdCrypt.getInstance().decrypt(data);
    }
    /**
     * 
     *功能描述：检查是否为null
     *@since Mar 18, 2010  9:21:03 PM
     *@author jacobliang
     *@version 1.0
     *@param	str		被检查对象
     *@return	boolean null?true:false
     */
    public static boolean isNull(Object obj){
    	if(obj == null)return true;
    	return false;
    }
    /**
     * 
     *功能描述：检查字符串是否为空
     *@since Mar 18, 2010  9:25:26 PM
     *@author jacobliang
     *@version 1.0
     *@param	str		被检查字符串
     *@return	boolean ""?true:false
     */
    public static boolean isEmpty(String str){
    	if("".equals(str.trim()))return true;
    	return false;
    }
    /**
     * 
     *功能描述：检查字符串是否为空或null
     *@since Mar 18, 2010  9:27:49 PM
     *@author jacobliang
     *@version 1.0
     *@param	str		被检查字符串
     *@return
     */
    public static boolean isNullOrEmpty(String str){
    	if(isNull(str) || isEmpty(str))return true;
    	return false;
    }
    /**
     * 功能说明：string字符串转换
     * @author jacobliang
     * @param str
     * @param code
     * @return
     * @throws 
     * @time Aug 15, 2010 10:02:16 AM
     */
    public static String StringEncode(String str,String code){
    	if(isNullOrEmpty(str))return null;
    	if(isNullOrEmpty(code))return null;
    	try {
			return new String(str.getBytes(),code);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    /**
	 * 功能说明：获得web根路径
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 24, 2010 6:05:03 PM
	 */
	public static String getResourcePath(){
		String resPath = System.getProperty(WEB_APP_ROOT_KEY);
		log.info("path of webAppRootKey is ----> " + resPath);
		return resPath;
	}
	
	/**
	 * 功能说明：系统集群时获得存放文件路径 为系统配置附件所用
	 * @author jacobliang
	 * @param fileName 文件名称 可能为空
	 * @return
	 * @time Dec 27, 2011 9:49:17 AM
	 */
	public static String getClusterResourcePath(String fileName){
		String separate = File.separator;
		String path = getResourcePath();
		String rootDir = separate;//默认为分隔符 linux unix
		if("\\".equals(separate)){//windows系统
			rootDir = path.substring(0, 2);
		}
		String sysConfigPaht = rootDir+separate+BHT_SYS_FILE_DIR+separate+fileName;
		File sysConfigFile = new File(sysConfigPaht);
		if(sysConfigFile.exists())return sysConfigPaht;
		return path+fileName;
	}
	/**
	 * 功能说明：将日期格式为年月日
	 * @author jacobliang
	 * @param
	 * @param
	 * @param date
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 11:01:08 AM
	 */
	public static String formatDateToYMD(Date date){
		if(date == null)return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 功能说明：将日期格式为年月日时分秒
	 * @author jacobliang
	 * @param
	 * @param
	 * @param date
	 * @return
	 * @throws 
	 * @time Sep 27, 2010 11:01:40 AM
	 */
	public static String formatDateToYMDHMS(Date date){
		if(date == null)return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	/**
	 * 功能说明：附件上传
	 * @author jacobliang
	 * @param request	
	 * @param file				file对象
	 * @param accessoryPath		附件路径
	 * @param fileName			附件名称
	 * @time Oct 19, 2010 10:28:00 PM
	 */
	public static String uploadFile(HttpServletRequest request,File file,String accessoryPath,String fileName) {
		if(file == null)return "";
	    //文件上传目录
		String uploadDir = getClusterResourcePath("")+ accessoryPath;
	    //如果目录不存在则自动创建
	    File dirPath = new File(uploadDir);        
	    if (!dirPath.exists())           
	      dirPath.mkdirs();
	    
	    String tempName = "";
	    for(int i=0;i<6;i++){
	    	tempName += getRandomChar();
	    }
	    fileName = tempName + "_" + fileName;
	    
	    //文件上传
	    String outPath = uploadDir + File.separator + fileName;
	    try {
			FileInputStream fis = new FileInputStream(file); 
			FileOutputStream fos = new FileOutputStream(outPath); 
			byte[] buffer = new byte[10240];   
			int len = 0;      
			while ((len = fis.read(buffer)) > 0) {   
			    fos.write(buffer, 0, len);   
			    fos.flush();   
			}   
			fis.close();   
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return fileName;
	}
	
	/**
	 * 功能说明：附件下载
	 * @author jacobliang
	 * @param request
	 * @param response
	 * @param accessoryPath			附件相对路径	
	 * @param fileName				下载的附件名称
	 * @param oldFileName			服务器上的附件名称
	 * @param isOnLine				是否在线打开
	 * @throws ApplicationException
	 * @time Dec 7, 2010 11:18:39 AM
	 */
	public static String downloadFile(HttpServletRequest request,HttpServletResponse response,
										String accessoryPath,String fileName,String oldFileName,boolean isOnLine) 
										throws ApplicationException{
		log.info("fileName --> " + fileName);
	    //文件下载
//	    String filePath = request.getRealPath(File.separator)+
//	    				 accessoryPath + File.separator + oldFileName;
	    String filePath = getClusterResourcePath("")+ accessoryPath + File.separator + oldFileName;
	    FileInputStream fis = null;
	    BufferedInputStream bufInStream = null;
	    OutputStream outStream = null;
	    try {
	    	File file = new File(filePath);
	    	if(!file.exists())
				throw new ApplicationException("对不起，文件"+fileName+"找不到.");
			fis = new FileInputStream(file); 
			bufInStream = new BufferedInputStream(fis);
			String 	nameStr = new String(fileName.getBytes("GBK"), "ISO8859-1") + "\"";
			response.reset();
			if (isOnLine) {// 在线打开方式
				URL u = new URL("file:///" + filePath);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; filename=" + nameStr);
			} else {// 纯下载方式
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + nameStr);
			}
			byte[] buffer = new byte[10240];   
			int len = 0;   
			outStream = response.getOutputStream();
			while ((len = bufInStream.read(buffer)) > 0) {   
				outStream.write(buffer, 0, len);
				outStream.flush();
			}   
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fis != null)
					fis.close();
				if(bufInStream != null)
					bufInStream.close();
//				if(outStream != null)
//					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 功能说明：随机生成字符，含大写、小写、数字
	 * @author jacobliang
	 * @return
	 * @time Nov 9, 2010 1:25:47 PM
	 */
    public static String getRandomChar() {   
        int index = (int) Math.round(Math.random() * 2);  
        String randChar = "";   
        switch (index) {   
        case 0://大写字符   
            randChar = String.valueOf((char)Math.round(Math.random() * 25 + 65));   
            break;   
        case 1://小写字符   
            randChar = String.valueOf((char)Math.round(Math.random() * 25 + 97));   
            break;   
        default://数字   
            randChar = String.valueOf(Math.round(Math.random() * 9));   
            break;   
        }   
        return randChar;   
    }   
    
	/**
	 * 功能说明：附件删除
	 * @author jacobliang
	 * @param request	
	 * @param file				file对象
	 * @param accessoryPath		附件路径
	 * @param newFileName		新文件名称
	 * @param oldFileName		旧文件名称
	 * @time Oct 19, 2010 10:28:00 PM
	 */
	public static String unuploadFileForUpdate(HttpServletRequest request,File file,String accessoryPath,
									  String newFileName,String oldFileName){
		 //文件上传目录
//	    String uploadDir = request.getRealPath(File.separator) + accessoryPath;
	    String uploadDir = getClusterResourcePath("")+ accessoryPath;
	    //如果目录不存在则自动创建
	    File dirPath = new File(uploadDir);        
	    if (!dirPath.exists()){           
	    	dirPath.mkdirs();
	    }
	    
    	if((oldFileName != null || !"".equals(oldFileName)) 
    			&& file != null){
	    	File[] files = dirPath.listFiles();
	    	for(File filee : files){
	    		if(filee.getName().equals(oldFileName)){
	    			filee.delete();
	    			break;
	    		}
	    	}
    	}
    	if(file != null)
    		return uploadFile(request, file, accessoryPath, newFileName);
		return "";
	}
	
	/**
	 * 功能说明：附件删除
	 * @author jacobliang
	 * @param request	
	 * @param accessoryPath		附件路径
	 * @param oldFileName		旧文件名称
	 * @time Oct 19, 2010 10:28:00 PM
	 */
	public static void deleteAccessoryFile(HttpServletRequest request,String accessoryPath,String oldFileName){
		 //文件上传目录
//	    String uploadDir = request.getRealPath(File.separator) + accessoryPath;
	    String uploadDir = getClusterResourcePath("")+ accessoryPath;
	    //如果目录不存在则自动创建
	    File dirPath = new File(uploadDir);        
    	if(oldFileName != null || !"".equals(oldFileName)){
	    	File[] files = dirPath.listFiles();
	    	for(File filee : files){
	    		if(filee.getName().equals(oldFileName)){
	    			filee.delete();
	    			break;
	    		}
	    	}
    	}
	}
	
	/**
	 * 功能说明：
	 * @author jacobliang
	 * @param filePath		文件路径
	 * @return
	 * @time Oct 20, 2010 10:57:46 AM
	 */
	public static void deleteFile(HttpServletRequest request,String filePath){
		 //文件上传目录
//		filePath = request.getRealPath(File.separator) + filePath;
		filePath = getClusterResourcePath("")+ filePath;
		File file = new File(filePath);
		if(file.exists())
			file.delete();
	}
	
	/**
	 * 功能说明：获得一句话的首字母
	 * @author jacobliang
	 * @param words
	 * @return
	 * @time Feb 20, 2012 11:35:08 AM
	 */
	public static String getFirstLetters(String words){
		return FirstLetter.getFirstLetters(words);
	}
	
	public static void  main(String[] args){
		String fileSepator = System.getProperties().getProperty("file.separator");
		UtilTools.class.getResourceAsStream(fileSepator);
		System.out.println(UtilTools.getResourcePath());
		System.out.println(System.getProperty("bhtec.root"));
		File[] drive = File.listRoots();
		for (int i = 0; i < drive.length; i++) {
			System.out.println("\t" + drive[i]);
		} 
	}
}
