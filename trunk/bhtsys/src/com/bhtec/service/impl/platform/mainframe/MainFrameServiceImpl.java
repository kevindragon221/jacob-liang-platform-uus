/**
 *功能说明：
 * @author jacobliang
 * @time @Aug 19, 2010 @8:22:10 PM
 */
package com.bhtec.service.impl.platform.mainframe;

import static com.bhtec.common.constant.ServiceVariable.*;
import static com.bhtec.common.tools.UtilTools.getResourcePath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.domain.pojohelper.platform.mainframe.MainFrame;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.mainframe.MainFrameService;
import com.bhtec.service.iface.platform.mainframefun.MainFrameFunService;
import com.bhtec.service.impl.BaseServiceImpl;

public class MainFrameServiceImpl extends BaseServiceImpl implements MainFrameService {
	private MainFrameFunService mainFrameFunService;
	
	public Map<String, List<MainFrameFun>> findAllMainFrameFun()throws ApplicationException {
		// TODO Auto-generated method stub
		return mainFrameFunService.findAllMainFrameFun();
	}
	
	/**
	 * 功能说明：根据条件查询所有主框架功能区
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 9, 2010 6:37:54 PM
	 */
	public Map<String,String> findMainFrame()throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINF_RAME_FILE_NAME_);
		Element header = (Element)document.selectSingleNode("/module/funArea/area[@name=\"header\"]");
		Element navigate = (Element)document.selectSingleNode("/module/funArea/area[@name=\"navigate\"]");
		Element outlookBar = (Element)document.selectSingleNode("/module/funArea/area[@name=\"outlookBar\"]");
		Element mainPage = (Element)document.selectSingleNode("/module/funArea/area[@name=\"mainPage\"]");
		Element stateBar = (Element)document.selectSingleNode("/module/funArea/area[@name=\"stateBar\"]");
		
		Map<String,String> mainFrameFunMap = new HashMap<String,String>();
		mainFrameFunMap.put(HEADER, header.getText());
		mainFrameFunMap.put(NAVIGATE, navigate.getText());
		mainFrameFunMap.put(OUTLOOK_BAR, outlookBar.getText());
		mainFrameFunMap.put(MAIN_PAGE, mainPage.getText());
		mainFrameFunMap.put(STATE_BAR, stateBar.getText());
		return mainFrameFunMap;
	}
	/**
	 * 功能说明：保存主页模版设置
	 * @author jacobliang
	 * @param mainFrame
	 * @throws 
	 * @time Aug 20, 2010 10:05:02 AM
	 */
	public void saveMainFrame(MainFrame mainFrame)throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+MAINF_RAME_FILE_NAME_);
		Element header = (Element)document.selectSingleNode("/module/funArea/area[@name=\"header\"]");
		Element navigate = (Element)document.selectSingleNode("/module/funArea/area[@name=\"navigate\"]");
		Element outlookBar = (Element)document.selectSingleNode("/module/funArea/area[@name=\"outlookBar\"]");
		Element mainPage = (Element)document.selectSingleNode("/module/funArea/area[@name=\"mainPage\"]");
		Element stateBar = (Element)document.selectSingleNode("/module/funArea/area[@name=\"stateBar\"]");
		header.setText(mainFrame.getHeader());
		outlookBar.setText(mainFrame.getOutlookBar());
		mainPage.setText(mainFrame.getMainPage());
		stateBar.setText(mainFrame.getStateBar());
		navigate.setText(mainFrame.getNavigate());
		XmlOpUtil.doc2XmlFile(document, getResourcePath()+MAINF_RAME_FILE_NAME_);
		StringBuffer sb = new StringBuffer();
		sb.append(HEADER+":"+mainFrame.getHeader()+SPLIT).append(NAVIGATE+":"+mainFrame.getNavigate()+SPLIT)
		.append(MAIN_PAGE+":"+mainFrame.getMainPage()+SPLIT).append(STATE_BAR+":"+mainFrame.getStateBar()+SPLIT)
		.append(OUTLOOK_BAR+":"+mainFrame.getOutlookBar()+SPLIT);
		
		this.saveLog(LOG_LEVEL_THIRD, MAIN_PAGE_SET, SAVE_OPT, sb.toString(), "-1");
	}
	
	public void setMainFrameFunService(MainFrameFunService mainFrameFunService) {
		this.mainFrameFunService = mainFrameFunService;
	}

}
