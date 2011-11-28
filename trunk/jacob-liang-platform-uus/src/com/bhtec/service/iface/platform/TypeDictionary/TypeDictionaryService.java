/**
 *功能说明：字典大类
 * @author jacobliang
 * @time Dec 22, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.platform.TypeDictionary;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplDicBigType;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface TypeDictionaryService extends BaseService {
	/**
	 * 功能说明：保存字典大类
	 * @author jacobliang
	 * @param sysplDicBigType		字典大类对象
	 * @throws ApplicationException
	 * @time Dec 22, 2010 8:52:38 PM
	 */
	public void saveSysplDicBigType(SysplDicBigType sysplDicBigType) throws ApplicationException;
	/**
	 * 功能说明：修改字典大类
	 * @author jacobliang
	 * @param sysplDicBigType 	字典大类对象
	 * @throws ApplicationException
	 * @time Dec 22, 2010 8:54:02 PM
	 */
	public void modifySysplDicBigType(SysplDicBigType sysplDicBigType) throws ApplicationException;
	/**
	 * 功能说明：根据字典大类ID查询字典大类
	 * @author jacobliang
	 * @param  bigTypeId	字典大类ID
	 * @return
	 * @time Dec 22, 2010 8:55:41 PM
	 */
	public SysplDicBigType findSysplDicBigTypeById(long bigTypeId);
	/**
	 * 功能说明：根据大类代码查询所有小类
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 22, 2010 8:39:15 PM
	 */
	public List<SysplDicSmallType> findSmallTypeDicByBigTypeCode(String bigTypeCode);
	/**
	 * 功能说明：停用启用字典大类
	 * @author jacobliang
	 * @param  disEnableFlag		停用启用标志
	 * @param  bigTypeId			字典大类ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Dec 22, 2010 1:29:34 PM
	 */
	public boolean disEnableDicBigType(long bigTypeId,String disEnableFlag)throws ApplicationException;
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
	public Map findTypeDictionaryByCon(int start,int limit,String bigTypeName,String bigTypeCode);
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 22, 2010 9:48:20 AM
	 */
	public boolean findBigTypeCodeIsExist(String bigTypeCode);
}
