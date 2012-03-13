/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.accessory;

import java.util.List;

import com.bhtec.dao.iface.platform.accessory.AccessoryDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplAccessory;

public class AccessoryDaoHibImpl extends BaseDaoHibImpl implements AccessoryDao {

	public void deleteAccessoryByIds(String accessoryIds){
		String hqlString = "delete from SysplAccessory acc " +
						   "where acc.accessoryId in("+accessoryIds+")";
		this.excuteHql(hqlString);
	}

	public List<SysplAccessory> findAllAccessoryById(long id) {
		String queryString = "from SysplAccessory acc " +
						   "where acc.infoId = " +id;
		return this.findByProperty(queryString);
	}
	
}
