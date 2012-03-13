/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 30, 2010 @3:10:17 PM
 */
package com.bhtec.service.iface.uum.user;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface UserService extends BaseService {
	/**
	 * 功能说明：保存用户
	 * @author jacobliang
	 * @param uumUser	用户对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public void saveUser(UumUser uumUser) throws ApplicationException;
	/**
	 * 功能说明：停用启用用户 删除用户所属角色并移到无角色用户 删除用户所拥有操作权限
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  UserId			用户ID
	 * @return	boolean true成功
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public boolean disEnableUser(long userId,String disEnableFlag)throws ApplicationException;
	
	/**
	 * 功能说明：根据用户ID查询某个用户
	 * @author jacobliang
	 * @param 	userId		用户ID
	 * @return	UumUser		用户对象
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public UumUser findUserByUserId(long userId);
	/**
	 * 功能说明：修改用户
	 * @author jacobliang
	 * @param UumUser	用户对象
	 * @return
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public void modifyUser(UumUser uumUser)throws ApplicationException;
	/**
	 * 功能说明：查询用户号是否重复
	 * @author jacobliang
	 * @param	userCode	用户登录名
	 * @return	boolean true用户名存在 false不存在
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public boolean findUserByUserCode(String userCode);
	/**
	 * 功能说明：根据条件分页查询用户
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	格式：id_1:机构 id_2:角色 查询某个树的节点
	 * @param	userName 用户名称
	 * @param	userCode 用户code
	 * @return	map 1.list 2.count
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public Map findUserByCon(int start,int limit,String treeId,String userName,String userCode);
	/**
	 * 功能说明：查询用户信息为管理员分配
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	userName 用户名称
	 * @param	userCode 用户code
	 * @return	map 1.list 2.count
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public Map findUserInfoForAdminSet(int start,int limit,String userName,String userCode);
	/**
	 * 功能说明：查询所有用户信息
	 * @author jacobliang
	 * @return	List<UumUser>	所有用户列表
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public List<UumUser> findAllUser();
	/**
	 * 功能说明：机构角色关系树，查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	treeId	机构或角色id
	 * @param	isCheckbox	是否需要checkbox
	 * @return	List<UumOrgan>	所有机构和角色
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public List<TreeVo> findNextLevelChildNodes(String treeId,String isCheckbox);
	/**
	 * 功能说明：根据用户code和密码获得用户信息
	 * @author jacobliang
	 * @param userCode	用户登录名
	 * @param password	用户密码
	 * @return	UumUser	用户对象
	 * @time Nov 3, 2010 2:20:11 PM
	 */
	public UumUser findUserByUserCodeAPwd(String userCode,String password) throws ApplicationException;
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
	 * @param userId	用户ID
	 * @param userName	用户名
	 * @param userDefaultPwd	用户默认密码
	 * @throws ApplicationException
	 * @time Nov 7, 2010 6:21:20 PM
	 */
	public void resetPwdByUserId(long userId,String userName,String userDefaultPwd)throws ApplicationException;
	/**
	 * 功能说明：修改用户的照片URL为空
	 * @author jacobliang
	 * @param userId	用户ID
	 * @throws ApplicationException
	 * @time Nov 9, 2010 2:02:53 PM
	 */
	public void modifyUserPhotoUrlToEmpty(long userId)throws ApplicationException;
	/**
	 * 功能说明：根据用户ID修改用户号 用户名 用户密码 
	 * @author jacobliang
	 * @param uumUser		用户对象
	 * @throws ApplicationException
	 * @time Nov 18, 2010 5:29:52 PM
	 */
	public void modifyUserInfo(UumUser uumUser)throws ApplicationException;
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
