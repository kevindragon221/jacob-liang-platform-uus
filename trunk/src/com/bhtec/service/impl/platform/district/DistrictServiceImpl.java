/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:30:57 PM
 */
package com.bhtec.service.impl.platform.district;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.CITY;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.DISTRICT;
import static com.bhtec.common.constant.ServiceVariable.ENABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.SYS_DISTRICT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.dao.iface.platform.district.DistrictDao;
import com.bhtec.domain.pojo.platform.SysplDicSmallType;
import com.bhtec.domain.pojo.platform.SysplDistrict;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.TypeDictionary.TypeDictionaryService;
import com.bhtec.service.iface.platform.district.DistrictService;
import com.bhtec.service.impl.BaseServiceImpl;

public class DistrictServiceImpl extends BaseServiceImpl implements DistrictService {
	Logger log = Logger.getLogger(this.getClass());
	private DistrictDao districtDao;
	private TypeDictionaryService typeDictionaryService;
	/**
	 * 功能说明：保存地区
	 * @author jacobliang
	 * @param sysplDistrict		地区对象
	 * @throws ApplicationException
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public void saveSysplDistrict(SysplDistrict sysplDistrict) throws ApplicationException{
		districtDao.save(sysplDistrict);
		writeLog(sysplDistrict,LOG_LEVEL_FIRST,SAVE_OPT);
	}
	
	/**
	 * 功能说明：停用启用地区
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  districtId		地区ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws ApplicationException
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public boolean disEnableDistrict(long districtId,String disEnableFlag)throws ApplicationException{
		if(DISABLE.equals(disEnableFlag)){
			int modCount = districtDao.findDownDistrictById(districtId);
			if(modCount > 0){//有下级地区
				return false;
			}else{
				SysplDistrict sysplDistrict = this.findSysplDistrictByDistrictId(districtId);
				sysplDistrict.setStatus(DISABLE);
				this.modifySysplDistrict(sysplDistrict);
				this.writeLog(sysplDistrict, LOG_LEVEL_SECOND, DISABLE_DIS);
				return true;
			}
		}else{
			SysplDistrict sysplDistrict = findSysplDistrictByDistrictId(districtId);
			sysplDistrict.setStatus(ENABLE);
			this.modifySysplDistrict(sysplDistrict);
			this.writeLog(sysplDistrict, LOG_LEVEL_SECOND, ENABLE_DIS);
			return true;
		}
		
	}
	
	/**
	 * 功能说明：根据地区ID查询某个地区
	 * @author jacobliang
	 * @param 	districtId		地区ID
	 * @return	sysplDistrict	地区对象
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public SysplDistrict findSysplDistrictByDistrictId(Long districtId){
		log.debug("will find district id is "+districtId);
		SysplDistrict sysplDistrict = (SysplDistrict)districtDao
						.getPojoById("com.bhtec.domain.pojo.platform.SysplDistrict", districtId);
		return sysplDistrict;
	}
	
	/**
	 * 功能说明：修改地区
	 * @author jacobliang
	 * @param sysplDistrict	地区对象
	 * @return
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public void modifySysplDistrict(SysplDistrict sysplDistrict)throws ApplicationException{
		districtDao.update(sysplDistrict);
		this.writeLog(sysplDistrict, LOG_LEVEL_SECOND, MODIFY_OPT);
	}
	
	/**
	 * 功能说明：查询地区名称是否重复
	 * @author jacobliang
	 * @param  districtName	地区名称
	 * @return boolean true 地区名存在 false 不存在
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public boolean findDistrictNameIsExist(String districtName){
		int totalProperty = districtDao.findDistrictByDistrictName(districtName);
		log.debug("find module total is "+totalProperty);
		if(totalProperty > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 功能说明：根据条件查询地区名称
	 * @author jacobliang
	 * @param start		开始记录
	 * @param limit		每页记录
	 * @param treeId	查询某个树的节点
	 * @param districtName	地区名称
	 * @param districtLevel 地区级别
	 * @return
	 * @time Dec 29, 2010 9:03:57 AM
	 */
	public Map findDistrictByCon(int start,int limit,long treeId,String districtName,String districtLevel){
		log.debug("start="+ start+" limit="+ limit+" treeId= "+ treeId
				+" districtName"+ districtName+" districtLevel"+ districtLevel);
		String ids = "";
		if(treeId >= 0){
			List<SysplDistrict> list = findChildNodeList(treeId,0);//过滤出所有下级
			for(SysplDistrict sysplDistrict:list){
				String districtId = sysplDistrict.getDistrictId().toString();
				ids = ids.equals("")?districtId:ids+","+districtId;//所有下级地区ID
			}
			if(ids.equals(""))ids="-1";
		}
		List<SysplDicSmallType> sysplDicSmallTypeList = typeDictionaryService.findSmallTypeDicByBigTypeCode(DISTRICT);
		Map map =  districtDao.findDistrictByCon(start, limit, ids, districtName, districtLevel);
		List<SysplDistrict> limitModuleList = (List<SysplDistrict>)map.get(BUSI_LIST);
		for(SysplDistrict sysplDistrict:limitModuleList){
			String districtLevell = sysplDistrict.getDistrictLevel();
			for(SysplDicSmallType sysplDicSmallType:sysplDicSmallTypeList){
				if(districtLevell.equalsIgnoreCase(sysplDicSmallType.getSmallTypeCode())){
					sysplDistrict.setDistrictLevel(sysplDicSmallType.getSmallTypeName());
					break;
				}
			}
			sysplDistrict.setUpDistrictName(sysplDistrict.getSysplDistrict().getDistrictName());//上级地区名称
		}
		return map;
	}
	
