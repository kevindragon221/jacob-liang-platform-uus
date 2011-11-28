/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 1, 2010 @11:41:41 AM
 */
package com.bhtec.service.impl.uum.privilege;

import static com.bhtec.common.constant.Common.FOURTH;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.PojoVariable.UUM_PRIVILEGE;
import static com.bhtec.common.constant.ServiceVariable.ASSIGNED_MODOPTS;
import static com.bhtec.common.constant.ServiceVariable.MODULE_MENU;
import static com.bhtec.common.constant.ServiceVariable.MODULE_OPERATE;
import static com.bhtec.common.constant.ServiceVariable.MOD_ASSIGN_OPT;
import static com.bhtec.common.constant.ServiceVariable.UNASSIGNED_MODOPTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.privilege.PrivilegeDao;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojo.uum.UumPrivilege;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.impl.BaseServiceImpl;

public class PrivilegeServiceImpl extends BaseServiceImpl implements
		PrivilegeService {
	Logger log = Logger.getLogger(this.getClass());
	private PrivilegeDao privilegeDao;
	private PlatformCommonService platformCommonService;
	
	/**
	 * 功能说明：根据用户或角色ID 和 标识查询所有已分配和未分配模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @return	Map<String,List<SysplModuleMemu>> 1.list 已分配	2.list	未分配
	 * @time Nov 1, 2010 11:36:34 AM
	 */
	public Map<String, List<SysplModuleMemu>> findSeledAUnseledModOptByOwnIdAPriType(
			long ownerId, String privilegeType) {
		//查找已经分配的操作
		List<SysplModOptRef> assignedModOpt = 
							this.privilegeDao.findPrivilegeByOwnIdAPriType(ownerId, privilegeType);
		//查找所有操作
		List<SysplModOptRef> allModOpt = platformCommonService.findAllSysplModOptRef();
		allModOpt.removeAll(assignedModOpt);
		List<SysplModOptRef> unassignedModOpt = allModOpt;
		//将操作转化成列表数据
		List<SysplModuleMemu>  assignedModMenuList = (List<SysplModuleMemu>)findFilterModOpt(assignedModOpt,true).get(MODULE_MENU);
		List<SysplModuleMemu>  unassignedModMenuList = (List<SysplModuleMemu>)findFilterModOpt(unassignedModOpt,true).get(MODULE_MENU);
		//将已分配和未分配的模块操作放入map
		Map<String,List<SysplModuleMemu>> moduleMemuMap = new HashMap<String,List<SysplModuleMemu>>();
		moduleMemuMap.put(ASSIGNED_MODOPTS, assignedModMenuList);
		moduleMemuMap.put(UNASSIGNED_MODOPTS, unassignedModMenuList);
		return moduleMemuMap;
	}
	/**
	 * 功能说明：根据登录的用户或角色ID 和 标识查询所有模块操作
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @return modMenuMap:1-list;2-map;3-map;4-map
	 * @time Nov 1, 2010 11:29:03 AM
	 */
	public Map findUserModOptPriForLogin(long ownerId,
			String privilegeType) {
		log.info("role or user's id is : "+ ownerId+". privilege type is : " + privilegeType);
		List<SysplModOptRef> sysplModOptRefList = 
			this.privilegeDao.findPrivilegeByOwnIdAPriType(ownerId, privilegeType);
		//过滤所有的模块操作
		Map sysplModOptMap = this.findFilterModOpt(sysplModOptRefList,false);
		List<SysplModuleMemu> sysplModuleMemuList = (List<SysplModuleMemu>)sysplModOptMap.get(MODULE_MENU);
		Map<String,List<SysplModuleMemu>> modOptMap = (Map<String,List<SysplModuleMemu>>)sysplModOptMap.get(MODULE_OPERATE);
		//1,2,3级菜单分类
		Map modMenuMap = platformCommonService.findFilterModuleMenu(sysplModuleMemuList);
		//modMenuMap:1-list;2-map;3-map;4-map
		modMenuMap.put(FOURTH, modOptMap);
		return modMenuMap;
	}
	
	/**
	 * 功能说明：为角色或用户保存分配权限
	 * @author jacobliang
	 * @param modOptIds			模块操作关系IDS
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:26:30 AM
	 */
	@SuppressWarnings("unchecked")
	public void savePrivilege(List<Long> modOptIds, long ownerId,
			String privilegeType) throws ApplicationException {
		privilegeDao.deletePrivilege(ownerId, privilegeType);//删除角色或用戶拥有操作权限
		StringBuffer optContent = new StringBuffer();
		optContent.append("模块操作关系ID:");
		if(modOptIds != null && modOptIds.size()>0){
			for(int i=0,j=modOptIds.size();i<j;i++){
				Long modOptId = modOptIds.get(i);
				if(modOptId == null)continue;
				UumPrivilege uumPrivilege = new UumPrivilege();
				long privilegeId = this.getPrimaryKeyByPojoName(UUM_PRIVILEGE);
				uumPrivilege.setPrivilegeId(privilegeId);
				
				SysplModOptRef sysplModOptRef = new SysplModOptRef();
				sysplModOptRef.setModOptId(modOptId);
				uumPrivilege.setSysplModOptRef(sysplModOptRef);
				
				uumPrivilege.setOwnerId(ownerId);
				uumPrivilege.setPrivilegeType(privilegeType);
				
				this.privilegeDao.save(uumPrivilege);//保存角色操作权限
				optContent.append(modOptId+SPLIT);
			}
			 
			this.saveLog(LOG_LEVEL_FIRST, MOD_ASSIGN_OPT, SAVE_OPT, 
					optContent.toString(), ownerId+SPLIT+privilegeType);
		}
	}
	/**
	 * 功能说明：过滤所有的模块操作，构成模块树，并获得相应模块下的操作
	 * @author jacobliang
	 * @param sysplModOptRefList	系统模块操作关系列表
	 * @param isTreeFlag			是否为树标志
	 * @return
	 * @time Nov 1, 2010 1:44:44 PM
	 */
	private Map findFilterModOpt(List<SysplModOptRef> sysplModOptRefList,boolean isTreeFlag){
		Map<String,List<SysplModuleMemu>> sysplModOptMap = new HashMap<String,List<SysplModuleMemu>>();//四级模块操作 
		List<SysplModuleMemu> sysplModuleMemuList = new ArrayList<SysplModuleMemu>();//模块菜单list
		for(SysplModOptRef sysplModOptRef:sysplModOptRefList){
			SysplModuleMemu sysplModuleMemu3 = sysplModOptRef.getSysplModuleMemu();
			SysplOperate sysplOperate = sysplModOptRef.getSysplOperate();
			//操作按钮
			SysplModuleMemu sysplModuleMemuOpt = new SysplModuleMemu();
			sysplModuleMemuOpt.setModuleId(sysplModOptRef.getModOptId()+1000);//将模块操作关系的id加上1000,防id重复
			sysplModuleMemuOpt.setModName(sysplOperate.getOperateName());
			sysplModuleMemuOpt.setModImgCls(sysplOperate.getOptImgLink());
			sysplModuleMemuOpt.setOptFunLink(sysplOperate.getOptFunLink());////为操作准备功能链接
			sysplModuleMemuOpt.setUpModId(sysplModuleMemu3.getModuleId());//将第三级菜单设置为操作上级
			sysplModuleMemuOpt.setModOrder(sysplOperate.getOptOrder());//操作按钮的顺序
			if(isTreeFlag){//如果为树则加入模块list
				sysplModuleMemuList.add(sysplModuleMemuOpt);
			}else{//不为树则维护操作按钮map
				String modEnId = sysplModuleMemu3.getModEnId();//模块英文id,作为操作列表map的key值
				if(sysplModOptMap.containsKey(modEnId)){
					List<SysplModuleMemu> modOptList = sysplModOptMap.get(modEnId);
					//根据操作顺序进行排序
					int listIndex = 0;//元素索引
					boolean orderBol = true;//是否插入指定索引元素
					for(SysplModuleMemu sysplModuleMemuOptt : modOptList){					
						int listModOrder = sysplModuleMemuOptt.getModOrder();//已经有的操作顺序
						int currModOrder = sysplModuleMemuOpt.getModOrder();//当前操作顺序
						if(listModOrder > currModOrder){
							modOptList.add(listIndex,sysplModuleMemuOpt);//顺序小的插在前
							orderBol = false;
							break;
						}
						listIndex++;
					}
					if(orderBol)
						modOptList.add(sysplModuleMemuOpt);//加到操作list中
				}else{
					List<SysplModuleMemu> modOptList = new ArrayList<SysplModuleMemu>();//操作list
					modOptList.add(sysplModuleMemuOpt);//加到操作list中
					sysplModOptMap.put(modEnId, modOptList);
				}
			}
			//如果包含相应菜单项则加入相应模块菜单
			if(!sysplModuleMemuList.contains(sysplModuleMemu3)){
				sysplModuleMemuList.add(sysplModuleMemu3);
				SysplModuleMemu sysplModuleMemu2 =  sysplModuleMemu3.getSysplModuleMemu();
				sysplModuleMemu3.setUpModId(sysplModuleMemu2.getModuleId());
				if(!sysplModuleMemuList.contains(sysplModuleMemu2)){
					sysplModuleMemuList.add(sysplModuleMemu2);
					SysplModuleMemu sysplModuleMemu1 =  sysplModuleMemu2.getSysplModuleMemu();
					sysplModuleMemu2.setUpModId(sysplModuleMemu1.getModuleId());
					if(!sysplModuleMemuList.contains(sysplModuleMemu1)){
						sysplModuleMemu1.setUpModId(sysplModuleMemu1.getSysplModuleMemu().getModuleId());
						sysplModuleMemuList.add(sysplModuleMemu1);
					}
				}
			}
			
		}
		Collections.sort(sysplModuleMemuList,new SysplModuleMemu());
		Map moduleOptMap = new HashMap();
		moduleOptMap.put(MODULE_MENU, sysplModuleMemuList);
		moduleOptMap.put(MODULE_OPERATE, sysplModOptMap);
		return moduleOptMap;
	}
	/**
	 * 功能说明：查询判断用户是否有特殊操作权限
	 * @author jacobliang
	 * @param userId		用户或角色ID
	 * @return true 有 false 无
	 * @time Nov 3, 2010 8:16:42 PM
	 */
	public boolean findUserHasSpecialPrivilege(long userId) {
		int countPri = this.privilegeDao.findUserSpecialPrivilegeByUserId(userId);
		if(countPri > 0)
			return true;
		return false;
	}
	
	/**
	 * 功能说明：根据用户或角色ID 和 标识删除已经分配的模块操作权限
	 * @author jacobliang
	 * @param ownerId			用户或角色ID
	 * @param privilegeType		为用户或角色标识
	 * @throws ApplicationException
	 * @time Nov 1, 2010 10:31:20 AM
	 */
	public void deletePrivilege(long ownerId,String privilegeType)throws ApplicationException{
		this.privilegeDao.deletePrivilege(ownerId, privilegeType);
	}
	
	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	public void setPlatformCommonService(PlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
}
