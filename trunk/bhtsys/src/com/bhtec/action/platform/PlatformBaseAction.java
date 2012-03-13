package com.bhtec.action.platform;

import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.BaseAction;
import com.bhtec.common.constant.ActionVariable;
import com.bhtec.common.tools.UtilTools;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.BaseService;

/**
 *功能描述：
 *@since   Mar 18, 2010 9:46:42 PM
 *@author  jacobliang
 *@version 1.0
 */
public class PlatformBaseAction extends BaseAction {
	private static final long serialVersionUID = 100000L;
	Logger log = Logger.getLogger(this.getClass());
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

}
