/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 21, 2010 @1:49:07 PM
 */
package com.bhtec.service.impl.platform.systemconfig;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.common.util.XMLSystemConifg;
import com.bhtec.domain.pojohelper.platform.systemconfig.SystemConfig;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.systemconfig.SystemConfigService;
import com.bhtec.service.impl.BaseServiceImpl;
import static com.bhtec.common.constant.ServiceVariable.*;

public class SystemConfigServiceImpl extends BaseServiceImpl implements
		SystemConfigService {
	/**
	 * 功能说明：获得系统配置信息对象
	 * @author jacobliang
	 * @return
	 * @time Nov 21, 2010 1:41:21 PM
	 */
	public SystemConfig obtainSystemConfigInfo() throws ApplicationException{
		XMLSystemConifg systemConifg = XMLSystemConifg.getInstance();
		String systemName = systemConifg.getSystemConfigText("/systemConfig/systemName/name");
		List<String> systemAdminCode = systemConifg.getSysytemConfigTextList("//systemConfig/systemMgr/userCode");
		String roleChange = systemConifg.getSystemConfigText("/systemConfig/roleChange/type");
		String logLevel = systemConifg.getSystemConfigText("/systemConfig/logger/level");
		String logKeepDays = systemConifg.getSystemConfigText("/systemConfig/logKeepDays/days");
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setSystemName(systemName);
		systemConfig.setSystemAdminCode(systemAdminCode);
		systemConfig.setRoleChange(roleChange);
		systemConfig.setLogLevel(logLevel);
		systemConfig.setLogKeepDays(logKeepDays);
		return systemConfig;
	}
	/**
	 * 功能说明：设置系统配置信息
	 * @author jacobliang
	 * @param systemConfig	系统配置对象
	 * @throws ApplicationException
	 * @time Nov 21, 2010 1:47:51 PM
	 */
	@SuppressWarnings("unchecked")
	public void setSystemConfigInfo(SystemConfig systemConfig,String userAddDelFlag)
			throws ApplicationException {
		XMLSystemConifg systemConifg = XMLSystemConifg.getInstance();
		Document doc = systemConifg.getSystemConfigDoc();
		StringBuffer optContent = new StringBuffer();
		if(systemConfig.getSystemName() != null){//系统名称
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/systemName/name");
			el.setText(systemConfig.getSystemName());
			optContent.append("设置系统名称:"+systemConfig.getSystemName()+SPLIT);
		}else if(systemConfig.getSystemAdminCode() != null){//管理员列表
			if(ADD.equals(userAddDelFlag)){//增加
				Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/systemMgr");
				for(String adminCode : systemConfig.getSystemAdminCode()){
					Element userCodeEl = el.addElement("userCode");
					userCodeEl.setText(adminCode);
					optContent.append("增加管理员:"+adminCode+SPLIT);
				}
			}else if(DELETE.equals(userAddDelFlag)){//删除
				Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/systemMgr");
				List<Element> elList = doc.selectNodes("//systemConfig/systemMgr/userCode");
				for(String adminCode : systemConfig.getSystemAdminCode()){
					for(Element ell:elList){
						if(ell.getText().equals(adminCode)){
							el.remove(ell);
						}
						optContent.append("删除管理员:"+adminCode+SPLIT);
					}
				}
			}
		}else if(systemConfig.getRoleChange() != null){//角色切换
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/roleChange/type");
			el.setText(systemConfig.getRoleChange());
			optContent.append("设置角色切换:"+systemConfig.getRoleChange()+SPLIT);
		}else if(systemConfig.getLogLevel() != null){//日志级别
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/logger/level");
			el.setText(systemConfig.getLogLevel());
			optContent.append("设置日志级别:"+systemConfig.getLogLevel()+SPLIT);
		}else if(systemConfig.getLogKeepDays() != null){//日志保留天数
			Element el = XmlOpUtil.selectSingleNode(doc,"/systemConfig/logKeepDays/days");
			el.setText(systemConfig.getLogKeepDays());
			optContent.append("设置日志保留天数:"+systemConfig.getLogKeepDays()+SPLIT);
		}
		systemConifg.writeConfigInfoToXML(doc);
		
		this.saveLog(LOG_LEVEL_THIRD, SYS_CONFIG_MGR, SAVE_OPT, optContent.toString(), "");
	}

}
