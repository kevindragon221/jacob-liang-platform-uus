/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @2:56:23 PM
 */
package com.bhtec.dao.iface.uum.organ;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumOrgan;

public interface OrganDao extends BaseDao {
	/**
	 * 功能说明：根据机构简称查询机构数量
	 * @author jacobliang
	 * @param
	 * @param
	 * @param simpleName
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 2:59:20 PM
	 */
	public int findOrgCountByOrgSimpleName(String simpleName);
	/**
	 * 功能说明：查询指定机构的下级
	 * @author jacobliang
	 * @param
	 * @param
	 * @param orgId	机构id
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 2:59:20 PM
	 */
	public int findDownOrgById(long orgId);
	/**
	 * 功能说明：根据条件查询机构
	 * @author jacobliang
	 * @param start	开始页
	 * @param limit	每页记录数
	 * @param ids	列表机构ID
	 * @param orgName机构名称
	 * @param orgCode机构代码
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 3:02:42 PM
	 */
	public Map findOrganByCon(int start,int limit,String ids,String orgName,String orgCode);
	/**
	 * 功能说明：查询所有机构信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 3:02:42 PM
	 */
	public List<UumOrgan> findAllOrgan();
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	orgId	机构id
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 11:55:37 AM
	 */
	public List<UumOrgan> findNextLevelChildNodes(long orgId,long filterOrgId);
	/**
	 * 功能说明：根据多个机构ID查询机构信息
	 * @author jacobliang
	 * @param orgIds
	 * @return
	 * @time Feb 14, 2012 5:28:40 PM
	 */
	public List findUumOrgansByOrgIds(String orgIds);
}
