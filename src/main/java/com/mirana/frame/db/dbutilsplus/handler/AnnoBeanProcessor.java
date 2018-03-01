package com.mirana.frame.db.dbutilsplus.handler;

import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.annoparse.BaseDBAnnoParse;
import com.mirana.frame.utils.BeanUtils;
import com.mirana.frame.utils.log.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * Edited by Assassin referenced BeanProcessor <br>
 * <br>
 * The columnAnnotationClass was designated as {@link ColumnPlus}<br>
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 01:59:52
 * @see org.apache.commons.dbutils.BeanProcessor
 */
public class AnnoBeanProcessor {

	/**
	 * Special array value used by <code>mapColumnsToProperties</code> that indicates there is no bean property that matches a column from a
	 * <code>ResultSet</code>.
	 */
	protected static final int PROPERTY_NOT_FOUND = -1;
	// added by Assassin for field anno
	protected static final int FIELD_NOT_FOUND = -2;
	private static final Class<? extends Annotation> columnAnnoClazz = ColumnPlus.class;
	/**
	 * Set a bean's primitive properties to these defaults when SQL NULL is returned. These are the same as the defaults that ResultSet get* methods
	 * return in the event of a NULL column.
	 */
	private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();

	static {
		primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
		primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
		primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
		primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
		primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
		primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
		primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
		primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
	}

	/**
	 * ResultSet column to bean property name overrides.
	 */
	private final Map<String, String> columnToPropertyOverrides;

	/**
	 * Constructor for BeanProcessor.
	 */
	public AnnoBeanProcessor () {
		this(new HashMap<String, String>());
	}

	/**
	 * Constructor for BeanProcessor configured with column to property name overrides.
	 *
	 * @param columnToPropertyOverrides ResultSet column to bean property name overrides
	 * @since 1.5
	 */
	public AnnoBeanProcessor (Map<String, String> columnToPropertyOverrides) {
		super();
		if (columnToPropertyOverrides == null) {
			throw new IllegalArgumentException("columnToPropertyOverrides map cannot be null");
		}
		this.columnToPropertyOverrides = columnToPropertyOverrides;
	}

	/**
	 * The following codes with comment are no longer used<br>
	 * <br>
	 * You should add Column Annotation to your entity Class if you want to invoke this method
	 *
	 * @param <T>         The type of bean to create
	 * @param rs          ResultSet that supplies the bean data
	 * @param type        Class from which to create the bean instance
	 * @param dbAnnoParse Bean's Annotation parser
	 * @return the newly created bean
	 * @throws SQLException if a database access error occurs
	 */
	public <T> T toBean (ResultSet rs, Class<T> type, BaseDBAnnoParse dbAnnoParse) throws SQLException {
		LogUtils.debug("toBean...");

		// PropertyDescriptor[] props = this.propertyDescriptors(type);

		// ResultSetMetaData rsmd = rs.getMetaData();
		// int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

		// return this.createBean(rs, type, props, columnToProperty);

		return toBeanByAnno(rs, type, dbAnnoParse);
	}

	public <T> T toBeanByAnno (ResultSet rs, Class<T> type, BaseDBAnnoParse dbAnnoParse) throws SQLException {
		LogUtils.debug("toBeanByAnno...");

		Field[] fields = BeanUtils.getFieldsWithAnno(type, columnAnnoClazz);

		ResultSetMetaData rsmd = rs.getMetaData();
		int[] columnToField = this.mapColumnsToFields(rsmd, fields);

		return this.createBeanByAnno(rs, type, dbAnnoParse, fields, columnToField);
	}

	/**
	 * The following codes with comment are no longer used<br>
	 * <br>
	 * You should add Column Annotation to your entity Class if you want to invoke this method
	 *
	 * @param <T>         The type of bean to create
	 * @param rs          ResultSet that supplies the bean data
	 * @param type        Class from which to create the bean instance
	 * @param dbAnnoParse Bean's Annotation parser
	 * @return the newly created List of beans
	 * @throws SQLException if a database access error occurs
	 */
	public <T> List<T> toBeanList (ResultSet rs, Class<T> type, BaseDBAnnoParse dbAnnoParse) throws SQLException {
		// List<T> results = new ArrayList<T>();
		//
		// if (!rs.next()) {
		// return results;
		// }
		//
		// PropertyDescriptor[] props = this.propertyDescriptors(type);
		// ResultSetMetaData rsmd = rs.getMetaData();
		// int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);
		//
		// do {
		// results.add(this.createBean(rs, type, props, columnToProperty));
		// } while (rs.next());
		//
		// return results;
		return toBeanListByAnno(rs, type, dbAnnoParse);
	}

