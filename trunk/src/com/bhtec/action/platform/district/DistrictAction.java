/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.district;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER_CODE;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.domain.pojo.platform.SysplDistrict;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.district.DistrictService;
import com.opensymphony.xwork2.ModelDriven;

public class DistrictAction extends PlatformBaseAction implements ModelDriven<SysplDistrict> {
	private static final long serialVersionUID = 10000L;
	Logger log = Logger.getLogger(this.getClass());
	private DistrictService districtService;
	private SysplDistrict sysplDistrict = new SysplDistrict();
	private List districtList;
	private boolean existDistrict;
	private int count;
	private String failMesg;
	private String disEnableFlag;
	private boolean disEnableBol;
	/**
	 * 功能说明：保存地区
	 * @author jacobliang
	 * @param sysplDistrict		地区对象
	 * @throws ApplicationException
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public void saveSysplDistrict(){
		try {
			districtService.setHttpServletRequest(this.getHttpServletRequest());
			SysplDistrict sysplDistrictParent = new SysplDistrict();
			sysplDistrictParent.setDistrictId(sysplDistrict.getUpDistrictId());
			sysplDistrict.setCreateDate(new Date());
			sysplDistrict.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			sysplDistrict.setSysplDistrict(sysplDistrictParent);
			districtService.saveSysplDistrict(sysplDistrict);
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
	public String findDistrictByCon(){
		Map map = districtService.findDistrictByCon(getStart(),getLimit(),getTreeId(),sysplDistrict.getDistrictName(), sysplDistrict.getDistrictLevel());
		districtList = (List<SysplDistrict>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据地区ID查询某个地区
	 * @author jacobliang
	 * @param 	districtId		地区ID
	 * @return	sysplDistrict	地区对象
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public String findSysplDistrictByDistrictId(){
		try {
			sysplDistrict = districtService.findSysplDistrictByDistrictId(new Long(getModViewRecId()));
			sysplDistrict.setUpDistrictId(sysplDistrict.getSysplDistrict().getDistrictId());
			sysplDistrict.setUpDistrictName(sysplDistrict.getSysplDistrict().getDistrictName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("getModViewRecId() is not number. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询地区名称是否重复
	 * @author jacobliang
	 * @param  districtName	地区名称
	 * @return boolean true 地区名存在 false 不存在
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public String findDistrictNameIsExist(){
		try {
			existDistrict = districtService.findDistrictNameIsExist(this.getHttpServletRequest().getParameter("districtName"));
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改地区
	 * @author jacobliang
	 * @param sysplDistrict	地区对象
	 * @return
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public void modifySysplDistrict(){
		try {
			districtService.setHttpServletRequest(this.getHttpServletRequest());
			SysplDistrict sysplDistrictParent = new SysplDistrict();
			//upmodid为0参数传不过来
			sysplDistrictParent.setDistrictId(sysplDistrict.getUpDistrictId()==null?0:sysplDistrict.getUpDistrictId());
			sysplDistrict.setSysplDistrict(sysplDistrictParent);
			districtService.modifySysplDistrict(sysplDistrict);
			this.returnSuccess();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("modifySysplDistrict() is not number. ", e);
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
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	moduleId	模块id
	 * @return
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public void findNextLevelChildNodes(){
		String filterModuleId = this.getHttpServletRequest().getParameter("filterModuleId");
		List<TreeVo> asyncTreeList = districtService.findNextLevelChildNodes(new Long(getModViewRecId()==null?"0":getModViewRecId()),
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
	public String disEnableDistrict(){
		try {
			districtService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = districtService.disEnableDistrict(new Long(getModViewRecId()), disEnableFlag);
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
	
	public SysplDistrict getModel() {
		return sysplDistrict;
	}

	public void setDistrictService(DistrictService districtService) {
		this.districtService = districtService;
	}

	public boolean getExistDistrict() {
		return existDistrict;
	}
	public void setExistDistrict(boolean existDistrict) {
		this.existDistrict = existDistrict;
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

	public boolean isDisEnableBol() {
		return disEnableBol;
	}

	public void setDisEnableBol(boolean disEnableBol) {
		this.disEnableBol = disEnableBol;
	}

	public List getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List districtList) {
		this.districtList = districtList;
	}
}
