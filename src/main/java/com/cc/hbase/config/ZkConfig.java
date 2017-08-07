/*
 * 文件名：ZkConfig.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月22日
 * 备注: 
 */
package com.cc.hbase.config;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;

/**
 * 名称: ZkConfig.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月22日 下午6:12:15<br>
 * @version [版本号, V1.0]
 * @since 2017年6月22日 下午6:12:15
 * @author YanCui
 */
public class ZkConfig {
	
	List<String> listpath = new  ArrayList<String>();
	ZkClient zkClient = new ZkClient("127.0.0.1:2181",5000);
	String rootpath = "/dubbo";
	
	public static void main(String[] args) {
		
	}
	public String zk(){
//		List<String> list = zkClient.getChildren();
//		if(!CollectionUtils.isEmpty(list)){
//			for(String s : list){
//				listpath.add(s);
//			}
//		}
//		zkClient.readData("/dubbo");
		return null;
	}
}
