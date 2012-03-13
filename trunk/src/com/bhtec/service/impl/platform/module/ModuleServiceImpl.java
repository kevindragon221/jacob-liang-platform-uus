/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.module;

import static com.bhtec.common.constant.Common.DELETE_OPT;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SECOND;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.THIRD;
import static com.bhtec.common.constant.ServiceVariable.ASSIGNED_MODOPTS;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ONE;
import static com.bhtec.common.constant.ServiceVariable.SYS_MODOPT_ASSIGN;
import static com.bhtec.common.constant.ServiceVariable.SYS_MODOPT_MGR;
import static com.bhtec.common.constant.ServiceVariable.SYS_MOD_MGR;
import static com.bhtec.common.constant.ServiceVariable.THREE;
import static com.bhtec.common.constant.ServiceVariable.TWO;
import static com.bhtec.common.constant.ServiceVariable.UNASSIGNED_MODOPTS;
import static com.bhtec.common.tools.UtilTools.getResourcePath;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.dao.iface.platform.module.ModuleDao;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.module.ModuleService;
import com.bhtec.service.iface.platform.moduleopt.ModuleOptService;
import com.bhtec.service.iface.uum.UumCommonService;
import com.bhtec.service.impl.BaseServiceImpl;

public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {
	Logger log = Logger.getLogger(this.getClass());
	private ModuleDao moduleDao;
	private ModuleOptService moduleOptService;
	private UumCommonService uumCommonService;
	/**
	 * 功能说明：保存模块
	 * @author jacobliang
	 * @param sysplModuleMemu	系统模块
	 * @throws 
	 * @time Jul 26, 2010 8:50:21 PM
	 */
	public void saveModule(SysplModuleMemu sysplModuleMemu)throws ApplicationException{
		moduleDao.save(sysplModuleMemu);
		writeLog(sysplModuleMemu,LOG_LEVEL_FIRST,SAVE_OPT);
	}
	
	/**
	 * 功能说明：停用启用模块
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param moduleId	模块ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public boolean disEnableModule(long moduleId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			int modCount = moduleDao.findDownModuleById(moduleId);
			if(modCount > 0){//有下级菜单
				return false;
			}else{
				SysplModuleMemu sysplModuleMemu = findModuleByModuleId(moduleId);
				sysplModuleMemu.setStatus(DISABLE);
				this.moduleDao.deleteModOptRefByModId(moduleId);//停用第三级模块时将自动删除模块下的操作
				this.writeLog(sysplModuleMemu, LOG_LEVEL_SECOND, DISABLE_DIS);
				return true;
			}
		}else{
			SysplModuleMemu sysplModuleMemu = findModuleByModuleId(moduleId);
			sysplModuleMemu.setStatus(ENABLE);
			this.writeLog(sysplModuleMemu, LOG_LEVEL_SECOND, ENABLE_DIS);
			return true;
		}
		
	}
	/**
	 * 功能说明：删除某个模块
	 * @author jacobliang
	 * @param	moduleId 	模块ID
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 8:51:04 AM
	 */
	public void deleteModuleById(Long moduleId)throws ApplicationException{
		log.debug("will delete module id is "+moduleId);
		SysplModuleMemu sysplModuleMemu = findModuleByModuleId(moduleId);
		moduleDao.delete(sysplModuleMemu);
		this.writeLog(sysplModuleMemu, LOG_LEVEL_THIRD, DELETE_OPT);
	}
	/**
	 * 功能说明：根据模块ID查询某个模块
	 * @author jacobliang
	 * @param  moduleId			模块ID
	 * @return SysplModuleMemu	模块菜单
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public SysplModuleMemu findModuleByModuleId(Long moduleId){
		log.debug("will find module id is "+moduleId);
		SysplModuleMemu sysplModuleMemu = (SysplModuleMemu)moduleDao
						.getPojoById("com.bhtec.domain.pojo.platform.SysplModuleMemu", moduleId);
		return sysplModuleMemu;
	}
	
	/**
	 * 功能说明：修改模块
	 * @author jacobliang
	 * @param sysplModuleMemu	模块对象
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyModule(SysplModuleMemu sysplModuleMemu)throws ApplicationException{
		moduleDao.update(sysplModuleMemu);
		this.writeLog(sysplModuleMemu, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @param  moduleName	模块名称
	 * @return boolean true 模块名存在 false 不存在
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public boolean findModuleByModuleName(String moduleName){
		int totalProperty = moduleDao.findModuleByModuleName(moduleName);
		log.debug("find module total is "+totalProperty);
		if(totalProperty > 0){
			return true;
		}
		return false;
	}
	
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
	public Map findModuleByCon(int start,int limit,long treeId,String modName,String modEnId){
		log.debug("start="+ start+" limit="+ limit+" treeId= "+ treeId+" modName"+ modName+" modEnId"+ modEnId);
		String ids = "";
		if(treeId >= 0){
			List<SysplModuleMemu> list = findChildNodeList(treeId,0);//过滤出所有下级
			for(SysplModuleMemu sysplModuleMemu:list){
				String moduleId = sysplModuleMemu.getModuleId().toString();
				ids = ids.equals("")?moduleId:ids+","+moduleId;//所有下级模块ID
			}
			if(ids.equals(""))ids="-1";
		}
		return moduleDao.findModuleByCon(start, limit, ids, modName, modEnId);
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @return List<SysplModuleMemu> 所有模块对象
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplModuleMemu> findAllModule(){
		List<SysplModuleMemu> list = moduleDao.findAllModule();
		if(list != null && list.size()>0){
			for(SysplModuleMemu mod : list){
				mod.setUpModId(mod.getSysplModuleMemu().getModuleId());//上级模块ID
			}
		}
		return list;
	}
	
	/**
	 * 功能说明：根据模块id查询所有模块节点
	 * @author jacobliang
	 * @param moduleId		上级模块id
	 * @param flag			0为下级，1 为上级
	 * @return
	 * @throws 
	 * @time Aug 15, 2010 9:17:13 PM
	 */
	public List<SysplModuleMemu> findChildNodeList(long moduleId,int flag){
		List<SysplModuleMemu> filterList = new ArrayList<SysplModuleMemu>();
		List<SysplModuleMemu> listAll = moduleDao.findAllModule();
		List<SysplModuleMemu> listAllTemp = new ArrayList<SysplModuleMemu>();
		listAllTemp.addAll(listAll);
		//删除根元素
		SysplModuleMemu sysplModuleMemu = new SysplModuleMemu();
		sysplModuleMemu.setModuleId(0L);
		listAllTemp.remove(sysplModuleMemu);
		//过滤出所有下级元素
		filterList = getFilterList(filterList, listAllTemp, moduleId);	
		if(flag == 1){
			//本元素
			SysplModuleMemu sysplModuleMemuOld = new SysplModuleMemu();
			sysplModuleMemuOld.setModuleId(moduleId);
			filterList.add(sysplModuleMemuOld);
			listAll.removeAll(filterList);
			filterList = listAll;
			if(filterList != null && filterList.size()>0){
				for(SysplModuleMemu mod : filterList){
					mod.setUpModId(mod.getSysplModuleMemu().getModuleId());
				}
			}
		}
		
		return filterList;
	}
	
	/**
	 * 功能说明：根据上级模块id递归过滤结点
	 * @author jacobliang
	 * @param filterList	过滤的子节点
	 * @param listAll		所有模块结果集
	 * @param moduleId		上级模块id
	 * @return	所有子结点
	 * @throws 
	 * @time Aug 15, 2010 9:18:41 PM
	 */
	private List getFilterList(List<SysplModuleMemu> filterList,List<SysplModuleMemu> listAll,long moduleId){
		List<SysplModuleMemu> subList = getSubList(listAll, moduleId);
		if(subList.size()>0)
			filterList.addAll(subList);
		for(SysplModuleMemu module : subList){
			getFilterList(filterList,listAll,module.getModuleId());
		}
		return filterList;
	}
	
	/**
	 * 功能说明：根据模块id获得所有子结点
	 * @author jacobliang
	 * @param listAll	所有模块结果集
	 * @param moduleId  上级模块id
	 * @return	所有子结点
	 * @throws 
	 * @time Aug 15, 2010 9:19:09 PM
	 */
	private List<SysplModuleMemu> getSubList(List<SysplModuleMemu> listAll,long moduleId){
		List<SysplModuleMemu> newList = new ArrayList<SysplModuleMemu>();
		for(SysplModuleMemu module : listAll){
			if(module.getSysplModuleMemu().getModuleId() == moduleId){
				newList.add(module);
			}
		}
		return newList;
	}
	
	/**
	 * 功能说明：对系统菜单进行排序
	 * @author jacobliang
	 * @param moduleList		菜单列表
	 * @param sysplModuleMemu	菜单对象
	 * @time Nov 20, 2010 11:11:37 AM
	 */
	private void moduleMenuOrder(List<SysplModuleMemu> moduleList,SysplModuleMemu sysplModuleMemu){
		//根据操作顺序进行排序
		int listIndex = 0;//元素索引
		boolean orderBol = true;//是否插入指定索引元素
		for(SysplModuleMemu sysplModuleMemuu : moduleList){					
			int listModOrder = sysplModuleMemuu.getModOrder();//已经有的操作顺序
			int currModOrder = sysplModuleMemu.getModOrder();//当前操作顺序
			if(listModOrder > currModOrder){
				moduleList.add(listIndex,sysplModuleMemu);//顺序小的插在前
				orderBol = false;
				break;
			}
			listIndex++;
		}
		if(orderBol)
			moduleList.add(sysplModuleMemu);//加到操作list中
	}
	/**
	 * 功能说明：过滤模块
	 * @author jacobliang
	 * @param	list 	需要过滤的模块列表
	 * @return	1-list 2-map 3-map
	 * @throws 
	 * @time Aug 20, 2010 8:37:50 PM
	 */
	public Map findFilterModuleMenu(List<SysplModuleMemu> list){
		List<SysplModuleMemu> firstList = new ArrayList<SysplModuleMemu>();
		List<SysplModuleMemu> secondList = new ArrayList<SysplModuleMemu>();
		List<SysplModuleMemu> thirdList = new ArrayList<SysplModuleMemu>();
		//过滤第一级、第二级、第三级菜单
		for(SysplModuleMemu sysplModuleMemu:list){
			if(ONE.equals(sysplModuleMemu.getModLevel())){
				moduleMenuOrder(firstList,sysplModuleMemu);
			}else if(TWO.equals(sysplModuleMemu.getModLevel())){
				secondList.add(sysplModuleMemu);
			}else if(THREE.equals(sysplModuleMemu.getModLevel())){
				thirdList.add(sysplModuleMemu);
			}
		}
		
		Map secondMap = new HashMap();//第二级
		Map thirdMap = new HashMap();//第三级
		
		for(SysplModuleMemu sysplModuleMemuFirst:firstList){//1
			List<SysplModuleMemu> secondListMap = new ArrayList<SysplModuleMemu>();
			for(SysplModuleMemu sysplModuleMemuSecond:secondList){//2
				List<SysplModuleMemu> thirdListMap = new ArrayList<SysplModuleMemu>();
				if(sysplModuleMemuFirst.getModuleId() == sysplModuleMemuSecond.getUpModId()){
					moduleMenuOrder(secondListMap,sysplModuleMemuSecond);
					for(SysplModuleMemu sysplModuleMemuThird:thirdList){//3
						if(sysplModuleMemuSecond.getModuleId() == sysplModuleMemuThird.getUpModId()){
							moduleMenuOrder(thirdListMap,sysplModuleMemuThird);
						}
					}
					thirdMap.put(sysplModuleMemuSecond.getModuleId().toString(), thirdListMap);
				}
				
			}
			secondMap.put(sysplModuleMemuFirst.getModuleId().toString(), secondListMap);
		}
		Map map = new HashMap();
		map.put(FIRST, firstList);
		map.put(SECOND, secondMap);
		map.put(THIRD, thirdMap);
		return map;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	moduleId		模块id
	 * @param	filterModuleId	过滤模块id,当修改模块菜单时将本模块ID过滤掉
	 * @return  List<TreeVo>	下一级模块菜单
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<TreeVo> findNextLevelChildNodes(long moduleId,long filterModuleId){
		List<SysplModuleMemu> list = this.moduleDao.findNextLevelChildNodes(moduleId,filterModuleId);
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		if(list != null && list.size()>0){
			for(SysplModuleMemu mod : list){
				TreeVo treeVo = new TreeVo();
				treeVo.setId(mod.getModuleId().toString());
				treeVo.setText(mod.getModName());
				treeVo.setIconCls(mod.getModImgCls());
				if(THREE.equals(mod.getModLevel())){
					treeVo.setLeaf(true);//模块菜单级别为3是叶子
				}
				treeVoList.add(treeVo);
			}
		}
		return treeVoList;
	}
	/**
	 * 功能说明：根据模块ID查询已经分配的模块操作
	 * @author  jacobliang
	 * @param 	moduleId			模块ID
	 * @return  List<SysplOperate>	已经分配的模块操作list
	 * @time Oct 29, 2010 11:06:51 AM
	 */
	public List<SysplOperate> findAssignedModOptByModId(long moduleId){
		List<SysplModOptRef> sysplModOptRefList = moduleDao.findAssignedModOptByModId(moduleId);
		List<SysplOperate> sysplOperateList = new ArrayList<SysplOperate>();
		if(sysplModOptRefList != null 
				&& sysplModOptRefList.size()>0){
			for(SysplModOptRef sysplModOptRef:sysplModOptRefList){
				sysplOperateList.add(sysplModOptRef.getSysplOperate());
			}
		}
		return sysplOperateList;
	}
	
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param sysplModuleMemu	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(SysplModuleMemu sysplModuleMemu,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append(sysplModuleMemu.getModName()+SPLIT).append(sysplModuleMemu.getModEnId()+SPLIT);
		saveLog(logLevel, SYS_MOD_MGR,opt,logContent.toString(), sysplModuleMemu.getModuleId()+"");
	}
	
	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}
	/**
	 * 功能说明：获得模块已经分配和未分配的操作列表
	 * @author jacobliang
	 * @param moduleId 		模块ID
	 * @return Map<String,List<SysplOperate>>	1 list 分配 2 list 未分配
	 * @time Oct 29, 2010 11:36:03 AM
	 */
	public Map<String, List<SysplOperate>> obtainAssignedAUnAssignedOpt(
			long moduleId) {
		List<SysplOperate> allSysplOperateList = moduleOptService.findAllModuleOpt();//所有操作
		List<SysplOperate> assignedModOpts = findAssignedModOptByModId(moduleId);//已经分配的操作
		allSysplOperateList.removeAll(assignedModOpts);//删除已经分配
		List<SysplOperate> unassignedModOpts = allSysplOperateList;//未分配的操作 
		Map<String, List<SysplOperate>> map = new HashMap<String, List<SysplOperate>>();
		map.put(ASSIGNED_MODOPTS, assignedModOpts);
		map.put(UNASSIGNED_MODOPTS, unassignedModOpts);
		return map;
	}
	/**
	 * 功能说明：保存模块操作关系
	 * @author jacobliang
	 * @param moduleId			模块ID
	 * @param operateIdList		模块操作列表
	 * @throws ApplicationException
	 * @time Oct 29, 2010 11:38:33 AM
	 */
	public void saveModuleOptRefs(long moduleId, List<Long> operateIdList)throws ApplicationException {
		//查询当前模块的所有操作
		List<SysplModOptRef> sysplModOptRefList = moduleDao.findAllSysplModOptRefByModuleId(moduleId);
		if(sysplModOptRefList == null || sysplModOptRefList.size()==0){//如果没有任何操作则直接增加
			this.saveModuleOptRefSingle(moduleId, operateIdList);
		}else{
			/*
			 * 模块操作关系：首先找出重复的操作ID，原模块操作列表删除重复的之后删除(并级联删除权限操作)
			 * 新分配的操作列表删除重复的之后增加
			 * */
			List<SysplModOptRef> repeateModOptRefList = new ArrayList<SysplModOptRef>();
			List<Long> repeateOperateIdList = new ArrayList<Long>();
			for(SysplModOptRef sysplModOptRef:sysplModOptRefList){
				long operateIdOld = sysplModOptRef.getSysplOperate().getOperateId();
				for(Long operateIdNew:operateIdList){
					if(operateIdOld == operateIdNew){
						repeateModOptRefList.add(sysplModOptRef);
						repeateOperateIdList.add(operateIdOld);
						break;
					}
				}
			}
			sysplModOptRefList.removeAll(repeateModOptRefList);//删除重复的模块操作
			operateIdList.removeAll(repeateOperateIdList);//删除重复的ID
			this.moduleDao.deleteAll(sysplModOptRefList);//进行模块操作关系删除
			StringBuffer modOptRefSb = new StringBuffer();
			for(SysplModOptRef sysplModOptRef:sysplModOptRefList){
				Long modOptId = sysplModOptRef.getModOptId();
				modOptRefSb = modOptRefSb.length()==0
							  ?modOptRefSb.append("'"+modOptId+"'")
							  :modOptRefSb.append(",'"+modOptId+"'");
			}
			uumCommonService.deletePrivilegeByModOptId(modOptRefSb.toString());
			//删除权限操作 sysplModOptRefList 模块操作关系
			this.saveModuleOptRefSingle(moduleId, operateIdList);//保存新分配的模块操作关系
		}
		
	}
	/**
	 * 功能说明：只单独保存模块操作关系
	 * @author jacobliang
	 * @param moduleId
	 * @param operateIdList
	 * @throws ApplicationException
	 * @time Nov 4, 2010 5:31:31 PM
	 */
	private void saveModuleOptRefSingle(long moduleId, List<Long> operateIdList)throws ApplicationException{
		String operateIds = "";
		if(operateIdList != null && operateIdList.size()>0){
			List<SysplModOptRef> listSysplModOptRef = new ArrayList<SysplModOptRef>();
			for(Long operateId:operateIdList){
				SysplModOptRef sysplModOptRef = new SysplModOptRef();
				
				SysplOperate sysplOperate = new SysplOperate();
				sysplOperate.setOperateId(operateId);
				
				SysplModuleMemu sysplModuleMemu = new SysplModuleMemu();
				sysplModuleMemu.setModuleId(moduleId);
				
				sysplModOptRef.setSysplOperate(sysplOperate);
				sysplModOptRef.setSysplModuleMemu(sysplModuleMemu);
				
				listSysplModOptRef.add(sysplModOptRef);
				operateIds = operateIds.equals("")?operateId+"":operateId+","+operateId;
			}
			this.moduleDao.batchSave(listSysplModOptRef);
		}
		StringBuffer logContent = new StringBuffer();
		logContent.append("模块分配操作"+SPLIT).append(operateIds);
		saveLog(LOG_LEVEL_FIRST, SYS_MODOPT_ASSIGN,SAVE_OPT,logContent.toString(), moduleId+"");
	}
	/**
	 * 功能说明：查询所有模块操作关系记录
	 * @author jacobliang
	 * @return	List<SysplModOptRef>	所有模块操作关系记录
	 * @time Nov 1, 2010 10:01:57 AM
	 */
	public List<SysplModOptRef> findAllSysplModOptRef(){
		return this.moduleDao.findAllSysplModOptRef();
	}
	
	/**
	 * 功能说明：根据操作ID删除模块操作关系,级联删除权限
	 * @author jacobliang
	 * @param moduleId	模块ID
	 * @return
	 * @time Oct 29, 2010 11:21:03 AM
	 */
	public void deleteModOptRefByOperateId(long operateId)throws ApplicationException{
		this.moduleDao.deleteModOptRefByOperateId(operateId);
		StringBuffer logContent = new StringBuffer();
		logContent.append("根据操作ID删除模块操作关系,级联删除权限");
		saveLog(LOG_LEVEL_THIRD, SYS_MODOPT_MGR,DELETE_OPT,logContent.toString(), operateId+"");
	}
	/**
	 * 功能说明：修改模块标签描述
	 * @author jacobliang
	 * @param xmlFile			文件名路径
	 * @param moduleLabelList	[0]为xml path name [1]为标签描述
	 * @throws ApplicationException
	 * @time Dec 28, 2010 9:47:26 AM
	 */
	public void modifyModuleLabel(String xmlFile,List<String[]> moduleLabelList)throws ApplicationException{
		Document document = XmlOpUtil.loadXMLFile(getResourcePath()+xmlFile);
		Element moduleEl = (Element)document.selectSingleNode("/module");
		Element fieldNamesEl = (Element)document.selectSingleNode("/module/fieldNames");
		moduleEl.remove(fieldNamesEl);//删除fieldNames结点并重新添加
		fieldNamesEl = moduleEl.addElement("fieldNames");
		for(String[] moduleLabelArr:moduleLabelList){
			Element funNameEl = fieldNamesEl.addElement("field");
			funNameEl.addAttribute("name", moduleLabelArr[0]);
			funNameEl.addText(moduleLabelArr[1]);
		}
		XmlOpUtil.doc2XmlFile(document, getResourcePath()+xmlFile);
	}
	/**
	 * 功能说明：根据模块关系表ID查询所有模块操作关系
	 * @author jacobliang
	 * @param modOptRefId
	 * @return
	 * @time Feb 7, 2012
	 */
	public List<SysplModOptRef> findSysplModOptRefByModOptRefIds(String modOptRefId){
		if(isNullOrEmpty(modOptRefId))return new ArrayList<SysplModOptRef>();
		return this.moduleDao.findSysplModOptRefByModOptRefIds(modOptRefId);
	}
	
	public void setModuleOptService(ModuleOptService moduleOptService) {
		this.moduleOptService = moduleOptService;
	}

	public void setUumCommonService(UumCommonService uumCommonService) {
		this.uumCommonService = uumCommonService;
	}

}
