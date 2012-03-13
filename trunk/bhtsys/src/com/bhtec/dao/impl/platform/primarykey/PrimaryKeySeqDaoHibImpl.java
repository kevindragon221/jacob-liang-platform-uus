package com.bhtec.dao.impl.platform.primarykey;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bhtec.common.tools.UtilTools;
import com.bhtec.dao.iface.platform.primarykey.PrimaryKeySeqDao;
import com.bhtec.dao.impl.BaseDaoHibImpl;

/**
 *功能描述：
 *@since   Mar 14, 2010 12:14:21 PM
 *@author  jacobliang
 *@version 1.0
 */
public class PrimaryKeySeqDaoHibImpl extends BaseDaoHibImpl implements
		PrimaryKeySeqDao {
	Logger log = Logger.getLogger(this.getClass());

	public Long getMaxIdOfTable(String pojoName,String primaryKeyName) {
		if(UtilTools.isNullOrEmpty(pojoName))throw new NullPointerException(pojoName+" is null or ''");
		if(UtilTools.isNullOrEmpty(primaryKeyName))throw new NullPointerException(primaryKeyName+" is null or ''");
		String hql = "select max(t."+primaryKeyName+") from "+pojoName + " t";
		log.debug("in getMaxIdOfTable hql = " + hql);
		Session session = this.openSession();
		Long maxId = (Long)session.createQuery(hql).uniqueResult();
		log.debug("maxId is " + maxId);
		return maxId;
	}
	
}
