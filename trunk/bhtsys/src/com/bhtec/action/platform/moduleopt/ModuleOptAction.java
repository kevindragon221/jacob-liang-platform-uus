/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.moduleopt;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.moduleopt.ModuleOptService;
import com.opensymphony.xwork2.ModelDriven;

public class ModuleOptAction extends PlatformBaseAction implements ModelDriven<SysplOperate> {
	private static final long serialVersionUID = 100000L;
	Logger log = Logger.getLogger(this.getClass());
	private ModuleOptService moduleOptService;
	SysplOperate sysplOperate = new SysplOperate();
	private List<SysplOperate> moduleOptList;
	private String moduleOptName;
	private boolean existModuleOpt;
	private int count;
	private String failMesg;
	private String disEnableFlag;
	private boolean disEnableBol;
	
	/**
	 * 功能说明：保存模块操作
	 * @author jacobliang
	 * @time Oct 28, 2010 2:04:12 PM
	 */
	public void saveModuleOpt(){
		try {
			moduleOptService.setHttpServletRequest(this.getHttpServletRequest());
			sysplOperate.setCreateDate(new Date());
			sysplOperate.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			moduleOptService.saveModuleOpt(sysplOperate);
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
	 * 功能说明：查询所有模块操作信息
	 * @author jacobliang
	 * @return
	 * @time Oct 28, 2010 2:05:59 PM
	 */
	public String findModuleOptByCon(){
		Map map = moduleOptService.findModuleOptByCon(getStart(),getLimit(),moduleOptName);
		moduleOptList = (List<SysplOperate>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据模块操作ID查询某个模块操作
	 * @author jacobliang
	 * @return
	 * @time Oct 28, 2010 2:08:28 PM
	 */
	public String findModuleOptByOperateId(){
		try {
			sysplOperate = moduleOptService.findModuleOptByOperateId(new Long(getModViewRecId()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("getModViewRecId() is not number. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询模块操作名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Oct 28, 2010 11:11:39 AM
	 */
	public String findModuleOptByName(){
		try {
			existModuleOpt = moduleOptService.findModuleOptByName(this.getHttpServletRequest().getParameter("moduleOptName"));
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改模块操作
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Oct 28, 2010 9:43:55 AM
	 */
	public void modifyModuleOpt(){
		try {
			moduleOptService.setHttpServletRequest(this.getHttpServletRequest());
			moduleOptService.modifyModuleOpt(sysplOperate);
			this.returnSuccess();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("modifyModule() is not number. ", e);
			this.returnFailur(this.ERROR);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Oct 28, 2010 9:28:04 PM
	 */
	public String findAllModuleOpt(){
		try {
			moduleOptList = moduleOptService.findAllModuleOpt();
		}catch (SystemException e) {
			e.printStackTrace();
			log.error("findAllModule() occur error. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：停用启用模块操作
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @return
	 * @throws 
	 * @time Oct 28, 2010 1:29:34 PM
	 */
	public String disEnableModuleOpt(){
		try {
			moduleOptService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = moduleOptService.disEnableModuleOpt(new Long(getModViewRecId()), disEnableFlag);
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
	
	public SysplOperate getModel() {
		return sysplOperate;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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

	public boolean getDisEnableBol() {
		return disEnableBol;
	}

	public void setDisEnableBol(boolean disEnableBol) {
		this.disEnableBol = disEnableBol;
	}

	public void setModuleOptService(ModuleOptService moduleOptService) {
		this.moduleOptService = moduleOptService;
	}

	public List<SysplOperate> getModuleOptList() {
		return moduleOptList;
	}

	public void setModuleOptList(List<SysplOperate> moduleOptList) {
		this.moduleOptList = moduleOptList;
	}

	public String getModuleOptName() {
		return moduleOptName;
	}

	public void setModuleOptName(String moduleOptName) {
		this.moduleOptName = moduleOptName;
	}

	public boolean getExistModuleOpt() {
		return existModuleOpt;
	}

	public void setExistModuleOpt(boolean existModuleOpt) {
		this.existModuleOpt = existModuleOpt;
	}
}
