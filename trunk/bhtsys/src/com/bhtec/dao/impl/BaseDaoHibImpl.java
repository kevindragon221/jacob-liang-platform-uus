package com.bhtec.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bhtec.dao.iface.BaseDao;
import com.bhtec.exception.SystemException;

/**
 *功能描述：
 *创建时间：Feb 24, 2010 4:27:57 PM 
 *@author lianglp
 *@version 1.0
 */


public class BaseDaoHibImpl <POJO> extends HibernateDaoSupport 
								         implements BaseDao<POJO> {
	
	Logger log = Logger.getLogger(BaseDaoHibImpl.class);	
	
	/**
	 * 将记录持久化到数据库
	 * @param  pojo 需要持久化的pojo，类型为泛型所指定类型
	 * @return void 
	 * */
	public void save(POJO pojo) {
		// TODO Auto-generated method stub
		if(pojo == null)
			throw new SystemException("POJO is null in BaseDAOHibernateImpl's sava(),it mustn't be null.");
		try {
			getHibernateTemplate().save(pojo);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			 log.error("BaseDAOHibernateImpl's sava() occur error : ",e);
			 throw new SystemException("BaseDAOHibernateImpl's sava() occur error : ",e);
		}
		
		log.debug("BaseDAOHibernateImpl's save() run successful.");
	}
	
	/**
	 * 将记录从数据库删除
	 * @param  pojo 需要删除的pojo，类型为泛型所指定类型
	 * @return void 
	 * */
	public void delete(POJO pojo) {
		// TODO Auto-generated method stub
		if(pojo == null)
			throw new SystemException("POJO is null in BaseDAOHibernateImpl's delete(),it mustn't be null.");
		try {
			getHibernateTemplate().delete(pojo);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's delete() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's delete() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's delete() run successful.");
	}
	
	/**
	 * 将记录从数据库中批量删除
	 * @param  entities 需要删除实体集合
	 * @return void 
	 * */
	public void deleteAll(Collection<POJO> entities){
		if(entities == null)
			throw new SystemException("POJO is null in BaseDAOHibernateImpl's deleteAll(),it mustn't be null.");
		try {
			getHibernateTemplate().deleteAll(entities);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's deleteAll() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's deleteAll() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's deleteAll() run successful.");
	}
	
	
	/**
	 * 更新数据库记录
	 * @param  pojo 需要更新的pojo，类型为泛型所指定类型
	 * @return void 
	 * */
	public void update(POJO pojo){
		if(pojo == null)
			throw new SystemException("POJO is null in BaseDAOHibernateImpl's update(),it mustn't be null.");
		try {
			getHibernateTemplate().update(pojo);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's update() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's update() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's update() run successful.");
	}

	/**
	 * 获得数据库所有记录 get
	 * @param  pojoName    pojo的类名字符串
	 * @param  orderBy	   字段排序
	 * @return List<POJO>  从数据库获得的相应记录的结果集，list的元素为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public List<POJO> getAll(String pojoName,String orderBy) {
		// TODO Auto-generated method stub
		if((pojoName == null)||"".equals(pojoName))
			throw new SystemException("pojoName is null or \" \" in  BaseDAOHibernateImpl's findAll(),it mustn't be that.");
		StringBuffer sb = new StringBuffer();
		sb.append("from " + pojoName);
		sb.append(" ");
		sb.append(orderBy);
		String queryString = sb.toString();
		log.debug(queryString);
		List<POJO> list = null;
		try {
			list = (List<POJO>)getHibernateTemplate().find(queryString);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's findAll() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's findAll() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's findAll() run successful.");
		return list;
	}
	
	/**
	 * 获得数据库所有记录 load
	 * @param  pojoName    pojo的类名字符串
	 * @return List<POJO>  从数据库获得的相应记录的结果集，list的元素为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public List<POJO> loadAll(POJO pojo) {
		// TODO Auto-generated method stub
		if((pojo == null)||"".equals(pojo))
			throw new SystemException("pojo is null or \" \" in  BaseDAOHibernateImpl's loadAll(),it mustn't be that.");
		List<POJO> list = null;
		try {
			list = (List<POJO>)getHibernateTemplate().loadAll(pojo.getClass());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's loadAll() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's loadAll() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's loadAll() run successful.");
		return list;
	}

	/**
	 * 根据主键，获得数据库一条对应的记录,如果没有相应的实体，返回 null
	 * @param  longPojoName    pojo的类包名字符串
	 * @param  ID              主键类型可以是(Integer,Float,Double,Short,Byte,String)
	 * @return POJO            从数据库获得的相应记录，POJO的实例，类型为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public POJO getPojoById(String longPojoName,Serializable ID) {
		// TODO Auto-generated method stub
		if((longPojoName == null)||"".equals(longPojoName))
			throw new SystemException("longPojoName is null  or \" \" in BaseDAOHibernateImpl's getPojoById(),it mustn't be that.");
		POJO pojo = null;
		try {
			pojo = (POJO)getHibernateTemplate().get(longPojoName, ID);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's getPojoById() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's getPojoById() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's getPojoById() run successful.");
		return pojo;
	}
	
	/**
	 * 根据主键，获得数据库一条对应的记录,如果没有相应的实体，抛出异常
	 * @param  longPojoName    pojo的类包名字符串
	 * @param  ID              主键类型可以是(Integer,Float,Double,Short,Byte,String)
	 * @return POJO            从数据库获得的相应记录，POJO的实例，类型为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public POJO loadPojoById(String longPojoName,Serializable ID) {
		// TODO Auto-generated method stub
		if((longPojoName == null)||"".equals(longPojoName))
			throw new SystemException("longPojoName is null  or \" \" in BaseDAOHibernateImpl's loadPojoById(),it mustn't be that.");
		POJO pojo = null;
		try {
			pojo = (POJO)getHibernateTemplate().load(longPojoName, ID);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's loadPojoById() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's loadPojoById() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's loadPojoById() run successful.");
		return pojo;
	}

	/**
	 * 根据POJO的属性获得数据库相应记录(相当于根据查询条件的字符串获得相应记录)
	 * @param  queryString  查询条件的字符串
	 * @return List<POJO>   从数据库获得的相应记录的结果集，list的元素为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public List<POJO> findByProperty(String queryString) {
		// TODO Auto-generated method stub
		if((queryString == null)||"".equals(queryString))
			throw new SystemException("queryString is null  or \" \" in BaseDAOHibernateImpl's findByProperty(),it mustn't be that.");
		List<POJO> list = null;
		try {
			list = (List<POJO>)getHibernateTemplate().find(queryString);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's findByProperty() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's findByProperty() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's findByProperty() run successful.");
		return list;
	}

	/**
	 * 使用带参数的HSQL语句检索数据
	 * @param queryString	查询条件
	 * @param paras			查询参数
	 */
    public List findByPropertyWithParas(String queryString, Object[] paras) {
    	if((queryString == null)||"".equals(queryString))
			throw new SystemException("queryString is null  or \" \" in BaseDAOHibernateImpl's findByPropertyWithParas(),it mustn't be that.");
        List<POJO> list = null;
		try {
			list = (List<POJO>)getHibernateTemplate().find(queryString, paras);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's findByPropertyWithParas() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's findByPropertyWithParas() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's findByPropertyWithParas() run successful.");
		return list;
    }
	
	/**
	 * 批量更新或保存记录
	 * @param  entities  	实体类集成
	 * @return void 
	 * */
	public void saveOrUpdateAll(Collection<POJO> entities){
		try {
			this.getHibernateTemplate().saveOrUpdateAll(entities);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("BaseDAOHibernateImpl's findByProperty() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's findByProperty() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's saveOrUpdateAll() run successful.");
	}
	
	/**
	 * 获得单行记录
	 * @param  pojoName    pojo的类名字符串
	 * @return int		   数据库表的总记录数 
	 * */
	public Object getSingleRowRecord(String queryString){
		if((queryString == null)||"".equals(queryString))
			throw new SystemException("queryString is null  or \" \" in BaseDAOHibernateImpl's getSingleRowRecord(),it mustn't be that.");
		Object record = null;
		try {
			Session session = openSession();
			record = session.createQuery(queryString).uniqueResult();
		} catch (DataAccessException e) {
			log.error("BaseDAOHibernateImpl's getSingleRowRecord() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's getSingleRowRecord() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's getSingleRowRecord() run successful.");
		return record;
	}
	
	/**
	 * 获得数据库表的总记录数
	 * @param  pojoName    pojo的类名字符串
	 * @return int		   数据库表的总记录数 
	 * */
	public int getRowCount(String queryString){
		if((queryString == null)||"".equals(queryString))
			throw new SystemException("queryString is null  or \" \" in BaseDAOHibernateImpl's getRowCount(),it mustn't be that.");
		int count = 0;
		try {
			Session session = openSession();
			Long countLong = (Long)session.createQuery(queryString).uniqueResult();
			count = countLong.intValue();
		} catch (DataAccessException e) {
			log.error("BaseDAOHibernateImpl's getRowCount() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's getRowCount() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's getRowCount() run successful.");
		return count;
	}
	
	/**
	 * 获得数据库表的总记录数
	 * @param  pojoName    pojo的类名字符串
	 * @param  params	   查询参数
	 * @return int		   数据库表的总记录数 
	 * */
	public int getRowCount(String queryString,List params){
		if((queryString == null)||"".equals(queryString))
			throw new SystemException("queryString is null  or \" \" in BaseDAOHibernateImpl's getRowCount(),it mustn't be that.");
		int count = 0;
		try {
			Session session = openSession();
			Query query = session.createQuery(queryString);
			if(params !=  null){
				for(int i=0;i<params.size();i++)
					query.setParameter(i, params.get(i));
			}
			Long countLong = (Long)query.uniqueResult();
			count = countLong.intValue();
		} catch (DataAccessException e) {
			log.error("BaseDAOHibernateImpl's getRowCount() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's getRowCount() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's getRowCount() run successful.");
		return count;
	}
	
	/**
	 * 保存或更新数据库的一条记录
	 * @param  POJO     保存或更新数据库的一条记录POJO
	 * @return void
	 * */
	public void merge(POJO pojo) {
		// TODO Auto-generated method stub
		if(pojo == null)
			throw new SystemException("POJO is null in BaseDAOHibernateImpl's merge(),it mustn't be null.");
		try {
			getHibernateTemplate().merge(pojo);
		} catch (DataAccessException e) {
			log.error("BaseDAOHibernateImpl's merge() occur error : ",e);
			throw new SystemException("BaseDAOHibernateImpl's merge() occur error : ",e);
		}
		log.debug("BaseDAOHibernateImpl's merge() run successful.");
	}
	/**
	 * 
	 *功能描述：获得hibernate session
	 *@since Mar 14, 2010  1:58:51 PM
	 *@author jacobliang
	 *@version 1.0
	 *@para
	 *@return Session
	 */
	public Session openSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
    }
	
	/**
	 * 功能说明：分页查询
	 * @author jacobliang
	 * @param start	第几页，页码
	 * @param limit	页面大小
	 * @param queryString	hql
	 * @return
	 * @throws Exception
	 * @throws 
	 * @time Aug 8, 2010 10:40:22 AM
	 */
	@SuppressWarnings("unchecked")
	public List<POJO> findByHqlWithPagination(final int start,
			final int limit, final String queryString,final List params)  {
		return (List<POJO>) getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback() {
					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						List<Object> list = null;
						final Query query = session.createQuery(queryString);
						query.setFirstResult(start);
						query.setMaxResults(limit);
						if(params != null){
							for(int i=0;i<params.size();i++){
								query.setParameter(i, params.get(i));
							}
						}
						list = query.list();
						return list;
					}
				});
	}
	/**
	 * 功能说明：执行HQL语句
	 * @author jacobliang
	 * @param hqlString
	 * @throws Exception
	 * @time Sep 28, 2010 10:46:56 AM
	 */
	public void excuteHql(final String hqlString)  {
		getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int result = session.createQuery(hqlString).executeUpdate();
						return result;
					}
				});
	}
	
	
	/**
	 * 功能说明：批量保存
	 * @author jacobliang
	 * @param entities
	 * @throws Exception
	 * @time Jan 16, 2012  PM
	 */
	public void batchSave(final Collection entities)  {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				int count = 0;
				Iterator it = entities.iterator();
				while(it.hasNext()){
					Object obj = it.next();
					if(obj != null){
						session.save(obj);
					}
					count++;
					if (count % 50 == 0) { //
						// flush a batch of inserts and release memory per 50
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
	}

}

