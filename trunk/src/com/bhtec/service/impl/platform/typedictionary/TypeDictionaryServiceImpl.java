/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.typedictionary;

import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.SYS_TYPEDIC;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.platform.typedictionary.TypeDictionaryDao;
import com.bhtec.domain.pojo.platform.SysplDicBigType;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.TypeDictionary.TypeDictionaryService;
import com.bhtec.service.impl.BaseServiceImpl;

public class TypeDictionaryServiceImpl extends BaseServiceImpl implements TypeDictionaryService {
	Logger log = Logger.getLogger(this.getClass());
	private TypeDictionaryDao typeDictionaryDao;
	
	/**
	 * 功能说明：保存字典大类
	 * @author jacobliang
	 * @param sysplDicBigType		字典大类对象
	 * @throws ApplicationException
	 * @time Dec 22, 2010 8:52:38 PM
	 */
	public void saveSysplDicBigType(SysplDicBigType sysplDicBigType) throws ApplicationException{
		this.typeDictionaryDao.save(sysplDicBigType);
		this.writeLog(sysplDicBigType, LOG_LEVEL_FIRST, SAVE_OPT);
	}
	/**
	 * 功能说明：修改字典大类
	 * @author jacobliang
	 * @param sysplDicBigType 	字典大类对象
	 * @throws ApplicationException
	 * @time Dec 22, 2010 8:54:02 PM
	 */
	public void modifySysplDicBigType(SysplDicBigType sysplDicBigType) throws ApplicationException{
		this.typeDictionaryDao.deleteSysplDicSmallTypeByBigTypeId(sysplDicBigType.getBigTypeId());
		this.typeDictionaryDao.update(sysplDicBigType);
		this.writeLog(sysplDicBigType, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	/**
	 * 功能说明：根据字典大类ID查询字典大类
	 * @author jacobliang
	 * @param  bigTypeId	字典大类ID
	 * @return
	 * @time Dec 10, 2010 8:55:41 PM
	 */
	public SysplDicBigType findSysplDicBigTypeById(long bigTypeId){
		return (SysplDicBigType)this.typeDictionaryDao.getPojoById("com.bhtec.domain.pojo.platform.SysplDicBigType", bigTypeId);
	}
	/**
	 * 功能说明：根据大类代码查询所有小类
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return
	 * @time Dec 21, 2010 8:39:15 PM
	 */
	public List<SysplDicSmallType> findSmallTypeDicByBigTypeCode(String bigTypeCode){
		return this.typeDictionaryDao.findSmallTypeDicByBigTypeCode(bigTypeCode);
	}
	
	/**
	 * 功能说明：停用启用字典大类
	 * @author jacobliang
	 * @param  disEnableFlag		停用启用标志
	 * @param  bigTypeId			字典大类ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws 
	 * @time Dec 22, 2010 1:29:34 PM
	 */
	public boolean disEnableDicBigType(long bigTypeId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			SysplDicBigType sysplDicBigType = this.findSysplDicBigTypeById(bigTypeId);
			this.typeDictionaryDao.modifyGroupStatus(bigTypeId, DISABLE);
			this.writeLog(sysplDicBigType, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}else{
			SysplDicBigType sysplDicBigType = this.findSysplDicBigTypeById(bigTypeId);
			this.typeDictionaryDao.modifyGroupStatus(bigTypeId, ENABLE);
			this.writeLog(sysplDicBigType, LOG_LEVEL_SECOND, ENABLE_DIS);
			return true;
		}
	}
	
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
		return this.typeDictionaryDao.findTypeDictionaryByCon(start, limit, bigTypeName, bigTypeCode);
	}
	
	
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param sysplModuleMemu	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(SysplDicBigType sysplDicBigType,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append("字典大类名称："+sysplDicBigType.getBigTypeName()+SPLIT);
		saveLog(logLevel, SYS_TYPEDIC,opt,logContent.toString(), sysplDicBigType.getBigTypeId()+"");
	}
	
	/**
	 * 功能说明：查询指定的大类代码是否存在
	 * @author jacobliang
	 * @param bigTypeCode	大类代码
	 * @return true代码已经存在  false代码不存在
	 * @time Dec 22, 2010 9:48:20 AM
	 */
	public boolean findBigTypeCodeIsExist(String bigTypeCode){
		int count = this.typeDictionaryDao.findBigTypeDicCountByBigTypeCode(bigTypeCode);
		if(count > 0)
			return true;
		return false;
	}
	
	public void setTypeDictionaryDao(TypeDictionaryDao typeDictionaryDao) {
		this.typeDictionaryDao = typeDictionaryDao;
	}
	
}
