package com.mirana.frame.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 21:25:22
 */
public class SpringCtxUtils implements ApplicationContextAware {

	public static ApplicationContext appctx;

	public static Object getBean (String beanname) {
		return appctx.getBean(beanname);
	}

	public static <T> Object getBean (Class<T> beanclazz) {
		return appctx.getBean(beanclazz);
	}

	@Autowired
	@Override
	public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
		SpringCtxUtils.appctx = applicationContext;
	}
}
