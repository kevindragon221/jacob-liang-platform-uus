package com.bhtec.dao.iface.platform.primarykey;

import com.bhtec.dao.iface.BaseDao;

/**
 *功能描述：
 *@since   Mar 14, 2010 12:13:23 PM
 *@author  jacobliang
 *@version 1.0
 */
public interface PrimaryKeySeqDao extends BaseDao {
	/**
	 * 
	 *功能描述：根据pojoname获得应用表的最大主键ID
	 *@since Mar 17, 2010
	 *@author jacobliang
	 *@param	pojoName	pojo name
	 *@param	primaryKeyName	主键名
	 *@version 1.0
	 *@return long    最大主键ID
	 */
	public Long getMaxIdOfTable(String pojoName,String primaryKeyName);
}
