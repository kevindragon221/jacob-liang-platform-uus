/**
 *功能说明：模块管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.accessory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.common.tools.UtilTools;
import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.accessory.AccessoryService;

public class AccessoryAction extends PlatformBaseAction {
	private static final long serialVersionUID = 10000L;
	private AccessoryService accessoryService;
	private String failMesg;
	private long accessoryId;//附件ID
	private String accessoryPath;//附件存放路径
	private String fileName;//文件名称
	private String oldFileName;//原文件名
	/**
	 * 功能说明：根据附件ID删除附件
	 * @author jacobliang
	 * @return
	 * @time Dec 3, 2010 4:46:52 PM
	 */
	public String deleteAccessory(){
		try {
			HttpServletRequest request = this.getHttpServletRequest();
			accessoryService.setHttpServletRequest(request);
			List<Long> accessoryIdList = new ArrayList<Long>();
			accessoryIdList.add(accessoryId);
			accessoryService.deleteAccessoryByIds(accessoryIdList);
			UtilTools.deleteAccessoryFile(request, accessoryPath, oldFileName);
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	/**
	 * 功能说明：附件下载
	 * @author jacobliang
	 * @return
	 * @time Dec 7, 2010 11:18:03 AM
	 */
	public String downloadFile(){
		HttpServletRequest request = this.getHttpServletRequest();
		HttpServletResponse response = this.getHttpServletResponse();
		accessoryPath = request.getParameter("accessoryPath");
		String accessoryId = request.getParameter("accessoryId");
		SysplAccessory sysplAccessory = accessoryService.findSysplAccessoryByAccId(Long.parseLong(accessoryId));
		String accessoryName = sysplAccessory.getAccessoryName();
		String oldFileName = accessoryName;
		int splitFlag = accessoryName.indexOf("_");
		fileName = accessoryName.substring(splitFlag+1);
		boolean isOnline = "Y".equals(request.getParameter("isOnline"))?true:false;
		try {
			UtilTools.downloadFile(request, response, accessoryPath, fileName,oldFileName, isOnline);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return this.SUCCESS;
	}
	
	public void setAccessoryService(AccessoryService accessoryService) {
		this.accessoryService = accessoryService;
	}

	public String getFailMesg() {
		return failMesg;
	}
	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public long getAccessoryId() {
		return accessoryId;
	}

	public void setAccessoryId(long accessoryId) {
		this.accessoryId = accessoryId;
	}

	public String getAccessoryPath() {
		return accessoryPath;
	}

	public void setAccessoryPath(String accessoryPath) {
		this.accessoryPath = accessoryPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}
	
}
