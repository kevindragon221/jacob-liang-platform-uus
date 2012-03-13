/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.module;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.FIRST;
import static com.bhtec.common.constant.Common.SECOND;
import static com.bhtec.common.constant.Common.THIRD;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.common.tools.JSONUtil;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.module.ModuleService;
import com.opensymphony.xwork2.ModelDriven;

public class ModuleAction extends PlatformBaseAction implements ModelDriven<SysplModuleMemu> {
	private static final long serialVersionUID = 10000L;
	Logger log = Logger.getLogger(this.getClass());
	private ModuleService moduleService;
	private SysplModuleMemu sysplModuleMemu = new SysplModuleMemu();
	private List moduleList;
	private String moduleName;
	private String moduleEnId;
	private boolean existModule;
	private int count;
	private List<SysplModuleMemu> firstList = new ArrayList<SysplModuleMemu>();//第一级
	private Map secondMap = new HashMap();//第二级
	private Map thirdMap = new HashMap();//第三级
	private String failMesg;
	private String disEnableFlag;
	private boolean disEnableBol;
	private Map<String,List<SysplOperate>> assiAUnassMap;
	private List<Long> operateIdList;
	//设置行权限
	private String rowPrivilegeLevel;
	private List<Long> moduleIdList;
	//模块标签修改
	private String xmlFile;
	private String moduleLabelListStr;
	/**
	 * 功能说明：保存模块
	 * @author jacobliang
	 * @param sysplModuleMemu
	 * @throws 
	 * @time Jul 26, 2010 8:50:21 PM
	 */
	public void saveModule(){
		try {
			moduleService.setHttpServletRequest(this.getHttpServletRequest());
			SysplModuleMemu sysplModuleMemuParent = new SysplModuleMemu();
			sysplModuleMemuParent.setModuleId(sysplModuleMemu.getUpModId());
			sysplModuleMemu.setCreateDate(new Date());
			sysplModuleMemu.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			sysplModuleMemu.setSysplModuleMemu(sysplModuleMemuParent);
			moduleService.saveModule(sysplModuleMemu);
			this.returnSuccess();
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public String findModuleByCon(){
		Map map = moduleService.findModuleByCon(getStart(),getLimit(),getTreeId(),moduleName, moduleEnId);
		moduleList = (List<SysplModuleMemu>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	/**
	 * 功能说明：删除某个模块
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 8:51:04 AM
	 */
	public String deleteModuleById(){
		try {
			moduleService.setHttpServletRequest(this.getHttpServletRequest());
			moduleService.deleteModuleById(new Long(getModViewRecId()));
		} catch (NumberFormatException e) {
			e.printStackTrace();		
			log.error("getModViewRecId() is not number. ", e);
			failMesg = this.ERROR;
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据模块ID查询某个模块
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public String findModuleByModuleId(){
		try {
			sysplModuleMemu = moduleService.findModuleByModuleId(new Long(getModViewRecId()));
			sysplModuleMemu.setUpModId(sysplModuleMemu.getSysplModuleMemu().getModuleId());
			sysplModuleMemu.setUpModName(sysplModuleMemu.getSysplModuleMemu().getModName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("getModViewRecId() is not number. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询模块名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public String findModuleByModuleName(){
		try {
			existModule = moduleService.findModuleByModuleName(this.getHttpServletRequest().getParameter("moduleName"));
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改模块
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyModule(){
		try {
			moduleService.setHttpServletRequest(this.getHttpServletRequest());
			SysplModuleMemu sysplModuleMemuParent = new SysplModuleMemu();
			//upmodid为0参数传不过来
			sysplModuleMemuParent.setModuleId(sysplModuleMemu.getUpModId()==null?0:sysplModuleMemu.getUpModId());
			sysplModuleMemu.setSysplModuleMemu(sysplModuleMemuParent);
			moduleService.modifyModule(sysplModuleMemu);
			this.returnSuccess();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("modifyModule() is not number. ", e);
			this.returnFailur(this.ERROR);
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public String findAllModule(){
		try {
			moduleList = moduleService.findAllModule();
		}catch (SystemException e) {
			e.printStackTrace();
			log.error("findAllModule() occur error. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据模块id查询所有模块子节点
	 * @author jacobliang
	 * @param moduleId		上级模块id
	 * @return
	 * @throws 
	 * @time Aug 15, 2010 9:17:13 PM
	 */
	public String findChildNodeList(){
		moduleList = moduleService.findChildNodeList(new Long(getModViewRecId()),0);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：过滤模块
	 * @author jacobliang
	 * @param	list 	需要过滤的模块列表
	 * @param
	 * @return
	 * @throws 
	 * @time Aug 20, 2010 8:37:50 PM
	 */
	public String filterModuleMenu(){
		Map map = moduleService.findFilterModuleMenu(moduleService.findAllModule());
		firstList = (List<SysplModuleMemu>)map.get(FIRST);
		secondMap = (Map)map.get(SECOND);
		thirdMap = (Map)map.get(THIRD);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	moduleId	模块id
	 * @return
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public void findNextLevelChildNodes(){
		String filterModuleId = this.getHttpServletRequest().getParameter("filterModuleId");
		List<TreeVo> asyncTreeList = moduleService.findNextLevelChildNodes(new Long(getModViewRecId()==null?"0":getModViewRecId()),
				new Long(filterModuleId==null?"0":filterModuleId));
		JSONArray jsonArray = JSONArray.fromObject(asyncTreeList);   
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能说明：停用启用模块
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnableModule(){
		try {
			moduleService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = moduleService.disEnableModule(new Long(getModViewRecId()), disEnableFlag);
		} catch (NumberFormatException e) {
			e.printStackTrace();		
			log.error("disEnableModule() is not number. ", e);
			failMesg = this.ERROR;
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：获得模块已经分配和未分配的操作列表
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @time Oct 29, 2010 11:36:03 AM
	 */
	public String obtainAssignedAUnAssignedOpt(){
		assiAUnassMap = moduleService.obtainAssignedAUnAssignedOpt(sysplModuleMemu.getModuleId());		
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：保存模块操作关系
	 * @author jacobliang
	 * @param moduleId			模块ID
	 * @param operateIdList		模块操作列表
	 * @throws ApplicationException
	 * @time Oct 29, 2010 11:38:33 AM
	 */
	public String saveModuleOptRefs(){
		try{
			moduleService.setHttpServletRequest(this.getHttpServletRequest());
			moduleService.saveModuleOptRefs(sysplModuleMemu.getModuleId(), operateIdList);
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改模块标签
	 * @author jacobliang
	 * @return
	 * @time Dec 28, 2010 8:56:35 AM
	 */
	public String modifyModuleLabel(){
		String[] moduleLabelArray1 = JSONUtil.getStringArray4Json(moduleLabelListStr);//一维
		List<String[]> moduleLabelList = new ArrayList<String[]>();
		for(String moduleLabel:moduleLabelArray1){
			log.info(moduleLabel);
			String[] moduleLabelArray2 = JSONUtil.getStringArray4Json(moduleLabel);//二维
			moduleLabelList.add(moduleLabelArray2);
		}
		
		try {
			moduleService.modifyModuleLabel(xmlFile, moduleLabelList);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public SysplModuleMemu getModel() {
		return sysplModuleMemu;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
		moduleService.setHttpServletRequest(this.getHttpServletRequest());
	}

	public List getModuleList() {
		return moduleList;
	}
	public void setModuleList(List moduleList) {
		this.moduleList = moduleList;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public boolean isExistModule() {
		return existModule;
	}
	public void setExistModule(boolean existModule) {
		this.existModule = existModule;
	}
	public String getModuleEnId() {
		return moduleEnId;
	}
	public void setModuleEnId(String moduleEnId) {
		this.moduleEnId = moduleEnId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<SysplModuleMemu> getFirstList() {
		return firstList;
	}
	public void setFirstList(List<SysplModuleMemu> firstList) {
		this.firstList = firstList;
	}
	public Map getSecondMap() {
		return secondMap;
	}
	public void setSecondMap(Map secondMap) {
		this.secondMap = secondMap;
	}
	public Map getThirdMap() {
		return thirdMap;
	}
	public void setThirdMap(Map thirdMap) {
		this.thirdMap = thirdMap;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public String getDisEnableFlag() {
		return disEnableFlag;
	}

	public void setDisEnableFlag(String disEnableFlag) {
		this.disEnableFlag = disEnableFlag;
	}

	public boolean isDisEnableBol() {
		return disEnableBol;
	}

	public void setDisEnableBol(boolean disEnableBol) {
		this.disEnableBol = disEnableBol;
	}

	public Map<String, List<SysplOperate>> getAssiAUnassMap() {
		return assiAUnassMap;
	}

	public void setAssiAUnassMap(Map<String, List<SysplOperate>> assiAUnassMap) {
		this.assiAUnassMap = assiAUnassMap;
	}

	public List<Long> getOperateIdList() {
		return operateIdList;
	}

	public void setOperateIdList(List<Long> operateIdList) {
		this.operateIdList = operateIdList;
	}

	public String getRowPrivilegeLevel() {
		return rowPrivilegeLevel;
	}

	public void setRowPrivilegeLevel(String rowPrivilegeLevel) {
		this.rowPrivilegeLevel = rowPrivilegeLevel;
	}

	public List<Long> getModuleIdList() {
		return moduleIdList;
	}

	public void setModuleIdList(List<Long> moduleIdList) {
		this.moduleIdList = moduleIdList;
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getModuleLabelListStr() {
		return moduleLabelListStr;
	}

	public void setModuleLabelListStr(String moduleLabelListStr) {
		this.moduleLabelListStr = moduleLabelListStr;
	}
}
