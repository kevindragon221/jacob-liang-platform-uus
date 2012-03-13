/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.typedictionary;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.typedictionary.TypeDictionaryDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplDicBigType;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;

public class TypeDictionaryDaoHibImpl extends BaseDaoHibImpl implements TypeDictionaryDao {
	
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
	public Map findTypeDictionaryByCon(int start,int limit,String bigTypeName,String bigTypeCode){
		String queryString = "from SysplDicBigType dicBig where 0 = 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(bigTypeName)){
			queryString += " and dicBig.bigTypeName like ?";
			params.add("%"+bigTypeName+"%");
		}
		if(!isNullOrEmpty(bigTypeCode)){
			queryString += " and dicBig.bigTypeCode like ?";
			params.add("%"+bigTypeCode+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by dicBig.bigTypeId desc"; 
		List<SysplDicBigType> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	/**
	 * 功能说明：根据大类代码查询所有小类
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public List<SysplDicSmallType> findSmallTypeDicByBigTypeCode(String bigTypeCode){
		String queryString = "from SysplDicBigType bigType " +
							 "where bigType.bigTypeCode = '"+bigTypeCode+"' ";
		
		SysplDicBigType sysplDicBigType = (SysplDicBigType)this.findByProperty(queryString).get(0);
		Iterator iterator = sysplDicBigType.getSysplDicSmallTypes().iterator();
		List<SysplDicSmallType> sysplDicSmallTypeList = new ArrayList<SysplDicSmallType>();
		while(iterator.hasNext()){
			SysplDicSmallType sysplDicSmallType = (SysplDicSmallType)iterator.next();
			sysplDicSmallTypeList.add(sysplDicSmallType);
		}
		Collections.sort(sysplDicSmallTypeList, new SysplDicSmallType().getSysplDicSmallTypeInst());
		return sysplDicSmallTypeList;
	}
	
	
	/**
	 * 功能说明：根据大类编码查询大类数量
	 * @author jacobliang
	 * @param bigTypeCode
	 * @return
	 * @time Dec 22, 2010 9:42:55 AM
	 */
	public int findBigTypeDicCountByBigTypeCode(String bigTypeCode){
		String queryString = "select count(*) from SysplDicBigType bigType " +
						   "where bigType.bigTypeCode = '"+bigTypeCode+"'";
		return this.getRowCount(queryString);
	}
	
	/**
	 * 功能说明：根据大类ID删除小类
	 * @author jacobliang
	 * @param bigTypeId
	 * @time Jan 27, 2011 2:20:08 PM
	 */
	public void deleteSysplDicSmallTypeByBigTypeId(long bigTypeId){
		String hqlString = "delete from SysplDicSmallType dst " +
		 				   "where dst.sysplDicBigType.bigTypeId = "+bigTypeId;
		this.excuteHql(hqlString);
	}
	/**
	 * 功能说明：修改大类状态
	 * @author jacobliang
	 * @param bigTypeId
	 * @param status
	 * @time Jan 27, 2011 3:27:49 PM
	 */
	public void modifyGroupStatus(long bigTypeId,String status){
		String hqlString = "update SysplDicBigType bigType " +
		 				   "set bigType.status = '"+status+"' " +
		 				   "where bigType.bigTypeId="+bigTypeId;
		this.excuteHql(hqlString);
	}
}
