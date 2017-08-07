/*
 * 文件名：RWSplitJdbcTemplate.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年3月22日
 * 备注: 
 */
package com.cc.hbase.service;


/**
 * 名称: RWSplitJdbcTemplate.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月22日 下午4:57:50<br>
 * @version [版本号, V1.0]
 * @since 2017年3月22日 下午4:57:50
 * @author YanCui
 */
public interface RWSplitService {
	
    public void readWriteTX();
    public void readTX();
    public void readReadonlyTX();
    public void read();
    public void readReadOnly();
}
