package com.bhtec.service.impl.platform.mainframefun;

import static com.bhtec.common.constant.ServiceVariable.*;
import static com.bhtec.common.tools.UtilTools.getResourcePath;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.mainframefun.MainFrameFunService;
import com.bhtec.service.impl.BaseServiceImpl;

public class MainFrameFunServiceImpl extends BaseServiceImpl implements MainFrameFunService{
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * 功能说明：计算功能区的最大ID
	 * @author jacobliang
	 * @param
	 * @param
	 * @param document
	 * @return
	 * @throws 
	 * @time Sep 17, 2010 10:34:58 AM
	 */
	public synchronized int getFunAreaMaxId(Document document){
		List<Element> list = document.selectNodes("//module/funArea/area");
		int id = -1;
		for(Element e : list){
			if(e.elementText("funId") != null){
				int funId = Integer.parseInt(e.elementText("funId"));
				if(funId > id){
					id = funId;
				}
			}
		}
		log.debug("funarea max id is " + (id+1));
		return id+1;
	}
	/**
	 * 功能说明：增加功能區,同时包含修改功能区功能
	 * @author jacobliang
	 * @param	mainFrameFun	功能区对象
	 * @throws 
	 * @time Jul 24, 2010 4:31:07 PM
	 */
	public void saveMainFrameFun(MainFrameFun mainFrameFun)throws ApplicationException{
		if(mainFrameFun.getFunId() != null)
			deleteMainFrameFun(mainFrameFun.getFunId());
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINFRAME_FILE_NAME);
		
		Element el = (Element)document.selectSingleNode("/module/funArea");
		Element areaEl = el.addElement("area");
		
		int id = getFunAreaMaxId(document);
		Element idEl = areaEl.addElement("funId");
		idEl.addText(id+"");
		
		Element funNameEl = areaEl.addElement("funName");
		funNameEl.addText(mainFrameFun.getFunName());
		
		Element funURIEl = areaEl.addElement("funURI");
		funURIEl.addText(mainFrameFun.getFunURI());
		
		Element funURIDescEl = areaEl.addElement("funURIDesc");
		funURIDescEl.addText(mainFrameFun.getFunURIDesc());
		
		Element funMemoEl = areaEl.addElement("funMemo");
		funMemoEl.addText(mainFrameFun.getFunMemo());
		
