package com.mirana.frame.db.base.mapping;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.utils.Assert;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 数据库对应JAVA实体类的映射
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/24 19:40:17
 */
public class ModelMapping {

	/**
	 * table注解class
	 **/
	private Class<? extends Annotation> annoClassTable;
	/**
	 * pk注解class
	 **/
	private Class<? extends Annotation> annoClassPk;
	/**
	 * column注解class
	 **/
	private Class<? extends Annotation> annoClassColumn;

	/**
	 * beanClass
	 **/
	private Class<? extends BaseModel> modelClass;

	/**
	 * 数据库表名
	 **/
	private String tableName;

	/**
	 * 主键Field
	 **/
	private Field pkField;

	/**
	 * 主键Field名称
	 **/
	private String pkFieldName;

	/**
	 * 数据库主键Name
	 **/
	private String pkColumnName;

	/**
	 * 字段Field数组
	 **/
	private Field[] fields;

	/**
	 * 字段Field名称数组
	 **/
	private String[] fieldNames;

	/**
	 * 数据库columName数组
	 **/
	private String[] columnNames;

	public Class<? extends Annotation> getAnnoClassTable () {
		return annoClassTable;
	}

	public void setAnnoClassTable (Class<? extends Annotation> annoClassTable) {
		this.annoClassTable = annoClassTable;
	}

	public Class<? extends Annotation> getAnnoClassPk () {
		return annoClassPk;
	}

	public void setAnnoClassPk (Class<? extends Annotation> annoClassPk) {
		this.annoClassPk = annoClassPk;
	}

	public Class<? extends Annotation> getAnnoClassColumn () {
		return annoClassColumn;
	}

	public void setAnnoClassColumn (Class<? extends Annotation> annoClassColumn) {
		this.annoClassColumn = annoClassColumn;
	}

	public Class<? extends BaseModel> getModelClass () {
		return modelClass;
	}

	public void setModelClass (Class<? extends BaseModel> modelClass) {
		this.modelClass = modelClass;
	}

	public String getTableName () {
		return tableName;
	}

	public void setTableName (String tableName) {
		this.tableName = tableName;
	}

	public Field getPkField () {
		return pkField;
	}

	public void setPkField (Field pkField) {
		this.pkField = pkField;
	}

	public String getPkFieldName () {
		return pkFieldName;
	}

	public void setPkFieldName (String pkFieldName) {
		this.pkFieldName = pkFieldName;
	}

	public String getPkColumnName () {
		return pkColumnName;
	}

	public void setPkColumnName (String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}

	public Field[] getFields () {
		return fields;
	}

	public void setFields (Field[] fields) {
		this.fields = fields;
	}

	/**
	 * 获取不带主键的字段
	 *
	 * @return
	 */
	public Field[] getFieldsWithoutPk () {
		Assert.noNullElements(fields);
		Assert.notNull(pkField);
		return ArrayUtils.removeElement(fields, pkField);
	}

	public String[] getFieldNames () {
		return fieldNames;
	}

	public void setFieldNames (String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String[] getColumnNames () {
		return columnNames;
	}

	public void setColumnNames (String[] columnNames) {
		this.columnNames = columnNames;
	}

}
