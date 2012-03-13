package com.bhtec.dao.iface;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

/**
 *功能描述：
 *创建时间：Feb 24, 2010 4:27:08 PM 
 *@author lianglp
 *@version 1.0
 */
 
public interface BaseDao<POJO> {
	/**
	 * 将pojo对象持久化到数据库
	 * @param  pojo 需要持久化的pojo，类型为泛型所指定类型
	 * @return void 
	 * */
	public void save(POJO pojo);
	/**
	 * 将记录从数据库删除
	 * @param  pojo 需要删除的pojo，类型为泛型所指定类型
	 * @return void 
	 * */
	public void delete(POJO pojo);
	/**
	 * 将记录从数据库中批量删除
	 * @param  entities 需要删除实体集合
	 * @return void 
	 * */
	public void deleteAll(Collection<POJO> entities);
	/**
	 * 更新数据库记录
	 * @param  pojo 需要更新的pojo，类型为泛型所指定类型
	 * @return void 
	 * */
	public void update(POJO pojo);
	/**
	 * 获得数据库所有记录get
	 * @param  pojoName    pojo的类名字符串
	 * @return List<POJO>  从数据库获得的相应记录的结果集，list的元素为POJO泛型 
	 * */
	public List<POJO> getAll(String pojoName,String orderBy);
	/**
	 * 获得数据库所有记录 load
	 * @param  pojoName    pojo的类名字符串
	 * @return List<POJO>  从数据库获得的相应记录的结果集，list的元素为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public List<POJO> loadAll(POJO pojo);
	/**
	 * 根据主键，获得数据库一条对应的记录
	 * @param  longPojoName    pojo的类包名字符串
	 * @param  ID              主键类型可以是(Integer,Float,Double,Short,Byte,String)
	 * @return POJO            从数据库获得的相应记录，POJO的实例，类型为POJO泛型 
	 * */
	public POJO getPojoById(String longPojoName,Serializable ID);
	/**
	 * 根据主键，获得数据库一条对应的记录,如果没有相应的实体，抛出异常
	 * @param  longPojoName    pojo的类包名字符串
	 * @param  ID              主键类型可以是(Integer,Float,Double,Short,Byte,String)
	 * @return POJO            从数据库获得的相应记录，POJO的实例，类型为POJO泛型 
	 * */
	@SuppressWarnings("unchecked")
	public POJO loadPojoById(String longPojoName,Serializable ID) ;
	/**
	 * 根据POJO的属性获得数据库相应记录(相当于根据查询条件的字符串获得相应记录)
	 * @param  queryString  查询条件的字符串
	 * @return List<POJO>   从数据库获得的相应记录的结果集，list的元素为POJO泛型 
	 * */
	public List<POJO> findByProperty(String queryString);
	/**
	 * 批量更新或保存记录
	 * @param  entities  	实体类集成
	 * @return void 
	 * */
	public void saveOrUpdateAll(Collection<POJO> entities);
	/**
	 * 获得数据库表的总记录数
	 * @param  queryString    count sql
	 * @return int		   数据库表的总记录数 
	 * */
	public int getRowCount(String queryString);
	/**
	 * 
	 *功能描述：获得hibernate session
	 *@since Mar 14, 2010  1:58:51 PM
	 *@author jacobliang
	 *@version 1.0
	 *@para
	 *@return Session
	 */
	public Session openSession();

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
			final int limit, final String queryString,final List params);
	/**
	 * 功能说明：执行HQL语句
	 * @author jacobliang
	 * @param hqlString
	 * @throws Exception
	 * @time Sep 28, 2010 10:46:56 AM
	 */
	public void excuteHql(final String hqlString) throws Exception;
	/**
	 * 获得单行记录
	 * @param  pojoName    pojo的类名字符串
	 * @return int		   数据库表的总记录数 
	 * */
	public Object getSingleRowRecord(String queryString);
	/**
	 * 功能说明：批量保存
	 * @author jacobliang
	 * @param entities
	 * @throws Exception
	 * @time Jan 16, 2012  PM
	 */
	public void batchSave(final Collection entities);
}
