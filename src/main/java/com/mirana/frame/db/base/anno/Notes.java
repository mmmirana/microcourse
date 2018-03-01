package com.mirana.frame.db.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Title Mysql数据库表或者列的注释注解
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 23:23:41
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RUNTIME)
public @interface Notes {

	String value () default "";

}