	/**
	 * @param <T>         The type of bean to create
	 * @param rs          ResultSet that supplies the bean data
	 * @param type        Class from which to create the bean instance
	 * @param dbAnnoParse Bean's Annotation parser
	 * @return the newly created List of beans
	 * @throws SQLException if a database access error occurs
	 */
	public <T> List<T> toBeanListByAnno (ResultSet rs, Class<T> type, BaseDBAnnoParse dbAnnoParse) throws SQLException {
		List<T> results = new ArrayList<T>();

		if (!rs.next()) {
			return results;
		}

		Field[] fields = BeanUtils.getFieldsWithAnno(type, columnAnnoClazz);

		ResultSetMetaData rsmd = rs.getMetaData();
		int[] columnToField = this.mapColumnsToFields(rsmd, fields);

		do {
			results.add(this.createBeanByAnno(rs, type, dbAnnoParse, fields, columnToField));
		} while (rs.next());

		return results;
	}

	/**
	 * @param rs
	 * @param type
	 * @param fields
	 * @param columnToField
	 * @param dbAnnoParse   Bean's Annotation parser
	 * @return
	 * @throws SQLException
	 */
	private <T> T createBeanByAnno (ResultSet rs, Class<T> type, BaseDBAnnoParse dbAnnoParse, Field[] fields, int[] columnToField)
		throws SQLException {

		T bean = this.newInstance(type);

		for (int i = 1; i < columnToField.length; i++) {

			if (columnToField[i] == FIELD_NOT_FOUND) {
				continue;
			}

			Field field = fields[columnToField[i]];
			Class<?> fieldType = field.getType();

			Object value = null;
			if (fieldType != null) {
				value = this.processColumn(rs, i, fieldType);

				if (value == null && fieldType.isPrimitive()) {
					value = primitiveDefaults.get(fieldType);
				}
			}

			this.callSetter(bean, field, value);
		}

		return bean;
	}

