package com.mirana.frame.db.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Title 数据库表注解
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 23:15:05
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface TablePlus {

	String name () default "";

}
