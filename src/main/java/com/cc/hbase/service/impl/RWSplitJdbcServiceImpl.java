/*
 * 文件名：RWSplitServiceImpl.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年3月22日
 * 备注: 
 */
package com.cc.hbase.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.Driver;
/**
 * 名称: RWSplitServiceImpl.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月22日 下午4:57:50<br>
 * @version [版本号, V1.0]
 * @since 2017年3月22日 下午4:57:50
 * @author YanCui
 */
@Service("splitJdbcService")
public class RWSplitJdbcServiceImpl{
	
    public void readWriteTest(){
    	try {
        	Driver driver = new Driver();
            Properties props = new Properties();
            // We want this for failover on the slaves
//            props.put("autoReconnect", "true");
            // We want to load balance between the slaves
//            props.put("roundRobinLoadBalance", "true");
            props.put("user", "root");
            props.put("password", "mysql");
//            props.put("logintimeout", "10000");
        	
//            Connection conn =
//                    driver.connect("jdbc:mysql:replication://172.29.152.4:3306,172.29.152.4:3307/testdb?useSSL=false",
//                        props);
            Connection conn =
                    driver.connect("jdbc:mysql:replication://172.16.35.97:6446,172.16.35.97:6447/mytest?useSSL=false",
                        props);

            System.out.println("--------带事务 预期:Master------");
            //带事务
            conn.setReadOnly(false);
            conn.setAutoCommit(false);
            //读写
            ResultSet rs = conn.createStatement().executeQuery("select * from test where id = 1;");
            conn.createStatement().executeUpdate("update test set updatetime = SYSDATE() where id = 1;");
            rs = conn.createStatement().executeQuery("select * from test where id = 1;");
            conn.commit();
            rs.next();
            System.out.println(rs.getString("id")+"-"+rs.getString("content"));
            //读
            rs = conn.createStatement().executeQuery("select * from test where id = 2;");
            conn.commit();
            rs.next();
            System.out.println(rs.getString("id")+"-"+rs.getString("content"));
            //读+readonly
            conn.setReadOnly(true);
            rs = conn.createStatement().executeQuery("select * from test where id = 3;");
            conn.commit();
            rs.next();
            System.out.println(rs.getString("id")+"-"+rs.getString("content"));
            

            System.out.println("------ 不带事务 预期:Slave-------");
            //不带事务
            conn.setAutoCommit(true);
            //readonly=false
            conn.setReadOnly(false);
            rs = conn.createStatement().executeQuery("select * from test where id = 4;");
            rs.next();
            System.out.println(rs.getString("id")+"-"+rs.getString("content"));
            //readonly=true
            conn.setReadOnly(true);
            rs = conn.createStatement().executeQuery("select * from test where id = 5;");
            rs.next();
            System.out.println(rs.getString("id")+"-"+rs.getString("content"));
            

            //带事务 读写+readonly
            conn.setAutoCommit(false);
            conn.setReadOnly(true);
            conn.createStatement().executeUpdate("INSERT INTO test(`id`, `content`, `updatetime`) VALUES ('6', '【TX-读写readonly】', NULL);");
            rs = conn.createStatement().executeQuery("select * from test where id = 6;");
            conn.commit();
            rs.next();
            System.out.println(rs.getString("id")+"-"+rs.getString("content"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
