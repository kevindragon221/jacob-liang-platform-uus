/**
 *功能说明：系统公告
 * @author jacobliang
 * @time @Jul 26, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.platform.accessory;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface AccessoryService extends BaseService {
	/**
	 * 功能说明：保存附件
	 * @author jacobliang
	 * @param sysplAccessory	附件对象
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveAccessory(SysplAccessory sysplAccessory) throws ApplicationException;
	/**
	 * 功能说明：删除某个附件
	 * @author jacobliang
	 * @param	accessoryIds 	附件ID
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void deleteAccessoryByIds(List<Long> accessoryIds)throws ApplicationException;
	/**
	 * 功能说明：根据业务表ID查询所有附件
	 * @author jacobliang
	 * @return id			业务表主键ID
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAccessory> findAllAccessoryById(long id);
	/**
	 * 功能说明：根据附件ID查询附件信息
	 * @author jacobliang
	 * @param accessoryId	附件ID
	 * @return
	 * @time Feb 23, 2011 3:45:22 PM
	 */
	public SysplAccessory findSysplAccessoryByAccId(long accessoryId);
}
