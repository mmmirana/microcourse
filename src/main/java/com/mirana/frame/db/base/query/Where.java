package com.mirana.frame.db.base.query;

import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.utils.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * where查询
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年9月24日上午12:09:25
 */
public class Where implements Serializable {

	private static final long serialVersionUID = 1L;

	// one key, no parameter
	/**
	 * is null
	 **/
	private List<String> isNullList    = new ArrayList<String>();
	/**
	 * is not null
	 **/
	private List<String> isNotNullList = new ArrayList<String>();

	// one key, one parameter
	/**
	 * less than
	 **/
	private Map<String, Object> ltMap   = new HashMap<String, Object>();
	/**
	 * less equal
	 **/
	private Map<String, Object> leMap   = new HashMap<String, Object>();
	/**
	 * equal
	 **/
	private Map<String, Object> eqMap   = new HashMap<String, Object>();
	/**
	 * not equal
	 **/
	private Map<String, Object> neMap   = new HashMap<String, Object>();
	/**
	 * great equal
	 **/
	private Map<String, Object> geMap   = new HashMap<String, Object>();
	/**
	 * great than
	 **/
	private Map<String, Object> gtMap   = new HashMap<String, Object>();
	/**
	 * like
	 **/
	private Map<String, Object> likeMap = new HashMap<String, Object>();

	// one key, one more json query
	/**
	 * json
	 **/
	private Map<SQLJsonKey, Map<String, Object>> jsonMap = new HashMap<SQLJsonKey, Map<String, Object>>();

	// one key, one page parameter, 默认没有分页
	/**
	 * page
	 **/
	private Map<String, Integer> pageMap = null;

	// one key, one more parameters
	/**
	 * between
	 **/
	private Map<String, Object[]> betweenMap = new HashMap<String, Object[]>();
	/**
	 * in
	 **/
	private Map<String, Object[]> inMap      = new HashMap<String, Object[]>();

	// one key, one more where parameters
	/**
	 * and where
	 **/
	private List<Where> andWhereList = new ArrayList<Where>();
	/**
	 * or where
	 **/
	private List<Where> orWhereList  = new ArrayList<Where>();

	// one key, one orderby parameter
	/**
	 * orderby
	 **/
	private Map<String, OrderBy> orderbyMap = new HashMap<String, OrderBy>();

	/**
	 * 私有无参构造
	 *
	 * @param builder
	 */
	private Where (Builder builder) {
		super();

		this.setIsNullList(builder.isNullList);
		this.setIsNotNullList(builder.isNotNullList);

		this.setLtMap(builder.ltMap);
		this.setLeMap(builder.leMap);
		this.setEqMap(builder.eqMap);
		this.setNeMap(builder.neMap);
		this.setGeMap(builder.geMap);
		this.setGtMap(builder.gtMap);
		this.setLikeMap(builder.likeMap);

		this.setJsonMap(builder.jsonMap);

		this.setPageMap(builder.pageMap);

		this.setBetweenMap(builder.betweenMap);
		this.setInMap(builder.inMap);

		this.setAndWhereList(builder.andWhereList);
		this.setOrWhereList(builder.orWhereList);

		this.setOrderbyMap(builder.orderbyMap);
	}

	public List<String> getIsNullList () {
		return isNullList;
	}

	public void setIsNullList (List<String> isNullList) {
		this.isNullList = isNullList;
	}

	public List<String> getIsNotNullList () {
		return isNotNullList;
	}

	public void setIsNotNullList (List<String> isNotNullList) {
		this.isNotNullList = isNotNullList;
	}

	public Map<String, Object> getLtMap () {
		return ltMap;
	}

	public void setLtMap (Map<String, Object> ltMap) {
		this.ltMap = ltMap;
	}

	public Map<String, Object> getLeMap () {
		return leMap;
	}

	public void setLeMap (Map<String, Object> leMap) {
		this.leMap = leMap;
	}

	public Map<String, Object> getEqMap () {
		return eqMap;
	}

	public void setEqMap (Map<String, Object> eqMap) {
		this.eqMap = eqMap;
	}

	public Map<String, Object> getNeMap () {
		return neMap;
	}

	public void setNeMap (Map<String, Object> neMap) {
		this.neMap = neMap;
	}

	public Map<String, Object> getGeMap () {
		return geMap;
	}

	public void setGeMap (Map<String, Object> geMap) {
		this.geMap = geMap;
	}

	public Map<String, Object> getGtMap () {
		return gtMap;
	}

