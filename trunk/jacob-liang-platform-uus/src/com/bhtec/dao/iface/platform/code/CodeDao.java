/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.code;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.domain.pojo.platform.SysplCode;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.exception.ApplicationException;

public interface CodeDao extends BaseDao {
	/**
	 * 功能说明：查询编码
	 * @author jacobliang
	 * @param start			开始记录
	 * @param limit			每页记录数
	 * @param codeEngName	英文编码名称
	 * @param codeName		中文编码名称
	 * @param moduleName	模块名称
	 * @return
	 * @time Jan 11, 2011 4:48:48 PM
	 */
	public Map findCodeByCon(int start,int limit,String codeEngName,String codeName,String moduleName);
	/**
	 * 功能说明：根据编码英文名称查询编码是否存在
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public int findSysplCodeByCodeEngName(String codeEngName);
	/**
	 * 功能说明：根据编码英文名称查询编码
	 * @author jacobliang
	 * @param	codeEngName	编码英文名称
	 * @return
	 * @time Jan 12, 2011 10:56:51 AM
	 */
	public SysplCode obtainSysplCodeByCodeEngName(String codeEngName);
}
