/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.moduleopt;

import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.SYS_MODOPT_MGR;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.platform.moduleopt.ModuleOptDao;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.module.ModuleService;
import com.bhtec.service.iface.platform.moduleopt.ModuleOptService;
import com.bhtec.service.impl.BaseServiceImpl;

public class ModuleOptServiceImpl extends BaseServiceImpl implements ModuleOptService {
	Logger log = Logger.getLogger(this.getClass());
	private ModuleOptDao moduleOptDao;
	private ModuleService moduleService;
	
	/**
	 * 功能说明：保存模块操作
	 * @author jacobliang
	 * @param sysplOperate		模块操作对象
	 * @throws ApplicationException
	 * @time Oct 28, 2010 11:38:05 AM
	 */
	public void saveModuleOpt(SysplOperate sysplOperate)throws ApplicationException{
		moduleOptDao.save(sysplOperate);
		writeLog(sysplOperate,LOG_LEVEL_FIRST,SAVE_OPT);
	}
	/**
	 * 功能说明：停用启用模块操作
	 * @author jacobliang
	 * @param operateId		模块操作ID
	 * @param disEnableFlag	停用启用标志
	 * @return	boolean true 为停用启用成功
	 * @throws ApplicationException
	 * @time Oct 28, 2010 11:40:56 AM
	 */
	public boolean disEnableModuleOpt(long operateId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			SysplOperate sysplOperate = findModuleOptByOperateId(operateId);
			sysplOperate.setStatus(DISABLE);
			this.modifyModuleOpt(sysplOperate);//修改模块操作
			moduleService.setHttpServletRequest(this.getRequest());
			moduleService.deleteModOptRefByOperateId(operateId);//根据操作ID删除模块操作关系,级联删除权限
			this.writeLog(sysplOperate, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}else{
			SysplOperate sysplOperate = findModuleOptByOperateId(operateId);
			sysplOperate.setStatus(ENABLE);
			this.modifyModuleOpt(sysplOperate);
			this.writeLog(sysplOperate, LOG_LEVEL_SECOND, ENABLE_DIS);
			return true;
		}
		
	}
	/**
	 * 功能说明：根据模块操作ID查询某个模块操作
	 * @author jacobliang
	 * @param operateId		模块操作ID
	 * @return SysplOperate	操作对象
	 * @time Oct 28, 2010 11:44:12 AM
	 */
	public SysplOperate findModuleOptByOperateId(Long operateId){
		log.debug("will find moduleopt id is "+operateId);
		SysplOperate SysplOperate = (SysplOperate)moduleOptDao
						.getPojoById("com.bhtec.domain.pojo.platform.SysplOperate", operateId);
		return SysplOperate;
	}
	/**
	 * 功能说明：修改模块操作
	 * @author jacobliang
	 * @param sysplOperate	模块操作对象
	 * @return
	 * @throws 
	 * @time Oct 28, 2010 11:44:12 AM
	 */
	public void modifyModuleOpt(SysplOperate sysplOperate)throws ApplicationException{
		moduleOptDao.update(sysplOperate);
		this.writeLog(sysplOperate, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	/**
	 * 功能说明：查询模块操作名称是否重复
	 * @author jacobliang
	 * @param moduleOptName	模块操作名称
	 * @return	true 存在 false 不存在
	 * @time Oct 28, 2010 11:49:07 AM
	 */
	public boolean findModuleOptByName(String moduleOptName){
		int totalProperty = moduleOptDao.findModuleOptByName(moduleOptName);
		log.debug("find module total is "+totalProperty);
		if(totalProperty > 0){
			return true;
		}
		return false;
	}
	/**
	 * 功能说明：根据条件查询模块操作
	 * @author jacobliang
	 * @param start			开始记录数
	 * @param limit			每页记录数
	 * @param modOptName	模块操作名称
	 * @return	Map 1.list 2.count
	 * @time Oct 28, 2010 11:05:05 AM
	 */
	public Map findModuleOptByCon(int start,int limit,String modOptName){
		log.debug("start="+ start+" limit="+ limit+" modOptName"+ modOptName);
		return moduleOptDao.findModuleOptByCon(start, limit, modOptName);
	}
	/**
	 * 功能说明：查询所有模块操作信息
	 * @author jacobliang
	 * @return	List<SysplOperate> 	所有模块操作对象
	 * @throws 
	 * @time Oct 28, 2010 9:28:04 PM
	 */
	public List<SysplOperate> findAllModuleOpt(){
		List<SysplOperate> list = moduleOptDao.findAllModuleOpt();
		return list;
	}
	
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param sysplOperate	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(SysplOperate sysplOperate,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append(sysplOperate.getOperateName()+SPLIT).append(sysplOperate.getOptImgLink()+SPLIT)
		.append(sysplOperate.getOptFunLink()+SPLIT);
		saveLog(logLevel, SYS_MODOPT_MGR,opt,logContent.toString(), sysplOperate.getOperateId()+"");
	}

	public void setModuleOptDao(ModuleOptDao moduleOptDao) {
		this.moduleOptDao = moduleOptDao;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
}
