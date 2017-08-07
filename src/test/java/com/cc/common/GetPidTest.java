/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;

import java.lang.management.ManagementFactory;


/**
 * 名称: Test.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public class GetPidTest {
	public static void main(String[] args) throws ClassNotFoundException {
		getPid();
	}
	
	/**
	 * 获取进程的PID
	 * ApplicationPid，可以获取PID。
	 * @return boolean
	 * @变更记录 2017年7月28日 下午3:46:39  YanCui 
	 */
	public static String getPid() {
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            return jvmName.split("@")[0];
        }
        catch (Throwable ex) {
            return null;
        }
    }
}
