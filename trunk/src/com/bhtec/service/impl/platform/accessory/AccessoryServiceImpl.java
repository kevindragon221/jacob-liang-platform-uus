/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.accessory;

import static com.bhtec.common.constant.Common.DELETE_OPT;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.SYS_ACCESSORY;

import java.util.List;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.platform.accessory.AccessoryDao;
import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.accessory.AccessoryService;
import com.bhtec.service.impl.BaseServiceImpl;

public class AccessoryServiceImpl extends BaseServiceImpl implements AccessoryService {
	Logger log = Logger.getLogger(this.getClass());
	private AccessoryDao accessoryDao;
	/**
	 * 功能说明：保存附件
	 * @author jacobliang
	 * @param sysplAccessory	附件对象
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveAccessory(SysplAccessory sysplAccessory) throws ApplicationException{
			accessoryDao.save(sysplAccessory);
			StringBuffer optContent = new StringBuffer();
			optContent.append("附件业务表ID："+sysplAccessory.getInfoId()+SPLIT)
						.append("附件名称:"+sysplAccessory.getAccessoryName());
			this.saveLog(LOG_LEVEL_FIRST, SYS_ACCESSORY, SAVE_OPT, optContent.toString(),
					sysplAccessory.getAccessoryId().toString());
	}
	/**
	 * 功能说明：删除某个附件
	 * @author jacobliang
	 * @param	accessoryId 	附件ID
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void deleteAccessoryByIds(List<Long> accessoryIdList)throws ApplicationException{
		if(accessoryIdList != null && accessoryIdList.size()>0){
			String accessoryIds = "";
			for(Long accessoryId:accessoryIdList){
				accessoryIds = "".equals(accessoryIds)?accessoryId.toString():accessoryIds+","+accessoryId;
			}
			accessoryDao.deleteAccessoryByIds(accessoryIds);
			StringBuffer optContent = new StringBuffer();
			optContent.append("删除附件ID："+accessoryIds);
			this.saveLog(LOG_LEVEL_THIRD, SYS_ACCESSORY, DELETE_OPT, optContent.toString(),
					"");
		}
	}
	/**
	 * 功能说明：根据业务表ID查询所有附件
	 * @author jacobliang
	 * @return id			业务表主键ID
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAccessory> findAllAccessoryById(long id){
		return accessoryDao.findAllAccessoryById(id);
	}
	
	/**
	 * 功能说明：根据附件ID查询附件信息
	 * @author jacobliang
	 * @param accessoryId	附件ID
	 * @return
	 * @time Feb 23, 2011 3:45:22 PM
	 */
	public SysplAccessory findSysplAccessoryByAccId(long accessoryId){
		return (SysplAccessory)accessoryDao.
			getPojoById("com.bhtec.domain.pojo.platform.SysplAccessory", accessoryId);
	}
	
	public void setAccessoryDao(AccessoryDao accessoryDao) {
		this.accessoryDao = accessoryDao;
	}
	
}