	/**
	 * 功能说明：查询所有地区信息
	 * @author jacobliang
	 * @return List<SysplDistrict> 所有地区对象
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplDistrict> findAllDistrict(){
		List<SysplDistrict> list = districtDao.findAllDistrict();
		if(list != null && list.size()>0){
			for(SysplDistrict mod : list){
				mod.setUpDistrictId(mod.getSysplDistrict().getDistrictId());//上级地区ID
			}
		}
		return list;
	}
	
	/**
	 * 功能说明：根据地区id查询所有地区节点
	 * @author jacobliang
	 * @param districtId		上级地区id
	 * @param flag			0为下级，1 为上级
	 * @return
	 * @throws 
	 * @time Aug 15, 2010 9:17:13 PM
	 */
	public List<SysplDistrict> findChildNodeList(long districtId,int flag){
		List<SysplDistrict> filterList = new ArrayList<SysplDistrict>();
		List<SysplDistrict> listAll = districtDao.findAllDistrict();
		List<SysplDistrict> listAllTemp = new ArrayList<SysplDistrict>();
		listAllTemp.addAll(listAll);
		//删除根元素
		SysplDistrict sysplDistrict = new SysplDistrict();
		sysplDistrict.setDistrictId(0L);
		listAllTemp.remove(sysplDistrict);
		//过滤出所有下级元素
		filterList = getFilterList(filterList, listAllTemp, districtId);	
		if(flag == 1){
			//本元素
			SysplDistrict sysplDistrictOld = new SysplDistrict();
			sysplDistrictOld.setDistrictId(districtId);
			filterList.add(sysplDistrictOld);
			listAll.removeAll(filterList);
			filterList = listAll;
			if(filterList != null && filterList.size()>0){
				for(SysplDistrict mod : filterList){
					mod.setUpDistrictId(mod.getSysplDistrict().getDistrictId());
				}
			}
		}
		
		return filterList;
	}
	
	/**
	 * 功能说明：根据上级地区id递归过滤结点
	 * @author jacobliang
	 * @param filterList	过滤的子节点
	 * @param listAll		所有地区结果集
	 * @param districtId		上级地区id
	 * @return	所有子结点
	 * @throws 
	 * @time Aug 15, 2010 9:18:41 PM
	 */
	private List getFilterList(List<SysplDistrict> filterList,List<SysplDistrict> listAll,long districtId){
		List<SysplDistrict> subList = getSubList(listAll, districtId);
		if(subList.size()>0)
			filterList.addAll(subList);
		for(SysplDistrict module : subList){
			getFilterList(filterList,listAll,module.getDistrictId());
		}
		return filterList;
	}
	
	/**
	 * 功能说明：根据地区id获得所有子结点
	 * @author jacobliang
	 * @param listAll	所有地区结果集
	 * @param districtId  上级地区id
	 * @return	所有子结点
	 * @throws 
	 * @time Aug 15, 2010 9:19:09 PM
	 */
	private List<SysplDistrict> getSubList(List<SysplDistrict> listAll,long districtId){
		List<SysplDistrict> newList = new ArrayList<SysplDistrict>();
		for(SysplDistrict module : listAll){
			if(module.getSysplDistrict().getDistrictId() == districtId){
				newList.add(module);
			}
		}
		return newList;
	}
	
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	districtId			地区id
	 * @param	filterdistrictId	过滤地区id,当修改地区时将本地区ID过滤掉
	 * @return  List<TreeVo>		下一级地区
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<TreeVo> findNextLevelChildNodes(long districtId,long filterdistrictId){
		List<SysplDistrict> list = this.districtDao.findNextLevelChildNodes(districtId,filterdistrictId);
		List<TreeVo> treeVoList = new ArrayList<TreeVo>();
		if(list != null && list.size()>0){
			for(SysplDistrict mod : list){
				TreeVo treeVo = new TreeVo();
				treeVo.setId(mod.getDistrictId().toString());
				treeVo.setText(mod.getDistrictName());
				if(CITY.equalsIgnoreCase(mod.getDistrictLevel())){
					treeVo.setLeaf(true);//城市叶子
				}
				treeVoList.add(treeVo);
			}
		}
		return treeVoList;
	}
	/**
	 * 功能说明：公用地区写日志
	 * @author jacobliang
	 * @param sysplDistrict	地区对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(SysplDistrict sysplDistrict,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append(sysplDistrict.getDistrictName()+SPLIT).append(sysplDistrict.getDistrictLevel()+SPLIT);
		saveLog(logLevel, SYS_DISTRICT,opt,logContent.toString(), sysplDistrict.getDistrictId()+"");
	}
	
	public void setDistrictDao(DistrictDao districtDao) {
		this.districtDao = districtDao;
	}

	public void setTypeDictionaryService(TypeDictionaryService typeDictionaryService) {
		this.typeDictionaryService = typeDictionaryService;
	}

}
