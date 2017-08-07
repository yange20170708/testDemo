/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;

import java.lang.reflect.Constructor;

/**
 * 名称: Test.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public class MainMethodLauchTest {
	public static void main(String[] args) throws ClassNotFoundException {
	}
	
	/**
     * Launch the application given the archive file and a fully configured classloader.
     */
    protected void launch(String[] args, String mainClass, ClassLoader classLoader)
            throws Exception {
        Runnable runner = createMainMethodRunner(mainClass, args, classLoader);
        Thread runnerThread = new Thread(runner);
        runnerThread.setContextClassLoader(classLoader);//设置当前线程类加载器
        runnerThread.setName(Thread.currentThread().getName());//设置当前线程名称
        runnerThread.start();
    }

    /**
     * Create the {@code MainMethodRunner} used to launch the application.
     */
    protected Runnable createMainMethodRunner(String mainClass, String[] args,
            ClassLoader classLoader) throws Exception {
        Class<?> runnerClass = classLoader.loadClass(null);//RUNNER_CLASS
        Constructor<?> constructor = runnerClass.getConstructor(String.class,
                String[].class);
        return (Runnable) constructor.newInstance(mainClass, args);//实例化mainclass并启动
    }
}