		XmlOpUtil.doc2XmlFile(document, getResourcePath()+MAINFRAME_FILE_NAME);
		StringBuffer sb = new StringBuffer();
		sb.append("增加的功能区信息 id:"+id+SPLIT).append("funName:"+mainFrameFun.getFunName()+SPLIT)
		.append("funURI:"+mainFrameFun.getFunURI()+SPLIT)
		.append("funURIDesc:"+mainFrameFun.getFunURIDesc()+SPLIT)
		.append("funMemo:"+mainFrameFun.getFunMemo()+SPLIT);
		saveLog(LOG_LEVEL_FIRST, MAIN_PAGE_FUN, SAVE_OPT, sb.toString(), id+"");
	}
	/**
	 * 功能说明：修改功能區
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:31:52 PM
	 */
	public void modifyMainFrameFun(){
		
	}
	/**
	 * 功能说明：刪除功能區
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:32:29 PM
	 */
	public void deleteMainFrameFun(String delRecIds)throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINFRAME_FILE_NAME);
		Element el = (Element)document.selectSingleNode("/module/funArea");
		List<Element> elList = document.selectNodes("//module/funArea/area/funId");
		String[] delIds = delRecIds.split(",");
		for(Element e : elList){
			String text = e.getText();
			for(String id : delIds){
				if(id.equals(text)){
					el.remove(e.getParent());
				}
			}
		}
		XmlOpUtil.doc2XmlFile(document, getResourcePath()+MAINFRAME_FILE_NAME);
		StringBuffer sb = new StringBuffer();
		sb.append("删除功能区的id:"+delRecIds+SPLIT);
		saveLog(LOG_LEVEL_THIRD, MAIN_PAGE_FUN, DELETE_OPT, sb.toString(), "");
	}
	/**
	 * 功能说明：根据名称查找功能区
	 * @author jacobliang
	 * @param	mainFrameName	功能区名称
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public MainFrameFun findMainFrameFunByFunName(String mainFrameName)throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINFRAME_FILE_NAME);
		List<Element> elList = document.selectNodes("//module/funArea/area/funName");
		Element targetEl = null;
		for(Element e : elList){
			String text = e.getText();
			if(mainFrameName.equals(text)){
				targetEl = e.getParent();
				break;
			}
		}
		MainFrameFun mainFrameFun = new MainFrameFun();
		if(targetEl != null){
			String funId = targetEl.elementText("funId");
			String funName = targetEl.elementText("funName");
			String funURI = targetEl.elementText("funURI");
			String funURIDesc = targetEl.elementText("funURIDesc");
			String funMemo = targetEl.elementText("funMemo");
			mainFrameFun.setFunId(funId);
			mainFrameFun.setFunName(funName);
			mainFrameFun.setFunURI(funURI);
			mainFrameFun.setFunMemo(funMemo);
			mainFrameFun.setFunURIDesc(funURIDesc);
		}
		return mainFrameFun;
	}
	
	/**
	 * 功能说明：根据id查找功能区
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public MainFrameFun findMainFrameFunByFunId(String modViewRecId)throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINFRAME_FILE_NAME);
		List<Element> elList = document.selectNodes("//module/funArea/area/funId");
		Element targetEl = null;
		for(Element e : elList){
			String text = e.getText();
			if(modViewRecId.equals(text)){
				targetEl = e.getParent();
				break;
			}
		}
		MainFrameFun mainFrameFun = new MainFrameFun();
		if(targetEl != null){
			String funId = targetEl.elementText("funId");
			String funName = targetEl.elementText("funName");
			String funURIDesc = targetEl.elementText("funURIDesc");
			String funURI = targetEl.elementText("funURI");
			String funMemo = targetEl.elementText("funMemo");
			mainFrameFun.setFunId(funId);
			mainFrameFun.setFunName(funName);
			mainFrameFun.setFunURI(funURI);
			mainFrameFun.setFunMemo(funMemo);
			mainFrameFun.setFunURIDesc(funURIDesc);
		}
		return mainFrameFun;
	}
	
	/**
	 * 功能说明：根据条件查询所有主框架功能区
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 9, 2010 6:37:54 PM
	 */
	public Map findMainFrameFunByCon(int start,int limit,String funName)throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINFRAME_FILE_NAME);
		List<Element> elList = document.selectNodes("//module/funArea/area");
		List<Element> newElList = new ArrayList<Element>();
		if(!isNullOrEmpty(funName)){
			for(Element condEl : elList){
				String funNameTemp = condEl.elementText("funName");
				Pattern p = Pattern.compile(funName);
				Matcher m = p.matcher(funNameTemp);
				boolean b = m.find();
				if(b){
					newElList.add(condEl);
				}
			}
		}else{
			newElList = elList;
		}
		
		List<MainFrameFun> frameList = new ArrayList<MainFrameFun>();
		int count = 0;
		for(Element targetEl : newElList){
			count++;
			if(count >= (start+1) && count <= (limit+start)){
				if(targetEl != null){
					String funId = targetEl.elementText("funId");
					String funNameTemp = targetEl.elementText("funName");
					String funURI = targetEl.elementText("funURI");
					String funMemo = targetEl.elementText("funMemo");
					String funURIDesc = targetEl.elementText("funURIDesc");
					MainFrameFun mainFrameFun = new MainFrameFun();
					mainFrameFun.setFunId(funId);
					mainFrameFun.setFunName(funNameTemp);
					mainFrameFun.setFunURI(funURI);
					mainFrameFun.setFunMemo(funMemo);
					mainFrameFun.setFunURIDesc(funURIDesc);
					frameList.add(mainFrameFun);
				}
			}
		}
		Map map = new HashMap();
		map.put(BUSI_LIST, frameList);
		map.put(TOTAL_PROPERTY, count);
		return map;
	}
	
	/**
	 * 功能说明：获得定义的所有功能区列表
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 19, 2010 7:37:38 PM
	 */
	public Map<String,List<MainFrameFun>> findAllMainFrameFun()throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINFRAME_FILE_NAME);
		List[] list = {new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList()};
		String[] funNameArr = {HEADER,NAVIGATE,OUTLOOK_BAR,MAIN_PAGE,STATE_BAR};
		List<Element> elList = document.selectNodes("//module/funArea/area");
		for(Element targetEl : elList){
			if(targetEl != null){
				String funNameTemp = targetEl.elementText("funName");
				String funURI = targetEl.elementText("funURI");
				String funURIDesc = targetEl.elementText("funURIDesc");
				MainFrameFun mainFrameFun = new MainFrameFun();
				mainFrameFun.setFunName(funNameTemp);
				mainFrameFun.setFunURI(funURI);
				mainFrameFun.setFunURIDesc(funURIDesc);
				if(funNameArr[0].equalsIgnoreCase(funNameTemp)){
					list[0].add(mainFrameFun);
				}else if(funNameArr[1].equalsIgnoreCase(funNameTemp)){
					list[1].add(mainFrameFun);
				}else if(funNameArr[2].equalsIgnoreCase(funNameTemp)){
					list[2].add(mainFrameFun);
				}else if(funNameArr[3].equalsIgnoreCase(funNameTemp)){
					list[3].add(mainFrameFun);
				}else if(funNameArr[4].equalsIgnoreCase(funNameTemp)){
					list[4].add(mainFrameFun);
				}
			}
		}
		Map<String,List<MainFrameFun>> mainFrameFunMap = new HashMap<String,List<MainFrameFun>>();
		for(int i=0;i<list.length;i++){
			mainFrameFunMap.put(funNameArr[i], list[i]);
		}
		return mainFrameFunMap;
	}
	
	public static void main(String[] args){
		System.setProperty("t", "bhtchPltUus");
		System.out.println(System.getProperty("t"));
		System.out.println(new MainFrameFunServiceImpl().getClass().getClassLoader().getResource(""));
	}
}
