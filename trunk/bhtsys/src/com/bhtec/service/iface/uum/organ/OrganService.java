/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.organ;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.domain.pojohelper.tree.TreeVoExtend;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface OrganService extends BaseService {
	/**
	 * 功能说明：保存机构
	 * @author jacobliang
	 * @param uumOrgan		机构对象
	 * @throws ApplicationException
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void saveOrgan(UumOrgan uumOrgan) throws ApplicationException;
	/**
	 * 功能说明：停用启用机构
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  organId			机构ID
	 * @return int 1.机构有下级2.机构下有角色3.机构下有用户4.被其它机构引用0.停用成功
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public int disEnableOrgan(long organId,String disEnableFlag)throws ApplicationException;
	
	/**
	 * 功能说明：根据机构ID查询某个机构
	 * @author 	jacobliang
	 * @param 	organId	机构ID
	 * @return 	UumOrgan	机构对象
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public UumOrgan findOrganByOrganId(long organId);
	/**
	 * 功能说明：修改机构
	 * @author jacobliang
	 * @param UumOrgan	机构对象
	 * @throws ApplicationException
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void modifyOrgan(UumOrgan uumOrgan)throws ApplicationException;
	/**
	 * 功能说明：查询机构名称是否重复
	 * @author jacobliang
	 * @param  organName 机构名称
	 * @return boolean true 为存在 false为不存在
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public boolean findOrganByOrganName(String organName);
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
	public Map findOrganByCon(int start,int limit,long treeId,String orgName,String orgEnId);
	/**
	 * 功能说明：查询所有机构信息
	 * @author  jacobliang
	 * @return	List<UumOrgan> 所有机构列表
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<UumOrgan> findAllOrgan();
	/**
	 * 功能说明：根据机构id查询所有机构子节点
	 * @author jacobliang
	 * @param organId			 上级机构id
	 * @param flag				 0为下级，1 为上级
	 * @return 	List<UumOrgan>	 所有子机构
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<UumOrgan> findChildNodeList(long organId,int flag);
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	organId		机构id
	 * @param	filterOrgId	过滤的机构id,修改时过滤掉本机构ID
	 * @return  List<UumOrgan>	下级机构列表
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<TreeVo> findNextLevelChildNodes(long organId,long filterOrgId);
	/**
	 * 功能说明：查找指定节点的下一级子节点带checkbox
	 * @author jacobliang
	 * @param	organId		机构id
	 * @param	filterOrgId	过滤的机构id,修改时过滤掉本机构ID
	 * @return  List<UumOrgan>	下级机构列表
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public List<TreeVoExtend> findNextLevelChildNodesWithCheckbox(long organId,long filterOrgId);
	/**
	 * 功能说明：根据多个机构ID查询机构信息
	 * @author jacobliang
	 * @param orgIds
	 * @return
	 * @time Feb 14, 2012 5:28:40 PM
	 */
	public List findUumOrgansByOrgIds(String orgIds);
}
