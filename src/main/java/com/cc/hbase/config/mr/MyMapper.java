/*
 * 文件名：MyMapper.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年3月16日
 * 备注: 
 */
package com.cc.hbase.config.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 名称: MyMapper.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月16日 下午2:21:39<br>
 * @version [版本号, V1.0]
 * @since 2017年3月16日 下午2:21:39
 * @author YanCui
 */
/**
 * 名称: MyMapper.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月16日 下午2:22:35<br>
 * 
 * @version [版本号, V1.0]
 * @since 2017年3月16日 下午2:22:35
 * @author YanCui
 */
public class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		//有多少行就有多少个map，也就是说，系统默认一行调用一个map函数，value值为一行的数据
		final String[] splited = value.toString().split(" ");
		Counter count = context.getCounter("map中的值value", value.toString());
		count.increment(1l);

		for (String word : splited) {
			context.write(new Text(word), new LongWritable(1L));

		}

	}

}
