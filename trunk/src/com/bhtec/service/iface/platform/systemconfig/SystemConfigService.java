/**
 *功能说明：系统配置文件维护
 * @author jacobliang
 * @time @Nov 21, 2010 @1:40:20 PM
 */
package com.bhtec.service.iface.platform.systemconfig;

import com.bhtec.common.util.SystemConfigBean;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface SystemConfigService extends BaseService {
	/**
	 * 功能说明：获得系统配置信息对象
	 * @author jacobliang
	 * @return
	 * @time Nov 21, 2010 1:41:21 PM
	 */
	public SystemConfigBean obtainSystemConfigInfo()throws ApplicationException;
	/**
	 * 功能说明：设置系统配置信息
	 * @author jacobliang
	 * @param systemConfig	系统配置对象
	 * @param userAddDelFlag 增加修改标志
	 * @throws ApplicationException
	 * @time Nov 21, 2010 1:47:51 PM
	 */
	public void setSystemConfigInfo(SystemConfigBean systemConfigBean,String userAddDelFlag)throws ApplicationException;
}
