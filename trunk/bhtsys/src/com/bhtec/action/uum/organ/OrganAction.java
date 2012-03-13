/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:33:09 PM
 */
package com.bhtec.action.uum.organ;

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

import com.bhtec.action.uum.UumBaseAction;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.domain.pojohelper.tree.TreeVoExtend;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.uum.organ.OrganService;
import com.opensymphony.xwork2.ModelDriven;

public class OrganAction extends UumBaseAction  implements ModelDriven<UumOrgan> {
	private static final long serialVersionUID = 1000000L;
	Logger log = Logger.getLogger(this.getClass());
	UumOrgan uumOrgan = new UumOrgan();
	private List<UumOrgan> orgList;
	private String orgName;
	private String orgCode;
	private boolean existOrg;
	private int count;
	private String failMesg;
	private String disEnableFlag;
	private int disEnableResultFlag;
	private OrganService organService;
	
	/**
	 * 功能说明：保存机构
	 * @author jacobliang
	 * @time Sep 23, 2010 6:03:41 PM
	 */
	public void saveOrgan(){
		try {
			organService.setHttpServletRequest(this.getHttpServletRequest());
			UumOrgan uumOrganParent = new UumOrgan();
			uumOrganParent.setOrgId(uumOrgan.getUporgId());
			uumOrgan.setUumOrgan(uumOrganParent);
			uumOrgan.setCreateDate(new Date());
			uumOrgan.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			organService.saveOrgan(uumOrgan);
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
	 * 功能说明：查询所有机构
	 * @author jacobliang
	 * @time Sep 23, 2010 6:47:32 PM
	 */
	public String findAllOrgan(){
		try {
			orgList = this.organService.findAllOrgan();
		}catch (SystemException e) {
			e.printStackTrace();
			log.error("findAllOrgan() occur error. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 
	 */
	public String findOrganByCon(){
		Map map = organService.findOrganByCon(getStart(),getLimit(),getTreeId(),orgName, this.uumOrgan.getOrgCode());
		orgList = (List<UumOrgan>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	organId	机构id
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void findNextLevelChildNodes(){
		String filterOrgId = this.getHttpServletRequest().getParameter("filterOrgId");
		List<TreeVo> asyncTreeList = organService.findNextLevelChildNodes(Long.parseLong(getModViewRecId()==null?"0":getModViewRecId())
				,new Long(filterOrgId==null?"0":filterOrgId));
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
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	organId	机构id
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void findNextLevelChildNodesWithCheckbox(){
		String filterOrgId = this.getHttpServletRequest().getParameter("filterOrgId");
		List<TreeVoExtend> asyncTreeList = organService.findNextLevelChildNodesWithCheckbox(Long.parseLong(getModViewRecId()==null?"0":getModViewRecId())
				,new Long(filterOrgId==null?"0":filterOrgId));
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
	 * 功能说明：根据机构ID查询某个机构
	 * @author jacobliang
	 * @param organId
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public String findOrganByOrganId(){
		try {
			uumOrgan = organService.findOrganByOrganId(new Long(getModViewRecId()));
		}catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("getModViewRecId() is not number. ", e);
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查询机构名称是否重复
	 * @author jacobliang
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public String findOrganByOrganName(){
		try {
			existOrg = organService.findOrganByOrganName(this.getHttpServletRequest().getParameter("organName"));
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：修改机构
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyOrgan(){
		try {
			organService.setHttpServletRequest(this.getHttpServletRequest());
			UumOrgan uumOrganParent = new UumOrgan();
			//为0参数传不过来
			uumOrganParent.setOrgId(uumOrgan.getUporgId()==null?0:uumOrgan.getUporgId());
			uumOrgan.setUumOrgan(uumOrganParent);
			organService.modifyOrgan(uumOrgan);
			this.returnSuccess();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("modifyOrgan() is not number. ", e);
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
	 * 功能说明：显示隐藏机构
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnableOrgan(){
		try {
			organService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableResultFlag = organService.disEnableOrgan(new Long(getModViewRecId()), disEnableFlag);
		} catch (NumberFormatException e) {
			e.printStackTrace();		
			log.error("disEnableOrgan() is not number. ", e);
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
	
	public UumOrgan getModel() {
		return uumOrgan;
	}
	
	public void setOrganService(OrganService organService) {
		this.organService = organService;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public UumOrgan getUumOrgan() {
		return uumOrgan;
	}

	public void setUumOrgan(UumOrgan uumOrgan) {
		this.uumOrgan = uumOrgan;
	}

	public List<UumOrgan> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<UumOrgan> orgList) {
		this.orgList = orgList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public boolean isExistOrg() {
		return existOrg;
	}

	public void setExistOrg(boolean existOrg) {
		this.existOrg = existOrg;
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

	public int getDisEnableResultFlag() {
		return disEnableResultFlag;
	}

	public void setDisEnableResultFlag(int disEnableResultFlag) {
		this.disEnableResultFlag = disEnableResultFlag;
	}

}
