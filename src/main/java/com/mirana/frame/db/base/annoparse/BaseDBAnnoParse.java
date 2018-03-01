package com.mirana.frame.db.base.annoparse;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.exception.DBException;
import com.mirana.frame.db.base.mapping.ModelMapping;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 数据库注解解析基类
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/24 20:24:19
 */
public abstract class BaseDBAnnoParse {

	private static final Logger log = LoggerFactory.getLogger(BaseDBAnnoParse.class);

	/**
	 * 实体类对应"数据库表"的注解类
	 **/
	protected Class<? extends Annotation> tableAnnoClazz;

	/**
	 * 实体类对应"数据库表主键"的注解类
	 **/
	protected Class<? extends Annotation> pkAnnoClazz;

	/**
	 * 实体类字段对应"数据库表列名"的注解类
	 **/
	protected Class<? extends Annotation> columnAnnoClazz;

	/**
	 * 将ModelClass解析成ModelMapping
	 *
	 * @param modelClass
	 * @return
	 */
	public ModelMapping parse (Class<? extends BaseModel> modelClass) {
		Assert.notNull(modelClass, "The parameter 'modelClass' must not be null... ");
		log.debug("[DBAnnoParse] Parsing model's class " + modelClass.getSimpleName());

		ModelMapping mapping = new ModelMapping();

		// modelClass
		mapping.setModelClass(modelClass);

		// anno
		fullAnnoProp(mapping);

		// table
		fullTableProp(mapping);

		// pk
		fullPkProp(mapping);

		// columns
		fullColumnProp(mapping);

		return mapping;
	}

	;

	/**
	 * 填充DB Anno属性
	 *
	 * @param mapping
	 */
	private void fullAnnoProp (ModelMapping mapping) {
		// annoClass
		mapping.setAnnoClassTable(tableAnnoClazz);
		mapping.setAnnoClassPk(pkAnnoClazz);
		mapping.setAnnoClassColumn(columnAnnoClazz);
	}

	/**
	 * 填充DB Table属性
	 *
	 * @param mapping
	 */
	private void fullTableProp (ModelMapping mapping) {
		// tablename
		mapping.setTableName(getTablename(mapping.getModelClass()));
	}

	/**
	 * 填充DB pk属性
	 *
	 * @param mapping
	 */
	private void fullPkProp (ModelMapping mapping) {
		Class<? extends BaseModel> modelClass = mapping.getModelClass();
		// primary key
		Field pkField = getPkField(modelClass);// pkField
		String pkFieldName = getPkFieldName(pkField);// pkFieldName
		String pkColumnName = getPkColumnName(pkField);// pkColumnName

		mapping.setPkField(pkField);
		mapping.setPkFieldName(pkFieldName);
		mapping.setPkColumnName(pkColumnName);
	}

	/**
	 * 填充DB column属性
	 *
	 * @param mapping
	 */
	private void fullColumnProp (ModelMapping mapping) {
		Field[] columnFields = getFields(mapping);// fields
		String[] fieldNames = getFieldNames(columnFields);// fieldNames
		String[] columnNames = getColumnNames(columnFields);// columnNames

		mapping.setFields(columnFields);
		mapping.setFieldNames(fieldNames);
		mapping.setColumnNames(columnNames);
	}

	/**
	 * 获取数据库表名
	 *
	 * @param modelClass
	 * @return
	 */
	public abstract String getTablename (Class<? extends BaseModel> modelClass);

	/**
	 * 获取数据库主键column name
	 *
	 * @param pkField
	 * @return
	 */
	public abstract String getPkColumnName (Field pkField);

	/**
	 * 获取数据库column name数组
	 *
	 * @param columnFields
	 * @return
	 */
	public abstract String[] getColumnNames (Field[] columnFields);

	/**
	 * 获取实体类主键字段
	 *
	 * @param modelClass
	 * @return
	 */
	public Field getPkField (Class<? extends BaseModel> modelClass) {
		Field[] pkFiles = BeanUtils.getFieldsWithAnno(modelClass, pkAnnoClazz);

		// 实体类只能有一个主键的注解
		if (pkFiles.length != 1) {
			throw new DBException("The model's class " + modelClass.getSimpleName()
				+ "must contains noly one field with Annotataion[ " + pkAnnoClazz.getSimpleName() + " ]");
		} else {
			// 获取主键注解
			Field pkField = pkFiles[0];
			return pkField;
		}
	}

	;

	/**
	 * 获取数据库主键Field name
	 *
	 * @param pkField
	 * @return
	 */
	public String getPkFieldName (Field pkField) {
		return pkField.getName();
	}

	;

	/**
	 * 获取实体类与数据库映射的字段数组
	 *
	 * @param mapping
	 * @return
	 */
	public Field[] getFields (ModelMapping mapping) {

		// 获取包含column注解的字段
		Field[] fields = BeanUtils.getFieldsWithAnno(mapping.getModelClass(), columnAnnoClazz);

		return fields;
	}

	;

	/**
	 * 获取实体类与数据库映射的字段名称数组
	 *
	 * @param columnFields
	 * @return
	 */
	public String[] getFieldNames (Field[] columnFields) {
		String[] fieldNames = new String[columnFields.length];
		for (int i = 0; i < columnFields.length; i++) {
			Field field = columnFields[i];
			fieldNames[i] = field.getName();
		}
		return fieldNames;
	}

	;

	public Class<? extends Annotation> getTableAnnoClazz () {
		return tableAnnoClazz;
	}

	public void setTableAnnoClazz (Class<? extends Annotation> tableAnnoClazz) {
		this.tableAnnoClazz = tableAnnoClazz;
	}

	public Class<? extends Annotation> getPkAnnoClazz () {
		return pkAnnoClazz;
	}

	public void setPkAnnoClazz (Class<? extends Annotation> pkAnnoClazz) {
		this.pkAnnoClazz = pkAnnoClazz;
	}

	public Class<? extends Annotation> getColumnAnnoClazz () {
		return columnAnnoClazz;
	}

	public void setColumnAnnoClazz (Class<? extends Annotation> columnAnnoClazz) {
		this.columnAnnoClazz = columnAnnoClazz;
	}

}
