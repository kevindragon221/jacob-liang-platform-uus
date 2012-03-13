/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.code;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.domain.pojo.platform.SysplCode;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.code.CodeService;
import com.opensymphony.xwork2.ModelDriven;

public class CodeAction extends PlatformBaseAction implements ModelDriven<SysplCode> {
	private Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 10000L;
	private SysplCode sysplCode = new SysplCode();
	private CodeService codeService;
	private String failMesg;
	private List<SysplCode> codeList;
	private int count;
	private String disEnableFlag;
	private boolean disEnableBol;
	private boolean existCode;
	
	/**
	 * 功能说明：保存编码
	 * @author jacobliang
	 * @param sysplCode		编码对象
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveSysplCode() {
		try {
			codeService.setHttpServletRequest(this.getHttpServletRequest());
			sysplCode.setCreateDate(new Date());
			sysplCode.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			codeService.saveSysplCode(sysplCode);
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
	 * 功能说明：根据编码ID查询某个编码
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findSysplCodeByCodeId(){
		sysplCode = codeService.findSysplCodeByCodeId(new Long(getModViewRecId()));
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据编码英文名称查询编码是否存在
	 * @author jacobliang
	 * @return boolean true 名称已经存在 false名称不存在
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public String findSysplCodeByCodeEngName(){
		existCode = codeService.findSysplCodeByCodeEngName(getHttpServletRequest().getParameter("codeEngName"));
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改编码
	 * @author jacobliang
	 * @param sysplCode	编码对象
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifySysplCode()throws ApplicationException{
		try {
			codeService.setHttpServletRequest(this.getHttpServletRequest());
			codeService.modifySysplCode(sysplCode);
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
	 * 功能说明：根据条件查询编码
	 * @author jacobliang
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findCodeByCon(){
		Map map = codeService.findCodeByCon(getStart(),getLimit(),
				sysplCode.getCodeEngName(), sysplCode.getCodeName(),
				sysplCode.getModuleName());
		codeList = (List<SysplCode>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	/**
	 * 功能说明：停用启用编码
	 * @author jacobliang
	 * @param   disEnableFlag	停用启用标志
	 * @param 	codeId		    编码ID
	 * @return  boolean			true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnableCode(){
		try {
			codeService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = codeService.disEnableCode(new Long(getModViewRecId()), disEnableFlag);
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
	
	public SysplCode getModel() {
		return sysplCode;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
	public String getFailMesg() {
		return failMesg;
	}
	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
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
	public List<SysplCode> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<SysplCode> codeList) {
		this.codeList = codeList;
	}
	public boolean getExistCode(){
		return this.existCode;
	}
	public void setExistCode(boolean existCode){
		this.existCode = existCode;
	}
}
