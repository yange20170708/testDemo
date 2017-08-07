/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;


/**
 * 名称: Test.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public class DeduceMainApplicationClassTest {
	public static void main(String[] args) throws ClassNotFoundException {
		deduceMainApplicationClass();
	}
	public void clazz() throws ClassNotFoundException {
		Class<?> clazz = Class.forName("net.xsoftlab.baike.TestReflect");
        // 取得父类
        Class<?> parentClass = clazz.getSuperclass();
        System.out.println("clazz的父类为：" + parentClass.getName());
        // clazz的父类为： java.lang.Object
        // 获取所有的接口
        Class<?> intes[] = clazz.getInterfaces();
        System.out.println("clazz实现的接口有：");
        for (int i = 0; i < intes.length; i++) {
            System.out.println((i + 1) + "：" + intes[i].getName());
        }
	}
	
	/**
	 * 获取原始启动的main函数
	 * 通过堆栈里获取的方式，判断main函数，找到原始启动的main函数。
	 * @return Class<?>  
	 * @变更记录 2017年7月28日 下午3:46:39  YanCui 
	 */
	public static Class<?> deduceMainApplicationClass() {
	    try {
	        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
	        for (StackTraceElement stackTraceElement : stackTrace) {
	            if ("main".equals(stackTraceElement.getMethodName())) {
	                return Class.forName(stackTraceElement.getClassName());
	            }
	        }
	    }
	    catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
}
