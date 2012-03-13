/**
 * 功能说明：编码管理
 * @author jacobliang
 */
package com.bhtec.service.iface.platform.code;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bhtec.domain.pojo.platform.SysplCode;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface CodeService extends BaseService {
	/**
	 * 功能说明：保存编码
	 * @author jacobliang
	 * @param sysplCode		编码对象
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveSysplCode(SysplCode sysplCode) throws ApplicationException;
	/**
	 * 功能说明：根据编码ID查询某个编码
	 * @author jacobliang
	 * @param  codeId		编码ID
	 * @return sysplCode	编码对象
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public SysplCode findSysplCodeByCodeId(Long codeId);
	/**
	 * 功能说明：修改编码
	 * @author jacobliang
	 * @param  sysplCode	编码对象
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifySysplCode(SysplCode sysplCode)throws ApplicationException;
	/**
	 * 功能说明：查询编码
	 * @author jacobliang
	 * @param start			开始记录
	 * @param limit			每页记录数
	 * @param codeEngName	英文编码名称
	 * @param codeName		中文编码名称
	 * @param moduleName	模块名称
	 * @return
	 * @time Jan 11, 2011 4:48:48 PM
	 */
	public Map findCodeByCon(int start,int limit,String codeEngName,String codeName,String moduleName);
	/**
	 * 功能说明：停用启用编码
	 * @author jacobliang
	 * @param   disEnableFlag	停用启用标志
	 * @param 	codeId		    编码ID
	 * @return  boolean			true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public boolean disEnableCode(long codeId,String disEnableFlag)throws ApplicationException;
	/**
	 * 功能说明：根据编码英文名称查询编码是否存在
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public boolean findSysplCodeByCodeEngName(String codeEngName);
	/**
	 * 功能说明：根据编码英文名称查询编码
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public String obtainSysplCodeByCodeEngName(String codeEngName,HttpServletRequest request);
}
