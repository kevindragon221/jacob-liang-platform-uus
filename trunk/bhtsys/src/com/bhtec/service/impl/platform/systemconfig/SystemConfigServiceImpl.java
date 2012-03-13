/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 21, 2010 @1:49:07 PM
 */
package com.bhtec.service.impl.platform.systemconfig;

import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.SYS_CONFIGFILE_XML;
import static com.bhtec.common.constant.ServiceVariable.ADD;
import static com.bhtec.common.constant.ServiceVariable.DELETE;
import static com.bhtec.common.constant.ServiceVariable.SYS_CONFIG_MGR;
import static com.bhtec.common.tools.UtilTools.getClusterResourcePath;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.common.util.SystemConfigBean;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.systemconfig.SystemConfigService;
import com.bhtec.service.impl.BaseServiceImpl;

public class SystemConfigServiceImpl extends BaseServiceImpl implements
		SystemConfigService {
	/**
	 * 功能说明：获得系统配置信息对象
	 * @author jacobliang
	 * @return
	 * @time Nov 21, 2010 1:41:21 PM
	 */
	public SystemConfigBean obtainSystemConfigInfo() throws ApplicationException{
		return SystemConifgXmlParse.getInstance().getSystemConfigBean();
	}
	/**
	 * 功能说明：设置系统配置信息
	 * @author jacobliang
	 * @param systemConfigBean	系统配置对象
	 * @throws ApplicationException
	 * @time Nov 21, 2010 1:47:51 PM
	 */
	@SuppressWarnings("unchecked")
	public void setSystemConfigInfo(SystemConfigBean systemConfigBean,String userAddDelFlag)
			throws ApplicationException {
		String fileName = getClusterResourcePath(SYS_CONFIGFILE_XML);
		String loggerLevel = systemConfigBean.getLoggerLevel();
		String logRecordKeepDays = systemConfigBean.getLogRecordKeepDays();
		List<String> systemMgr = systemConfigBean.getSystemMgr();
		String systemName = systemConfigBean.getSystemName();
		String rowPrivilegeLevel = systemConfigBean.getRowPrivilegeLevel();
		
		Document doc = XmlOpUtil.loadXMLFile(fileName);
		StringBuffer optContent = new StringBuffer();
		
		if(systemName != null){//系统名称
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/systemName");
			el.setText(systemName);
			optContent.append("设置系统名称:"+systemName+SPLIT);
		}else if(systemMgr != null){//管理员列表
			if(ADD.equals(userAddDelFlag)){//增加
				Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/systemMgr");
				for(String adminCode : systemConfigBean.getSystemMgr()){
					Element userCodeEl = el.addElement("userCode");
					userCodeEl.setText(adminCode);
					optContent.append("增加管理员:"+adminCode+SPLIT);
				}
			}else if(DELETE.equals(userAddDelFlag)){//删除
				Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/systemMgr");
				List<Element> elList = doc.selectNodes("//systemConfig/systemMgr/userCode");
				for(String adminCode : systemConfigBean.getSystemMgr()){
					for(Element ell:elList){
						if(ell.getText().equals(adminCode)){
							el.remove(ell);
						}
						optContent.append("删除管理员:"+adminCode+SPLIT);
					}
				}
			}
		}else if(loggerLevel != null){//日志级别
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/loggerLevel");
			el.setText(loggerLevel);
			optContent.append("设置日志级别:"+loggerLevel+SPLIT);
		}else if(rowPrivilegeLevel != null){//权限级别
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/rowPrivilegeLevel");
			el.setText(rowPrivilegeLevel);
			optContent.append("设置权限级别:"+rowPrivilegeLevel+SPLIT);
		}else if(logRecordKeepDays != null){//日志保留天数
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/logRecordKeepDays");
			el.setText(logRecordKeepDays);
			optContent.append("设置日志保留天数:"+logRecordKeepDays+SPLIT);
		}
		XmlOpUtil.doc2XmlFile(doc, fileName);
		
		this.saveLog(LOG_LEVEL_THIRD, SYS_CONFIG_MGR, SAVE_OPT, optContent.toString(), "");
	}

}
