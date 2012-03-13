/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:22:30 PM
 */
package com.bhtec.service.impl.uum.organ;

import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ORGAN_MGR;
import static com.bhtec.common.constant.ServiceVariable.TWO;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.uum.organ.OrganDao;
import com.bhtec.domain.pojo.platform.SysplDistrict;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.domain.pojohelper.tree.TreeVoExtend;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.uum.organ.OrganService;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.impl.BaseServiceImpl;

public class OrganServiceImpl extends BaseServiceImpl implements OrganService {
	Logger log = Logger.getLogger(this.getClass());
	private OrganDao organDao;
	private RoleOrganService roleOrganService;
	private PlatformCommonService platformCommonService;
	private PrivilegeService privilegeService;
	/**
	 * 功能说明：停用启用机构
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  organId			机构ID
	 * @return int 1.机构有下级2.机构下有角色3.机构下有用户4.被其它机构引用0.停用成功
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public int disEnableOrgan(long organId, String disEnableFlag)
			throws ApplicationException {
		if(DISABLE.equals(disEnableFlag)){
			/**
			 * 1、机构有下级
			 * 2、机构下有角色
			 * 3、机构下有用户 暂不用实现
			 *    删除机构行权限
			 * 4、被其它系统引用
			 * 0、停用启用成功
			 */
			int modCount = organDao.findDownOrgById(organId);
			if(modCount > 0)return 1;
			int roleTotal = roleOrganService.findUumOrgRoleRefTotalByOrgId(organId);
			if(roleTotal > 0)return 2;
			UumOrgan uumOrgan = findOrganByOrganId(organId);
			uumOrgan.setStatus(DISABLE);
			privilegeService.deleteRowPriByResource(organId+"");//删除机构行权限
			this.writeLog(uumOrgan, LOG_LEVEL_SECOND, DISABLE_DIS);
			return 0;
		}else{
			UumOrgan uumOrgan = findOrganByOrganId(organId);
			uumOrgan.setStatus(ENABLE);
			this.writeLog(uumOrgan, LOG_LEVEL_SECOND, ENABLE_DIS);
			return 0;
		}
	}
	/**
	 * 功能说明：查询所有机构信息
	 * @author  jacobliang
	 * @return	List<UumOrgan> 所有机构列表
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<UumOrgan> findAllOrgan() {
		return organDao.findAllOrgan();
	}
	/**
	 * 功能说明：根据机构id查询所有机构子节点
	 * @author jacobliang
	 * @param 	organId			上级机构id
	 * @param 	flag			0为下级，1 为上级
	 * @return 	List<UumOrgan>	 所有子机构
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<UumOrgan> findChildNodeList(long organId, int flag) {
		// TODO Auto-generated method stub
		List<UumOrgan> filterList = new ArrayList<UumOrgan>();
		List<UumOrgan> listAll = organDao.findAllOrgan();
		List<UumOrgan> listAllTemp = new ArrayList<UumOrgan>();
		listAllTemp.addAll(listAll);
		//删除根元素
		UumOrgan uumOrgan = new UumOrgan();
		uumOrgan.setOrgId(0L);
		listAllTemp.remove(uumOrgan); 
		
		//过滤出所有下级元素
		filterList = getFilterList(filterList, listAllTemp, organId);
		if(organId != 0){
			//加入本元素
			for(UumOrgan organ : listAll){
				if(organ.getOrgId() == organId){
					filterList.add(organ);
				}
			}
		}
		if(flag == 1){
			listAll.removeAll(filterList);
			filterList = listAll;
			if(filterList != null && filterList.size()>0){
				for(UumOrgan organ : filterList){
					organ.setUporgId(organ.getUumOrgan().getOrgId());
				}
			}
		}
		
		return filterList;
	}
	
	/**
	 * 功能说明：根据上级机构id递归过滤结点
	 * @author jacobliang
	 * @param filterList	过滤的子节点
	 * @param listAll		所有模块结果集
	 * @param organId		上级模块id
	 * @return	所有子结点
	 * @throws 
	 * @time Aug 15, 2010 9:18:41 PM
	 */
	private List getFilterList(List<UumOrgan> filterList,List<UumOrgan> listAll,long organId){
		List<UumOrgan> subList = getSubList(listAll, organId);
		if(subList.size()>0)
			filterList.addAll(subList);
		for(UumOrgan module : subList){
			getFilterList(filterList,listAll,module.getOrgId());
		}
		return filterList;
	}
	
	/**
	 * 功能说明：根据机构id获得所有子结点
	 * @author jacobliang
	 * @param listAll	所有机构结果集
	 * @param organId   上级机构id
	 * @return		    所有子结点
	 * @throws 
	 * @time Sep 23, 2010
	 */
	private List<UumOrgan> getSubList(List<UumOrgan> listAll,long organId){
		List<UumOrgan> newList = new ArrayList<UumOrgan>();
		for(UumOrgan organ : listAll){
			if(organ.getUumOrgan().getOrgId() == organId){
				newList.add(organ);
			}
		}
		return newList;
	}
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	organId		机构id
	 * @param	filterOrgId	过滤的机构id,修改时过滤掉本机构ID
	 * @return  List<UumOrgan>	下级机构列表
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<TreeVo> findNextLevelChildNodes(long organId,long filterOrgId) {
		List<UumOrgan> list = this.organDao.findNextLevelChildNodes(organId,filterOrgId);
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		if(list != null && list.size()>0){
			for(UumOrgan uumOrgan : list){
				TreeVo treeVo = new TreeVo();
				treeVo.setId(uumOrgan.getOrgId().toString());
				treeVo.setText(uumOrgan.getOrgSimpleName());
				if(TWO.equals(uumOrgan.getOrgType())){
					treeVo.setLeaf(true);
				}else{
					treeVo.setLeaf(false);
				}
				treeVoList.add(treeVo);
			}
		}
		return treeVoList;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点带checkbox
	 * @author jacobliang
	 * @param	organId		机构id
	 * @param	filterOrgId	过滤的机构id,修改时过滤掉本机构ID
	 * @return  List<UumOrgan>	下级机构列表
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<TreeVoExtend> findNextLevelChildNodesWithCheckbox(long organId,long filterOrgId) {
		List<UumOrgan> list = this.organDao.findNextLevelChildNodes(organId,filterOrgId);
		List<TreeVoExtend> treeVoList = new ArrayList<TreeVoExtend>();
		if(list != null && list.size()>0){
			for(UumOrgan uumOrgan : list){
				TreeVoExtend treeVo = new TreeVoExtend();
				treeVo.setId(uumOrgan.getOrgId().toString());
				treeVo.setText(uumOrgan.getOrgSimpleName());
				treeVo.setChecked(false);
				if(TWO.equals(uumOrgan.getOrgType())){
					treeVo.setLeaf(true);
				}else{
					treeVo.setLeaf(false);
				}
				treeVoList.add(treeVo);
			}
		}
		return treeVoList;
	}
	/**
	 * 功能说明：根据条件分页查询机构
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	查询某个树的节点
	 * @param	orgName 机构名称
	 * @param	orgEnId 机构code
	 * @return	Map 1.list 2.count
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public Map findOrganByCon(int start, int limit, long treeId,
			String orgName, String orgCode) {
		// TODO Auto-generated method stub
		log.debug("start="+ start+" limit="+ limit+" treeId= "+ treeId+" orgName"+ orgName+" orgCode"+ orgCode);
		String ids = "";
		if(treeId >= 0){
			List<UumOrgan> list = findChildNodeList(treeId,0);
			for(UumOrgan uumOrgan:list){
				String organId = uumOrgan.getOrgId().toString();
				ids = ids.equals("")?organId:ids+","+organId;
			}
			if(ids.equals("")){
				Map map = new HashMap();
				map.put(TOTAL_PROPERTY, 0);
				map.put(BUSI_LIST, new ArrayList());
				return map;
			}
		}
		return organDao.findOrganByCon(start, limit, ids, orgName, orgCode);
	}
	/**
	 * 功能说明：根据机构ID查询某个机构
	 * @author 	jacobliang
	 * @param 	organId	机构ID
	 * @return 	UumOrgan	机构对象
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public UumOrgan findOrganByOrganId(long organId) {
		log.debug("will find organ id is "+organId);
		UumOrgan uumOrgan = (UumOrgan)organDao
						.getPojoById("com.bhtec.domain.pojo.uum.UumOrgan", organId);
		uumOrgan.setUporgId(uumOrgan.getUumOrgan().getOrgId());
		uumOrgan.setUpOrgName(uumOrgan.getUumOrgan().getOrgSimpleName());
		SysplDistrict sysplDistrict = platformCommonService.findSysplDistrictByDistrictId(uumOrgan.getOrgBelongDist());
		uumOrgan.setOrgBelongDistName(sysplDistrict.getDistrictName());
		return uumOrgan;
	}
	/**
	 * 功能说明：查询机构名称是否重复
	 * @author jacobliang
	 * @param  organName 机构名称
	 * @return boolean true 为存在 false为不存在
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public boolean findOrganByOrganName(String organName) {
		int totalProperty = organDao.findOrgCountByOrgSimpleName(organName);
		log.debug("find organ total is "+totalProperty);
		if(totalProperty > 0){
			return true;
		}
		return false;
	}
	/**
	 * 功能说明：修改机构
	 * @author jacobliang
	 * @param UumOrgan	机构对象
	 * @throws ApplicationException
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void modifyOrgan(UumOrgan uumOrgan) throws ApplicationException {
		organDao.update(uumOrgan);
		this.writeLog(uumOrgan, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	/**
	 * 功能说明：保存机构
	 * @author jacobliang
	 * @param uumOrgan		机构对象
	 * @throws ApplicationException
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void saveOrgan(UumOrgan uumOrgan) throws ApplicationException {
		this.organDao.save(uumOrgan);
		this.writeLog(uumOrgan, LOG_LEVEL_FIRST, SAVE_OPT);
	}

	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param UumOrgan	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(UumOrgan uumOrgan,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append("机构名称："+uumOrgan.getOrgSimpleName()+SPLIT)
		.append("机构代码："+uumOrgan.getOrgCode()+SPLIT)
		.append("上级机构："+uumOrgan.getUpOrgName()+SPLIT);
		saveLog(logLevel, ORGAN_MGR,opt,logContent.toString(), uumOrgan.getOrgId()+"");
	}
	
	/**
	 * 功能说明：根据多个机构ID查询机构信息
	 * @author jacobliang
	 * @param orgIds
	 * @return
	 * @time Feb 14, 2012 5:28:40 PM
	 */
	public List findUumOrgansByOrgIds(String orgIds){
		if(isNullOrEmpty(orgIds))return null;
		return this.organDao.findUumOrgansByOrgIds(orgIds);
	}

	public void setOrganDao(OrganDao organDao) {
		this.organDao = organDao;
	}

	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}
	public void setPlatformCommonService(PlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

}
