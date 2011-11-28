package com.bhtec.service.iface.platform.primarykey;

import java.util.List;
import java.util.Map;

import com.bhtec.service.iface.BaseService;

/**
 *功能描述：用于主键生成
 *@since   Mar 14, 2010 11:56:04 AM
 *@author  jacobliang
 *@version 1.0
 */
public interface PrimaryKeySeqService extends BaseService {
	
	/**
	 * 功能描述：保存主键信息
	 * @since Mar 17, 2010
	 * @author jacobliang
	 * @version 1.0
	 * @return
	 */
	public void savePrimaryKeySeq();
	/**
	 * 功能描述：删除主键信息
	 * @since Mar 17, 2010
	 * @author jacobliang
	 * @version 1.0
	 * @return
	 */
	public void deletePrimaryKeySeq(String pojoName);
	/**
	 * 功能描述：查找所有主键信息
	 * @since Mar 17, 2010
	 * @author jacobliang
	 * @version 1.0
	 * @return
	 */
	public List findAllPrimaryKeySeq();
	/**
	 * 功能描述：更新主键信息
	 * @since Mar 17, 2010
	 * @author jacobliang
	 * @version 1.0
	 * @return
	 */
	public void updatePrimaryKeySeq();
	/**
	 * 
	 *功能描述：获得所有序列表的map
	 *@since Mar 17, 2010  9:36:40 PM
	 *@author jacobliang
	 *@version 1.0
	 *@param
	 *@return map
	 */
	public Map getSeqMap();
}
