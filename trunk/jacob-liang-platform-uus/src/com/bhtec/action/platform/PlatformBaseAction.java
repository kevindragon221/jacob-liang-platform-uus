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
	
	/**
	 * 
	 *功能描述：
	 *@since Mar 18, 2010  9:48:23 PM
	 *@author jacobliang
	 *@version 1.0
	 *@param
	 *@return
	 */
	public synchronized long getPrimaryKeyByPojoName(String pojoName){
		Map map = (Map)this.getServletContext().getAttribute(ActionVariable.SEQ_MAP);
		if(UtilTools.isNull(map)){
			log.error("in getPrimaryKeyByPojoName map is null", new SystemException());
			throw new SystemException("in getPrimaryKeyByPojoName map is null");
		}
		if(UtilTools.isNullOrEmpty(pojoName)){
			log.error("in getPrimaryKeyByPojoName pojoName is null", new SystemException());
			throw new SystemException("in getPrimaryKeyByPojoName pojoName is null");
		}
		Long maxId = (Long)map.get(pojoName);
		maxId = maxId==null?0:maxId;
		Long currId = maxId.longValue()+1;
		map.put(pojoName, currId);
		log.info("pojoName is "+pojoName+" and ID is " + currId);
		return currId;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

}
