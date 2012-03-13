/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.affiche;

import static com.bhtec.common.constant.Common.DELETE_OPT;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.LOG_LEVEL_THIRD;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.SYS_AFFICHE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.common.tools.UtilTools;
import com.bhtec.dao.iface.platform.affiche.AfficheDao;
import com.bhtec.domain.pojo.platform.SysplAccessory;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.accessory.AccessoryService;
import com.bhtec.service.iface.platform.affiche.AfficheService;
import com.bhtec.service.impl.BaseServiceImpl;

public class AfficheServiceImpl extends BaseServiceImpl implements AfficheService {
	Logger log = Logger.getLogger(this.getClass());
	private AfficheDao afficheDao;
	private AccessoryService accessoryService;
	/**
	 * 功能说明：保存公告
	 * @author jacobliang
	 * @param sysplAffiche	公告公告
	 * @throws ApplicationException
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void saveAffiche(SysplAffiche sysplAffiche) throws ApplicationException{
		afficheDao.save(sysplAffiche);
		StringBuffer optContent = new StringBuffer();
		optContent.append("保存公告标题："+sysplAffiche.getAfficheTitle()+SPLIT)
					.append("有效期限:"+sysplAffiche.getAfficheInvalidate().toString());
		this.saveLog(LOG_LEVEL_FIRST, SYS_AFFICHE, SAVE_OPT, optContent.toString(),
				sysplAffiche.getAfficheId().toString());
	}
	/**
	 * 功能说明：删除某个公告
	 * @author jacobliang
	 * @param	afficheId 	公告ID
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void deleteAfficheByIds(List<Long> afficheIdList)throws ApplicationException{
		if(afficheIdList != null && afficheIdList.size()>0){
			String afficheIds = "";
			accessoryService.setHttpServletRequest(this.getRequest());
			for(Long afficheId:afficheIdList){
				afficheIds = "".equals(afficheIds)?afficheId.toString():afficheIds+","+afficheId;
				//根据附件ID查询附件列表
				List<SysplAccessory> sysplAccessoryList = accessoryService.findAllAccessoryById(afficheId);
				List<Long> accessoryIdList = new ArrayList<Long>();
				for(SysplAccessory sysplAccessory:sysplAccessoryList){
					accessoryIdList.add(sysplAccessory.getAccessoryId());//附件ID
					UtilTools.deleteAccessoryFile(this.getRequest(), "uploadFile\\affiche",
							sysplAccessory.getAccessoryName());//删除附件
				}
				accessoryService.deleteAccessoryByIds(accessoryIdList);//删除附件数据库记录
			}
			this.afficheDao.deleteAfficheByIds(afficheIds);
			StringBuffer optContent = new StringBuffer();
			optContent.append("删除的公告ID："+afficheIds);
			this.saveLog(LOG_LEVEL_THIRD, SYS_AFFICHE, DELETE_OPT, optContent.toString(),
					"");
		}
		
	}
	/**
	 * 功能说明：根据公告ID查询某个公告
	 * @author jacobliang
	 * @param AfficheId
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public SysplAffiche findAfficheByAfficheId(Long afficheId){
		List<SysplAccessory> accessoryList = accessoryService.findAllAccessoryById(afficheId);
		SysplAffiche sysplAffiche = (SysplAffiche)afficheDao.getPojoById("com.bhtec.domain.pojo.platform.SysplAffiche", afficheId);
		sysplAffiche.setAccessoryList(accessoryList);
		return sysplAffiche;
	}
	/**
	 * 功能说明：修改公告
	 * @author jacobliang
	 * @param sysplAffiche	公告对象
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void modifyAffiche(SysplAffiche sysplAffiche)throws ApplicationException{
		this.afficheDao.update(sysplAffiche);
		StringBuffer optContent = new StringBuffer();
		optContent.append("修改公告ID："+sysplAffiche.getAfficheId()+SPLIT)
		.append("修改公告标题："+sysplAffiche.getAfficheTitle()+SPLIT)
		.append("有效期："+sysplAffiche.getAfficheInvalidate()+SPLIT)
		.append("是否发布："+sysplAffiche.getAffichePulish()+SPLIT);
		this.saveLog(LOG_LEVEL_SECOND, SYS_AFFICHE, MODIFY_OPT, optContent.toString(),
				sysplAffiche.getAfficheId().toString());
	}
	/**
	 * 功能说明：查询公告名称是否重复
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	查询某个树的节点
	 * @param	afficheTitle 公告标题
	 * @param	affichePulish 公告是否发布
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public Map findAfficheByCon(int start,int limit,String afficheTitle,Short affichePulish){
		return this.afficheDao.findAfficheByCon(start, limit, afficheTitle, affichePulish);
	}
	/**
	 * 功能说明：查询所有公告信息
	 * @author jacobliang
	 * @return List<SysplAffiche>	所有公告信息
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAffiche> findAllAfficheBeforeValidate(){
		return afficheDao.findAllAfficheBeforeValidate();
	}
	
	public void setAfficheDao(AfficheDao afficheDao) {
		this.afficheDao = afficheDao;
	}
	public void setAccessoryService(AccessoryService accessoryService) {
		this.accessoryService = accessoryService;
	}
}
