/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:08:56 PM
 */
package com.bhtec.dao.impl.uum.user;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.PojoVariable.UUM_USER;
import static com.bhtec.common.constant.ServiceVariable.Y;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.dao.iface.uum.user.UserDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.exception.ApplicationException;

public class UserDaoHibImpl extends BaseDaoHibImpl implements UserDao {

	public List<UumUser> findAllUser() {
		List<UumUser> UserList = getAll(UUM_USER," order by userId desc");
		return UserList;
	}

	public Map findUserByCon(int start,int limit,String treeId,String orgIds,String userName,String userCode) {
		int id = 0;
		int flag = 0;
		if(!isNullOrEmpty(treeId)){
			if(treeId.length() >= 3 ){
				id = Integer.parseInt(treeId.substring(0,treeId.indexOf("_")));
				flag = Integer.parseInt(treeId.substring(treeId.indexOf("_")+1));
			}
		}
		
		String queryString = "";
		if(flag == 0){
			queryString = "from UumUser uumUser where 1=1 ";
		}else if(flag == 1){
			queryString = "from UumUser uumUser where uumUser.uumOrgan.orgId in ( " + orgIds +")";
		}else{
			queryString = "from UumUser uumUser where uumUser.userId in " +
							 "(select uumRoleUserRef.uumUser.userId from UumRoleUserRef uumRoleUserRef " +
							 "where uumRoleUserRef.uumOrgRoleRef.orgRoleId = "+id+")";
		}
		List params = new ArrayList();
		if(!isNullOrEmpty(userName)){
			queryString += " and uumUser.userName like ?";
			params.add("%"+userName+"%");
		}
		if(!isNullOrEmpty(userCode)){
			queryString += " and uumUser.userCode like ?";
			params.add("%"+userCode+"%");
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by uumUser.userId desc"; 
		List<UumUser> limitList =  this.findByHqlWithPagination(start, limit, queryString, params);//分页
		for(UumUser uumUser:limitList){
			if(!uumUser.getUumRoleUserRefs().isEmpty()){
				Object[] objs = uumUser.getUumRoleUserRefs().toArray();
				for(int i=0;i<objs.length;i++){
					UumRoleUserRef uumRoleUserRef = (UumRoleUserRef)objs[i];
					if(Y.equals(uumRoleUserRef.getIsDefault())){
						String roleName = uumRoleUserRef.getUumOrgRoleRef().getUumRole().getRoleName();
						long orgRoleId = uumRoleUserRef.getUumOrgRoleRef().getOrgRoleId();
						uumUser.setUumRoleName(roleName);
						uumUser.setUumOrgRoleId(orgRoleId);
						uumUser.setUumOrgName(uumUser.getUumOrgan().getOrgSimpleName());
						break;
					}
				}
			}
		}
		
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitList);
		return map;
	}

	public Map findUserByCon(int start, int limit, long treeId,
			String userName, String userCode) {
		return null;
	}

	public int findUserByUserCode(String userCode) {
		String queryString = "select count(*) from UumUser uumUser " +
		 "where uumUser.userCode = '"+userCode+"'";
		return this.getRowCount(queryString);
	}

	public List<UumUser> findUserByUserCodeAPwd(String userCode, String password) {
		String queryString = "from UumUser uumUser where uumUser.userCode = ? " +
		 					 "and uumUser.userPassword = ?";
		String[] paras = new String[]{userCode,password};
		if(isNullOrEmpty(password)){
			queryString = "from UumUser uumUser where uumUser.userCode = ? ";
			paras = new String[]{userCode};
		}
		
		List<UumUser> uumUserList = this.findByPropertyWithParas(queryString, paras);
		return uumUserList;
	}
	
	/**
	 * 功能说明：当为用户分配多个角色时，更改用户的默认角色
	 * @author jacobliang
	 * @param organId
	 * param userId
	 * @throws ApplicationException
	 * @time Nov 7, 2010 11:09:05 AM
	 */
	public void modifyUserBelongOrgIdAssignRole(long organId,long userId){
		String hqlString = "update UumUser uumUser set uumUser.uumOrgan.orgId = "+organId+
						   " where uumUser.userId = "+userId;
		this.excuteHql(hqlString);
	}
	
	/**
	 * 功能说明：根据用户ID重置用户密码
	 * @author jacobliang
	 * @param userId
	 * @param defaultPassword
	 * @throws ApplicationException
	 * @time Nov 7, 2010 6:21:20 PM
	 */
	public void resetPwdByUserId(long userId,String defaultPassword){
		String hqlString = "update UumUser uumUser set uumUser.userPassword = '"+defaultPassword+
					 "' where uumUser.userId = "+userId;
		this.excuteHql(hqlString);
	}
	
	/**
	 * 功能说明：修改用户的照片URL为空
	 * @author jacobliang
	 * @param userId
	 * @throws ApplicationException
	 * @time Nov 9, 2010 2:02:53 PM
	 */
	public void modifyUserPhotoUrlToEmpty(long userId)throws ApplicationException{
		String hqlString = "update UumUser uumUser set uumUser.userPhotoUrl = '' " +
							"where uumUser.userId = "+userId;
		this.excuteHql(hqlString);
	}
	
	/**
	 * 功能说明：根据机构ID查询用户信息
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Jan 26, 2011 11:04:24 AM
	 */
	public List<UumUser> findUumUserByOrganId(long organId){
		String queryString = "from UumUser uumUser where uumUser.uumOrgan.orgId = ?";
		return this.findByPropertyWithParas(queryString, new Long[]{organId});
	}
	
	/**
	 * 功能说明：根据用户名模糊用户信息
	 * @author jacobliang
	 * @param userName
	 * @return
	 * @time Feb 14, 2012 4:38:16 PM
	 */
	public List<UumUser> findAllUumUserByUserName(String userName){
		String queryString = "select userId,userName from UumUser uumUser " +
							 "where uumUser.userName like ?";
		return this.findByPropertyWithParas(queryString, new String[]{"%"+userName+"%"});
	}
	
	/**
	 * 功能说明：根据拼音代码查询用户信息
	 * @author jacobliang
	 * @param userNamePy
	 * @return
	 * @time Feb 20, 2012 1:47:34 PM
	 */
	public List findAllUumUserByUserNamePy(String userNamePy,long userId){
		String queryString = "select userId,userName from UumUser uumUser " +
							 "where uumUser.userId != "+userId+" and uumUser.userNamePy like ?";
		return this.findByPropertyWithParas(queryString, new String[]{userNamePy+"%"});
	}
	/**
	 * 功能说明：根据指定的用户ID集合查询用户信息
	 * @author jacobliang
	 * @param userIds
	 * @return
	 * @time Feb 14, 2012 4:35:35 PM
	 */
	public List findUumUserByUserIds(String userIds){
		String queryString = "select userId,userName " +
							 "from UumUser uumUser " +
		 					 "where uumUser.userId in ("+userIds+")";
		return this.findByProperty(queryString);
	}
}
