package com.bhtec.service.impl;

import static com.bhtec.common.constant.Common.ORGAN_NAME;
import static com.bhtec.common.constant.Common.ROLE_NAME;
import static com.bhtec.common.constant.Common.USER_NAME;
import static com.bhtec.common.constant.PojoVariable.SYSPL_SYS_OPT_LOG;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bhtec.common.constant.ActionVariable;
import com.bhtec.common.tools.UtilTools;
import com.bhtec.common.util.XMLSystemConifg;
import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplSysOptLog;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.BaseService;
import com.bhtec.service.iface.platform.logger.SysPlLoggerService;

/**
 *功能描述：
 *创建时间：Feb 24, 2010 4:29:00 PM 
 *@author lianglp
 *@version 1.0
 */

public class BaseServiceImpl implements BaseService {
	Logger log = Logger.getLogger(this.getClass()); 
	private SysPlLoggerService sysPlLoggerService;
	private BaseDao baseDao;
	private HttpServletRequest request;
	
	public synchronized long getPrimaryKeyByPojoName(String pojoName){
		ServletContext servletContext = request.getSession().getServletContext();
		Map<String,Long> map = (Map<String,Long>)servletContext.getAttribute(ActionVariable.SEQ_MAP);
		if(UtilTools.isNull(map))
			throw new NullPointerException("in getPrimaryKeyByPojoName map is null");
		Long maxId = (Long)map.get(pojoName);
		maxId = maxId==null?0:maxId;
		Long currId = maxId.longValue()+1;
		map.put(pojoName, currId);
		log.debug("pojoName is "+pojoName+" and ID is " + currId);
		return currId;
	}
	
	/**
	 * 功能说明：公用记录系统操作日志
	 * @author jacobliang
	 * @param
	 * @param
	 * @param optModName
	 * @param optName
	 * @param optContent
	 * @param optBusinessId
	 * @throws 
	 * @time Sep 11, 2010 11:21:08 AM
	 */
	public void saveLog(String level,String optModName,String optName,
						String optContent,String optBusinessId)throws ApplicationException{
		String sysConfigLevel = XMLSystemConifg.getInstance().getSystemConfigText("/systemConfig/logger/level");
		String[] levelArr = {level,sysConfigLevel};
		for(String alevel:levelArr){//检查日志级别是否合法
			if("".equals(alevel) || alevel == null || 
					"0123".indexOf(alevel) < 0 || alevel.length() > 1){
				throw new ApplicationException("日志级别错误,值为:" + alevel);
			}
		}
		int iLevel = Integer.parseInt(level);
		int iSysConfigLevel = Integer.parseInt(sysConfigLevel);
		if(iSysConfigLevel == 0){
			return;
		}else{
			if(iLevel < iSysConfigLevel)return;
		}
		if(request == null){
			log.error(new SystemException("request is null."));
			throw new SystemException("request is null.");
		}
		String ipAddr = request.getRemoteAddr();
		InetAddress IP = null;
		try {
			IP = InetAddress.getByName(ipAddr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("unknown IP"+IP+". "+e.toString());
		}
		HttpSession session = request.getSession();
		//得到计算机名称
		String computName = IP.getHostName();
		SysplSysOptLog sysplSysOptLog = new SysplSysOptLog();
		long id = this.getPrimaryKeyByPojoName(SYSPL_SYS_OPT_LOG);
		sysplSysOptLog.setOptId(id);
		sysplSysOptLog.setOptModName(optModName);
		sysplSysOptLog.setOptName(optName);
		sysplSysOptLog.setOptContent(optContent);
		sysplSysOptLog.setOptBusinessId(optBusinessId);
		sysplSysOptLog.setOptTime(new Date());
		sysplSysOptLog.setOptPcName(computName);
		sysplSysOptLog.setOptPcIp(ipAddr);
		sysplSysOptLog.setOptUserName((String)session.getAttribute(USER_NAME));
		sysplSysOptLog.setOptUserRole((String)session.getAttribute(ROLE_NAME));
		sysplSysOptLog.setOptUserOgan((String)session.getAttribute(ORGAN_NAME));
		this.sysPlLoggerService.saveLog(sysplSysOptLog);
	}
	
	public void setSysPlLoggerService(SysPlLoggerService sysPlLoggerService) {
		this.sysPlLoggerService = sysPlLoggerService;
	}

	public void setHttpServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
}
