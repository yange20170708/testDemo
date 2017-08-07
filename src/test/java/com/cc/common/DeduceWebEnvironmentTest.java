/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;

import org.springframework.util.ClassUtils;


/**
 * 名称: Test.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public class DeduceWebEnvironmentTest {
	public static void main(String[] args) throws ClassNotFoundException {
		deduceWebEnvironment();
	}
	
	private static final String[] WEB_ENVIRONMENT_CLASSES = { "javax.servlet.Servlet",
    "org.springframework.web.context.ConfigurableWebApplicationContext" };

	/**
	 * 判断是否在web环境
	 * spring boot在启动时，先通过一个简单的查找Servlet类的方式来判断是不是在web环境
	 * @return boolean
	 * @变更记录 2017年7月28日 下午3:46:39  YanCui 
	 */
	public static boolean deduceWebEnvironment() {
	    for (String className : WEB_ENVIRONMENT_CLASSES) {
	        if (!ClassUtils.isPresent(className, null)) {
	            return false;
	        }
	    }
	    return true;
	}
}
