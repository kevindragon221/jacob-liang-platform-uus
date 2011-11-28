/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:00:17 PM
 */
package com.bhtec.dao.iface.platform.affiche;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.exception.ApplicationException;

public interface AfficheDao extends BaseDao {
	/**
	 * 功能说明：删除某个公告
	 * @author jacobliang
	 * @param	afficheId 	公告ID
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public void deleteAfficheByIds(String afficheIds)throws ApplicationException;
	/**
	 * 功能说明：查询公告名称是否重复
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	afficheTitle 公告标题
	 * @param	affichePulish 公告是否发布
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public Map findAfficheByCon(int start,int limit,String afficheTitle,Short affichePulish);
	/**
	 * 功能说明：查询所有公告信息
	 * @author jacobliang
	 * @return List<SysplAffiche>	所有公告信息
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAffiche> findAllAfficheBeforeValidate();
}
