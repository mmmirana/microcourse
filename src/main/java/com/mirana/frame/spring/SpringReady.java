package com.mirana.frame.spring;

import com.mirana.frame.service.ISpringReadyService;
import com.mirana.frame.utils.log.LogUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * Title：spring 初始化bean完毕后执行的方法
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/823:54
 */
public class SpringReady implements InitializingBean {

	@Resource
	private ISpringReadyService springReadyService;

	@Override
	public void afterPropertiesSet () throws Exception {

	}

	public void init () {
		LogUtils.info("Spring组件初始化完毕...");
		springReadyService.init();
	}

}
