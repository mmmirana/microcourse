package com.mirana.frame.base.dao;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.query.Page;
import com.mirana.frame.db.base.query.PageUtils;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.db.base.query.WhereUtils;
import com.mirana.frame.db.dbutilsplus.dbtemplate.DBUtilsTemplate;
import com.mirana.frame.exception.DaoException;
import com.mirana.frame.spring.SpringRequestUtils;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.BeanUtils;
import com.mirana.module.common.model.McUser;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title BaseDao实现类（抽象类，不能被实例化）
 * @Description
 * @Created Assassin
 * @DateTime 2017/05/16 15:40:23
 */
public abstract class BaseDaoImpl<T extends BaseModel> implements IBaseDao<T> {

	/**
	 * BaseDaoImpl泛型
	 **/
	private Class<T> clazz;

	/**
	 * 向baseDao中注入dbUtils模板
	 */
	@Resource(name = "dbutilsTemplate")
	private DBUtilsTemplate dbutilsTemplate;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl () {
		super();
		this.clazz = (Class<T>) BeanUtils.getGenericClass(getClass());
		if (this.clazz == null) {
			throw new DaoException("BaseDao获取泛型class发生异常！");
		}
	}

	/**
	 * 插入实体类
	 *
	 * @param entity 插入的实体属性
	 * @return 影响的行数
	 * @throws SQLException
	 */
	@Override
	public int insert (T entity) throws SQLException {
		// 设置新增人和新增时间
		McUser activeUser = (McUser) SpringRequestUtils.getCurrentRequest().getAttribute(SysConstants.SESSION_KEY_ADMINISTRATOR);
		entity.setCreatedBy(activeUser == null ? SysConstants.DEFAULT_SYSTEM : activeUser.getUsername());
		entity.setCreatedTime(new Date());

		return getDbutilsTemplate().insert(entity);
	}

	/**
	 * 插入实体类并返回主键
	 *
	 * @param entity 插入的实体属性
	 * @return 新增数据的主键id
	 * @throws SQLException
	 */
	@Override
	public Long insertAndGetPk (T entity) throws SQLException {
		// 设置新增人和新增时间
		McUser activeUser = (McUser) SpringRequestUtils.getCurrentRequest().getAttribute(SysConstants.SESSION_KEY_ADMINISTRATOR);
		entity.setCreatedBy(activeUser == null ? SysConstants.DEFAULT_SYSTEM : activeUser.getUsername());
		entity.setCreatedTime(new Date());

		return getDbutilsTemplate().insertAndGetPk(entity);
	}

	/**
	 * 根据主键id更新一条数据
	 *
	 * @param entity 更新的实体属性（pk不能为空）
	 * @return 影响的行数
	 * @throws SQLException
	 */
	@Override
	public int update (T entity) throws SQLException {
		// 设置更新人和更新时间
		McUser activeUser = (McUser) SpringRequestUtils.getCurrentSession().getAttribute(SysConstants.SESSION_KEY_ADMINISTRATOR);
		entity.setUpdatedBy(activeUser == null ? SysConstants.DEFAULT_SYSTEM : activeUser.getUsername());
		entity.setUpdatedTime(new Date());

		Map<String, Object> setMap = BeanUtils.bean2map(entity, true);
		return update(setMap, WhereUtils.EQ(SysConstants.DB_PRIMARY_KEY, entity.getId()));
	}

	/**
	 * 根据Where条件更新
	 *
	 * @param setMap 要更新的值
	 * @param where  Where参数  where条件
	 * @return 影响的行数
	 * @throws SQLException
	 */
	@Override
	public int update (Map<String, Object> setMap, Where where) throws SQLException {
		// 设置更新人和更新时间
		McUser activeUser = (McUser) SpringRequestUtils.getCurrentSession().getAttribute(SysConstants.SESSION_KEY_ADMINISTRATOR);
		setMap.put("updatedBy", activeUser == null ? SysConstants.DEFAULT_SYSTEM : activeUser.getUsername());
		setMap.put("updatedTime", new Date());

		return getDbutilsTemplate().update(clazz, setMap, where);
	}

	/**
	 * 根据主键id查询唯一实体，不存在返回null，查找到多个抛出异常
	 *
	 * @param pk
	 * @return BaseModel
	 * @throws SQLException
	 */
	@Override
	public T findByPk (Long pk) throws SQLException {
		return findUniqueByWhere(WhereUtils.EQ(SysConstants.DB_PRIMARY_KEY, pk));
	}

	/**
	 * 根据Map参数查询唯一实体，不存在返回null，查找到多个抛出异常
	 *
	 * @param paramMap paramMap
	 * @return BaseModel
	 * @throws SQLException
	 */
	@Override
	public T findUniqueByMap (Map<String, Object> paramMap) throws SQLException {
		return findUniqueByWhere(WhereUtils.EQ(paramMap));
	}

