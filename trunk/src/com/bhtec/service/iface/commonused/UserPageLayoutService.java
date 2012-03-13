/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 4, 2010 @9:52:03 AM
 */
package com.bhtec.service.iface.commonused;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumUserPageLayout;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface UserPageLayoutService extends BaseService {
	/**
	 * 功能说明：保存用户设置的页面布局
	 * @author jacobliang
	 * @param uumUserPageLayout		用户页面布局对象
	 * @time Nov 4, 2010 9:47:48 AM
	 */
	public void saveUserPageLayout(UumUserPageLayout uumUserPageLayout)throws ApplicationException;
	/**
	 * 功能说明：根据用户ID查询页面布局
	 * @author jacobliang
	 * @param userId	根据用户ID主键查询用户所设置页面布局
	 * @return
	 * @time Nov 4, 2010 9:50:25 AM
	 */
	public UumUserPageLayout findUserPageLayoutByUserId(long userId);
	/**
	 * 功能说明：获得定义的所有功能区列表
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 19, 2010 7:37:38 PM
	 */
	public Map<String,List<MainFrameFun>> findAllMainFrameFun()throws ApplicationException;
}
