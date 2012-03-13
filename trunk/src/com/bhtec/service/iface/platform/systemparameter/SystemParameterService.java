/**
 *功能说明：系统参数
 * @author jacobliang
 * @time @Jul 26, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.platform.systemparameter;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.domain.pojo.platform.SysplSysParameter;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface SystemParameterService extends BaseService {
	/**
	 * 功能说明：保存系统参数
	 * @author jacobliang
	 * @param sysplSysParameter		系统参数对象
	 * @throws ApplicationException
	 * @time Dec 10, 2010 8:52:38 PM
	 */
	public void saveSysplSysParameter(SysplSysParameter sysplSysParameter) throws ApplicationException;
	/**
	 * 功能说明：修改系统参数
	 * @author jacobliang
	 * @param sysplSysParameter 	系统参数对象
	 * @throws ApplicationException
	 * @time Dec 10, 2010 8:54:02 PM
	 */
	public void modifySysplSysParameter(SysplSysParameter sysplSysParameter) throws ApplicationException;
	/**
	 * 功能说明：根据参数ID查询系统参数
	 * @author jacobliang
	 * @param paraId
	 * @return
	 * @time Dec 10, 2010 8:55:41 PM
	 */
	public SysplSysParameter findSysplSysParameterById(long paraId);
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public SysplSysParameter findSystemParaByParaName(String paraKeyName);
	/**
	 * 功能说明：停用启用系统
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  paraId			系统参数ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public boolean disEnablePara(long paraId,String disEnableFlag)throws ApplicationException;
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
	public Map findSystemParaByCon(int start,int limit,String paraName,String paraKeyName);
}
