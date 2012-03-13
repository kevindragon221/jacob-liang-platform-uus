/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 26, 2010 @11:24:54 AM
 */
package com.bhtec.service.impl.commonused;

import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.USER_COMM_FUN;

import java.util.ArrayList;
import java.util.List;

import com.bhtec.dao.iface.commonused.usercommfun.UserCommFunDao;
import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.domain.pojo.uum.UumUserCommfunId;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.commonused.UserCommFunService;
import com.bhtec.service.impl.BaseServiceImpl;

public class UserCommFunServiceImpl extends BaseServiceImpl implements
		UserCommFunService {
	private UserCommFunDao userCommFunDao;
	
	/**
	 * 功能说明：根据用户ID查询用户常用功能
	 * @author jacobliang
	 * @param userId	用户ID
	 * @return
	 * @time Nov 26, 2010 11:00:32 AM
	 */
	public List<UumUserCommfun> findUserCommFunByUserId(long userId){
		return userCommFunDao.findUserCommFunByUserId(userId);
	}
	/**
	 * 功能说明：保存用户常用功能
	 * @author jacobliang
	 * @param userId		用户ID
	 * @param moduleIdList		多模块ID
	 * @throws ApplicationException
	 * @time Nov 26, 2010 11:23:16 AM
	 */
	public void saveUserCommFun(long userId,List<Long> moduleIdList)throws ApplicationException{
		this.userCommFunDao.deleteUserCommFun(userId);//首先删除相关常用功能
		String moduleIds = "";
		if(moduleIdList != null && moduleIdList.size()>0){
			List<UumUserCommfun> uumUserCommfunList = new ArrayList<UumUserCommfun>();
			for(long moduleId:moduleIdList){
				UumUserCommfun uumUserCommfun = new UumUserCommfun();
				UumUserCommfunId id = new UumUserCommfunId();
				id.setUserId(userId);
				id.setModuleId(moduleId);
				uumUserCommfun.setId(id);
				moduleIds = "".equals(moduleIds)?moduleId+"":moduleIds+","+moduleId;
				uumUserCommfunList.add(uumUserCommfun);
			}
			this.userCommFunDao.batchSave(uumUserCommfunList);
		}
		StringBuffer optContent = new StringBuffer(); 
		optContent.append("用户常用功能保存:用户ID" + userId+SPLIT).append("模块ID："+moduleIds);
		this.saveLog(LOG_LEVEL_FIRST, USER_COMM_FUN, SAVE_OPT, optContent.toString(), "");
	}
	
	/**
	 * 功能说明：删除用户无用常用功能
	 * @author jacobliang
	 * @param 	uumUserCommfunList		 用户常用功能列表
	 * @throws ApplicationException
	 * @time Nov 26, 2010 10:57:57 AM
	 */
	public void deleteUserCommFunById(List<UumUserCommfun> uumUserCommfunList)throws ApplicationException{
		if(!uumUserCommfunList.isEmpty())
			this.userCommFunDao.deleteAll(uumUserCommfunList);
	}
	
	/**
	 * 功能说明：用户停用时删除用户常用功能
	 * @author jacobliang
	 * @param userId
	 * @time Mar 2, 2012 10:51:22 AM
	 */
	public void deleteUserCommFunByUserId(long userId)throws ApplicationException{
		userCommFunDao.deleteUserCommFun(userId);
	}
	public void setUserCommFunDao(UserCommFunDao userCommFunDao) {
		this.userCommFunDao = userCommFunDao;
	}

}
