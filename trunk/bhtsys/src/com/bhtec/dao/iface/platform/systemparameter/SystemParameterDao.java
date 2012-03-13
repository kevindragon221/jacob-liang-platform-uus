/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.systemparameter;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplSysParameter;

public interface SystemParameterDao extends BaseDao {
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
	public Map findSystemParaByCon(int start,int limit,String paraName,String paraKeyName);
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public SysplSysParameter findSystemParaByParaName(String paraName);
}
