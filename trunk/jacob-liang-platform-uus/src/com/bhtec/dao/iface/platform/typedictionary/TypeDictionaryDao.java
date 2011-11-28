/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.typedictionary;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;

public interface TypeDictionaryDao extends BaseDao {
	/**
	 * 功能说明：根据大类名称及大类代码查询类别字典
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param bigTypeName	大类名称
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 21, 2010 8:37:29 PM
	 */
	public Map findTypeDictionaryByCon(int start,int limit,String bigTypeName,String bigTypeCode);
	/**
	 * 功能说明：根据大类代码查询所有小类
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public List<SysplDicSmallType> findSmallTypeDicByBigTypeCode(String bigTypeCode);
	/**
	 * 功能说明：根据大类编码查询大类数量
	 * @author jacobliang
	 * @param bigTypeCode
	 * @return
	 * @time Dec 22, 2010 9:42:55 AM
	 */
	public int findBigTypeDicCountByBigTypeCode(String bigTypeCode);
	/**
	 * 功能说明：根据大类ID删除小类
	 * @author jacobliang
	 * @param bigTypeId
	 * @time Jan 27, 2011 2:20:08 PM
	 */
	public void deleteSysplDicSmallTypeByBigTypeId(long bigTypeId);
	/**
	 * 功能说明：修改大类状态
	 * @author jacobliang
	 * @param bigTypeId
	 * @param status
	 * @time Jan 27, 2011 3:27:49 PM
	 */
	public void modifyGroupStatus(long bigTypeId,String status);
}
