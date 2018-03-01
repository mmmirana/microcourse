package com.mirana.frame.base.service;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.query.Page;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.exception.ServiceException;
import com.mirana.frame.utils.BeanUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Title BaseService实现类
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年9月24日上午2:49:04
 */
public abstract class BaseServiceImpl<T extends BaseModel> implements IBaseService<T> {

	/**
	 * 泛型class
	 **/
	private Class<T> clazz;

	private IBaseDao<T> baseDao;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl () {
		super();
		this.clazz = (Class<T>) BeanUtils.getGenericClass(getClass());
		if (this.clazz == null) {
			throw new ServiceException("BaseService获取泛型class发生异常！");
		}
	}

	@Override
	public int insert (T entity) throws SQLException {
		return getBaseDao().insert(entity);
	}

	@Override
	public Long insertAndGetPk (T entity) throws SQLException {
		return getBaseDao().insertAndGetPk(entity);
	}

	@Override
	public int update (T entity) throws SQLException {
		return getBaseDao().update(entity);
	}

	@Override
	public int update (Map<String, Object> paramMap, Where where) throws SQLException {
		return getBaseDao().update(paramMap, where);
	}

	@Override
	public T findByPk (Long pk) throws SQLException {
		return getBaseDao().findByPk(pk);
	}

	@Override
	public T findUniqueByMap (Map<String, Object> paramMap) throws SQLException {
		return getBaseDao().findUniqueByMap(paramMap);
	}

	@Override
	public T findUniqueByWhere (Where where) throws SQLException {
		return getBaseDao().findUniqueByWhere(where);
	}

	@Override
	public List<T> findByMap (Map<String, Object> paramMap) throws SQLException {
		return getBaseDao().findByMap(paramMap);
	}

	@Override
	public List<T> findByWhere (Where where) throws SQLException {
		return getBaseDao().findByWhere(where);
	}

	@Override
	public List<T> findAll () throws SQLException {
		return getBaseDao().findAll();
	}

	@Override
	public Page<T> findByPage (int pageNo, int pageSize) throws SQLException {
		return getBaseDao().findByPage(pageNo, pageSize);
	}

	@Override
	public Page<T> findByPage (int pageNo, int pageSize, Map<String, Object> paramMap) throws SQLException {
		return getBaseDao().findByPage(pageNo, pageSize, paramMap);
	}

	@Override
	public Page<T> findByPage (int pageNo, int pageSize, Where where) throws SQLException {
		return getBaseDao().findByPage(pageNo, pageSize, where);
	}

	@Override
	public int deleteByPk (Long pk) throws SQLException {
		return getBaseDao().deleteByPk(pk);
	}

	@Override
	public int deleteByMap (Map<String, Object> paramMap) throws SQLException {
		return getBaseDao().deleteByMap(paramMap);
	}

	@Override
	public int deleteByWhere (Where where) throws SQLException {
		return getBaseDao().deleteByWhere(where);
	}

	@Override
	public int countByMap (java.util.Map<String, Object> paramMap) throws SQLException {
		return getBaseDao().countByMap(paramMap);
	}

	@Override
	public int countByWhere (Where where) throws SQLException {
		return getBaseDao().countByWhere(where);
	}

	public IBaseDao<T> getBaseDao () {
		return baseDao;
	}

	public void setBaseDao (IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

}