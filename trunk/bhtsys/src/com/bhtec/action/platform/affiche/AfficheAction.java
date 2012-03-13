/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.affiche;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.common.tools.UtilTools;
import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.accessory.AccessoryService;
import com.bhtec.service.iface.platform.affiche.AfficheService;
import com.opensymphony.xwork2.ModelDriven;

public class AfficheAction extends PlatformBaseAction implements ModelDriven<SysplAffiche> {
	private static final long serialVersionUID = 10000L;
	private SysplAffiche sysplAffiche = new SysplAffiche();
	private AfficheService afficheService;
	
	private String failMesg;
	private List<Long> afficheIdList;
	private List<SysplAffiche> afficheList;
	private int count;
	private AccessoryService accessoryService;
	String afficheAttachment = "uploadFile"+File.separator+"affiche";
	/**
	 * private File fileUpload 中fileUpload是与FileUpload.js中‘选择文件’
	 * 的id属性相对应的，如果需要修改必须同步进行
	 * 
	 * private String fileUploadFileName（*FileName）
	 * 前缀*必须与File 定义变量相一致
	 * 如果File变量有改动，其也随之修改
	 */
	private File[] accessoryUpload;
	private String[] accessoryUploadFileName;
	/**
	 * 功能说明：保存公告
	 * @author jacobliang
	 * @param sysplAffiche	公告公告
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveAffiche() {
		try {
			//保存公告
			afficheService.setHttpServletRequest(this.getHttpServletRequest());
			afficheService.saveAffiche(sysplAffiche);
			//保存附件
			if(accessoryUpload != null){
				int i = 0;
				for(File accessoryFile:accessoryUpload){
					String accessoryName = "";
					if(accessoryFile != null){
						accessoryName = UtilTools.uploadFile(getHttpServletRequest(),accessoryFile,
								afficheAttachment,accessoryUploadFileName[i]);
					}
					SysplAccessory sysplAccessory = new SysplAccessory();
					accessoryService.setHttpServletRequest(this.getHttpServletRequest());
					sysplAccessory.setInfoId(sysplAffiche.getAfficheId());
					sysplAccessory.setAccessoryName(accessoryName);
					accessoryService.saveAccessory(sysplAccessory);
					i++;
				}
			}
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
	 * 功能说明：删除某个公告
	 * @author jacobliang
	 * @param	afficheId 	公告ID
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String deleteAfficheByIds(){
		try {
			afficheService.setHttpServletRequest(this.getHttpServletRequest());
			afficheService.deleteAfficheByIds(afficheIdList);
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
	 * 功能说明：根据公告ID查询某个公告
	 * @author jacobliang
	 * @param AfficheId
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findAfficheByAfficheId(){
		sysplAffiche = afficheService.findAfficheByAfficheId(new Long(getModViewRecId()));
		return this.SUCCESS;
	}
	/**
	 * 功能说明：修改公告
	 * @author jacobliang
	 * @param sysplAffiche	公告对象
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifyAffiche()throws ApplicationException{
		try {
			afficheService.setHttpServletRequest(this.getHttpServletRequest());
			//保存附件
			if(accessoryUpload != null){
				int i = 0;
				for(File accessoryFile:accessoryUpload){
					String accessoryName = "";
					if(accessoryFile != null){
						accessoryName = UtilTools.uploadFile(getHttpServletRequest(),accessoryFile,
								afficheAttachment,accessoryUploadFileName[i]);
					}
					SysplAccessory sysplAccessory = new SysplAccessory();
					accessoryService.setHttpServletRequest(this.getHttpServletRequest());
					sysplAccessory.setInfoId(sysplAffiche.getAfficheId());
					sysplAccessory.setAccessoryName(accessoryName);
					accessoryService.saveAccessory(sysplAccessory);
					i++;
				}
			}
			afficheService.modifyAffiche(sysplAffiche);
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
	 * 功能说明：根据条件查询公告
	 * @author jacobliang
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findAfficheByCon(){
		Map map = afficheService.findAfficheByCon(getStart(),getLimit(),
				sysplAffiche.getAfficheTitle(), sysplAffiche.getAffichePulish());
		afficheList = (List<SysplAffiche>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	/**
	 * 功能说明：查询所有有效期公告信息
	 * @author jacobliang
	 * @return List<SysplAffiche>	所有公告信息
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAffiche> findAllAfficheBeforeValidate(){
		afficheList = afficheService.findAllAfficheBeforeValidate();
		return afficheList;
	}
	
	public SysplAffiche getModel() {
		return sysplAffiche;
	}

	public void setAfficheService(AfficheService afficheService) {
		this.afficheService = afficheService;
	}
	public String getFailMesg() {
		return failMesg;
	}
	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}
	public List<Long> getAfficheIdList() {
		return afficheIdList;
	}
	public void setAfficheIdList(List<Long> afficheIdList) {
		this.afficheIdList = afficheIdList;
	}
	public List<SysplAffiche> getAfficheList() {
		return afficheList;
	}
	public void setAfficheList(List<SysplAffiche> afficheList) {
		this.afficheList = afficheList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public File[] getAccessoryUpload() {
		return accessoryUpload;
	}
	public void setAccessoryUpload(File[] accessoryUpload) {
		this.accessoryUpload = accessoryUpload;
	}
	public String[] getAccessoryUploadFileName() {
		return accessoryUploadFileName;
	}
	public void setAccessoryUploadFileName(String[] accessoryUploadFileName) {
		this.accessoryUploadFileName = accessoryUploadFileName;
	}
	public void setAccessoryService(AccessoryService accessoryService) {
		this.accessoryService = accessoryService;
	}
	
}
