/**
 *功能说明：系统参数管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.typedictionary;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.common.tools.JSONUtil;
import com.bhtec.domain.pojo.platform.SysplDicBigType;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.TypeDictionary.TypeDictionaryService;
import com.opensymphony.xwork2.ModelDriven;

public class TypeDictionaryAction extends PlatformBaseAction implements ModelDriven<SysplDicBigType> {
	private static final long serialVersionUID = 10000L;
	Logger log = Logger.getLogger(this.getClass());
	private SysplDicBigType sysplDicBigType = new SysplDicBigType();
	private String failMesg;
	private String sysplDicSmallTypeList;
	private List<SysplDicBigType> sysplDicBigTypeList;
	private List<SysplDicSmallType> sysplDicSmallTypeListt;
	private int count;
	private String disEnableFlag;
	private boolean disEnableBol;
	private boolean bigTypeCodeIsExist;
	
	private TypeDictionaryService typeDictionaryService;
	/**
	 * 功能说明：保存字典大类
	 * @author jacobliang
	 * @time Dec 22, 2010 8:52:38 PM
	 */
	public void saveSysplDicBigType() {
		try {
			typeDictionaryService.setHttpServletRequest(this.getHttpServletRequest());
			sysplDicBigType.setCreateDate(new Date());
			sysplDicBigType.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			Set<SysplDicSmallType> sysplDicSmallTypes = new HashSet<SysplDicSmallType>();
			List<SysplDicSmallType> dicSmallTypeList = JSONUtil.getList4Json(sysplDicSmallTypeList, SysplDicSmallType.class);
			for(SysplDicSmallType sysplDicSmallType:dicSmallTypeList){
				sysplDicSmallType.setSysplDicBigType(sysplDicBigType);
				sysplDicSmallTypes.add(sysplDicSmallType);
			}
			sysplDicBigType.setSysplDicSmallTypes(sysplDicSmallTypes);
			typeDictionaryService.saveSysplDicBigType(sysplDicBigType);
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
	 * 功能说明：根据字典大类ID查询字典大类
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findSysplDicBigTypeById(){
		sysplDicBigType = typeDictionaryService.findSysplDicBigTypeById(new Long(getModViewRecId()));
		return this.SUCCESS;
	}
	/**
	 * 功能说明：修改系统参数
	 * @author jacobliang
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifySysplDicBigType(){
		try {
			typeDictionaryService.setHttpServletRequest(this.getHttpServletRequest());
			Set<SysplDicSmallType> sysplDicSmallTypes = new HashSet<SysplDicSmallType>();
			List<SysplDicSmallType> dicSmallTypeList = JSONUtil.getList4Json(sysplDicSmallTypeList, SysplDicSmallType.class);
			for(SysplDicSmallType sysplDicSmallType:dicSmallTypeList){
//				long smallTypeId = this.getPrimaryKeyByPojoName(SYSPL_DIC_SMALLTYPE);
//				sysplDicSmallType.setSmallTypeId(smallTypeId);
				sysplDicSmallType.setSysplDicBigType(sysplDicBigType);
				sysplDicSmallTypes.add(sysplDicSmallType);
			}
			sysplDicBigType.setSysplDicSmallTypes(sysplDicSmallTypes);
			typeDictionaryService.modifySysplDicBigType(sysplDicBigType);
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
	 * 功能说明：根据大类名称及大类代码查询类别字典
	 * @author jacobliang
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 22, 2010 2:51:21 PM
	 */
	public String findTypeDictionaryByCon(){
		Map map = typeDictionaryService.findTypeDictionaryByCon(getStart(),getLimit(),
				sysplDicBigType.getBigTypeName(), sysplDicBigType.getBigTypeCode());
		sysplDicBigTypeList = (List<SysplDicBigType>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public String findBigTypeCodeIsExist(){
		bigTypeCodeIsExist = typeDictionaryService.findBigTypeCodeIsExist(sysplDicBigType.getBigTypeCode());
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据大类代码查询所有小类
	 * @author jacobliang
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public String findSmallTypeDicByBigTypeCode(){
		sysplDicSmallTypeListt = typeDictionaryService.findSmallTypeDicByBigTypeCode(sysplDicBigType.getBigTypeCode());
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：停用启用
	 * @author jacobliang
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnableDicBigType()throws ApplicationException{
		try {
			typeDictionaryService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = typeDictionaryService.disEnableDicBigType(new Long(getModViewRecId()), disEnableFlag);
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

	public SysplDicBigType getModel() {
		return sysplDicBigType;
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

	public void setTypeDictionaryService(TypeDictionaryService typeDictionaryService) {
		this.typeDictionaryService = typeDictionaryService;
	}

	public String getSysplDicSmallTypeList() {
		return sysplDicSmallTypeList;
	}

	public void setSysplDicSmallTypeList(
			String sysplDicSmallTypeList) {
		this.sysplDicSmallTypeList = sysplDicSmallTypeList;
	}

	public SysplDicBigType getSysplDicBigType() {
		return sysplDicBigType;
	}

	public void setSysplDicBigType(SysplDicBigType sysplDicBigType) {
		this.sysplDicBigType = sysplDicBigType;
	}
	
	public void setBigTypeCodeIsExist(boolean bigTypeCodeIsExist){
		this.bigTypeCodeIsExist = bigTypeCodeIsExist;
	}
	
	public boolean getBigTypeCodeIsExist(){
		return this.bigTypeCodeIsExist;
	}

	public List<SysplDicBigType> getSysplDicBigTypeList() {
		return sysplDicBigTypeList;
	}

	public void setSysplDicBigTypeList(List<SysplDicBigType> sysplDicBigTypeList) {
		this.sysplDicBigTypeList = sysplDicBigTypeList;
	}

	public List<SysplDicSmallType> getSysplDicSmallTypeListt() {
		return sysplDicSmallTypeListt;
	}

	public void setSysplDicSmallTypeListt(
			List<SysplDicSmallType> sysplDicSmallTypeListt) {
		this.sysplDicSmallTypeListt = sysplDicSmallTypeListt;
	}

}
