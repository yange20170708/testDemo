/*
 * 文件名：ZkClientTest.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年4月10日
 * 备注: 
 */
package com.cc.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * 名称: ZkClientTest.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年4月10日 上午10:09:40<br>
 * @version [版本号, V1.0]
 * @since 2017年4月10日 上午10:09:40
 * @author YanCui
 */
public class ZkClientTest {
	private static final String connectString = "localhost:2181";//"hadoop01:2181,hadoop02:2181,hadoop03:2181";

    private static final int sessionTimeout = 2000;
    
    /**
     * main函数
     * @param args
     * @throws Exception
     */
    @Test
    public  void rmrTest() throws Exception {
        //调用rmr,删除所有目录
        rmr("/dubbo");
    }

    /**
     * 递归删除 因为zookeeper只允许删除叶子节点，如果要删除非叶子节点，只能使用递归
     * @param path
     * @throws IOException
     */
    public void rmr(String path) throws Exception {
        ZooKeeper zk = getZookeeper();
        zk.addAuthInfo("digest", "super:8Xi/vRSyW+fugQ38RNZSyUS8UTE=".getBytes());
        //获取路径下的节点
        List<String> children = zk.getChildren(path, false);
        for (String pathCd : children) {
            //获取父节点下面的子节点路径
            String newPath = "";
            //递归调用,判断是否是根节点
            if (path.equals("/")) {
                newPath = "/" + pathCd;
            } else {
                newPath = path + "/" + pathCd;
            }
            rmr(newPath);
        }
        //删除节点,并过滤zookeeper节点和 /节点
        if (path != null && !path.trim().startsWith("/zookeeper") && !path.trim().equals("/")) {
            zk.delete(path, -1);
            //打印删除的节点路径
            System.out.println("被删除的节点为：" + path);
        }
    }

    /**
     * 获取Zookeeper实例
     * @return
     * @throws IOException
     */
    public static ZooKeeper getZookeeper() throws IOException {
        return new ZooKeeper(connectString, sessionTimeout, new MyWatch());
    }
    
    /**
     * 自定义watch类，不做任何事
     * @author LiJie
     *  */
    static class MyWatch implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            // DO Nothing
        }

    }
}
