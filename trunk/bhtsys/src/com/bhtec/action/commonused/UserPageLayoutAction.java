/**
 *功能说明：用户自定义系统页面布局类
 * @author jacobliang
 * @time @Nov 4, 2010 @10:00:44 AM
 */
package com.bhtec.action.commonused;

import static com.bhtec.common.constant.Common.USER_ID;

import java.util.List;
import java.util.Map;

import com.bhtec.action.BaseAction;
import com.bhtec.domain.pojo.uum.UumUserPageLayout;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.commonused.UserPageLayoutService;
import com.opensymphony.xwork2.ModelDriven;

public class UserPageLayoutAction extends BaseAction implements ModelDriven<UumUserPageLayout> {
	private static final long serialVersionUID = 100000L;
	private UumUserPageLayout uumUserPageLayout = new UumUserPageLayout();
	private UserPageLayoutService userPageLayoutService;
	private Map<String,List<MainFrameFun>> mainFrameFunMap;//五个不同区域列表
	private String failMesg;
	
	/**
	 * 功能说明：保存用户设置的页面布局
	 * @author jacobliang
	 * @param uumUserPageLayout
	 * @time Nov 4, 2010 9:47:48 AM
	 */
	public void saveUserPageLayout(){
		try {
			userPageLayoutService.setHttpServletRequest(this.getHttpServletRequest());
			long userId = (Long)this.getSession().get(USER_ID);
			uumUserPageLayout.setUserId(userId);
			this.userPageLayoutService.saveUserPageLayout(uumUserPageLayout);
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
	 * 功能说明：根据用户ID查询页面布局
	 * @author jacobliang
	 * @param userId
	 * @return
	 * @time Nov 4, 2010 9:50:25 AM
	 */
	public String findUserPageLayoutByUserId(){
		long userId = (Long)this.getSession().get(USER_ID);
		uumUserPageLayout = 
			userPageLayoutService.findUserPageLayoutByUserId(userId);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：获得定义的所有功能区列表
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 19, 2010 7:37:38 PM
	 */
	public String findAllMainFrameFun(){
		try{
			mainFrameFunMap = this.userPageLayoutService.findAllMainFrameFun();
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public UumUserPageLayout getModel() {
		return uumUserPageLayout;
	}
	
	public void setUserPageLayoutService(UserPageLayoutService userPageLayoutService) {
		this.userPageLayoutService = userPageLayoutService;
	}
	public Map<String, List<MainFrameFun>> getMainFrameFunMap() {
		return mainFrameFunMap;
	}
	public void setMainFrameFunMap(Map<String, List<MainFrameFun>> mainFrameFunMap) {
		this.mainFrameFunMap = mainFrameFunMap;
	}
	public String getFailMesg() {
		return failMesg;
	}
	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

}
