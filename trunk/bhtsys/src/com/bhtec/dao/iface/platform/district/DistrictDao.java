/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.district;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplDistrict;

public interface DistrictDao extends BaseDao {
	/**
	 * 功能说明：查询地区名称是否重复
	 * @author jacobliang
	 * @param	districtName	地区名称
	 * @return	地区数量
	 * @time Dec 29, 2010 9:01:11 AM
	 */
	public int findDistrictByDistrictName(String districtName);
	/**
	 * 功能说明：查询指定地区的下级
	 * @author jacobliang
	 * @param districtId	地区ID
	 * @return	下级数量
	 * @time Dec 29, 2010 9:01:11 AM
	 */
	public int findDownDistrictById(long districtId);
	/**
	 * 功能说明：根据条件查询地区名称
	 * @author jacobliang
	 * @param start		开始记录
	 * @param limit		每页记录
	 * @param ids		下级
	 * @param districtName	地区名称
	 * @param districtLevel 地区级别
	 * @return
	 * @time Dec 29, 2010 9:03:57 AM
	 */
	public Map findDistrictByCon(int start,int limit,String ids,String districtName,String districtLevel);
	/**
	 * 功能说明：查询所有地区信息
	 * @author jacobliang
	 * @return List<SysplDistrict> 所有地区信息
	 * @time Dec 29, 2010 9:05:06 AM
	 */
	public List<SysplDistrict> findAllDistrict();
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param districtId		地区ID
	 * @param filterDistrictId	过滤模块id,当修改模块菜单时将本模块ID过滤掉
	 * @return
	 * @time Dec 29, 2010 9:06:42 AM
	 */
	public List<SysplDistrict> findNextLevelChildNodes(long districtId,long filterDistrictId);
	
}
