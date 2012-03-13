/**
 *功能说明：平台系统对外的公共接口
 * @author jacobliang
 * @time @Nov 17, 2010 @9:39:04 AM
 */
package com.bhtec.service.iface.platform;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplDistrict;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplSysParameter;
import com.bhtec.service.iface.BaseService;

public interface PlatformCommonService extends BaseService {
	/**
	 * 功能说明：查询所有模块信息
	 * 源自：ModuleService 应用：LoginServiceImpl
	 * @author jacobliang
	 * @return List<SysplModuleMemu>	所有模块信息
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplModuleMemu> findAllModule();
	/**
	 * 功能说明：过滤模块一二三级
	 * 源自：ModuleService 应用：LoginServiceImpl
	 * @author jacobliang
	 * @param	list 	需要过滤的模块列表
	 * @return Map 1:list 2:map 3:map
	 * @time Aug 20, 2010 8:37:50 PM
	 */
	public Map findFilterModuleMenu(List<SysplModuleMemu> list);
	/**
	 * 功能说明：查询所有模块操作关系记录
	 * @author jacobliang
	 * @return	List<SysplModOptRef>	所有模块操作关系记录
	 * @time Nov 1, 2010 10:01:57 AM
	 */
	public List<SysplModOptRef> findAllSysplModOptRef();
	/**
	 * 功能说明：根据参数名称查找系统参数
	 * @author jacobliang
	 * @return
	 * @time Dec 10, 2010 8:39:15 PM
	 */
	public String findSystemParaByParaName(String paraKeyName);
	/**
	 * 功能说明：根据地区ID查询某个地区
	 * @author jacobliang
	 * @param 	districtId		地区ID
	 * @return	sysplDistrict	地区对象
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public SysplDistrict findSysplDistrictByDistrictId(Long districtId);
	/**
	 * 功能说明：根据模块关系表ID查询所有模块操作关系
	 * @author jacobliang
	 * @param modOptRefId
	 * @return
	 * @time Feb 7, 2012
	 */
	public List<SysplModOptRef> findSysplModOptRefByModOptRefIds(String modOptRefId);
}
