/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.accessory;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.exception.ApplicationException;

public interface AccessoryDao extends BaseDao {
	/**
	 * 功能说明：删除某个附件
	 * @author jacobliang
	 * @param	accessoryId 	附件ID
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void deleteAccessoryByIds(String accessoryIds);
	/**
	 * 功能说明：根据业务表ID查询所有附件
	 * @author jacobliang
	 * @return id			业务表主键ID
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAccessory> findAllAccessoryById(long id);
}
