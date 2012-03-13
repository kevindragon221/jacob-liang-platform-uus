/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.systemparameter;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.SYSTEM_TYPE;
import static com.bhtec.common.constant.ServiceVariable.SYS_PARAMETER;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.platform.systemparameter.SystemParameterDao;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;
import com.bhtec.domain.pojo.platform.SysplSysParameter;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.TypeDictionary.TypeDictionaryService;
import com.bhtec.service.iface.platform.systemparameter.SystemParameterService;
import com.bhtec.service.impl.BaseServiceImpl;

public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {
	Logger log = Logger.getLogger(this.getClass());
	private SystemParameterDao systemParameterDao;
	private TypeDictionaryService typeDictionaryService;
	/**
	 * 功能说明：保存系统参数
	 * @author jacobliang
	 * @param sysplSysParameter		系统参数对象
	 * @throws ApplicationException
	 * @time Dec 10, 2010 8:52:38 PM
	 */
	public void saveSysplSysParameter(SysplSysParameter sysplSysParameter) throws ApplicationException{
		this.systemParameterDao.save(sysplSysParameter);
		this.writeLog(sysplSysParameter, LOG_LEVEL_FIRST, SAVE_OPT);
	}
	/**
	 * 功能说明：修改系统参数
	 * @author jacobliang
	 * @param sysplSysParameter 	系统参数对象
	 * @throws ApplicationException
	 * @time Dec 10, 2010 8:54:02 PM
	 */
	public void modifySysplSysParameter(SysplSysParameter sysplSysParameter) throws ApplicationException{
		this.systemParameterDao.update(sysplSysParameter);
		this.writeLog(sysplSysParameter, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	/**
	 * 功能说明：根据参数ID查询系统参数
	 * @author jacobliang
	 * @param paraId
	 * @return
	 * @time Dec 10, 2010 8:55:41 PM
	 */
	public SysplSysParameter findSysplSysParameterById(long paraId){
		return (SysplSysParameter)this.systemParameterDao.getPojoById("com.bhtec.domain.pojo.platform.SysplSysParameter", paraId);
	}
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public SysplSysParameter findSystemParaByParaName(String paraName){
		return this.systemParameterDao.findSystemParaByParaName(paraName);
	}
	
	/**
	 * 功能说明：停用启用系统
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  paraId			系统参数ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public boolean disEnablePara(long paraId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			SysplSysParameter sysplSysParameter = this.findSysplSysParameterById(paraId);
			sysplSysParameter.setStatus(DISABLE);
			this.modifySysplSysParameter(sysplSysParameter);
			this.writeLog(sysplSysParameter, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}else{
			SysplSysParameter sysplSysParameter = this.findSysplSysParameterById(paraId);
			sysplSysParameter.setStatus(ENABLE);
			this.modifySysplSysParameter(sysplSysParameter);
			this.writeLog(sysplSysParameter, LOG_LEVEL_SECOND, ENABLE_DIS);
			return true;
		}
		
	}
	
	
	/**
	 * 功能说明：根据系统参数名称及键值查询系统参数
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param paraName
	 * @param paraKeyName
	 * @return
	 * @time Dec 10, 2010 8:37:29 PM
	 */
	public Map findSystemParaByCon(int start,int limit,String paraName,String paraKeyName){
		Map map =  this.systemParameterDao.findSystemParaByCon(start, limit, paraName, paraKeyName);
		return map;
	}
	
	
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param sysplModuleMemu	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(SysplSysParameter sysplSysParameter,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append("参数名称："+sysplSysParameter.getParaName()+SPLIT)
		.append("参数键值："+sysplSysParameter.getParaKeyName()+SPLIT)
		.append("参数值："+sysplSysParameter.getParaValue())
		.append("参数类型："+sysplSysParameter.getParaType());
		saveLog(logLevel, SYS_PARAMETER,opt,logContent.toString(), sysplSysParameter.getParaId()+"");
	}
	
	public void setSystemParameterDao(SystemParameterDao systemParameterDao) {
		this.systemParameterDao = systemParameterDao;
	}
	public void setTypeDictionaryService(TypeDictionaryService typeDictionaryService) {
		this.typeDictionaryService = typeDictionaryService;
	}
	
}
