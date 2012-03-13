/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.platform.module;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface ModuleService extends BaseService {
	/**
	 * 功能说明：保存模块
	 * @author jacobliang
	 * @param sysplModuleMemu	系统模块
	 * @throws 
	 * @time Jul 26, 2010 8:50:21 PM
	 */
	public void saveModule(SysplModuleMemu sysplModuleMemu) throws ApplicationException;
	/**
	 * 功能说明：停用启用模块
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param moduleId	模块ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public boolean disEnableModule(long moduleId,String disEnableFlag)throws ApplicationException;
	/**
	 * 功能说明：删除某个模块
	 * @author jacobliang
	 * @param	moduleId 	模块ID
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 8:51:04 AM
	 */
	public void deleteModuleById(Long moduleId)throws ApplicationException;
	/**
	 * 功能说明：根据操作ID删除模块操作关系,级联删除权限
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return
	 * @time Oct 29, 2010 11:21:03 AM
	 */
	public void deleteModOptRefByOperateId(long operateId)throws ApplicationException;
	/**
	 * 功能说明：根据模块ID查询某个模块
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public SysplModuleMemu findModuleByModuleId(Long moduleId);
	/**
	 * 功能说明：修改模块
	 * @author jacobliang
	 * @param sysplModuleMemu	模块对象
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyModule(SysplModuleMemu sysplModuleMemu)throws ApplicationException;
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @param  moduleName	模块名称
	 * @return boolean true 模块名存在 false 不存在
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public boolean findModuleByModuleName(String moduleName);
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	查询某个树的节点
	 * @param	modName 模块名称
	 * @param	modEnId 模块英文ID
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public Map findModuleByCon(int start,int limit,long treeId,String modName,String modEnId);
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @return List<SysplModuleMemu>	所有模块信息
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplModuleMemu> findAllModule();
	/**
	 * 功能说明：根据模块id查询所有模块子节点
	 * @author jacobliang
	 * @param moduleId		上级模块id
	 * @return
	 * @throws 
	 * @time Aug 15, 2010 9:17:13 PM
	 */
	public List<SysplModuleMemu> findChildNodeList(long moduleId,int flag);
	/**
	 * 功能说明：过滤模块
	 * @author jacobliang
	 * @param	list 	需要过滤的模块列表
	 * @return Map 1:list 2:map 3:map
	 * @time Aug 20, 2010 8:37:50 PM
	 */
	public Map findFilterModuleMenu(List<SysplModuleMemu> list);
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	moduleId		模块id
	 * @param	filterModuleId	过滤模块id,当修改模块菜单时将本模块ID过滤掉
	 * @return  List<TreeVo>	下一级模块菜单
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<TreeVo> findNextLevelChildNodes(long moduleId,long filterModuleId);
	/**
	 * 功能说明：根据模块ID查询已经分配的模块操作
	 * @author  jacobliang
	 * @param 	moduleId			模块ID
	 * @return  List<SysplOperate>	已经分配的模块操作list
	 * @time Oct 29, 2010 11:06:51 AM
	 */
	public List<SysplOperate> findAssignedModOptByModId(long moduleId);
	/**
	 * 功能说明：获得模块已经分配和未分配的操作列表
	 * @author jacobliang
	 * @param moduleId 		模块ID
	 * @return Map<String,List<SysplOperate>>	1 list 分配 2 list 未分配
	 * @time Oct 29, 2010 11:36:03 AM
	 */
	public Map<String,List<SysplOperate>> obtainAssignedAUnAssignedOpt(long moduleId);
	/**
	 * 功能说明：保存模块操作关系
	 * @author jacobliang
	 * @param moduleId			模块ID
	 * @param operateIdList		模块操作列表
	 * @throws ApplicationException
	 * @time Oct 29, 2010 11:38:33 AM
	 */
	public void saveModuleOptRefs(long moduleId,List<Long> operateIdList) throws ApplicationException;
	/**
	 * 功能说明：查询所有模块操作关系记录
	 * @author jacobliang
	 * @return	List<SysplModOptRef>	所有模块操作关系记录
	 * @time Nov 1, 2010 10:01:57 AM
	 */
	public List<SysplModOptRef> findAllSysplModOptRef();
	/**
	 * 功能说明：修改模块标签描述
	 * @author jacobliang
	 * @param xmlFile			文件名路径
	 * @param moduleLabelList	[0]为xml path name [1]为标签描述
	 * @throws ApplicationException
	 * @time Dec 28, 2010 9:47:26 AM
	 */
	public void modifyModuleLabel(String xmlFile,List<String[]> moduleLabelList)throws ApplicationException;
	/**
	 * 功能说明：根据模块关系表ID查询所有模块操作关系
	 * @author jacobliang
	 * @param modOptRefId
	 * @return
	 * @time Feb 7, 2012
	 */
	public List<SysplModOptRef> findSysplModOptRefByModOptRefIds(String modOptRefId);
}
