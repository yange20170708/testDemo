/*
 * 文件名：RWSplitServiceImpl.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年3月22日
 * 备注: 
 */
package com.cc.hbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cc.hbase.service.RWSplitService;
/**
 * 名称: RWSplitServiceImpl.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月22日 下午4:57:50<br>
 * @version [版本号, V1.0]
 * @since 2017年3月22日 下午4:57:50
 * @author YanCui
 */
@Service("splitService")
@Transactional
public class RWSplitServiceImpl implements RWSplitService{
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    public void readWriteTX(){
    	StringBuilder sql = new StringBuilder();
    	//-- 带事务 读写混合 		预期:Master
    	sql.append("select * from test where id = 1;");
        System.out.println(JSON.toJSONString(jdbcTemplate.queryForList(sql.toString())));
        sql.setLength(0);
    	sql.append("update test set oldcontent = content , content = SYSDATE() where id = 1;");
    	jdbcTemplate.update(sql.toString());
        sql.setLength(0);
    	sql.append("select * from test where id = 1;");
        System.out.println(JSON.toJSONString(jdbcTemplate.queryForList(sql.toString())));
        System.out.println("-- 带事务 读写混合 		预期:Master");
    }
    public void readTX(){
    	StringBuilder sql = new StringBuilder();
    	//-- 带事务 读		 	预期:Master
    	sql.append("select * from test where id = 2;");
        System.out.println(JSON.toJSONString(jdbcTemplate.queryForList(sql.toString())));
        System.out.println("-- 带事务 读 		预期:Master");
    }
	@Transactional(readOnly=true) 
    public void readReadonlyTX(){
    	StringBuilder sql = new StringBuilder();
    	//-- 带事务 readonly  读	预期:Slave
    	sql.append("select * from test where id = 3;");
        System.out.println(JSON.toJSONString(jdbcTemplate.queryForList(sql.toString())));
        System.out.println("-- 带事务 readonly  读	预期:Slave");
    }
	@Transactional(propagation=Propagation.NOT_SUPPORTED) 
    public void read(){
    	StringBuilder sql = new StringBuilder();
    	//-- 不带事务 读			预期:Slave
    	sql.append("select * from test where id = 4;");
        System.out.println(JSON.toJSONString(jdbcTemplate.queryForList(sql.toString())));
        System.out.println("-- 不带事务 读			预期:Slave");
    }
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true) 
    public void readReadOnly(){
    	StringBuilder sql = new StringBuilder();
    	//-- 不带事务 readonly 读	预期:Slave
    	sql.append("select * from test where id = 5;");
        System.out.println(JSON.toJSONString(jdbcTemplate.queryForList(sql.toString())));
        System.out.println("-- 不带事务 readonly 读	预期:Slave");
    }
}
