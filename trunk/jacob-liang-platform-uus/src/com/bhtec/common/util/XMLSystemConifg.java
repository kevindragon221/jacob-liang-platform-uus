/**
 *功能说明：解析系统XML配置文件
 * @author jacobliang
 * @time @Sep 11, 2010 @9:27:23 AM
 */
package com.bhtec.common.util;

import static com.bhtec.common.tools.UtilTools.getResourcePath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.exception.ApplicationException;

public class XMLSystemConifg {
	private static XMLSystemConifg xmlSystemConifg = new XMLSystemConifg();
	private long fileLastModifyTime = 0;
	private File systemConfigFile = null;
	private Document doc = null;
	private final String FILE_NAME = "SystemConfig.xml";
	
	private XMLSystemConifg(){
		
	}
	
	public static XMLSystemConifg getInstance(){
		return xmlSystemConifg;
	}
	/**
	 * 功能说明：获得系统配置文件XML的document
	 * @author jacobliang
	 * @param
	 * @param
	 * @return
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 11, 2010 10:02:38 AM
	 */
	public Document getSystemConfigDoc()throws ApplicationException{
		String fileName = getResourcePath()+FILE_NAME;
		systemConfigFile = new File(fileName);
		if(!systemConfigFile.exists())
			throw new ApplicationException("对不起，文件"+fileName+"找不到.");

		long sysConfigMod = systemConfigFile.lastModified();
		if(sysConfigMod > this.fileLastModifyTime){
			doc = XmlOpUtil.loadXMLFile(fileName);
			fileLastModifyTime = sysConfigMod;
		}
		return doc;
	}
	/**
	 * 功能说明：获得系统配置文件的描述
	 * @author jacobliang
	 * @param
	 * @param
	 * @param xmlPath
	 * @return
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 11, 2010 10:04:55 AM
	 */
	public String getSystemConfigText(String xmlPath)throws ApplicationException{
		Element el = XmlOpUtil.selectSingleNode(getSystemConfigDoc(), xmlPath);
		String descrition = el.getText();
		return descrition;
	}
	
	/**
	 * 功能说明：获得相应结点下的所有子结点
	 * @author jacobliang
	 * @param xmlPath
	 * @return
	 * @throws ApplicationException
	 * @time Nov 4, 2010 1:56:11 PM
	 */
	public List<String> getSysytemConfigTextList(String xmlPath)throws ApplicationException{
		List<String> textList = new ArrayList<String>();
		List<Element> elements = getSystemConfigDoc().selectNodes(xmlPath);
		if(elements != null && elements.size()>0){
			for(Element element:elements){
				textList.add(element.getText());
			}
		}
		return textList;
	}
	/**
	 * 功能说明：将配置信息写入配置文件
	 * @author jacobliang
	 * @param doc		document
	 * @throws ApplicationException
	 * @time Nov 22, 2010 10:47:49 AM
	 */
	public void writeConfigInfoToXML(Document doc)throws ApplicationException{
		XmlOpUtil.doc2XmlFile(doc, getResourcePath()+FILE_NAME);
	}

	public static void main(String[] args){
		try {
			XMLSystemConifg xMLSystemConifg = XMLSystemConifg.getInstance();
			List<String> textList = 
				xMLSystemConifg.getSysytemConfigTextList("//systemConfig/systemMgr/userCode");
			for(String text:textList){
				System.out.println(text);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
