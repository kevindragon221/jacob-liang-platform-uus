/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:09:33 PM
 */
package com.bhtec.service.iface.platform.district;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.platform.SysplDistrict;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface DistrictService extends BaseService {
	/**
	 * 功能说明：保存地区
	 * @author jacobliang
	 * @param sysplDistrict		地区对象
	 * @throws ApplicationException
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public void saveSysplDistrict(SysplDistrict sysplDistrict) throws ApplicationException;
	/**
	 * 功能说明：停用启用地区
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  districtId		地区ID
	 * @return boolean	true为停用启用成功，false不能停用
	 * @throws ApplicationException
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public boolean disEnableDistrict(long districtId,String disEnableFlag)throws ApplicationException;
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
	 * 功能说明：修改地区
	 * @author jacobliang
	 * @param sysplDistrict	地区对象
	 * @return
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public void modifySysplDistrict(SysplDistrict sysplDistrict)throws ApplicationException;
	/**
	 * 功能说明：查询地区名称是否重复
	 * @author jacobliang
	 * @param  districtName	地区名称
	 * @return boolean true 地区名存在 false 不存在
	 * @throws 
	 * @time Dec 29, 2010 9:32:47 AM
	 */
	public boolean findDistrictNameIsExist(String districtName);
	/**
	 * 功能说明：根据条件查询地区名称
	 * @author jacobliang
	 * @param start		开始记录
	 * @param limit		每页记录
	 * @param treeId		树结点ID
	 * @param districtName	地区名称
	 * @param districtLevel 地区级别
	 * @return
	 * @time Dec 29, 2010 9:03:57 AM
	 */
	public Map findDistrictByCon(int start,int limit,long treeId,String districtName,String districtLevel);
	/**
	 * 功能说明：查询所有地区信息
	 * @author jacobliang
	 * @return List<sysplDistrict>	所有地区信息
	 * @time Jul 26, 2010 9:28:04 PM
	 */
	public List<SysplDistrict> findAllDistrict();
	 
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	districtId			地区id
	 * @param	filterdistrictId	过滤地区id,当修改地区时将本地区ID过滤掉
	 * @return  List<TreeVo>		下一级地区
	 * @throws 
	 * @time Sep 19, 2010 11:55:37 AM
	 */
	public List<TreeVo> findNextLevelChildNodes(long districtId,long filterdistrictId);
}