	/**
	 * 根据where参数查询唯一实体，不存在返回null，查找到多个抛出异常
	 *
	 * @param where Where参数
	 * @return BaseModel
	 * @throws SQLException
	 */
	@Override
	public T findUniqueByWhere (Where where) throws SQLException {
		List<T> list = findByWhere(where);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new DaoException(
				"Invoking the method findUniqueByWhere(), result list'size() should return 1, actually return " + list.size() + " !");
		}
	}

	/**
	 * 根据Map参数查询实体集合
	 *
	 * @param paramMap paramMap
	 * @return BaseModel集合
	 * @throws SQLException
	 */
	@Override
	public List<T> findByMap (Map<String, Object> paramMap) throws SQLException {
		return findByWhere(WhereUtils.EQ(paramMap));
	}

	/**
	 * 根据where参数查询实体集合
	 *
	 * @param where Where参数
	 * @return BaseModel集合
	 * @throws SQLException
	 */
	@Override
	public List<T> findByWhere (Where where) throws SQLException {
		return getDbutilsTemplate().query(clazz, where);
	}

	/**
	 * 查询所有实体
	 *
	 * @return BaseModel集合
	 * @throws SQLException
	 */
	@Override
	public List<T> findAll () throws SQLException {
		return getDbutilsTemplate().query(clazz, new Where.Builder().build());
	}

	/**
	 * 分页查询
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页记录数
	 * @return BaseModel分页数据
	 * @throws SQLException
	 */
	@Override
	public Page<T> findByPage (int pageNo, int pageSize) throws SQLException {
		return findByPage(pageNo, pageSize, new HashMap<String, Object>());
	}

	/**
	 * 根据Map参数分页查询
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页记录数
	 * @param paramMap paramMap Map参数
	 * @return BaseModel分页数据
	 * @throws SQLException
	 */
	@Override
	public Page<T> findByPage (int pageNo, int pageSize, Map<String, Object> paramMap) throws SQLException {
		return findByPage(pageNo, pageSize, WhereUtils.EQ(paramMap));
	}

	/**
	 * 根据Where参数查找查询
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页记录数
	 * @param where    Where参数    Where参数
	 * @return BaseModel分页数据
	 * @throws SQLException
	 */
	@Override
	public Page<T> findByPage (int pageNo, int pageSize, Where where) throws SQLException {
		Assert.greatThan(pageNo, 0, "The parameter pageNo should great than zero!");
		Assert.greatThan(pageSize, 0, "The parameter pageNo should great than zero!");
		// total rows
		int totalRows = countByWhere(where);

		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put(SysConstants.PAGE_PARAMS_PAGENO, pageNo);
		pageMap.put(SysConstants.PAGE_PARAMS_PAGESIZE, pageSize);
		where.setPageMap(pageMap);
		List<T> pageResult = findByWhere(where);

		return PageUtils.buildPage(pageNo, pageSize, totalRows, pageResult);
	}

	/**
	 * 根据主键删除实体
	 *
	 * @param pk 主键id
	 * @return 影响的行数
	 * @throws SQLException
	 */
	@Override
	public int deleteByPk (Long pk) throws SQLException {
		return deleteByWhere(WhereUtils.EQ(SysConstants.DB_PRIMARY_KEY, pk));
	}

	/**
	 * 根据Map参数删除
	 *
	 * @param paramMap paramMap Map参数
	 * @return 影响的行数
	 * @throws SQLException
	 */
	@Override
	public int deleteByMap (Map<String, Object> paramMap) throws SQLException {
		return deleteByWhere(WhereUtils.EQ(paramMap));
	}

	/**
	 * 根据Where参数删除
	 *
	 * @param where Where参数
	 * @return 影响的行数
	 * @throws SQLException
	 */
	@Override
	public int deleteByWhere (Where where) throws SQLException {
		return getDbutilsTemplate().delete(clazz, where);
	}

	/**
	 * 计数
	 *
	 * @param paramMap paramMap Map参数
	 * @return 复合条件的个数
	 * @throws SQLException
	 */
	@Override
	public int countByMap (Map<String, Object> paramMap) throws SQLException {
		return countByWhere(WhereUtils.EQ(paramMap));
	}

	/**
	 * 计数
	 *
	 * @param where Where参数
	 * @return 复合条件的个数
	 * @throws SQLException
	 */
	@Override
	public int countByWhere (Where where) throws SQLException {
		return getDbutilsTemplate().count(clazz, where);
	}

	/**
	 * 执行原生sql语句，一般用于插入、更新、删除，这里使用的是sql字段，非实体字段
	 *
	 * @param sql    执行的sql语句
	 * @param params sql参数
	 * @return
	 * @throws SQLException
	 */
	public int execute (String sql, Object... params) throws SQLException {
		return getDbutilsTemplate().execute(sql, params);
	}

	/**
	 * 执行原生sql语句查询结果，这里使用的是sql字段，非实体字段
	 *
	 * @param rsh    {@link ResultSetHandler}
	 * @param sql    sql 执行的sql语句
	 * @param params sql参数
	 * @return
	 * @throws SQLException
	 */
	public <X> X executeQuery (ResultSetHandler<X> rsh, String sql, Object... params) throws SQLException {
		return getDbutilsTemplate().executeQuery(rsh, sql, params);
	}

	public DBUtilsTemplate getDbutilsTemplate () {
		return dbutilsTemplate;
	}

	public RedisTemplate<String, Object> getRedisTemplate () {
		return redisTemplate;
	}
}
