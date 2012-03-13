/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.moduleopt;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.SYSPL_MODULEOPT_MEMU;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.platform.moduleopt.ModuleOptDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;

public class ModuleOptDaoHibImpl extends BaseDaoHibImpl implements ModuleOptDao {
	
	public List<SysplOperate> findAllModuleOpt() {
		//查询未被停用的操作
		String queryString = "from SysplOperate sysplOperate " +
							 "where sysplOperate.status != '"+DISABLE+
							 "' order by operateId asc";
		List<SysplOperate> moduleOptList = this.findByProperty(queryString);
		return moduleOptList;
	}
	
	public Map findModuleOptByCon(int start, int limit, String modOptName) {
		String queryString = "from SysplOperate opt ";
		List params = new ArrayList();
		if(!isNullOrEmpty(modOptName)){ 
			queryString += " where opt.operateName like ?";
			params.add("%"+modOptName+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by opt.operateId desc"; 
		List<SysplModuleMemu> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString,params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	public int findModuleOptByName(String moduleOptName) {
		String queryString = "select count(*) from SysplOperate opt where opt.operateName = '" + moduleOptName+"'";
		int totalProperty = this.getRowCount(queryString);//总记录数
		return totalProperty;
	}
}
