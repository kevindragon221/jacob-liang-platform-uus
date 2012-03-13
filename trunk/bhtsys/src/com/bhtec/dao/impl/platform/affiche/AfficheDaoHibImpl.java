/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 26, 2010 @5:03:17 PM
 */
package com.bhtec.dao.impl.platform.affiche;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bhtec.dao.iface.platform.affiche.AfficheDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;
import com.bhtec.domain.pojo.platform.SysplAffiche;
import com.bhtec.exception.ApplicationException;

public class AfficheDaoHibImpl extends BaseDaoHibImpl implements AfficheDao {
	
	public void deleteAfficheByIds(String afficheIds)throws ApplicationException{
		String hqlString = "delete from SysplAffiche aff " +
						   "where aff.afficheId in ("+afficheIds+")";
		this.excuteHql(hqlString);
	}

	public Map findAfficheByCon(int start, int limit, String afficheTitle,
			Short affichePulish) {
		String queryString = "from SysplAffiche aff where 0 = 0 ";
		List params = new ArrayList();
		if(!isNullOrEmpty(afficheTitle)){
			queryString += " and aff.afficheTitle like ?";
			params.add("%"+afficheTitle+"%");
		}
		if(affichePulish != null){
			queryString += " and aff.affichePulish = ?";
			params.add(affichePulish);
		}
		
		String countSql = "select count(*) " + queryString;
		queryString += " order by aff.afficheId desc"; 
		List<SysplAffiche> limitModuleList =  this.findByHqlWithPagination(start, limit, queryString, params);//分页
		int totalProperty = this.getRowCount(countSql,params);//总记录数
		Map map = new HashMap();
		map.put(TOTAL_PROPERTY, totalProperty);
		map.put(BUSI_LIST, limitModuleList);
		return map;
	}
	
	/**
	 * 功能说明：查询所有公告信息
	 * @author jacobliang
	 * @return List<SysplAffiche>	所有公告信息
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public List<SysplAffiche> findAllAfficheBeforeValidate(){
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(SysplAffiche.class);
		Calendar cal = Calendar.getInstance();
		criteria.add(Expression.ge("afficheInvalidate", cal.getTime()));
		short affichePulish = 0;
		criteria.add(Expression.eq("affichePulish", affichePulish));
		return criteria.list();
	}
	
}
