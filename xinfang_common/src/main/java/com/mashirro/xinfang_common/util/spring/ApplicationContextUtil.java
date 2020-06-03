package com.mashirro.xinfang_common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 实现了ApplicationContextAware接口的bean，当spring容器初始化的时候，
 * 会自动调用setApplicationContext方法将ApplicationContext注入进来
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    //声明一个静态变量保存
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("执行setApplicationContext之前...");
        ApplicationContextUtil.applicationContext = applicationContext;
        System.out.println("执行setApplicationContext完成...");
    }

    /**
     * 获取bean
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(requiredType);
    }


}
