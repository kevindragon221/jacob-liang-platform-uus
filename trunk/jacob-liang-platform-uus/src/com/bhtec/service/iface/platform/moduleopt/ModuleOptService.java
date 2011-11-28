/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.platform.moduleopt;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface ModuleOptService extends BaseService {
	/**
	 * 功能说明：保存模块操作
	 * @author jacobliang
	 * @param sysplOperate		模块操作对象
	 * @throws ApplicationException
	 * @time Oct 28, 2010 11:38:05 AM
	 */
	public void saveModuleOpt(SysplOperate sysplOperate) throws ApplicationException;
	/**
	 * 功能说明：停用启用模块操作
	 * @author jacobliang
	 * @param operateId		模块操作ID
	 * @param disEnableFlag	停用启用标志
	 * @return	boolean true 为停用启用成功
	 * @throws ApplicationException
	 * @time Oct 28, 2010 11:40:56 AM
	 */
	public boolean disEnableModuleOpt(long operateId,String disEnableFlag)throws ApplicationException;
	/**
	 * 功能说明：根据模块操作ID查询某个模块操作
	 * @author jacobliang
	 * @param operateId		模块操作ID
	 * @return SysplOperate	操作对象
	 * @time Oct 28, 2010 11:44:12 AM
	 */
	public SysplOperate findModuleOptByOperateId(Long operateId);
	/**
	 * 功能说明：修改模块操作
	 * @author jacobliang
	 * @param sysplOperate	模块操作对象
	 * @return
	 * @throws 
	 * @time Oct 28, 2010 11:44:12 AM
	 */
	public void modifyModuleOpt(SysplOperate sysplOperate)throws ApplicationException;
	/**
	 * 功能说明：查询模块操作名称是否重复
	 * @author jacobliang
	 * @param moduleOptName	模块操作名称
	 * @return	true 存在 false 不存在
	 * @time Oct 28, 2010 11:49:07 AM
	 */
	public boolean findModuleOptByName(String moduleOptName);
	/**
	 * 功能说明：根据条件查询模块操作
	 * @author jacobliang
	 * @param start			开始记录数
	 * @param limit			每页记录数
	 * @param modOptName	模块操作名称
	 * @return	Map 1.list 2.count
	 * @time Oct 28, 2010 11:05:05 AM
	 */
	public Map findModuleOptByCon(int start,int limit,String modOptName);
	/**
	 * 功能说明：查询所有模块操作信息
	 * @author jacobliang
	 * @return	List<SysplOperate> 	所有模块操作对象
	 * @throws 
	 * @time Oct 28, 2010 9:28:04 PM
	 */
	public List<SysplOperate> findAllModuleOpt();	
}
