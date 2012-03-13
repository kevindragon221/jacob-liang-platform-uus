/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.district;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.SYSPL_DISTRICT;
import static com.bhtec.common.tools.UtilTools.isEmpty;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.district.DistrictDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplDistrict;

public class DistrictDaoHibImpl extends BaseDaoHibImpl implements DistrictDao {
	/**
	 * 功能说明：查询地区名称是否重复
	 * @author jacobliang
	 * @param	districtName	地区名称
	 * @return	地区数量
	 * @time Dec 29, 2010 9:01:11 AM
	 */
	public int findDistrictByDistrictName(String districtName){
		String queryString = "select count(*) from SysplDistrict district where district.districtName = '" + districtName+"'";
		int totalProperty = this.getRowCount(queryString);//总记录数
		return totalProperty;
	}
	/**
	 * 功能说明：查询指定地区的下级
	 * @author jacobliang
	 * @param districtId	地区ID
	 * @return	下级数量
	 * @time Dec 29, 2010 9:01:11 AM
	 */
	public int findDownDistrictById(long districtId){
		String queryString = "select count(*) from SysplDistrict district where district.sysplDistrict.districtId = " + districtId;
		int totalProperty = this.getRowCount(queryString);//总记录数
		return totalProperty;
	}
	
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
	public Map findDistrictByCon(int start,int limit,String ids,String districtName,String districtLevel){
		String queryString = "from SysplDistrict district where district.districtId != 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(districtName)){
			queryString += " and district.districtName like ?";
			params.add("%"+districtName+"%");
		}
		if(!isNullOrEmpty(districtLevel)){
			queryString += " and district.districtLevel = ?";
			params.add(districtLevel);
		}
		if(!isEmpty(ids)){
			queryString += " and district.districtId in (" + ids+") ";
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by district.districtId desc"; 
		List<SysplDistrict> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	public List<SysplDistrict> findAllDistrict() {
		List<SysplDistrict> districtList = this.getAll(SYSPL_DISTRICT,
				" order by sysplDistrict.districtId,districtId");
		return districtList;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param districtId		地区ID
	 * @param filterDistrictId	过滤模块id,当修改模块菜单时将本模块ID过滤掉
	 * @return
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<SysplDistrict> findNextLevelChildNodes(long districtId,long filterDistrictId){
		String filterSql = " and district.districtId not in (0";
		if(filterDistrictId != 0){
			filterSql += ","+filterDistrictId;
		}
		filterSql += ")";
		String queryString = "from SysplDistrict district where district.sysplDistrict.districtId = ? " +
							 filterSql+" order by districtId";
		List<SysplDistrict> list = this.findByPropertyWithParas(queryString, new Long[]{districtId});
		return list;
	}
}
