package com.mirana.frame.db.generator;

import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.exception.DBException;
import com.mirana.frame.db.base.extend.type.MysqlTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SqlTypeUtils {

	private static Map<Class<?>, MysqlTypeEnum> typeMappingMap = new LinkedHashMap<Class<?>, MysqlTypeEnum>();

	static {
		typeMappingMap.put(Short.class, MysqlTypeEnum.TINYINT);
		typeMappingMap.put(short.class, MysqlTypeEnum.TINYINT);

		typeMappingMap.put(Integer.class, MysqlTypeEnum.INT);
		typeMappingMap.put(int.class, MysqlTypeEnum.INT);

		typeMappingMap.put(Long.class, MysqlTypeEnum.BIGINT);
		typeMappingMap.put(long.class, MysqlTypeEnum.BIGINT);

		typeMappingMap.put(Float.class, MysqlTypeEnum.FLOAT);
		typeMappingMap.put(float.class, MysqlTypeEnum.FLOAT);

		typeMappingMap.put(Double.class, MysqlTypeEnum.DOUBLE);
		typeMappingMap.put(double.class, MysqlTypeEnum.DOUBLE);

		typeMappingMap.put(Boolean.class, MysqlTypeEnum.BIT);
		typeMappingMap.put(boolean.class, MysqlTypeEnum.BIT);

		typeMappingMap.put(String.class, MysqlTypeEnum.VARCHAR);

		typeMappingMap.put(Date.class, MysqlTypeEnum.DATETIME);
		typeMappingMap.put(java.sql.Date.class, MysqlTypeEnum.DATETIME);
		typeMappingMap.put(Timestamp.class, MysqlTypeEnum.DATETIME);

	}

	/**
	 * 根据字段生成对应字段的sql类型和长度
	 * <p>
	 * <pre>
	 * int(11)
	 * varchar(255)
	 * decimal(5,2)
	 * datetime
	 * float
	 * double
	 * text
	 * blob
	 *
	 * </pre>
	 *
	 * @param columnField
	 * @return
	 */
	public static String getSqlTypeAndLength (Field columnField) {
		// column annotation
		ColumnPlus columnAnno = (ColumnPlus) columnField.getAnnotation(ColumnPlus.class);

		// Anno MysqlTypeEnum
		MysqlTypeEnum columnMysqlType = columnAnno.sqlType();

		if (columnMysqlType == MysqlTypeEnum.UNKENOW) {
			// column mysqlType
			columnMysqlType = typeMappingMap.get(columnField.getType());
		}

		if (columnMysqlType == null || columnMysqlType == MysqlTypeEnum.UNKENOW) {
			throw new DBException("在MysqlTypeEnum中找不到Java Class: " + columnField.getType() + "对应的Sql类型");
		}

		// 默认类型是MysqlTypeEnum.UNKENOW
		String defultTypename = columnMysqlType.getName();
		// 默认长度是""
		String defultLength = columnMysqlType.getLength();

		// 生成数据库列对应的类型和长度
		StringBuffer finalSqlTypeAndLength = new StringBuffer();
		if (columnMysqlType == MysqlTypeEnum.UNKENOW) {
			finalSqlTypeAndLength.append(defultTypename);
		} else {
			finalSqlTypeAndLength.append(columnMysqlType.name());
		}

		if (StringUtils.isBlank(columnAnno.length())) {
			finalSqlTypeAndLength.append(StringUtils.isBlank(defultLength) ? "" : " (" + defultLength + ")");
		} else {
			finalSqlTypeAndLength.append(" (" + columnAnno.length().trim() + ")");
		}

		return finalSqlTypeAndLength.toString();

	}
}
