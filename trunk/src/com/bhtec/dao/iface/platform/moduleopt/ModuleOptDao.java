/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.moduleopt;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;

public interface ModuleOptDao extends BaseDao {
	/**
	 * 功能说明：根据模块操作名查询模块操作数量
	 * @author jacobliang
	 * @param moduleOptName		模块操作名称
	 * @return
	 * @time Oct 28, 2010 11:01:38 AM
	 */
	public int findModuleOptByName(String moduleOptName);
	
	/**
	 * 功能说明：根据条件查询模块操作
	 * @author jacobliang
	 * @param start			开始记录数
	 * @param limit			每页记录数
	 * @param modOptName	模块操作名称
	 * @return
	 * @time Oct 28, 2010 11:05:05 AM
	 */
	public Map findModuleOptByCon(int start,int limit,String modOptName);
	/**
	 * 功能说明：查询所有的模块操作
	 * @author jacobliang
	 * @return
	 * @time Oct 28, 2010 11:06:52 AM
	 */
	public List<SysplOperate> findAllModuleOpt();	
}
