/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.code;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.code.CodeDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplCode;

public class CodeDaoHibImpl extends BaseDaoHibImpl implements CodeDao {
	
	public Map findCodeByCon(int start,int limit,String codeEngName,String codeName,String moduleName){
		String queryString = "from SysplCode code where 0 = 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(codeEngName)){
			queryString += " and code.codeEngName like ?";
			params.add("%"+codeEngName+"%");
		}
		if(!isNullOrEmpty(codeName)){
			queryString += " and code.codeName like ?";
			params.add("%"+codeName+"%");
		}
		if(!isNullOrEmpty(moduleName)){
			queryString += " and code.moduleName like ?";
			params.add("%"+moduleName+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by code.codeId desc"; 
		List<SysplCode> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString, params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	/**
	 * 功能说明：根据编码英文名称查询编码是否存在
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public int findSysplCodeByCodeEngName(String codeEngName){
		String queryString = "select count(*) from SysplCode code " +
							 "where code.codeEngName = ?";
		List params = new ArrayList();
		params.add(codeEngName);
		int totalProperty = this.getRowCount(queryString,params);//总记录数
		return totalProperty;
	}
	/**
	 * 功能说明：根据编码英文名称查询编码
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public SysplCode obtainSysplCodeByCodeEngName(String codeEngName){
		String queryString = "from SysplCode code " +
							 "where code.codeEngName = ?";
		String[] paras = {codeEngName};
		List list = this.findByPropertyWithParas(queryString, paras);
		SysplCode sysplCode = null;
		if(list != null && !list.isEmpty()){
			sysplCode = (SysplCode)list.get(0);
		}
		return sysplCode;
	}
}
