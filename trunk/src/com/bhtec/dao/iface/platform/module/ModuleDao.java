/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.module;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.exception.ApplicationException;

public interface ModuleDao extends BaseDao {
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public int findModuleByModuleName(String moduleName);
	/**
	 * 功能说明：查询指定模块的下级
	 * @author jacobliang
	 * @param
	 * @param
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public int findDownModuleById(long moduleId);
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public Map findModuleByCon(int start,int limit,String ids,String modName,String modEnId);
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplModuleMemu> findAllModule();
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	moduleId	模块id
	 * @return
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<SysplModuleMemu> findNextLevelChildNodes(long moduleId,long filterModuleId);
	/**
	 * 功能说明：根据模块ID查询已经分配的模块操作
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return	List<SysplModOptRef>
	 * @time Oct 29, 2010 11:06:51 AM
	 */
	public List<SysplModOptRef> findAssignedModOptByModId(long moduleId);
	/**
	 * 功能说明：根据模块ID删除模块操作关系,级联删除权限
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return
	 * @time Oct 29, 2010 11:21:03 AM
	 */
	public void deleteModOptRefByModId(long moduleId);
	/**
	 * 功能说明：根据操作ID删除模块操作关系,级联删除权限
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return
	 * @time Oct 29, 2010 11:21:03 AM
	 */
	public void deleteModOptRefByOperateId(long operateId);
	/**
	 * 功能说明：查询所有模块操作关系记录
	 * @author jacobliang
	 * @return
	 * @time Nov 1, 2010 10:01:57 AM
	 */
	public List<SysplModOptRef> findAllSysplModOptRef();
	/**
	 * 功能说明：查询当前模块的所有操作
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @time Nov 4, 2010 4:45:27 PM
	 */
	public List<SysplModOptRef> findAllSysplModOptRefByModuleId(long moduleId);
	/**
	 * 功能说明：修改行权限
	 * @author jacobliang
	 * @param moduleIds 模块集合
	 * @param rowPrivilegeLevel	权限类型
	 * @time Nov 7, 2010 2:34:01 PM
	 */
	public void modifyModuleRowPrivilege(String moduleIds,String rowPrivilegeLevel);
	/**
	 * 功能说明：根据模块关系表ID查询所有模块操作关系
	 * @author jacobliang
	 * @param modOptRefId
	 * @return
	 * @time Feb 7, 2012
	 */
	public List<SysplModOptRef> findSysplModOptRefByModOptRefIds(String modOptRefId);
}
