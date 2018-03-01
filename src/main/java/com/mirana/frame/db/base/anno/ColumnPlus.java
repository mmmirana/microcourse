package com.mirana.frame.db.base.anno;

import com.mirana.frame.db.base.extend.type.MysqlTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Title 数据库列名注解
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 23:17:47
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RUNTIME)
public @interface ColumnPlus {

	/**
	 * 数据库列名
	 *
	 * @return
	 */
	String name () default "";

	/**
	 * 是否唯一，默认false
	 *
	 * @return
	 */
	boolean unique () default false;

	/**
	 * 是否可以为null，默认true
	 *
	 * @return
	 */
	boolean nullable () default true;

	/**
	 * sql的类型
	 *
	 * @return
	 */
	MysqlTypeEnum sqlType () default MysqlTypeEnum.UNKENOW;

	/**
	 * 如果是数值类型，有小数的部分请填写"?,?"，第一个?代表整数长度，第二个?代表小数部分长度 <br>
	 * <br>
	 * 比如金额price字段，<code>length = "5,2"</code>表示整数最大长度5，小数最大长度2
	 * <p>
	 * <pre>
	 * <code>
	 * &#64;ColumnPlus(name = "price", type = MysqlTypeEnum.DECIMAL, length = "5,2")
	 * private Double price;
	 * </code>
	 * </pre>
	 *
	 * @return
	 */
	String length () default "";

}
