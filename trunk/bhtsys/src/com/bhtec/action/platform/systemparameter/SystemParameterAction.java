/**
 *功能说明：系统参数管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.systemparameter;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.domain.pojo.platform.SysplSysParameter;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.systemparameter.SystemParameterService;
import com.opensymphony.xwork2.ModelDriven;

public class SystemParameterAction extends PlatformBaseAction implements ModelDriven<SysplSysParameter> {
	private static final long serialVersionUID = 10000L;
	Logger log = Logger.getLogger(this.getClass());
	private SysplSysParameter sysplSysParameter = new SysplSysParameter();
	private String failMesg;
	private List<SysplSysParameter> systemParameterServiceList;
	private int count;
	private String disEnableFlag;
	private boolean disEnableBol;
	
	private SystemParameterService systemParameterService;
	/**
	 * 功能说明：保存系统参数
	 * @author jacobliang
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveSystemParameter() {
		try {
			systemParameterService.setHttpServletRequest(this.getHttpServletRequest());
			sysplSysParameter.setCreateDate(new Date());
			sysplSysParameter.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			systemParameterService.saveSysplSysParameter(sysplSysParameter);
			this.returnSuccess();
		}catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：根据系统参数ID查询系统参数
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findSysplSysParameterById(){
		sysplSysParameter = systemParameterService.findSysplSysParameterById(new Long(getModViewRecId()));
		return this.SUCCESS;
	}
	/**
	 * 功能说明：修改系统参数
	 * @author jacobliang
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifySysplSysParameter(){
		try {
			systemParameterService.setHttpServletRequest(this.getHttpServletRequest());
			systemParameterService.modifySysplSysParameter(sysplSysParameter);
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
	 * 功能说明：根据条件查询系统参数
	 * @author jacobliang
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findSystemParaByCon(){
		Map map = systemParameterService.findSystemParaByCon(getStart(),getLimit(),
				sysplSysParameter.getParaName(), sysplSysParameter.getParaKeyName());
		systemParameterServiceList = (List<SysplSysParameter>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public String findSystemParaByParaName(){
		sysplSysParameter = systemParameterService.findSystemParaByParaName(sysplSysParameter.getParaKeyName());
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：停用启用系统
	 * @author jacobliang
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnablePara()throws ApplicationException{
		try {
			systemParameterService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = systemParameterService.disEnablePara(new Long(getModViewRecId()), disEnableFlag);
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

	public SysplSysParameter getModel() {
		return sysplSysParameter;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public List<SysplSysParameter> getSystemParameterServiceList() {
		return systemParameterServiceList;
	}

	public void setSystemParameterServiceList(
			List<SysplSysParameter> systemParameterServiceList) {
		this.systemParameterServiceList = systemParameterServiceList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public void setSystemParameterService(
			SystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}
	
}
