/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.module;

import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.SYSPL_MODULE_MEMU;
import static com.bhtec.common.tools.UtilTools.isEmpty;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.module.ModuleDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;

public class ModuleDaoHibImpl extends BaseDaoHibImpl implements ModuleDao {
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public int findModuleByModuleName(String moduleName){
		String queryString = "select count(*) from SysplModuleMemu module " +
							 "where module.modName = ?";;
		List params = new ArrayList();
		params.add(moduleName);
		int totalProperty = this.getRowCount(queryString,params);//总记录数
		return totalProperty;
	}
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
	public int findDownModuleById(long moduleId){
		String queryString = "select count(*) from SysplModuleMemu module where module.sysplModuleMemu.moduleId = " + moduleId;
		int totalProperty = this.getRowCount(queryString);//总记录数
		return totalProperty;
	}
	
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public Map findModuleByCon(int start,int limit,String ids,String modName,String modEnId){
		String queryString = "from SysplModuleMemu module where module.moduleId != 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(modName)){
			queryString += " and module.modName like ?";
			params.add("%"+modName+"%");
		}
		if(!isNullOrEmpty(modEnId)){
			queryString += " and module.modEnId like ?";
			params.add("%"+modEnId+"%");
		}
		if(!isEmpty(ids)){
			queryString += " and module.moduleId in (" + ids+") ";
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by module.moduleId desc"; 
		List<SysplModuleMemu> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString, params);//分页
		int totalProperty = this.getRowCount(countSql, params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	public List<SysplModuleMemu> findAllModule() {
		List<SysplModuleMemu> moduleList = this.getAll(SYSPL_MODULE_MEMU,
				" order by sysplModuleMemu.moduleId,moduleId,modOrder");
		return moduleList;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	moduleId	模块id
	 * @return
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<SysplModuleMemu> findNextLevelChildNodes(long moduleId,long filterModuleId){
		String filterSql = " and sysplModuleMemu.moduleId not in (0";
		if(filterModuleId != 0){
			filterSql += ","+filterModuleId;
		}
		filterSql += ")";
		String queryString = "from SysplModuleMemu sysplModuleMemu where sysplModuleMemu.sysplModuleMemu.moduleId = ? " +
							 filterSql+" order by modLevel,modOrder";
		List<SysplModuleMemu> list = this.findByPropertyWithParas(queryString, new Long[]{moduleId});
		return list;
	}
	
	public List<SysplModOptRef> findAssignedModOptByModId(long moduleId){
		String queryString = "from SysplModOptRef mor where mor.sysplModuleMemu.moduleId = " + moduleId;
		List<SysplModOptRef> sysplModOptRefList = this.findByProperty(queryString);
		return sysplModOptRefList;
	}
	/**
	 * 功能说明：根据模块ID删除模块操作关系,级联删除权限
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return
	 * @time Oct 29, 2010 11:21:03 AM
	 */
	public void deleteModOptRefByModId(long moduleId){
		String queryString = "from SysplModOptRef mor where mor.sysplModuleMemu.moduleId = " + moduleId;
		List<SysplModOptRef> sysplModOptRefList = this.findByProperty(queryString);
		this.deleteAll(sysplModOptRefList);
	}
	/**
	 * 功能说明：根据操作ID删除模块操作关系,级联删除权限
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return
	 * @time Oct 29, 2010 11:21:03 AM
	 */
	public void deleteModOptRefByOperateId(long operateId){
		String queryString = "from SysplModOptRef mor where mor.sysplOperate.operateId = " + operateId;
		List<SysplModOptRef> sysplModOptRefList = this.findByProperty(queryString);
		this.deleteAll(sysplModOptRefList);
	}
	
	public List<SysplModOptRef> findAllSysplModOptRef(){
		String queryString = "from SysplModOptRef mor";
		List<SysplModOptRef> sysplModOptRefList = this.findByProperty(queryString);
		return sysplModOptRefList;
	}
	
	/**
	 * 功能说明：查询当前模块的所有操作
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @time Nov 4, 2010 4:45:27 PM
	 */
	public List<SysplModOptRef> findAllSysplModOptRefByModuleId(long moduleId){
		String queryString = "from SysplModOptRef mor where mor.sysplModuleMemu.moduleId = ?";
		return this.findByPropertyWithParas(queryString, new Long[]{moduleId});
	}
	/**
	 * 功能说明：修改行权限
	 * @author jacobliang
	 * @param moduleIds 模块集合
	 * @param rowPrivilegeLevel	权限类型
	 * @time Nov 7, 2010 2:34:01 PM
	 */
	public void modifyModuleRowPrivilege(String moduleIds,String rowPrivilegeLevel){
		String hqlString = "update SysplModuleMemu sysplModuleMemu " +
						   "set sysplModuleMemu.privilegeLevel = '" + rowPrivilegeLevel + 
						   "' where sysplModuleMemu.moduleId in("+moduleIds+")";
		this.excuteHql(hqlString);
	}
	/**
	 * 功能说明：根据模块关系表ID查询所有模块操作关系
	 * @author jacobliang
	 * @param modOptRefId
	 * @return
	 * @time Feb 7, 2012
	 */
	public List<SysplModOptRef> findSysplModOptRefByModOptRefIds(String modOptRefId){
		String queryString = "from SysplModOptRef mor where mor.modOptId in(" + modOptRefId +")";
		if(PRIVILEGE_SCOPE_ALL.equals(modOptRefId)){
			queryString = "from SysplModOptRef mor";
		}
		List<SysplModOptRef> sysplModOptRefList = this.findByProperty(queryString);
		return sysplModOptRefList;
	}
}
