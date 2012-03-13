/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.systemparameter;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.systemparameter.SystemParameterDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplSysParameter;

public class SystemParameterDaoHibImpl extends BaseDaoHibImpl implements SystemParameterDao {
	
	/**
	 * 功能说明：根据系统参数名称及键值查询系统参数
	 * @author jacobliang
	 * @param start
	 * @param limit
	 * @param paraName
	 * @param paraKeyName
	 * @return
	 * @time Dec 10, 2010 8:37:29 PM
	 */
	public Map findSystemParaByCon(int start,int limit,String paraName,String paraKeyName){
		String queryString = "from SysplSysParameter para where 0 = 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(paraName)){
			queryString += " and para.paraName like ?";
			params.add("%"+paraName+"%");
		}
		if(!isNullOrEmpty(paraKeyName)){
			queryString += " and para.paraKeyName like ?";
			params.add("%"+paraKeyName+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by para.paraId desc"; 
		List<SysplSysParameter> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public SysplSysParameter findSystemParaByParaName(String paraKeyName){
		String queryString = "from SysplSysParameter para where para.paraKeyName = ?";
		List paraList = this.findByPropertyWithParas(queryString, new String[]{paraKeyName});
		if(paraList == null || paraList.size()==0)return null;
		return (SysplSysParameter)paraList.get(0);
	}
	
}
