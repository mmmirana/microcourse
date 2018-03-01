package com.mirana.frame.base.service;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.query.Page;
import com.mirana.frame.db.base.query.Where;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Title BaseService接口
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年9月24日上午9:32:35
 */
public interface IBaseService<T extends BaseModel> {
	/**
	 * 插入实体类
	 *
	 * @param entity 插入的实体属性
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int insert (T entity) throws SQLException;

	/**
	 * 插入实体类并返回主键
	 *
	 * @param entity 插入的实体属性
	 * @return 新增数据的主键id
	 * @throws SQLException
	 */
	public Long insertAndGetPk (T entity) throws SQLException;

	/**
	 * 根据主键id更新一条数据
	 *
	 * @param entity 更新的实体属性（pk不能为空）
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int update (T entity) throws SQLException;

	/**
	 * 根据Where条件更新
	 *
	 * @param paramMap 要更新的值
	 * @param where    where条件
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int update (Map<String, Object> paramMap, Where where) throws SQLException;

	/**
	 * 根据主键id查询唯一实体，不存在返回null，查找到多个抛出异常
	 *
	 * @param pk
	 * @return BaseModel
	 * @throws SQLException
	 */
	public T findByPk (Long pk) throws SQLException;

	/**
	 * 根据Map参数查询唯一实体，不存在返回null，查找到多个抛出异常
	 *
	 * @param paramMap
	 * @return BaseModel
	 * @throws SQLException
	 */
	public T findUniqueByMap (Map<String, Object> paramMap) throws SQLException;

	/**
	 * 根据where参数查询唯一实体，不存在返回null，查找到多个抛出异常
	 *
	 * @param where
	 * @return BaseModel
	 * @throws SQLException
	 */
	public T findUniqueByWhere (Where where) throws SQLException;

	/**
	 * 根据Map参数查询实体集合
	 *
	 * @param paramMap
	 * @return BaseModel集合
	 * @throws SQLException
	 */
	public List<T> findByMap (Map<String, Object> paramMap) throws SQLException;

	/**
	 * 根据where参数查询实体集合
	 *
	 * @param where
	 * @return BaseModel集合
	 * @throws SQLException
	 */
	public List<T> findByWhere (Where where) throws SQLException;

	/**
	 * 查询所有实体
	 *
	 * @return BaseModel集合
	 * @throws SQLException
	 */
	public List<T> findAll () throws SQLException;

	/**
	 * 分页查询
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页记录数
	 * @return BaseModel分页数据
	 * @throws SQLException
	 */
	public Page<T> findByPage (int pageNo, int pageSize) throws SQLException;

	/**
	 * 根据Map参数分页查询
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页记录数
	 * @param paramMap Map参数
	 * @return BaseModel分页数据
	 * @throws SQLException
	 */
	public Page<T> findByPage (int pageNo, int pageSize, Map<String, Object> paramMap) throws SQLException;

	/**
	 * 根据Where参数查找查询
	 *
	 * @param pageNo   当前页码
	 * @param pageSize 每页记录数
	 * @param where    Where参数
	 * @return BaseModel分页数据
	 * @throws SQLException
	 */
	public Page<T> findByPage (int pageNo, int pageSize, Where where) throws SQLException;

	/**
	 * 根据主键删除实体
	 *
	 * @param pk 主键id
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int deleteByPk (Long pk) throws SQLException;

	/**
	 * 根据Map参数删除
	 *
	 * @param paramMap Map参数
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int deleteByMap (Map<String, Object> paramMap) throws SQLException;

	/**
	 * 根据Where参数删除
	 *
	 * @param where Where参数
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public int deleteByWhere (Where where) throws SQLException;

	/**
	 * 计数
	 *
	 * @param paramMap Map参数
	 * @return 计数的个数
	 * @throws SQLException
	 */
	public int countByMap (Map<String, Object> paramMap) throws SQLException;

	/**
	 * 计数
	 *
	 * @param where Map参数
	 * @return 复合条件的个数
	 * @throws SQLException
	 */
	int countByWhere (Where where) throws SQLException;
}
