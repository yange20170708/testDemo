/*
 * 文件名：MyReduce.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年3月16日
 * 备注: 
 */
package com.cc.hbase.config.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 名称: MyReduce.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年3月16日 下午2:27:54<br>
 * 
 * @version [版本号, V1.0]
 * @since 2017年3月16日 下午2:27:54
 * @author YanCui
 */
public class MyReduce extends Reducer<Text, LongWritable, Text, LongWritable> {
	@Override
	protected void reduce(Text k2, java.lang.Iterable<LongWritable> v2s, Context ctx) throws java.io.IOException,
			InterruptedException {

		long times = 0L;
		for (LongWritable count : v2s) {
			times += count.get();

		}
		Counter count1 = ctx.getCounter("reduce中的值" + k2.toString(),
				new LongWritable(times).toString());
		count1.increment(1l);
		ctx.write(k2, new LongWritable(times));

	}

}
