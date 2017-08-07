/*
 * 文件名：Snippet.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年3月16日
 * 备注: 
 */
package com.cc.hbase.config.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 名称: Snippet.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月16日 上午11:45:47<br>
 * @version [版本号, V1.0]
 * @since 2017年3月16日 上午11:45:47
 * @author YanCui
 */
public class HBaseUtil {

    // 声明静态配置
    static Configuration conf = null;
    static Connection connection = null;
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "libin2");
        try {
			connection = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /*
     * 创建表
     * 
     * @tableName 表名
     * 
     * @family 列族列表
     */
    public static void creatTable(String tableName, String[] family)
            throws Exception {
        Admin admin = connection.getAdmin();
        HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println("table Exists!");
            System.exit(0);
        } else {
            admin.createTable(desc);
            System.out.println("create table Success!");
        }
    }

    /*
     * 为表添加数据（适合知道有多少列族的固定表）
     * 
     * @rowKey rowKey
     * 
     * @tableName 表名
     * 
     * @column1 第一个列族列表
     * 
     * @value1 第一个列的值的列表
     * 
     * @column2 第二个列族列表
     * 
     * @value2 第二个列的值的列表
     */
    public static void addData(String rowKey, String tableName,
            String[] column1, String[] value1, String[] column2, String[] value2)
            throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        Table table = connection.getTable(TableName.valueOf(tableName));
        HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // 获取所有的列族
                .getColumnFamilies();

        for (int i = 0; i < columnFamilies.length; i++) {
            String familyName = columnFamilies[i].getNameAsString(); // 获取列族名
            if (familyName.equals("article")) { // article列族put数据
                for (int j = 0; j < column1.length; j++) {
                    put.addColumn(Bytes.toBytes(familyName),
                            Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
                }
            }
            if (familyName.equals("author")) { // author列族put数据
                for (int j = 0; j < column2.length; j++) {
                    put.addColumn(Bytes.toBytes(familyName),
                            Bytes.toBytes(column2[j]), Bytes.toBytes(value2[j]));
                }
            }
        }
        table.put(put);
        System.out.println("add data Success!");

        /*
         * Put put = new Put(Bytes.toBytes("rowkey1"));
         * put.addColumn(Bytes.toBytes("article"), Bytes.toBytes("title"),
         * Bytes.toBytes("Head First HBase")); put.addColumn(Bytes.toBytes("article"),
         * Bytes.toBytes("content"), Bytes.toBytes(
         * "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data."
         * )); put.addColumn(Bytes.toBytes("article"), Bytes.toBytes("tags"),
         * Bytes.toBytes("Hadoop,HBase,NoSQL"));
         * put.addColumn(Bytes.toBytes("author"), Bytes.toBytes("name"),
         * Bytes.toBytes("nicholas")); put.addColumn(Bytes.toBytes("author"),
         * Bytes.toBytes("nickname"), Bytes.toBytes("lee")); HTable table = new
         * HTable(conf, Bytes.toBytes("blog2"));table.put(put);
         */
    }

    /*
     * 根据rwokey查询
     * 
     * @rowKey rowKey
     * 
     * @tableName 表名
     */
    public static Result getResult(String tableName, String rowKey)
            throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        Table table = connection.getTable(TableName.valueOf(tableName));
        Result result = table.get(get);
        for (Cell kv : result.listCells()) {
            System.out.println("family:" + Bytes.toString(CellUtil.cloneFamily(kv)));
            System.out
                    .println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(kv)));
            System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(kv)));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
        return result;
    }

    /*
     * 遍历查询hbase表
     * 
     * @tableName 表名
     */
    public static void getResultScann(String tableName) throws IOException {
        Scan scan = new Scan();
        ResultScanner rs = null;
        Table table = connection.getTable(TableName.valueOf(tableName));
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                for (Cell kv : r.listCells()) {
                    System.out.println("family:" + Bytes.toString(CellUtil.cloneFamily(kv)));
                    System.out
                            .println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(kv)));
                    System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(kv)));
                    System.out.println("Timestamp:" + kv.getTimestamp());
                    System.out.println("-------------------------------------------");
                }
            }
        } finally {
            rs.close();
        }
    }

    /*
     * 查询表中的某一列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     */
    public static void getResultByColumn(String tableName, String rowKey,
            String familyName, String columnName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
        // assertThat(Bytes.toString(table.get(get).list().get(0).getValue()),is("一叶渡江"));
        Result result = table.get(get);
        for (Cell kv : result.listCells()) {
            System.out.println("family:" + Bytes.toString(CellUtil.cloneFamily(kv)));
            System.out
                    .println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(kv)));
            System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(kv)));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
    }

    /*
     * 更新表中的某一列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
     * 
     * @columnName 列名
     * 
     * @value 更新后的值
     */
    public static void updateTable(String tableName, String rowKey,
            String familyName, String columnName, String value)
            throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
                Bytes.toBytes(value));
        table.put(put);
        System.out.println("update table Success!");
    }

    /*
     * 查询某列数据的多个版本
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
     * 
     * @columnName 列名
     */
    public static void getResultByVersion(String tableName, String rowKey,
            String familyName, String columnName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
        get.setMaxVersions(5);
        Result result = table.get(get);
        for (Cell kv : result.listCells()) {
            System.out.println("family:" + Bytes.toString(CellUtil.cloneFamily(kv)));
            System.out
                    .println("qualifier:" + Bytes.toString(CellUtil.cloneQualifier(kv)));
            System.out.println("value:" + Bytes.toString(CellUtil.cloneValue(kv)));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
        /*
         * List<?> results = table.get(get).list(); Iterator<?> it =
         * results.iterator(); while (it.hasNext()) {
         * System.out.println(it.next().toString()); }
         */
    }

    /*
     * 删除指定的列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
     * 
     * @columnName 列名
     */
    public static void deleteColumn(String tableName, String rowKey,
            String falilyName, String columnName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.addColumns(Bytes.toBytes(falilyName),
                Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        System.out.println(falilyName + ":" + columnName + "is deleted!");
    }

    /*
     * 删除指定的列
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     */
    public static void deleteAllColumn(String tableName, String rowKey)
            throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
        table.delete(deleteAll);
        System.out.println("all columns are deleted!");
    }

    /*
     * 删除表
     * 
     * @tableName 表名
     */
    public static void deleteTable(String tableName) throws IOException {
        Admin admin = connection.getAdmin();
        admin.disableTable(TableName.valueOf(tableName));
        admin.deleteTable(TableName.valueOf(tableName));
        System.out.println(tableName + "is deleted!");
    }

    public static void main(String[] args) throws Exception {

        // 创建表
        /*
         * String tableName = "blog2"; String[] family = { "article","author" };
         * creatTable(tableName,family);
         */

        // 为表添加数据
        /*
         * String[] column1 = { "title", "content", "tag" }; String[] value1 = {
         * "Head First HBase",
         * "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data."
         * , "Hadoop,HBase,NoSQL" }; String[] column2 = { "name", "nickname" };
         * String[] value2 = { "nicholas", "lee" }; addData("rowkey1", "blog2",
         * column1, value1, column2, value2);
         */

        // 删除一列
        // deleteColumn("blog2", "rowkey1", "author", "nickname");

        // 删除所有列
        //deleteAllColumn("blog2", "rowkey1");
        
        //删除表
        //deleteTable("blog2");

        // 查询
        // getResult("blog2", "rowkey1");

        // 查询某一列的值
        // getResultByColumn("blog2", "rowkey1", "author", "name");
        // updateTable("blog2", "rowkey1", "author", "name","bin");
        // getResultByColumn("blog2", "rowkey1", "author", "name");

        // 遍历查询
        // getResultScann("blog2");

        // 查询某列的多版本
        // getResultByVersion("blog2", "rowkey1", "author", "name");
    }
}

