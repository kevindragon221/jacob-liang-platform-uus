/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @2:56:23 PM
 */
package com.bhtec.dao.iface.uum.user;

import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;

public interface UserDao extends BaseDao {
	/**
	 * 功能说明：查询用户号是否重复
	 * @author jacobliang
	 * @return 此用户ID的数量
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public int findUserByUserCode(String userCode);
	/**
	 * 功能说明：根据条件分页查询用户
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	格式：id_1:机构 id_2:角色 查询某个树的节点
	 * @param	userName 用户名称
	 * @param	userCode 用户code
	 * @param	orgIds   下级所有机构ID
	 * @return
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public Map findUserByCon(int start,int limit,String treeId,String userName,String orgIds,String userCode);
	/**
	 * 功能说明：查询所有用户信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 3:02:42 PM
	 */
	public List<UumUser> findAllUser();
	/**
	 * 功能说明：根据用户code和密码获得用户信息
	 * @author jacobliang
	 * @param userCode
	 * @param password
	 * @return
	 * @time Nov 3, 2010 2:20:11 PM
	 */
	public List<UumUser> findUserByUserCodeAPwd(String userCode,String password);
	/**
	 * 功能说明：当为用户分配多个角色时，更改用户的默认角色
	 * @author jacobliang
	 * @param organId
	 * @param userId
	 * @throws ApplicationException
	 * @time Nov 7, 2010 11:09:05 AM
	 */
	public void modifyUserBelongOrgIdAssignRole(long organId,long userId);
	/**
	 * 功能说明：根据用户ID重置用户密码
	 * @author jacobliang
	 * @param userId
	 * @param defaultPassword
	 * @throws ApplicationException
	 * @time Nov 7, 2010 6:21:20 PM
	 */
	public void resetPwdByUserId(long userId,String defaultPassword);
	/**
	 * 功能说明：修改用户的照片URL为空
	 * @author jacobliang
	 * @param userId
	 * @throws ApplicationException
	 * @time Nov 9, 2010 2:02:53 PM
	 */
	public void modifyUserPhotoUrlToEmpty(long userId)throws ApplicationException;
	/**
	 * 功能说明：根据机构ID查询用户信息
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Jan 26, 2011 11:04:24 AM
	 */
	public List<UumUser> findUumUserByOrganId(long organId);
	/**
	 * 功能说明：根据用户名模糊用户信息
	 * @author jacobliang
	 * @param userName
	 * @return
	 * @time Feb 14, 2012 4:38:16 PM
	 */
	public List<UumUser> findAllUumUserByUserName(String userName);
	/**
	 * 功能说明：根据拼音代码查询用户信息
	 * @author jacobliang
	 * @param userNamePy
	 * @return
	 * @time Feb 20, 2012 1:47:34 PM
	 */
	public List findAllUumUserByUserNamePy(String userNamePy,long userId);
	/**
	 * 功能说明：根据指定的用户ID集合查询用户信息
	 * @author jacobliang
	 * @param userIds
	 * @return
	 * @time Feb 14, 2012 4:35:35 PM
	 */
	public List findUumUserByUserIds(String userIds);
}