	public void setGtMap (Map<String, Object> gtMap) {
		this.gtMap = gtMap;
	}

	public Map<String, Object> getLikeMap () {
		return likeMap;
	}

	public void setLikeMap (Map<String, Object> likeMap) {
		this.likeMap = likeMap;
	}

	public Map<SQLJsonKey, Map<String, Object>> getJsonMap () {
		return jsonMap;
	}

	public void setJsonMap (Map<SQLJsonKey, Map<String, Object>> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Map<String, Object[]> getBetweenMap () {
		return betweenMap;
	}

	public void setBetweenMap (Map<String, Object[]> betweenMap) {
		this.betweenMap = betweenMap;
	}

	public Map<String, Object[]> getInMap () {
		return inMap;
	}

	public void setInMap (Map<String, Object[]> inMap) {
		this.inMap = inMap;
	}

	public Map<String, Integer> getPageMap () {
		return pageMap;
	}

	public void setPageMap (Map<String, Integer> pageMap) {
		this.pageMap = pageMap;
	}

	public List<Where> getAndWhereList () {
		return andWhereList;
	}

	public void setAndWhereList (List<Where> andWhereList) {
		this.andWhereList = andWhereList;
	}

	public List<Where> getOrWhereList () {
		return orWhereList;
	}

	public void setOrWhereList (List<Where> orWhereList) {
		this.orWhereList = orWhereList;
	}

	public Map<String, OrderBy> getOrderbyMap () {
		return orderbyMap;
	}

	public void setOrderbyMap (Map<String, OrderBy> orderbyMap) {
		this.orderbyMap = orderbyMap;
	}

	/**
	 * Where构造器
	 *
	 * @Title
	 * @Description
	 * @CreatedBy Assassin
	 * @DateTime 2017年10月11日下午4:24:06
	 */
	public static class Builder {

		// one key, no parameter
		/**
		 * is null
		 **/
		private List<String> isNullList    = new ArrayList<String>();
		/**
		 * is not null
		 **/
		private List<String> isNotNullList = new ArrayList<String>();

		// one key, one parameter
		/**
		 * less than
		 **/
		private Map<String, Object> ltMap   = new HashMap<String, Object>();
		/**
		 * less equal
		 **/
		private Map<String, Object> leMap   = new HashMap<String, Object>();
		/**
		 * equal
		 **/
		private Map<String, Object> eqMap   = new HashMap<String, Object>();
		/**
		 * not equal
		 **/
		private Map<String, Object> neMap   = new HashMap<String, Object>();
		/**
		 * great equal
		 **/
		private Map<String, Object> geMap   = new HashMap<String, Object>();
		/**
		 * great than
		 **/
		private Map<String, Object> gtMap   = new HashMap<String, Object>();
		/**
		 * like
		 **/
		private Map<String, Object> likeMap = new HashMap<String, Object>();

		// one key, one more json query
		/**
		 * json
		 **/
		private Map<SQLJsonKey, Map<String, Object>> jsonMap = new HashMap<SQLJsonKey, Map<String, Object>>();

		// one key, one page parameter
		/**
		 * page
		 **/
		private Map<String, Integer> pageMap = null;

		// one key, one more parameters
		/**
		 * between
		 **/
		private Map<String, Object[]> betweenMap = new HashMap<String, Object[]>();
		/**
		 * in
		 **/
		private Map<String, Object[]> inMap      = new HashMap<String, Object[]>();

		// one key, one more where parameters
		/**
		 * and where
		 **/
		private List<Where> andWhereList = new ArrayList<Where>();
		/**
		 * or where
		 **/
		private List<Where> orWhereList  = new ArrayList<Where>();

		// one key, one orderby parameter
		/**
		 * orderby
		 **/
		private Map<String, OrderBy> orderbyMap = new HashMap<String, OrderBy>();

		/**
		 * is null
		 **/
		public Builder IsNull (String key) {
			Assert.notNull(key, "The 'IS NULL' key must not be null");
			isNullList.add(key);
			return this;
		}

		/**
		 * is not null
		 **/
		public Builder IsNotNull (String key) {
			Assert.notNull(key, "The 'IS NOT NULL' key must not be null");
			isNotNullList.add(key);
			return this;
		}

		/**
		 * less than
		 **/
		public Builder LT (String key, Object value) {
			Assert.notNull(key, "The 'LESS THAN' key must not be null");
			Assert.notNull(value, "The 'LESS THAN' value must not be null");
			ltMap.put(key, value);
			return this;
		}

		/**
		 * less equal
		 **/
		public Builder LE (String key, Object value) {
			Assert.notNull(key, "The 'LESS EQUAL' key must not be null");
			Assert.notNull(value, "The 'LESS EQUAL' value must not be null");
			leMap.put(key, value);
			return this;
		}

		/**
		 * equal
		 **/
		public Builder EQ (String key, Object value) {
			Assert.notNull(key, "The 'EQUAL' key must not be null");
			Assert.notNull(value, "The 'EQUAL' value must not be null");
			eqMap.put(key, value);
			return this;
		}

		/**
		 * not equal
		 **/
		public Builder NE (String key, Object value) {
			Assert.notNull(key, "The 'NOT EQUAL' key must not be null");
			Assert.notNull(value, "The 'NOT EQUAL' value must not be null");
			neMap.put(key, value);
			return this;
		}

		/**
		 * great equal
		 **/
		public Builder GE (String key, Object value) {
			Assert.notNull(key, "The 'GREAT EQUAL' key must not be null");
			Assert.notNull(value, "The 'GREAT EQUAL' value must not be null");
			geMap.put(key, value);
			return this;
		}

		/**
		 * great than
		 **/
		public Builder GT (String key, Object value) {
			Assert.notNull(key, "The 'GREAT THAN' key must not be null");
			Assert.notNull(value, "The 'GREAT THAN' value must not be null");
			gtMap.put(key, value);
			return this;
		}

		/**
		 * like
		 **/
		public Builder Like (String key, Object value) {
			Assert.notNull(key, "The 'LIKE' key must not be null");
			Assert.notNull(value, "The 'LIKE' value must not be null");
			likeMap.put(key, value);
			return this;
		}

		/**
		 * other sql express
		 **/
		public Builder JSON (SQLJsonKey sqlJsonKey, String key, Object value) {
			Assert.notNull(sqlJsonKey, "The 'json' key must not be null");
			Assert.notNull(key, "The 'json' key must not be null");
			Assert.notNull(value, "The 'json' value must not be null");
			// 根据不同的json查询方式
			Map<String, Object> jsonItemMap = jsonMap.containsKey(sqlJsonKey.name()) ? jsonMap.get(sqlJsonKey.name()) : new HashMap<String, Object>();
			jsonItemMap.put(key, value);
			jsonMap.put(sqlJsonKey, jsonItemMap);
			return this;
		}

		/**
		 * between
		 **/
		public Builder Between (String key, Object begin, Object end) {
			Assert.notNull(key, "The 'BETWEEN'S key must not be null");
			Assert.notNull(begin, "The 'BETWEEN'S BEGIN' value must not be null");
			Assert.notNull(end, "The 'BETWEEN'S END' value must not be null");
			Object[] value = new Object[]{begin, end};
			betweenMap.put(key, value);
			return this;
		}

		/**
		 * in
		 **/
		public Builder In (String key, Object[] value) {
			Assert.notNull(key, "The 'IN' key must not be null");
			Assert.notNull(value, "The 'IN' value must not be null");
			inMap.put(key, value);
			return this;
		}

		/**
		 * page
		 **/
		public Builder Page (int pageNo, int pageSize) {
			Map<String, Integer> pageMap = new HashMap<String, Integer>();
			pageMap.put(SysConstants.PAGE_PARAMS_PAGENO, pageNo);
			pageMap.put(SysConstants.PAGE_PARAMS_PAGESIZE, pageSize);
			this.pageMap = pageMap;
			return this;
		}

		/**
		 * and
		 **/
		public Builder And (Where... andWheres) {
			Assert.notNull(andWhereList, "The 'Where List: andWhereList' must not be null");
			this.andWhereList.addAll(Arrays.asList(andWheres));
			return this;
		}

		/**
		 * or
		 **/
		public Builder Or (Where... orWheres) {
			Assert.notNull(orWhereList, "The 'Where List: orWhereList' must not be null");
			this.orWhereList.addAll(Arrays.asList(orWheres));
			return this;
		}

		/**
		 * order by
		 **/
		public Builder OrderBy (String key, OrderBy value) {
			Assert.notNull(key, "The model's fieldname must not be null");
			orderbyMap.put(key, value);
			return this;
		}

		/**
		 * build query object {@link Where}
		 *
		 * @return
		 */
		public Where build () {
			Where where = new Where(this);
			return where;
		}
	}

}