	/**
	 * Calls the setter method on the target object for the given property. If no setter method exists for the property, this method does nothing.
	 *
	 * @param target The object to set the property on.
	 * @param prop   The property to set.
	 * @param value  The value to pass into the setter.
	 * @throws SQLException if an error occurs setting the property.
	 */
	@SuppressWarnings("unchecked")
	private void callSetter (Object target, PropertyDescriptor prop, Object value) throws SQLException {

		Method setter = prop.getWriteMethod();

		if (setter == null) {
			return;
		}

		Class<?>[] params = setter.getParameterTypes();
		try {
			// convert types for some popular ones
			if (value instanceof java.util.Date) {
				final String targetType = params[0].getName();
				if ("java.sql.Date".equals(targetType)) {
					value = new java.sql.Date(((java.util.Date) value).getTime());
				} else if ("java.sql.Time".equals(targetType)) {
					value = new java.sql.Time(((java.util.Date) value).getTime());
				} else if ("java.sql.Timestamp".equals(targetType)) {
					Timestamp tsValue = (Timestamp) value;
					int nanos = tsValue.getNanos();
					value = new java.sql.Timestamp(tsValue.getTime());
					((Timestamp) value).setNanos(nanos);
				}
			} else if (value instanceof String && params[0].isEnum()) {
				value = Enum.valueOf(params[0].asSubclass(Enum.class), (String) value);
			}

			// Don't call setter if the value object isn't the right type
			if (this.isCompatibleType(value, params[0])) {
				setter.invoke(target, new Object[]{value});
			} else {
				throw new SQLException("Cannot set " + prop.getName() + ": incompatible types, cannot convert " + value.getClass().getName() + " to "
					+ params[0].getName());
				// value cannot be null here because isCompatibleType allows
				// null
			}

		} catch (IllegalArgumentException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());

		} catch (InvocationTargetException e) {
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * @param target
	 * @param field
	 * @param value
	 */
	private <T> void callSetter (Object target, Field field, Object value) throws SQLException {
		// Introspector caches BeanInfo classes for better performance
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(target.getClass());

		} catch (IntrospectionException e) {
			throw new SQLException("Bean introspection failed: " + e.getMessage());
		}
		PropertyDescriptor[] propDescArray = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor prop : propDescArray) {
			if (prop.getName().equals(field.getName())) {
				callSetter(target, prop, value);
				break;
			}
		}
	}

	/**
	 * ResultSet.getObject() returns an Integer object for an INT column. The setter method for the property might take an Integer or a primitive int.
	 * This method returns true if the value can be successfully passed into the setter method. Remember, Method.invoke() handles the unwrapping of
	 * Integer into an int.
	 *
	 * @param value The value to be passed into the setter method.
	 * @param type  The setter's parameter type (non-null)
	 * @return boolean True if the value is compatible (null => true)
	 */
	private boolean isCompatibleType (Object value, Class<?> type) {
		// Do object check first, then primitives
		if (value == null || type.isInstance(value)) {
			return true;

		} else if (type.equals(Integer.TYPE) && value instanceof Integer) {
			return true;

		} else if (type.equals(Long.TYPE) && value instanceof Long) {
			return true;

		} else if (type.equals(Double.TYPE) && value instanceof Double) {
			return true;

		} else if (type.equals(Float.TYPE) && value instanceof Float) {
			return true;

		} else if (type.equals(Short.TYPE) && value instanceof Short) {
			return true;

		} else if (type.equals(Byte.TYPE) && value instanceof Byte) {
			return true;

		} else if (type.equals(Character.TYPE) && value instanceof Character) {
			return true;

		} else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
			return true;

		}
		return false;

	}

	/**
	 * Factory method that returns a new instance of the given Class. This is called at the start of the bean creation process and may be overridden
	 * to provide custom behavior like returning a cached bean instance.
	 *
	 * @param <T> The type of object to create
	 * @param c   The Class to create an object from.
	 * @return A newly created object of the Class.
	 * @throws SQLException if creation failed.
	 */
	protected <T> T newInstance (Class<T> c) throws SQLException {
		try {
			return c.newInstance();

		} catch (InstantiationException e) {
			throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * The positions in the returned array represent column numbers. The values stored at each position represent the index in the
	 * <code>PropertyDescriptor[]</code> for the bean property that matches the column name. If no bean property was found for a column, the position
	 * is set to <code>PROPERTY_NOT_FOUND</code>.
	 *
	 * @param rsmd  The <code>ResultSetMetaData</code> containing column information.
	 * @param props The bean property descriptors.
	 * @return An int[] with column index to property index mappings. The 0th element is meaningless because JDBC column indexing starts at 1.
	 * @throws SQLException if a database access error occurs
	 */
	protected int[] mapColumnsToProperties (ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {

		int cols = rsmd.getColumnCount();
		int[] columnToProperty = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(col);
			}
			String propertyName = columnToPropertyOverrides.get(columnName);
			if (propertyName == null) {
				propertyName = columnName;
			}
			for (int i = 0; i < props.length; i++) {

				if (propertyName.equalsIgnoreCase(props[i].getName())) {
					columnToProperty[col] = i;
					break;
				}
			}
		}

		return columnToProperty;
	}

	/**
	 * added by wangzf Field加入注解后的Bean解析器
	 *
	 * @param rsmd
	 * @param fields
	 * @return
	 */
	private int[] mapColumnsToFields (ResultSetMetaData rsmd, Field[] fields) throws SQLException {
		int cols = rsmd.getColumnCount();
		int[] columnToField = new int[cols + 1];
		Arrays.fill(columnToField, FIELD_NOT_FOUND);

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(col);
			}
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];

				// 默认的数据库列名是字段名称
				String annoColumnname = field.getName();

				ColumnPlus columnAnno = (ColumnPlus) field.getAnnotation(columnAnnoClazz);
				if (StringUtils.isNotBlank(columnAnno.name())) {
					annoColumnname = columnAnno.name();
				}

				if (columnName.equalsIgnoreCase(annoColumnname.trim())) {
					columnToField[col] = i;
					break;
				}
			}
		}

		return columnToField;
	}

	/**
	 * Convert a <code>ResultSet</code> column into an object. Simple implementations could just call <code>rs.getObject(index)</code> while more
	 * complex implementations could perform type manipulation to match the column's type to the bean property type.
	 * <p>
	 * <p>
	 * This implementation calls the appropriate <code>ResultSet</code> getter method for the given property type to perform the type conversion. If
	 * the property type doesn't match one of the supported <code>ResultSet</code> types, <code>getObject</code> is called.
	 * </p>
	 *
	 * @param rs       The <code>ResultSet</code> currently being processed. It is positioned on a valid row before being passed into this method.
	 * @param index    The current column index being processed.
	 * @param propType The bean property type that this column needs to be converted into.
	 * @return The object from the <code>ResultSet</code> at the given column index after optional type processing or <code>null</code> if the column
	 * value was SQL NULL.
	 * @throws SQLException if a database access error occurs
	 */
	protected Object processColumn (ResultSet rs, int index, Class<?> propType) throws SQLException {

		if (!propType.isPrimitive() && rs.getObject(index) == null) {
			return null;
		}

		if (propType.equals(String.class)) {
			return rs.getString(index);

		} else if (propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(index));

		} else if (propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
			return Boolean.valueOf(rs.getBoolean(index));

		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return Long.valueOf(rs.getLong(index));

		} else if (propType.equals(Double.TYPE) || propType.equals(Double.class)) {
			return Double.valueOf(rs.getDouble(index));

		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return Float.valueOf(rs.getFloat(index));

		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return Short.valueOf(rs.getShort(index));

		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return Byte.valueOf(rs.getByte(index));

		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(index);

		} else if (propType.equals(SQLXML.class)) {
			return rs.getSQLXML(index);

		} else {
			return rs.getObject(index);
		}

	}
}
