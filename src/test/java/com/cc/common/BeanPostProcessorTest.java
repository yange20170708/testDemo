/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;


/**
 * 名称: Test.java<br>
 * 描述:所有的bean在创建完之后，都会回调postProcessAfterInitialization函数，这时就可以确定bean是已经创建好的了。<br>
 * 所以扫描自定义的annotation的代码大概是这个样子的：
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public class BeanPostProcessorTest implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                MyListener myListener = AnnotationUtils.findAnnotation(method, MyListener.class);
                myListener.listenerName();
                //do something
            }
        }
        return bean;
    }
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyListener {
        /**
         * listener名称注解，默认值为类名称
         * @return
         */
        public String listenerName() default "MyListener";
    }

}
