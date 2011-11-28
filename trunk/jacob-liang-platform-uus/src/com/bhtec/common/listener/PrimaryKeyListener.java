package com.bhtec.common.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bhtec.common.constant.ActionVariable;
import com.bhtec.common.constant.ServiceVariable;
import com.bhtec.common.tools.SpringContextUtil;
import com.bhtec.service.iface.platform.primarykey.PrimaryKeySeqService;

/**
 *功能描述：用于生成主键初始化
 *@author  jacobliang
 *@version 1.0
 */
public class PrimaryKeyListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		initSeqenceMap(sce);
	}
	
	/**
	 * 
	 *功能描述：初始化主键
	 *@since Mar 17, 2010  9:31:59 PM
	 *@author jacobliang
	 *@version 1.0
	 *@param
	 *@return
	 */
	private void initSeqenceMap(ServletContextEvent sce){
		PrimaryKeySeqService primaryKeySeqServic = (PrimaryKeySeqService) SpringContextUtil
				.getBean(ServiceVariable.PRIMARYKEY_SEQ_SERVICE);
		ServletContext context = sce.getServletContext();
		Map<String,Long> seqMap = primaryKeySeqServic.getSeqMap();
		context.setAttribute(ActionVariable.SEQ_MAP, seqMap);		
	}

}
