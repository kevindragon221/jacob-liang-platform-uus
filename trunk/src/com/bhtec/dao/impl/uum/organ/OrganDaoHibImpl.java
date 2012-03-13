/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:08:56 PM
 */
package com.bhtec.dao.impl.uum.organ;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.UUM_ORGAN;
import static com.bhtec.common.tools.UtilTools.isEmpty;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.uum.organ.OrganDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumOrgan;
public class OrganDaoHibImpl extends BaseDaoHibImpl implements OrganDao {

	public List<UumOrgan> findAllOrgan() {
		List<UumOrgan> organList = this.getAll(UUM_ORGAN,
				" order by uumOrgan.orgId,orgId");
		return organList;
	}

	public int findDownOrgById(long orgId) {
		String queryString = "select count(*) from UumOrgan uumOrgan where uumOrgan.uumOrgan.orgId = " + orgId;
		return this.getRowCount(queryString);
	}

	public List<UumOrgan> findNextLevelChildNodes(long orgId,long firlterOrgId) {
		String filterSql = " and uumOrgan.orgId not in (0";
		if(firlterOrgId != 0){
			filterSql += ","+firlterOrgId;
		}
		filterSql += ")";
		String queryString = "from UumOrgan uumOrgan where uumOrgan.uumOrgan.orgId = ? "+
							 filterSql +" order by uumOrgan.uumOrgan.orgId,uumOrgan.orgId";
		List<UumOrgan> list = this.findByPropertyWithParas(queryString, new Long[]{orgId});
		return list;
	}

	public int findOrgCountByOrgSimpleName(String simpleName) {
		String queryString = "select count(*) from UumOrgan uumOrgan " +
							 "where uumOrgan.orgSimpleName = '"+simpleName+"'";
		return this.getRowCount(queryString);
	}

	public Map findOrganByCon(int start, int limit, String ids, String orgName,
			String orgCode) {
		String queryString = "from UumOrgan uumOrgan where uumOrgan.orgId != 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(orgName)){
			queryString += " and uumOrgan.orgSimpleName like ?";
			params.add("%"+orgName+"%");
		}
		if(!isNullOrEmpty(orgCode)){
			queryString += " and uumOrgan.orgCode like ?";
			params.add("%"+orgCode+"%");
		}
		if(!isEmpty(ids)){
			queryString += " and uumOrgan.orgId in (" + ids+") ";
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by uumOrgan.orgId desc"; 
		List<UumOrgan> limitOrganList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		if(!limitOrganList.isEmpty()){
			for(UumOrgan uumOrgan:limitOrganList){
				uumOrgan.setUpOrgName(uumOrgan.getUumOrgan().getOrgSimpleName());
			}
		}
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitOrganList);
		return map;
	}
	
	/**
	 * 功能说明：根据多个机构ID查询机构信息
	 * @author jacobliang
	 * @param orgIds
	 * @return
	 * @time Feb 14, 2012 5:28:40 PM
	 */
	public List findUumOrgansByOrgIds(String orgIds){
		String queryString = "select orgId,orgSimpleName from UumOrgan uumOrgan " +
							 "where uumOrgan.orgId in(" + orgIds +")";
		return this.findByProperty(queryString);
	}
}
